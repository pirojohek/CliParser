package by.pirog.statistics;

public class StringStatistics implements Statistics {

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

    @Override
    public void print(boolean full) {
        if (count == 0) {
            return;
        }

        System.out.println("Strings:");
        System.out.println("  count = " + count);

        if (full) {
            System.out.println("  minLength = " + minLength);
            System.out.println("  maxLength = " + maxLength);
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
