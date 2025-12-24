package by.pirog.outputStrategy;

import by.pirog.cli.CliOptions;

import java.util.ArrayList;
import java.util.List;

public class StatisticsOutputStrategyFactory {

    public static List<StatisticsOutputStrategy> getStatisticsOutputStrategies(CliOptions options) {
        List<StatisticsOutputStrategy> statisticsOutputStrategies = new ArrayList<>();

        if (options.isJsonStats()) {
            statisticsOutputStrategies.add(new JsonStatisticsOutput(options.getPrefix()));
        }

        statisticsOutputStrategies.add(new ConsoleStatisticsOutput());

        return statisticsOutputStrategies;
    }
}
