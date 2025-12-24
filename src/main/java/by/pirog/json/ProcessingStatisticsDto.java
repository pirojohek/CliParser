package by.pirog.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessingStatisticsDto {
    private Long processingTimeMs;
    private Integer filesProcessed;


    public ProcessingStatisticsDto() {}

    public Long getProcessingTimeMs() { return processingTimeMs; }
    public void setProcessingTimeMs(Long processingTimeMs) { this.processingTimeMs = processingTimeMs; }

    public Integer getFilesProcessed() { return filesProcessed; }
    public void setFilesProcessed(Integer filesProcessed) { this.filesProcessed = filesProcessed; }
}
