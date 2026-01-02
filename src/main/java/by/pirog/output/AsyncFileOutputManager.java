package by.pirog.output;

import by.pirog.exception.OutputException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AsyncFileOutputManager implements OutputManager {

    private final OutputManager delegate;
    private final BlockingQueue<WriteTask> queue = new LinkedBlockingQueue<>(10000);

    private volatile boolean running = true;
    private volatile Exception writerException = null;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AsyncFileOutputManager(OutputManager delegate) {
        this.delegate = delegate;
        executor.execute(this::runWriter);
    }

    private void runWriter() {
        List<WriteTask> batch = new ArrayList<>(100);
        try {
            while (running || !queue.isEmpty()) {
                WriteTask task = queue.poll(10, TimeUnit.MILLISECONDS);

                if (task != null) {
                    batch.add(task);
                    queue.drainTo(batch, 99);
                    for (WriteTask t : batch) {
                        delegate.write(t.type(), t.value());
                    }

                    batch.clear();
                }
            }

        } catch (Exception e) {
            writerException = e;
            System.err.println("Ошибка в потоке записи: " + e.getMessage());
            running = false;
        }
    }

    @Override
    public void write(DataType type, String value) throws IOException {

        if (writerException != null) {
            throw new IOException("Writer thread failed: " + writerException.getMessage(), writerException);
        }

        try {
            if (!queue.offer(new WriteTask(type, value), 5, TimeUnit.SECONDS)) {
                throw new IOException("Очередь записи переполнена (превышен таймаут 5 сек)");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Прервано при добавлении в очередь записи", e);
        }
    }

    @Override
    public void close() throws IOException {
        running = false;
        executor.shutdown();
        try{
            try {
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                    System.err.println("Принудительное завершение потока записи (таймаут 30 сек)");
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
                throw new IOException("Прервано при закрытии потока записи", e);
            }

            if (writerException != null) {
                throw new IOException("Поток записи завершился с ошибкой", writerException);
            }
        } finally {
            delegate.close();
        }
    }
}
