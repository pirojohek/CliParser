package by.pirog.statistics.processingStatistics;

public interface ProcessingStatistics {
    void incrementFilesProcessed();
    void incrementFilesSucceeded();
    void incrementFilesFailed();

    void incrementLines();
    void incrementValidLines();
    void incrementInvalidLines();

    void setExecutionTimeMs(long executionTimeMs);

    int getFilesProcessed();
    int getFilesSucceeded();
    int getFilesFailed();
    long getTotalLines();
    long getValidLines();
    long getInvalidLines();
    long getExecutionTimeMs();
}
