package by.pirog.statistics.processingStatistics;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AsyncProcessingStatisticsTest {

    @Test
    void incrementFilesProcessed_isThreadSafe() throws InterruptedException {
        ProcessingStatistics stats = new AsyncProcessingStatistics();
        int threadsCount = 10;
        int incrementsPerThread = 100;

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        CountDownLatch latch = new CountDownLatch(threadsCount);

        for (int t = 0; t < threadsCount; t++) {
            executor.submit(() -> {
                try {
                    for (int i = 0; i < incrementsPerThread; i++) {
                        stats.incrementFilesProcessed();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();

        assertEquals(threadsCount * incrementsPerThread, stats.getFilesProcessed());
    }

    @Test
    void incrementLines_isThreadSafe() throws InterruptedException {
        ProcessingStatistics stats = new AsyncProcessingStatistics();
        int threadsCount = 10;
        int incrementsPerThread = 1000;

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        CountDownLatch latch = new CountDownLatch(threadsCount);

        for (int t = 0; t < threadsCount; t++) {
            executor.submit(() -> {
                try {
                    for (int i = 0; i < incrementsPerThread; i++) {
                        stats.incrementLines();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();

        assertEquals(threadsCount * incrementsPerThread, stats.getTotalLines());
    }

    @Test
    void mixedOperations_areThreadSafe() throws InterruptedException {
        ProcessingStatistics stats = new AsyncProcessingStatistics();
        int threadsCount = 5;

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        CountDownLatch latch = new CountDownLatch(threadsCount);

        for (int t = 0; t < threadsCount; t++) {
            executor.submit(() -> {
                try {
                    for (int i = 0; i < 100; i++) {
                        stats.incrementFilesProcessed();
                        stats.incrementFilesSucceeded();
                        stats.incrementLines();
                        stats.incrementValidLines();
                        if (i % 10 == 0) {
                            stats.incrementFilesFailed();
                            stats.incrementInvalidLines();
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();

        assertEquals(500, stats.getFilesProcessed());
        assertEquals(500, stats.getFilesSucceeded());
        assertEquals(50, stats.getFilesFailed());
        assertEquals(500, stats.getTotalLines());
        assertEquals(500, stats.getValidLines());
        assertEquals(50, stats.getInvalidLines());
    }

    @Test
    void setExecutionTimeMs_setsTime() {
        ProcessingStatistics stats = new AsyncProcessingStatistics();

        stats.setExecutionTimeMs(5000);

        assertEquals(5000, stats.getExecutionTimeMs());
    }

    @Test
    void initialValues_areZero() {
        ProcessingStatistics stats = new AsyncProcessingStatistics();

        assertEquals(0, stats.getFilesProcessed());
        assertEquals(0, stats.getFilesSucceeded());
        assertEquals(0, stats.getFilesFailed());
        assertEquals(0, stats.getTotalLines());
        assertEquals(0, stats.getValidLines());
        assertEquals(0, stats.getInvalidLines());
        assertEquals(0, stats.getExecutionTimeMs());
    }
}

