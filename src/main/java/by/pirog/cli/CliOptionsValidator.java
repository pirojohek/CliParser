package by.pirog.cli;

import by.pirog.exception.ValidationException;

public final class CliOptionsValidator {

    private CliOptionsValidator() {

    }

    public static void validate(CliOptions options) throws ValidationException {
        int statsCount = 0;
        if (options.isShortStats()) statsCount++;
        if (options.isFullStats()) statsCount++;
        if (options.isFullCustomStats()) statsCount++;

        if (statsCount > 1) {
            throw new ValidationException("Нельзя использовать несколько типов статистики одновременно");
        }

        if (statsCount == 0) {
            throw new ValidationException("Необходимо указать тип статистики: -s (краткая), -f (полная) или --full (полная кастомная статистика)");
        }

        if (options.getInputFiles().isEmpty()) {
            throw new ValidationException("Не указаны входные файлы");
        }

        if (options.getThreadCount() != null && !options.isAsync()) {
            throw new ValidationException("Опция --threads доступна только для синхронной обработки");
        }

        if (options.getThreadCount() != null && options.getThreadCount() <= 0) {
            throw new ValidationException("Количество потоков должно быть положительным числом");
        }
    }
}
