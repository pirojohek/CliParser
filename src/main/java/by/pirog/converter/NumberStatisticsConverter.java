package by.pirog.converter;

import by.pirog.json.NumberStatisticsDto;
import by.pirog.statistics.NumberStatistics;

public class NumberStatisticsConverter {
    public static NumberStatisticsDto toDto(NumberStatistics statistics) {
        if (statistics == null || statistics.getCount() == 0) {
            return null;
        }

        NumberStatisticsDto dto = new NumberStatisticsDto();
        dto.setCount(statistics.getCount());
        dto.setMin(statistics.getMin());
        dto.setMax(statistics.getMax());
        dto.setSum(statistics.getSum());

        return dto;
    }
}
