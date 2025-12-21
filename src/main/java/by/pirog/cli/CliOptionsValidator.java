package by.pirog.cli;

public final class CliOptionsValidator {

    private CliOptionsValidator() {

    }


    public static void validate(CliOptions options) throws CliException {
        if (options.isShortStats() && options.isFullStats()) {
            throw new CliException("Нельзя использовать -f и -s одновременно");
        }

        if (options.getInputFiles().isEmpty()) {
            throw new CliException("Не указаны входные файлы");
        }
    }
}
