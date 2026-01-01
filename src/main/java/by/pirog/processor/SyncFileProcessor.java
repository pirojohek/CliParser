package by.pirog.processor;


import by.pirog.classifier.LineClassifier;
import by.pirog.exception.FileProcessingException;
import by.pirog.output.OutputManager;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;

import java.nio.file.Path;
import java.util.List;

public class SyncFileProcessor implements FileProcessor {

    private final SingleFileProcess singleFileProcess;

    public SyncFileProcessor(SingleFileProcess singleFileProcess) {
        this.singleFileProcess = singleFileProcess;
    }

    public void process(
            List<Path> inputFiles,
            LineClassifier classifier,
            OutputManager output,
            NumberStatistics numberStatistics,
            StringStatistics stringStatistics,
            ProcessingStatistics processingStatistics
    ) throws FileProcessingException {
        for (Path path : inputFiles) {
            singleFileProcess.processSingleFile(path, classifier, output, numberStatistics, stringStatistics, processingStatistics);
            processingStatistics.incrementFilesProcessed();
        }
    }


}
