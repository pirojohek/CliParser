package by.pirog.outputStrategy;

import by.pirog.statistics.StatisticsContainer;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;

public class ConsoleStatisticsOutput implements StatisticsOutputStrategy {

    @Override
    public void output(StatisticsContainer statistics) {
        System.out.println("=== Статистика ===");

        printProcessingStatistics(statistics.getProcessingStatistics() , statistics.isFullCustomStats());
        printNumberStatistics(statistics.getNumberStatistics(), statistics.isFullStats(), statistics.isFullCustomStats());
        printStringStatistics(statistics.getStringStatistics(), statistics.isFullStats(), statistics.isFullCustomStats());
    }

    private void printProcessingStatistics(ProcessingStatistics stats, boolean fullCustomStats) {
        if (fullCustomStats) {
            System.out.println("\nОбщая статистика обработки:");
            System.out.println("  Файлов обработано: " + stats.getFilesProcessed());
            System.out.println("  Файлов успешно: " + stats.getFilesSucceeded());
            System.out.println("  Файлов с ошибками: " + stats.getFilesFailed());
            System.out.println("  Всего строк: " + stats.getTotalLines());
            System.out.println("  Валидных строк: " + stats.getValidLines());
            System.out.println("  Невалидных строк: " + stats.getInvalidLines());

            if (stats.getExecutionTimeMs() > 0) {
                System.out.println("  Время выполнения: " + stats.getExecutionTimeMs() + " мс");
            }
        }
    }

    private void printNumberStatistics(NumberStatistics stats, boolean full, boolean fullCustomStats) {
        if (stats.getCount() == 0) {
            return;
        }

        System.out.println("\nЧисловая статистика:");
        System.out.println("  Количество: " + stats.getCount());

        if (full) {
            System.out.println("  Минимум: " + stats.getMin());
            System.out.println("  Максимум: " + stats.getMax());
            System.out.println("  Сумма: " + stats.getSum());
        }
    }

    private void printStringStatistics(StringStatistics stats, boolean full, boolean fullCustomStats) {
        if (stats.getCount() == 0) {
            return;
        }

        System.out.println("\nСтроковая статистика:");
        System.out.println("  Количество: " + stats.getCount());

        if (full) {
            System.out.println("  Минимальная длина: " + stats.getMinLength());
            System.out.println("  Максимальная длина: " + stats.getMaxLength());
        }

        if (fullCustomStats) {
            System.out.println("  Минимальная длина: " + stats.getMinLength());
            System.out.println("  Максимальная длина: " + stats.getMaxLength());
            System.out.println("  Строка с минимальной длиной: " + stats.getMinLengthString());
            System.out.println("  Строка с максимальной длиной: " + stats.getMaxLengthString());
        }
    }
}
