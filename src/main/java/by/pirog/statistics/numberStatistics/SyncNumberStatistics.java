package by.pirog.statistics.numberStatistics;

public class SyncNumberStatistics implements NumberStatistics {
    private long count = 0;
    private Double min = null;
    private Double max = null;
    private double sum = 0.0;

    public void accept(double value){
        count++;
        sum+=value;

        if (min == null || value < min) {
            min = value;
        }
        if (max == null || value > max) {
            max = value;
        }
    }


    public long getCount() {
        return count;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public double getSum() {
        return sum;
    }
}
