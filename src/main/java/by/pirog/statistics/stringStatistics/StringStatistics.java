package by.pirog.statistics.stringStatistics;

public interface StringStatistics {

    void accept(String value);
    long getCount();
    Integer getMinLength();
    Integer getMaxLength();

    String getMaxLengthString();
    String getMinLengthString();
}
