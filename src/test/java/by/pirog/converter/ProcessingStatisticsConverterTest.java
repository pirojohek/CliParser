package by.pirog.converter;

import by.pirog.json.ProcessingStatisticsDto;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.processingStatistics.SyncProcessingStatistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessingStatisticsConverterTest {

    @Test
    void toDto_withValidStatistics_convertsCorrectly() {
        ProcessingStatistics stats = getProcessingStatistics();

        ProcessingStatisticsDto dto = ProcessingStatisticsConverter.toDto(stats);

        assertNotNull(dto);
        assertEquals(3, dto.getFilesProcessed());
        assertEquals(2, dto.getFilesSucceeded());
        assertEquals(1, dto.getFilesFailed());
        assertEquals(5, dto.getTotalLines());
        assertEquals(4, dto.getValidLines());
        assertEquals(1, dto.getInvalidLines());
        assertEquals(1500, dto.getExecutionTimeMs());
    }

    private static ProcessingStatistics getProcessingStatistics() {
        ProcessingStatistics stats = new SyncProcessingStatistics();
        stats.incrementFilesProcessed();
        stats.incrementFilesProcessed();
        stats.incrementFilesProcessed();

        stats.incrementFilesSucceeded();
        stats.incrementFilesSucceeded();

        stats.incrementFilesFailed();

        stats.incrementLines();
        stats.incrementLines();
        stats.incrementLines();
        stats.incrementLines();
        stats.incrementLines();

        stats.incrementValidLines();
        stats.incrementValidLines();
        stats.incrementValidLines();
        stats.incrementValidLines();

        stats.incrementInvalidLines();

        stats.setExecutionTimeMs(1500);
        return stats;
    }

    @Test
    void toDto_withNullStatistics_returnsNull() {
        ProcessingStatisticsDto dto = ProcessingStatisticsConverter.toDto(null);

        assertNull(dto);
    }
}

