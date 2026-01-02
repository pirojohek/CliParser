package by.pirog.processor;

import by.pirog.classifier.LineClassifier;
import by.pirog.output.DataType;
import by.pirog.output.OutputManager;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;


public class SingleFileProcessTest {


    @TempDir
    public Path tempDir;


    @Test
    void testProcessSingleFile_successTest_writesLines() throws IOException {

        Path path = tempDir.resolve("test.txt");

        SingleFileProcess processor = new SingleFileProcess();
        var classifier = new LineClassifier();
        OutputManager output = mock(OutputManager.class);
        NumberStatistics numberStatistics = mock(NumberStatistics.class);
        StringStatistics stringStatistics = mock(StringStatistics.class);
        ProcessingStatistics processingStatistics = mock(ProcessingStatistics.class);


        Files.write(path, List.of(
                "123",
                "hello",
                "3.14",
                "42"
        ));


        processor.processSingleFile(
                path,
                classifier,
                output,
                numberStatistics,
                stringStatistics,
                processingStatistics
        );

        verify(output).write(DataType.INTEGER, "123");
        verify(output).write(DataType.STRING, "hello");
        verify(output).write(DataType.FLOAT, "3.14");
        verify(output).write(DataType.INTEGER, "42");

        verify(numberStatistics).accept(123L);
        verify(numberStatistics).accept(3.14);
        verify(numberStatistics).accept(42L);
        verify(stringStatistics).accept("hello");


        verify(processingStatistics, times(4)).incrementLines();
        verify(processingStatistics, times(4)).incrementValidLines();
        verify(processingStatistics, never()).incrementInvalidLines();
    }


    @Test
    void testProcessSingleFile_cannotOpenFile_incrementsFailed() {

        SingleFileProcess processor = new SingleFileProcess();
        var classifier = mock(LineClassifier.class);
        OutputManager output = mock(OutputManager.class);
        NumberStatistics numberStatistics = mock(NumberStatistics.class);
        StringStatistics stringStatistics = mock(StringStatistics.class);
        ProcessingStatistics processingStatistics = mock(ProcessingStatistics.class);


        processor.processSingleFile(
                Path.of("non_existent_file.txt"),
                classifier,
                output,
                numberStatistics,
                stringStatistics,
                processingStatistics
        );

        verify(processingStatistics, times(1)).incrementFilesFailed();
        verify(processingStatistics, never()).incrementFilesSucceeded();
    }

    @Test
    void testProcessSingleFile_cannotWriteFile_incrementsInvalidLines() throws IOException {

        Path path = tempDir.resolve("test.txt");

        SingleFileProcess processor = new SingleFileProcess();
        var classifier = new LineClassifier();
        OutputManager output = mock(OutputManager.class);
        NumberStatistics numberStatistics = mock(NumberStatistics.class);
        StringStatistics stringStatistics = mock(StringStatistics.class);
        ProcessingStatistics processingStatistics = mock(ProcessingStatistics.class);

        Files.write(path, List.of(
                "123",
                "invalid_line",
                "3.14"
        ));

        doThrow(
                new IOException("Write error")
        ).when(output).write(DataType.STRING, "invalid_line");


        processor.processSingleFile(
                path,
                classifier,
                output,
                numberStatistics,
                stringStatistics,
                processingStatistics
        );

        verify(processingStatistics, times(1)).incrementInvalidLines();
        verify(processingStatistics, never()).incrementFilesFailed();
        verify(processingStatistics, times(3)).incrementLines();
        verify(processingStatistics, times(2)).incrementValidLines();
    }
}
