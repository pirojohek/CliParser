package by.pirog.statistics;

import by.pirog.statistics.numberStatistics.AsyncNumberStatistics;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.numberStatistics.SyncNumberStatistics;
import by.pirog.statistics.processingStatistics.AsyncProcessingStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.processingStatistics.SyncProcessingStatistics;
import by.pirog.statistics.stringStatistics.AsyncStringStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;
import by.pirog.statistics.stringStatistics.SyncStringStatistics;

public class StatisticsFactory {

    public static NumberStatistics createNumberStatistics(boolean async) {
        return async ? new AsyncNumberStatistics() : new SyncNumberStatistics();
    }

    public static StringStatistics createStringStatistics(boolean async) {
        return async ? new AsyncStringStatistics() : new SyncStringStatistics();
    }

    public static ProcessingStatistics createProcessingStatistics(boolean async) {
        return async ? new AsyncProcessingStatistics() : new SyncProcessingStatistics();
    }
}
