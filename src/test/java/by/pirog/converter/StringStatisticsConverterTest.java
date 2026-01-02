package by.pirog.converter;

import by.pirog.json.StringStatisticsDto;
import by.pirog.statistics.stringStatistics.StringStatistics;
import by.pirog.statistics.stringStatistics.SyncStringStatistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringStatisticsConverterTest {

    @Test
    void toDto_withValidStatistics_convertsCorrectly() {
        StringStatistics stats = new SyncStringStatistics();
        stats.accept("hello");
        stats.accept("world");
        stats.accept("a");

        StringStatisticsDto dto = StringStatisticsConverter.toDto(stats);

        assertNotNull(dto);
        assertEquals(3, dto.getCount());
        assertEquals(1, dto.getMinLength());
        assertEquals(5, dto.getMaxLength());
    }

    @Test
    void toDto_withEmptyStatistics_returnsNull() {
        StringStatistics stats = new SyncStringStatistics();

        StringStatisticsDto dto = StringStatisticsConverter.toDto(stats);

        assertNull(dto);
    }

    @Test
    void toDto_withNullStatistics_returnsNull() {
        StringStatisticsDto dto = StringStatisticsConverter.toDto(null);

        assertNull(dto);
    }
}

