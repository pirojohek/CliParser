package by.pirog.statistics;

import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.processingStatistics.SyncProcessingStatistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExecutionTimerTest {

    @Test
    void execute_measuresExecutionTime() {
        ProcessingStatistics stats = new SyncProcessingStatistics();
        ExecutionTimer timer = new ExecutionTimer(false);

        timer.execute(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, stats);

        assertTrue(stats.getExecutionTimeMs() >= 90,
            "Время выполнения должно быть >= 90ms, но было " + stats.getExecutionTimeMs());
        assertTrue(stats.getExecutionTimeMs() < 200,
            "Время выполнения должно быть < 200ms, но было " + stats.getExecutionTimeMs());
    }
}

