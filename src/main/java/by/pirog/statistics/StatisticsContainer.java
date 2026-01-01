package by.pirog.statistics;

import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;

public class StatisticsContainer {
    private final NumberStatistics numberStatistics;
    private final StringStatistics stringStatistics;
    private final ProcessingStatistics processingStatistics;

    private final boolean isFullStats;
    private final boolean isFullCustomStats;

    public NumberStatistics getNumberStatistics() {
        return numberStatistics;
    }

    public StringStatistics getStringStatistics() {
        return stringStatistics;
    }

    public ProcessingStatistics getProcessingStatistics() {
        return processingStatistics;
    }

    public boolean isFullCustomStats() {
        return isFullCustomStats;
    }

    public boolean isFullStats() {
        return isFullStats;
    }

    public StatisticsContainer(NumberStatistics numberStatistics,
                               StringStatistics stringStatistics,
                               ProcessingStatistics processingStatistics,
                               boolean isFullStats,
                               boolean isFullCustomStats) {
        this.numberStatistics = numberStatistics;
        this.stringStatistics = stringStatistics;
        this.processingStatistics = processingStatistics;
        this.isFullStats = isFullStats;
        this.isFullCustomStats = isFullCustomStats;
    }
}
