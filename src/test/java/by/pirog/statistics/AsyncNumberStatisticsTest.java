package by.pirog.statistics;

import by.pirog.statistics.numberStatistics.AsyncNumberStatistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncNumberStatisticsTest {
    @Test
    void testMultiThreadedStatistics() throws InterruptedException {
        AsyncNumberStatistics stats = new AsyncNumberStatistics();

        Runnable task1 = () -> {
            for (int i = 1; i <= 1000; i++) stats.accept(i);
        };
        Runnable task2 = () -> {
            for (int i = 1001; i <= 2000; i++) stats.accept(i);
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertEquals(2000, stats.getCount());
        assertEquals(1.0, stats.getMin());
        assertEquals(2000.0, stats.getMax());
        assertEquals((2000.0 * 2001.0) / 2.0, stats.getSum()); // сумма арифметической прогрессии
    }

}
