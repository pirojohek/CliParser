package by.pirog.statistics.numberStatistics;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.DoubleAdder;

public class AsyncNumberStatistics implements NumberStatistics {
    private final AtomicLong counter = new AtomicLong();
    private final AtomicReference<Double> min = new AtomicReference<>(null);
    private final AtomicReference<Double> max = new AtomicReference<>(null);
    private final DoubleAdder sum = new DoubleAdder();

    @Override
    public void accept(double value) {
        counter.incrementAndGet();
        sum.add(value);

        min.updateAndGet(current -> (current == null || value < current) ? value : current);
        max.updateAndGet(current -> (current == null || value > current) ? value : current);
    }

    @Override
    public long getCount() {
        return counter.get();
    }

    @Override
    public Double getMin() {
        return min.get();
    }

    @Override
    public Double getMax() {
        return max.get();
    }

    @Override
    public double getSum() {
        return sum.sum();
    }
}
