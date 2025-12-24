package by.pirog.statistics;

public class StringStatistics {

    private long count = 0;
    private Integer minLength = null;
    private Integer maxLength = null;


    public void accept(String value) {
        count++;
        int length = value.length();

        if (minLength == null || length < minLength) {
            minLength = length;
        }
        if (maxLength == null || length > maxLength) {
            maxLength = length;
        }
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public long getCount() {
        return count;
    }
}
