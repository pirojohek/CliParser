package by.pirog.processor;

import by.pirog.classifier.LineClassifier;
import by.pirog.exception.FileProcessingException;
import by.pirog.output.OutputManager;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncFileProcessor implements FileProcessor {

    private final SingleFileProcess singleFileProcess;
    private int threadCount = Runtime.getRuntime().availableProcessors();

    public AsyncFileProcessor(SingleFileProcess singleFileProcess) {
        this.singleFileProcess = singleFileProcess;
    }

    @Override
    public void process(List<Path> inputFiles, LineClassifier classifier, OutputManager output, NumberStatistics numberStatistics, StringStatistics stringStatistics, ProcessingStatistics processingStatistics) throws FileProcessingException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        int totalFiles = inputFiles.size();
        for (Path path : inputFiles){
            executorService.submit(() -> {
                singleFileProcess.processSingleFile(path, classifier, output, numberStatistics, stringStatistics, processingStatistics);
                processingStatistics.incrementFilesProcessed();
            });
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                int processedFiles = processingStatistics.getFilesProcessed();
                throw new FileProcessingException(
                    "Превышено время ожидания завершения потоков (таймаут: 60 сек)",
                    totalFiles,
                    processedFiles
                );
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            int processedFiles = processingStatistics.getFilesProcessed();
            throw new FileProcessingException(
                "Обработка прервана: " + e.getMessage(),
                e,
                totalFiles,
                processedFiles
            );
        }
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
