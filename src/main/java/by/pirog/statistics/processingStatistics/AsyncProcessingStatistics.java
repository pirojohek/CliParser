package by.pirog.statistics.processingStatistics;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AsyncProcessingStatistics implements ProcessingStatistics {

    private final AtomicInteger filesProcessed = new AtomicInteger(0);
    private final AtomicInteger filesSucceeded = new AtomicInteger(0);
    private final AtomicInteger filesFailed = new AtomicInteger(0);

    private final AtomicLong executionTimeMs = new AtomicLong(0);

    private final AtomicLong totalLines = new AtomicLong(0);
    private final AtomicLong validLines = new AtomicLong(0);
    private final AtomicLong failedLines = new AtomicLong(0);

    @Override
    public void incrementFilesProcessed() {
        filesProcessed.incrementAndGet();
    }

    @Override
    public void incrementFilesSucceeded() {
        filesSucceeded.incrementAndGet();
    }

    @Override
    public void incrementFilesFailed() {
        filesFailed.incrementAndGet();
    }

    @Override
    public void incrementLines() {
        totalLines.incrementAndGet();
    }

    @Override
    public void incrementValidLines() {
        validLines.incrementAndGet();
    }

    @Override
    public void incrementInvalidLines() {
        failedLines.incrementAndGet();
    }

    @Override
    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs.set(executionTimeMs);
    }

    @Override
    public int getFilesProcessed() {
        return this.filesProcessed.get();
    }

    @Override
    public int getFilesSucceeded() {
        return filesSucceeded.get();
    }

    @Override
    public int getFilesFailed() {
        return filesFailed.get();
    }

    @Override
    public long getTotalLines() {
        return totalLines.get();
    }

    @Override
    public long getValidLines() {
        return validLines.get();
    }

    @Override
    public long getInvalidLines() {
        return failedLines.get();
    }

    @Override
    public long getExecutionTimeMs() {
        return executionTimeMs.get();
    }
}
