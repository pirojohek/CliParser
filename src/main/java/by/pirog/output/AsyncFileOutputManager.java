package by.pirog.output;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class AsyncFileOutputManager implements OutputManager {

    private final OutputManager delegate;
    private final BlockingQueue<WriteTask> queue = new LinkedBlockingQueue<>();

    private final Thread writeThread;
    private volatile boolean running = true;

    public AsyncFileOutputManager(OutputManager delegate) {
        this.delegate = delegate;

        writeThread = new Thread(this::runWriter, "writer-thread");
        writeThread.start();
    }

    private void runWriter() {
        try{
            while (running || !queue.isEmpty()){
                WriteTask task = queue.poll(100, TimeUnit.MILLISECONDS);
                if (task != null){
                    delegate.write(task.type(), task.value());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Writer thread failed", e);
        }
    }

    @Override
    public void write(DataType type, String value) throws IOException {
        queue.offer(new WriteTask(type, value));
    }

    @Override
    public void close() throws IOException {
        running = false;
        try{
            writeThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        delegate.close();
    }
}
