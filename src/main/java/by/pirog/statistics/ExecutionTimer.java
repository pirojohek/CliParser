package by.pirog.statistics;

public class ExecutionTimer {

    private final boolean printTime;

    public ExecutionTimer(boolean printTime) {
        this.printTime = printTime;
    }

    public void execute(Runnable task){
        long start = 0;
        if (printTime) start = System.nanoTime();
        task.run();

        if (printTime){
            long end = System.nanoTime();
            long ms = (end - start) / 1_000_000;
            System.out.println("Время выполнения: " + ms + " мс");
        }
    }
}
