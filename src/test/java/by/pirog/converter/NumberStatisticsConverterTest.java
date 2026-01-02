package by.pirog.converter;

import by.pirog.json.NumberStatisticsDto;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.numberStatistics.SyncNumberStatistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumberStatisticsConverterTest {


    @Test
    void toDto_countEqualsZero_returnsNull() {
        NumberStatistics stats = new SyncNumberStatistics();

        NumberStatisticsDto dto = NumberStatisticsConverter.toDto(stats);

        assertNull(dto);
    }


    @Test
    void toDto_withMixedNumbers_convertsCorrectly() {
        NumberStatistics stats = new SyncNumberStatistics();
        stats.accept(10L);
        stats.accept(3.14);
        stats.accept(-5L);

        NumberStatisticsDto dto = NumberStatisticsConverter.toDto(stats);

        assertNotNull(dto);
        assertEquals(3, dto.getCount());
        assertEquals(-5.0, dto.getMin());
        assertEquals(10.0, dto.getMax());
        assertEquals(8.14, dto.getSum(), 0.001);
    }

}

