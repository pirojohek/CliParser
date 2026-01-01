package by.pirog.statistics;

import by.pirog.statistics.processingStatistics.ProcessingStatistics;

public class ExecutionTimer {

    private final boolean printTime;

    public ExecutionTimer(boolean printTime) {
        this.printTime = printTime;
    }

    public void execute(Runnable task, ProcessingStatistics statistics){
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();
        long ms = (end - start) / 1_000_000;

        statistics.setExecutionTimeMs(ms);

        if (printTime) {
            System.out.println("Время выполнения: " + ms + " мс");
        }
    }
}
