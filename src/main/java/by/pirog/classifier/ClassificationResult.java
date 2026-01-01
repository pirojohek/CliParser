package by.pirog.classifier;

import by.pirog.output.DataType;

public class ClassificationResult {
    private final DataType type;
    private final Object parsedValue;

    public ClassificationResult(DataType type, Object parsedValue) {
        this.type = type;
        this.parsedValue = parsedValue;
    }

    public DataType getType() {
        return type;
    }

    public Object getParsedValue() {
        return parsedValue;
    }

    public Long getAsLong() {
        return (Long) parsedValue;
    }

    public Double getAsDouble() {
        return (Double) parsedValue;
    }
}

