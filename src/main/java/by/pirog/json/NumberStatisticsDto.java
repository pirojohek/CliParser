package by.pirog.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NumberStatisticsDto {
    private Long count;
    private Double min;
    private Double max;
    private Double sum;
    private Double average;


    public NumberStatisticsDto() {}

    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }

    public Double getMin() { return min; }
    public void setMin(Double min) { this.min = min; }

    public Double getMax() { return max; }
    public void setMax(Double max) { this.max = max; }

    public Double getSum() { return sum; }
    public void setSum(Double sum) { this.sum = sum; }

    public Double getAverage() { return average; }
    public void setAverage(Double average) { this.average = average; }
}
