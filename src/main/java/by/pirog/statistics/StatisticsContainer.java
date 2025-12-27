package by.pirog.statistics;

public class StatisticsContainer {
    private final NumberStatistics numberStatistics;
    private final StringStatistics stringStatistics;
    private final ProcessingStatistics processingStatistics;

    private final boolean isFullStats;

    public NumberStatistics getNumberStatistics() {
        return numberStatistics;
    }

    public StringStatistics getStringStatistics() {
        return stringStatistics;
    }

    public ProcessingStatistics getProcessingStatistics() {
        return processingStatistics;
    }

    public boolean isFullStats() {
        return isFullStats;
    }

    public StatisticsContainer(NumberStatistics numberStatistics,
                               StringStatistics stringStatistics,
                               ProcessingStatistics processingStatistics,
                               boolean isFullStats) {
        this.numberStatistics = numberStatistics;
        this.stringStatistics = stringStatistics;
        this.processingStatistics = processingStatistics;
        this.isFullStats = isFullStats;
    }
}
