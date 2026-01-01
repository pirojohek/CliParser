package by.pirog;

import by.pirog.classifier.LineClassifier;
import by.pirog.exception.FileProcessingException;
import by.pirog.exception.OutputException;
import by.pirog.exception.ValidationException;
import by.pirog.output.OutputManager;
import by.pirog.outputStrategy.StatisticsOutputStrategy;
import by.pirog.outputStrategy.StatisticsOutputStrategyFactory;
import by.pirog.processor.FileProcessor;
import by.pirog.processor.FileProcessorFactory;
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

    public void run() throws ValidationException, FileProcessingException, OutputException {
        List<Path> inputFiles = resolveInputFiles();
        processFiles(inputFiles);
        outputStatistics();
    }

    private List<Path> resolveInputFiles() throws ValidationException {
        return FileScanner.resolveAndValidate(context.getOptions().getInputFiles());
    }

    private void processFiles(List<Path> inputFiles) throws FileProcessingException, OutputException {
        FileProcessor fileProcessor = FileProcessorFactory.createFileProcessor(context.getOptions());
        ExecutionTimer timer = new ExecutionTimer(context.getOptions().isTimeStatistics());
        OutputManager outputManager = context.getOutputManager();

        try {
            timer.execute(() -> {
                try {
                    fileProcessor.process(
                            inputFiles,
                            new LineClassifier(),
                            outputManager,
                            context.getNumberStatistics(),
                            context.getStringStatistics(),
                            context.getProcessingStatistics()
                    );
                } catch (FileProcessingException e) {
                    throw new RuntimeException(e);
                }
            }, context.getProcessingStatistics());
        } catch (RuntimeException e) {
            if (e.getCause() instanceof FileProcessingException) {
                throw (FileProcessingException) e.getCause();
            }
            throw e;
        } finally {
            closeOutputManager(outputManager);
        }
    }

    private void closeOutputManager(OutputManager outputManager) throws OutputException {
        try {
            outputManager.close();
        } catch (IOException e) {
            throw new OutputException("Ошибка закрытия OutputManager", e, null, null);
        }
    }

    private void outputStatistics() {
        List<StatisticsOutputStrategy> strategies =
                StatisticsOutputStrategyFactory.getStatisticsOutputStrategies(context.getOptions());

        StatisticsContainer container = new StatisticsContainer(
                context.getNumberStatistics(),
                context.getStringStatistics(),
                context.getProcessingStatistics(),
                context.getOptions().isFullStats(),
                context.getOptions().isFullCustomStats()
        );

        for (StatisticsOutputStrategy strategy : strategies) {
            strategy.output(container);
        }
    }
}

