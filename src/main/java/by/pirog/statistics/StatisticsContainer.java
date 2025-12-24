package by.pirog.statistics;

public class StatisticsContainer {
    private final NumberStatistics numberStatistics;
    private final StringStatistics stringStatistics;

    private final boolean isFullStats;

    public NumberStatistics getNumberStatistics() {
        return numberStatistics;
    }

    public StringStatistics getStringStatistics() {
        return stringStatistics;
    }


    public boolean isFullStats() {
        return isFullStats;
    }

    public StatisticsContainer(NumberStatistics numberStatistics, StringStatistics stringStatistics,
                               boolean isFullStats) {
        this.numberStatistics = numberStatistics;
        this.stringStatistics = stringStatistics;
        this.isFullStats = isFullStats;
    }
}
