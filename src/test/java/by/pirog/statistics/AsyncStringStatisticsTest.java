package by.pirog.statistics;

import by.pirog.statistics.stringStatistics.AsyncStringStatistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncStringStatisticsTest {

    @Test
    void testAsyncStringStatistics() throws InterruptedException {
        AsyncStringStatistics asyncStringStatistics = new AsyncStringStatistics();

        Runnable task1 = () -> {
            for (int i = 0; i < 1000; i++) {
                asyncStringStatistics.accept("string" + i);
            }
        };

        Runnable task2 = () -> {
            for (int i = 1000; i < 2000; i++) {
                asyncStringStatistics.accept("str" + i);
            }
        };

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertEquals(2000, asyncStringStatistics.getCount());
        assertEquals(7, asyncStringStatistics.getMinLength());
        assertEquals(9, asyncStringStatistics.getMaxLength());
    }
}
