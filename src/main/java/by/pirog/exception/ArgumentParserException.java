package by.pirog.exception;

/**
 * Исключение при парсинге аргументов командной строки.
 * Exit code: 1
 */
public class ArgumentParserException extends ApplicationException {
    private static final int EXIT_CODE = 1;

    public ArgumentParserException(String message) {
        super(message, EXIT_CODE);
    }

    public ArgumentParserException(String message, Throwable cause) {
        super(message, cause, EXIT_CODE);
    }
}
