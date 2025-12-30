package by.pirog;

import by.pirog.classifier.LineClassifier;
import by.pirog.cli.CliException;
import by.pirog.output.OutputManager;
import by.pirog.outputStrategy.StatisticsOutputStrategy;
import by.pirog.outputStrategy.StatisticsOutputStrategyFactory;
import by.pirog.processor.FileProcessor;
import by.pirog.processor.FileScanner;
import by.pirog.statistics.ExecutionTimer;
import by.pirog.statistics.StatisticsContainer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ApplicationRunner {
    private final ApplicationContext context;

    public ApplicationRunner(ApplicationContext context) {
        this.context = context;
    }

    public void run() throws CliException {
        List<Path> inputFiles = resolveInputFiles();
        processFiles(inputFiles);
        outputStatistics();
    }

    private List<Path> resolveInputFiles() throws CliException {
        return FileScanner.resolveAndValidate(context.getOptions().getInputFiles());
    }

    private void processFiles(List<Path> inputFiles) {
        FileProcessor fileProcessor = new FileProcessor();
        ExecutionTimer timer = new ExecutionTimer(context.getOptions().isTimeStatistics());
        OutputManager outputManager = context.getOutputManager();

        timer.execute(() -> {
            fileProcessor.process(
                    inputFiles,
                    new LineClassifier(),
                    outputManager,
                    context.getNumberStatistics(),
                    context.getStringStatistics(),
                    context.getProcessingStatistics()
            );
            closeOutputManager(outputManager);
        }, context.getProcessingStatistics());
    }

    private void closeOutputManager(OutputManager outputManager) {
        try {
            outputManager.close();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка закрытия OutputManager", e);
        }
    }

    private void outputStatistics() {
        List<StatisticsOutputStrategy> strategies =
                StatisticsOutputStrategyFactory.getStatisticsOutputStrategies(context.getOptions());

        StatisticsContainer container = new StatisticsContainer(
                context.getNumberStatistics(),
                context.getStringStatistics(),
                context.getProcessingStatistics(),
                context.getOptions().isFullStats()
        );

        for (StatisticsOutputStrategy strategy : strategies) {
            strategy.output(container);
        }
    }
}

