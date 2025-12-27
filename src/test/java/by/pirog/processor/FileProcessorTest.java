package by.pirog.processor;


import by.pirog.classifier.LineClassifier;
import by.pirog.output.DataType;
import by.pirog.output.FileOutputManager;
import by.pirog.output.OutputManager;
import by.pirog.statistics.NumberStatistics;
import by.pirog.statistics.ProcessingStatistics;
import by.pirog.statistics.StringStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

public class FileProcessorTest {

    @TempDir
    private Path tempDir;

    @Test
    void testProcess_CallsDependenciesCorrectly() throws Exception {
        Path file = tempDir.resolve("file.txt");
        Files.write(file, List.of("123", "3.14", "hello world"));

        LineClassifier classifier = mock(LineClassifier.class);
        OutputManager outputManager = mock(FileOutputManager.class);
        NumberStatistics numberStatistics = mock(NumberStatistics.class);
        StringStatistics stringStatistics = mock(StringStatistics.class);
        ProcessingStatistics processingStatistics = mock(ProcessingStatistics.class);


        when(classifier.classify("123")).thenReturn(DataType.INTEGER);
        when(classifier.classify("3.14")).thenReturn(DataType.FLOAT);
        when(classifier.classify("hello world")).thenReturn(DataType.STRING);

        FileProcessor processor = new FileProcessor();

        processor.process(List.of(file), classifier, outputManager, numberStatistics, stringStatistics,
                processingStatistics);

        verify(outputManager).write(DataType.INTEGER, "123");
        verify(outputManager).write(DataType.FLOAT, "3.14");
        verify(outputManager).write(DataType.STRING, "hello world");

        verify(numberStatistics).accept(123L);
        verify(numberStatistics).accept(3.14f);
        verify(stringStatistics).accept("hello world");

        verifyNoMoreInteractions(outputManager, numberStatistics, stringStatistics);
    }
}
