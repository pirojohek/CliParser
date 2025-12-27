package by.pirog.converter;

import by.pirog.json.ProcessingStatisticsDto;
import by.pirog.statistics.ProcessingStatistics;

public class ProcessingStatisticsConverter {

    public static ProcessingStatisticsDto toDto(ProcessingStatistics statistics) {
        if (statistics == null) {
            return null;
        }

        ProcessingStatisticsDto dto = new ProcessingStatisticsDto();
        dto.setFilesProcessed(statistics.getFilesProcessed());
        dto.setFilesSucceeded(statistics.getFilesSucceeded());
        dto.setFilesFailed(statistics.getFilesFailed());
        dto.setTotalLines(statistics.getTotalLines());
        dto.setValidLines(statistics.getValidLines());
        dto.setInvalidLines(statistics.getInvalidLines());
        dto.setExecutionTimeMs(statistics.getExecutionTimeMs());

        return dto;
    }
}

