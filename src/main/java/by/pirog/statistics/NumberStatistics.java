package by.pirog.statistics;

public class NumberStatistics implements Statistics {

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


    @Override
    public void print(boolean full) {
        if (count == 0) {
            return;
        }

        System.out.println("Numbers:");
        System.out.println("  count = " + count);

        if (full) {
            System.out.println("  min = " + min);
            System.out.println("  max = " + max);
            System.out.println("  sum = " + sum);
            System.out.println("  avg = " + (sum / count));
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
