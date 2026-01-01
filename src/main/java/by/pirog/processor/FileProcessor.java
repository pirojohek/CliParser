package by.pirog.processor;
import by.pirog.classifier.LineClassifier;
import by.pirog.exception.FileProcessingException;
import by.pirog.output.OutputManager;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;

import java.nio.file.Path;
import java.util.List;

public interface FileProcessor {

    void process(List<Path> inputFiles,
                 LineClassifier classifier,
                 OutputManager output,
                 NumberStatistics numberStatistics,
                 StringStatistics stringStatistics,
                 ProcessingStatistics processingStatistics) throws FileProcessingException;
}
