package by.pirog.converter;

import by.pirog.json.StringStatisticsDto;
import by.pirog.statistics.StringStatistics;

public class StringStatisticsConverter {

    public static StringStatisticsDto toDto(StringStatistics statistics) {
        if (statistics == null || statistics.getCount() == 0) {
            return null;
        }

        StringStatisticsDto dto = new StringStatisticsDto();
        dto.setCount(statistics.getCount());
        dto.setMinLength(statistics.getMinLength());
        dto.setMaxLength(statistics.getMaxLength());

        return dto;
    }
}
