package by.pirog.statistics.processingStatistics;

public class SyncProcessingStatistics implements ProcessingStatistics {
    private int filesProcessed = 0;

    private int filesSucceeded = 0;
    private int filesFailed = 0;
    private long totalLines = 0;
    private long validLines = 0;
    private long invalidLines = 0;
    private long executionTimeMs = 0;

    public void incrementFilesProcessed() {
        this.filesProcessed++;
    }

    public void incrementLines() {
        this.totalLines++;
    }

    public void incrementFilesSucceeded() {
        this.filesSucceeded++;
    }

    public void incrementValidLines() {
        this.validLines++;
    }

    public void incrementInvalidLines() {
        this.invalidLines++;
    }
    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public int getFilesProcessed() {
        return filesProcessed;
    }

    public long getTotalLines() {
        return totalLines;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void incrementFilesFailed() {
        this.filesFailed++;
    }

    public int getFilesFailed() {
        return filesFailed;
    }

    public int getFilesSucceeded() {
        return filesSucceeded;
    }

    public long getValidLines() {
        return validLines;
    }

    public long getInvalidLines() {
        return invalidLines;
    }
}
