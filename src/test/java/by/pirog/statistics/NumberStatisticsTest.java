package by.pirog.statistics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberStatisticsTest {

    @Test
    void accept_integers_calculatesStatsCorrectly() {
        NumberStatistics stats = new NumberStatistics();

        stats.accept(10L);
        stats.accept(20L);
        stats.accept(-5L);

        assertEquals(3, stats.getCount());
        assertEquals(-5, stats.getMin());
        assertEquals(20, stats.getMax());
        assertEquals(25, stats.getSum());
        assertEquals(25.0 / 3, stats.getSum()/ stats.getCount());
    }

    @Test
    void accept_floats_calculatesStatsCorrectly() {
        NumberStatistics stats = new NumberStatistics();
        stats.accept(1.5f);
        stats.accept(2.5f);

        assertEquals(2, stats.getCount());
        assertEquals(1.5, stats.getMin());
        assertEquals(2.5, stats.getMax());
        assertEquals(4.0, stats.getSum());
        assertEquals(2.0, stats.getSum()/stats.getCount());
    }
}
