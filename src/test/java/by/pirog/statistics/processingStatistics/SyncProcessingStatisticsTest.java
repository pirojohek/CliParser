package by.pirog.statistics.processingStatistics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SyncProcessingStatisticsTest {

    @Test
    void incrementFilesProcessed_incrementsCounter() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        stats.incrementFilesProcessed();
        stats.incrementFilesProcessed();
        stats.incrementFilesProcessed();

        assertEquals(3, stats.getFilesProcessed());
    }

    @Test
    void incrementFilesSucceeded_incrementsCounter() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        stats.incrementFilesSucceeded();
        stats.incrementFilesSucceeded();

        assertEquals(2, stats.getFilesSucceeded());
    }

    @Test
    void incrementFilesFailed_incrementsCounter() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        stats.incrementFilesFailed();

        assertEquals(1, stats.getFilesFailed());
    }

    @Test
    void incrementLines_incrementsCounter() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        for (int i = 0; i < 100; i++) {
            stats.incrementLines();
        }

        assertEquals(100, stats.getTotalLines());
    }

    @Test
    void incrementValidLines_incrementsCounter() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        stats.incrementValidLines();
        stats.incrementValidLines();
        stats.incrementValidLines();

        assertEquals(3, stats.getValidLines());
    }

    @Test
    void incrementInvalidLines_incrementsCounter() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        stats.incrementInvalidLines();
        stats.incrementInvalidLines();

        assertEquals(2, stats.getInvalidLines());
    }

    @Test
    void setExecutionTimeMs_setsTime() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        stats.setExecutionTimeMs(1234);

        assertEquals(1234, stats.getExecutionTimeMs());
    }

    @Test
    void initialValues_areZero() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        assertEquals(0, stats.getFilesProcessed());
        assertEquals(0, stats.getFilesSucceeded());
        assertEquals(0, stats.getFilesFailed());
        assertEquals(0, stats.getTotalLines());
        assertEquals(0, stats.getValidLines());
        assertEquals(0, stats.getInvalidLines());
        assertEquals(0, stats.getExecutionTimeMs());
    }

    @Test
    void mixedOperations_calculatesCorrectly() {
        ProcessingStatistics stats = new SyncProcessingStatistics();

        stats.incrementFilesProcessed();
        stats.incrementFilesProcessed();
        stats.incrementFilesProcessed();

        stats.incrementFilesSucceeded();
        stats.incrementFilesSucceeded();

        stats.incrementFilesFailed();

        stats.incrementLines();
        stats.incrementLines();
        stats.incrementLines();
        stats.incrementLines();
        stats.incrementLines();

        stats.incrementValidLines();
        stats.incrementValidLines();
        stats.incrementValidLines();
        stats.incrementValidLines();

        stats.incrementInvalidLines();

        stats.setExecutionTimeMs(500);

        assertEquals(3, stats.getFilesProcessed());
        assertEquals(2, stats.getFilesSucceeded());
        assertEquals(1, stats.getFilesFailed());
        assertEquals(5, stats.getTotalLines());
        assertEquals(4, stats.getValidLines());
        assertEquals(1, stats.getInvalidLines());
        assertEquals(500, stats.getExecutionTimeMs());
    }
}

