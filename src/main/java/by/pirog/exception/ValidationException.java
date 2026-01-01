package by.pirog.exception;

/**
 * Исключение при валидации параметров CLI.
 * Exit code: 1
 */
public class ValidationException extends ApplicationException {
    private static final int EXIT_CODE = 1;

    public ValidationException(String message) {
        super(message, EXIT_CODE);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause, EXIT_CODE);
    }
}
