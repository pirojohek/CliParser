package by.pirog.outputStrategy;

import by.pirog.statistics.NumberStatistics;
import by.pirog.statistics.StatisticsContainer;
import by.pirog.statistics.StringStatistics;

public class ConsoleStatisticsOutput implements StatisticsOutputStrategy {


    @Override
    public void output(StatisticsContainer statistics) {
        System.out.println("=== Статистика ===");

        printNumberStatistics(statistics.getNumberStatistics(), statistics.isFullStats());
        printStringStatistics(statistics.getStringStatistics(), statistics.isFullStats());
    }

    private void printNumberStatistics(NumberStatistics stats, boolean full) {
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

    private void printStringStatistics(StringStatistics stats, boolean full) {
        if (stats.getCount() == 0) {
            return;
        }

        System.out.println("\nСтроковая статистика:");
        System.out.println("  Количество: " + stats.getCount());

        if (full) {
            System.out.println("  Минимальная длина: " + stats.getMinLength());
            System.out.println("  Максимальная длина: " + stats.getMaxLength());
        }
    }
}
