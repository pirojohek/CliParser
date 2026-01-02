package by.pirog.processor;


import by.pirog.classifier.LineClassifier;
import by.pirog.output.OutputManager;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

public class SyncFileProcessorTest {

    @Test
    void shouldProcessAllFiles() throws Exception {
        SingleFileProcess single = mock(SingleFileProcess.class);
        SyncFileProcessor processor = new SyncFileProcessor(single);

        ProcessingStatistics processingStatistics = mock(ProcessingStatistics.class);

        List<Path> files = List.of(
                Path.of("a.txt"),
                Path.of("b.txt"),
                Path.of("c.txt")
        );

        processor.process(
                files,
                mock(LineClassifier.class),
                mock(OutputManager.class),
                mock(NumberStatistics.class),
                mock(StringStatistics.class),
                processingStatistics
        );

        verify(single, times(3))
                .processSingleFile(any(), any(), any(), any(), any(), any());
        verify(processingStatistics, times(3)).incrementFilesProcessed();
    }
}
