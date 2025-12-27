package by.pirog;

import by.pirog.classifier.LineClassifier;
import by.pirog.cli.ArgumentParser;
import by.pirog.cli.CliException;
import by.pirog.cli.CliOptions;
import by.pirog.cli.CliOptionsValidator;
import by.pirog.output.AsyncFileOutputManager;
import by.pirog.output.FileOutputManager;
import by.pirog.output.OutputManager;
import by.pirog.outputStrategy.StatisticsOutputStrategy;
import by.pirog.outputStrategy.StatisticsOutputStrategyFactory;
import by.pirog.processor.FileProcessor;
import by.pirog.processor.FileScanner;
import by.pirog.statistics.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class App 
{
    public static void main( String[] args )
    {
        try{
            CliOptions options = ArgumentParser.parse(args);
            CliOptionsValidator.validate(options);

            OutputManager outputManager = new FileOutputManager(options);

            final OutputManager om = options.isAsync()
                    ? new AsyncFileOutputManager(outputManager)
                    : outputManager;

            NumberStatistics numberStatistics = new NumberStatistics();
            StringStatistics stringStatistics = new StringStatistics();
            ProcessingStatistics processingStatistics = new ProcessingStatistics();

            List<Path> inputFiles = FileScanner.resolveAndValidate(options.getInputFiles());

            FileProcessor fileProcessor = new FileProcessor();

            ExecutionTimer timer = new ExecutionTimer(options.isTimeStatistics());

            timer.execute(() -> {

                fileProcessor.process(
                        inputFiles,
                        new LineClassifier(),
                        om,
                        numberStatistics,
                        stringStatistics,
                        processingStatistics
                );
                try{
                    om.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error closing output manager");
                }
            }, processingStatistics);

            List<StatisticsOutputStrategy> categories =
                    StatisticsOutputStrategyFactory.getStatisticsOutputStrategies(options);

            StatisticsContainer container = new StatisticsContainer(
                    numberStatistics,
                    stringStatistics,
                    processingStatistics,
                    options.isFullStats()
            );
            for (StatisticsOutputStrategy category : categories){
                category.output(container);
            }

        } catch (CliException e){
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e){
            System.err.println("Неожиданная ошибка:" +  e.getMessage());
        }
    }
}
