package by.pirog.statistics.stringStatistics;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class AsyncStringStatistics implements StringStatistics {
    private final AtomicLong count = new AtomicLong();
    private final AtomicReference<String> minLengthString = new AtomicReference<>(null);
    private final AtomicReference<String> maxLengthString = new AtomicReference<>(null);


    @Override
    public void accept(String value) {
        count.incrementAndGet();

        minLengthString.updateAndGet(current ->
                current == null || value.length() < current.length() ? value : current
        );

        // Обновляем строку с максимальной длиной
        maxLengthString.updateAndGet(current ->
                current == null || value.length() > current.length() ? value : current
        );
    }

    @Override
    public long getCount() {
        return count.get();
    }

    @Override
    public Integer getMinLength() {
        String minString = minLengthString.get();
        return minString != null ? minString.length() : null;
    }

    @Override
    public Integer getMaxLength() {
        String maxString = maxLengthString.get();
        return maxString != null ? maxString.length() : null;
    }

    @Override
    public String getMaxLengthString() {
        return this.maxLengthString.get();
    }

    @Override
    public String getMinLengthString() {
        return this.minLengthString.get();
    }
}
