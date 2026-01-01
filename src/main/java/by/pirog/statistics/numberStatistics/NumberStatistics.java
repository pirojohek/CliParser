package by.pirog.statistics.numberStatistics;

public interface NumberStatistics {
    void accept(double value);
    long getCount();
    Double getMin();
    Double getMax();
    double getSum();
}
