package by.pirog.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonStatistics {
    private NumberStatisticsDto numbers;
    private StringStatisticsDto strings;
    private ProcessingStatisticsDto processing;


    public JsonStatistics() {}

    public NumberStatisticsDto getNumbers() { return numbers; }
    public void setNumbers(NumberStatisticsDto numbers) { this.numbers = numbers; }

    public StringStatisticsDto getStrings() { return strings; }
    public void setStrings(StringStatisticsDto strings) { this.strings = strings; }

    public ProcessingStatisticsDto getProcessing() { return processing; }
    public void setProcessing(ProcessingStatisticsDto processing) { this.processing = processing; }
}
