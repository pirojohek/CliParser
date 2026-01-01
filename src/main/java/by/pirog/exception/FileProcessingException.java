package by.pirog.exception;

import java.nio.file.Path;

/**
 * Исключение при обработке файлов.
 * Exit code: 2 (частичный успех возможен)
 */
public class FileProcessingException extends ApplicationException {
    private static final int EXIT_CODE = 2;

    private final int totalFiles;
    private final int processedFiles;
    private final Path failedFile;
    private final boolean isPartialSuccess;

    public FileProcessingException(String message, int totalFiles, int processedFiles) {
        super(message, EXIT_CODE);
        this.totalFiles = totalFiles;
        this.processedFiles = processedFiles;
        this.failedFile = null;
        this.isPartialSuccess = processedFiles > 0;
    }

    public FileProcessingException(String message, Throwable cause, Path failedFile) {
        super(message, cause, EXIT_CODE);
        this.totalFiles = 0;
        this.processedFiles = 0;
        this.failedFile = failedFile;
        this.isPartialSuccess = false;
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause, EXIT_CODE);
        this.totalFiles = 0;
        this.processedFiles = 0;
        this.failedFile = null;
        this.isPartialSuccess = false;
    }

    public FileProcessingException(String message, Throwable cause, int totalFiles, int processedFiles) {
        super(message, cause, EXIT_CODE);
        this.totalFiles = totalFiles;
        this.processedFiles = processedFiles;
        this.failedFile = null;
        this.isPartialSuccess = processedFiles > 0;
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public int getProcessedFiles() {
        return processedFiles;
    }

    public Path getFailedFile() {
        return failedFile;
    }

    public boolean isPartialSuccess() {
        return isPartialSuccess;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());

        if (totalFiles > 0) {
            sb.append(String.format(" (обработано %d из %d файлов)", processedFiles, totalFiles));
        }

        if (failedFile != null) {
            sb.append(String.format(" [файл: %s]", failedFile));
        }

        return sb.toString();
    }
}
