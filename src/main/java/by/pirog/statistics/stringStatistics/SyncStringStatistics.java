package by.pirog.statistics.stringStatistics;

public class SyncStringStatistics implements StringStatistics {
    private long count = 0;
    private String minLengthString = null;
    private String maxLengthString = null;

    public void accept(String value) {
        count++;
        int length = value.length();

        if (minLengthString == null || length < minLengthString.length()) {
            minLengthString = value;
        }
        if (maxLengthString == null || length > maxLengthString.length()) {
            maxLengthString = value;
        }
    }

    public Integer getMinLength() {
        return minLengthString != null ? minLengthString.length() : null;
    }

    public Integer getMaxLength() {
        return maxLengthString != null ? maxLengthString.length() : null;
    }

    @Override
    public String getMaxLengthString() {
        return maxLengthString;
    }

    @Override
    public String getMinLengthString() {
        return minLengthString;
    }

    public long getCount() {
        return count;
    }
}
