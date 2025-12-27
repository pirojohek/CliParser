package by.pirog.outputStrategy;

import by.pirog.converter.NumberStatisticsConverter;
import by.pirog.converter.ProcessingStatisticsConverter;
import by.pirog.converter.StringStatisticsConverter;
import by.pirog.json.JsonStatistics;
import by.pirog.json.NumberStatisticsDto;
import by.pirog.statistics.NumberStatistics;
import by.pirog.statistics.StatisticsContainer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Path;

public class JsonStatisticsOutput implements StatisticsOutputStrategy {

    private final String prefix;
    private final String outputFileName = "statistics.json";


    public JsonStatisticsOutput(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void output(StatisticsContainer statistics) {
        JsonStatistics jsonStatistics = convertToJsonStatistics(statistics);
        String outputPath = prefix + outputFileName;

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(outputPath), jsonStatistics);
            System.out.println("Статистика успешно записана в JSON файл: " + outputPath);
        } catch (Exception e){
            System.err.println("Ошибка при записи в JSON файл: " + e.getMessage());
        }
    }

    private JsonStatistics convertToJsonStatistics(StatisticsContainer statistics) {
        JsonStatistics jsonStatistics = new JsonStatistics();
        jsonStatistics.setNumbers(NumberStatisticsConverter.toDto(statistics.getNumberStatistics()));
        jsonStatistics.setStrings(StringStatisticsConverter.toDto(statistics.getStringStatistics()));
        jsonStatistics.setProcessing(ProcessingStatisticsConverter.toDto(statistics.getProcessingStatistics()));

        return jsonStatistics;
    }
}
