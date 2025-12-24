package by.pirog.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StringStatisticsDto {
    private Long count;
    private Integer minLength;
    private Integer maxLength;

    public StringStatisticsDto() {}

    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }

    public Integer getMinLength() { return minLength; }
    public void setMinLength(Integer minLength) { this.minLength = minLength; }

    public Integer getMaxLength() { return maxLength; }
    public void setMaxLength(Integer maxLength) { this.maxLength = maxLength; }
}
