package by.pirog.output;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AsyncFileOutputManagerTest {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void shouldWriteSingleValue() throws IOException {
        OutputManager delegate = mock(OutputManager.class);
        AsyncFileOutputManager async = new AsyncFileOutputManager(delegate);

        async.write(DataType.STRING, "test");
        async.close();

        verify(delegate).write(DataType.STRING, "test");
        verify(delegate).close();
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void shouldWriteMultipleValues() throws IOException {
        OutputManager delegate = mock(OutputManager.class);
        AsyncFileOutputManager async = new AsyncFileOutputManager(delegate);

        async.write(DataType.STRING, "test1");
        async.write(DataType.FLOAT, "3.14");
        async.write(DataType.INTEGER, "42");
        async.close();

        verify(delegate).write(DataType.STRING, "test1");
        verify(delegate).write(DataType.FLOAT, "3.14");
        verify(delegate).write(DataType.INTEGER, "42");
        verify(delegate).close();
    }


    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void shouldPropagateWriterException() throws IOException, InterruptedException {
        OutputManager delegate = mock(OutputManager.class);
        doThrow(new IOException("Write failed")).when(delegate).write(any(), anyString());

        AsyncFileOutputManager async = new AsyncFileOutputManager(delegate);
        async.write(DataType.STRING, "test1");

        Thread.sleep(100);

        IOException exception = assertThrows(IOException.class, () ->
            async.write(DataType.STRING, "test2")
        );

        assertTrue(exception.getMessage().contains("Writer thread failed"));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void shouldThrowExceptionOnCloseIfWriterFailed() throws IOException{
        OutputManager delegate = mock(OutputManager.class);
        doThrow(new IOException("Write failed")).when(delegate).write(any(), anyString());

        AsyncFileOutputManager async = new AsyncFileOutputManager(delegate);
        async.write(DataType.STRING, "test");

        IOException exception = assertThrows(IOException.class, async::close);
        assertTrue(exception.getMessage().contains("завершился с ошибкой"));
        verify(delegate).close();
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void shouldHandleConcurrentWrites() throws Exception {
        OutputManager delegate = mock(OutputManager.class);
        AsyncFileOutputManager async = new AsyncFileOutputManager(delegate);

        int threadsCount = 10;
        int writesPerThread = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        CountDownLatch latch = new CountDownLatch(threadsCount);

        for (int t = 0; t < threadsCount; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    for (int i = 0; i < writesPerThread; i++) {
                        async.write(DataType.STRING, "thread" + threadId + "_value" + i);
                    }
                } catch (IOException e) {
                    fail("Не должно быть исключений при записи: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Не все потоки завершились вовремя");
        executor.shutdown();
        async.close();

        verify(delegate, times(threadsCount * writesPerThread)).write(eq(DataType.STRING), anyString());
        verify(delegate).close();
    }


    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void shouldCloseEvenWithEmptyQueue() throws IOException {
        OutputManager delegate = mock(OutputManager.class);
        AsyncFileOutputManager async = new AsyncFileOutputManager(delegate);

        async.close();

        verify(delegate).close();
        verify(delegate, never()).write(any(), anyString());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void shouldProcessAllItemsBeforeClosing() throws IOException, InterruptedException {
        OutputManager delegate = mock(OutputManager.class);
        CountDownLatch firstWriteLatch = new CountDownLatch(1);
        List<String> processedValues = new ArrayList<>();

        doAnswer(invocation -> {
            firstWriteLatch.await();
            String value = invocation.getArgument(1);
            synchronized (processedValues) {
                processedValues.add(value);
            }
            return null;
        }).when(delegate).write(any(), anyString());

        AsyncFileOutputManager async = new AsyncFileOutputManager(delegate);

        int count = 100;
        for (int i = 0; i < count; i++) {
            async.write(DataType.STRING, "value" + i);
        }

        Thread.sleep(100);

        firstWriteLatch.countDown();
        async.close();

        assertEquals(count, processedValues.size());
    }

}
