package by.pirog.exception;

import by.pirog.output.DataType;

/**
 * Исключение при записи в выходные файлы.
 * Exit code: 3
 */
public class OutputException extends ApplicationException {
    private static final int EXIT_CODE = 3;

    private final DataType dataType;
    private final String outputPath;

    public OutputException(String message, DataType dataType) {
        super(message, EXIT_CODE);
        this.dataType = dataType;
        this.outputPath = null;
    }

    public OutputException(String message, Throwable cause, DataType dataType, String outputPath) {
        super(message, cause, EXIT_CODE);
        this.dataType = dataType;
        this.outputPath = outputPath;
    }

    public DataType getDataType() {
        return dataType;
    }

    public String getOutputPath() {
        return outputPath;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());

        if (dataType != null) {
            sb.append(String.format(" [тип: %s]", dataType));
        }

        if (outputPath != null) {
            sb.append(String.format(" [путь: %s]", outputPath));
        }

        return sb.toString();
    }
}

