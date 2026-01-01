package by.pirog.exception;

/**
 * Базовое исключение для всех ошибок приложения.
 * Содержит код ошибки для exit code.
 */
public abstract class ApplicationException extends Exception {
    private final int exitCode;

    protected ApplicationException(String message, int exitCode) {
        super(message);
        this.exitCode = exitCode;
    }

    protected ApplicationException(String message, Throwable cause, int exitCode) {
        super(message, cause);
        this.exitCode = exitCode;
    }

    public int getExitCode() {
        return exitCode;
    }
}

