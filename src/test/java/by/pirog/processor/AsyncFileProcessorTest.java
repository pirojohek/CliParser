package by.pirog.processor;

import by.pirog.classifier.LineClassifier;
import by.pirog.output.OutputManager;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

public class AsyncFileProcessorTest {

    @TempDir
    Path tempDir;

    @Test
    void testProcessMultipleFiles_callsDelegateAndIncrementsStats() throws Exception {
        SingleFileProcess singleFileProcess = mock(SingleFileProcess.class);
        LineClassifier classifier = mock(LineClassifier.class);
        OutputManager output = mock(OutputManager.class);
        NumberStatistics numberStatistics = mock(NumberStatistics.class);
        StringStatistics stringStatistics = mock(StringStatistics.class);
        ProcessingStatistics processingStatistics = mock(ProcessingStatistics.class);

        AsyncFileProcessor asyncProcessor = new AsyncFileProcessor(singleFileProcess);
        asyncProcessor.setThreadCount(2);

        List<Path> files = List.of(tempDir.resolve("file1.txt"), tempDir.resolve("file2.txt"));

        asyncProcessor.process(files, classifier, output, numberStatistics, stringStatistics, processingStatistics);

        for (Path file : files) {
            verify(singleFileProcess).processSingleFile(file, classifier, output, numberStatistics, stringStatistics, processingStatistics);
        }

        verify(processingStatistics, times(files.size())).incrementFilesProcessed();
    }
}
