package by.pirog.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessingStatisticsDto {
    private Integer filesProcessed;

    private Integer filesSucceeded;
    private Integer filesFailed;
    private Long totalLines;
    private Long validLines;
    private Long invalidLines;
    private Long executionTimeMs;


    public ProcessingStatisticsDto(Integer filesProcessed, Integer filesSucceeded, Integer filesFailed, Long totalLines, Long validLines, Long invalidLines, Long executionTimeMs) {
        this.filesProcessed = filesProcessed;
        this.filesSucceeded = filesSucceeded;
        this.filesFailed = filesFailed;
        this.totalLines = totalLines;
        this.validLines = validLines;
        this.invalidLines = invalidLines;
        this.executionTimeMs = executionTimeMs;
    }

    public ProcessingStatisticsDto() {
    }

    public Integer getFilesProcessed() {
        return filesProcessed;
    }

    public void setFilesProcessed(Integer filesProcessed) {
        this.filesProcessed = filesProcessed;
    }

    public Integer getFilesSucceeded() {
        return filesSucceeded;
    }

    public void setFilesSucceeded(Integer filesSucceeded) {
        this.filesSucceeded = filesSucceeded;
    }

    public Integer getFilesFailed() {
        return filesFailed;
    }

    public void setFilesFailed(Integer filesFailed) {
        this.filesFailed = filesFailed;
    }

    public Long getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(Long totalLines) {
        this.totalLines = totalLines;
    }

    public Long getValidLines() {
        return validLines;
    }

    public void setValidLines(Long validLines) {
        this.validLines = validLines;
    }

    public Long getInvalidLines() {
        return invalidLines;
    }

    public void setInvalidLines(Long invalidLines) {
        this.invalidLines = invalidLines;
    }

    public Long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(Long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}
