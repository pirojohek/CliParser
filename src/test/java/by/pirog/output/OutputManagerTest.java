package by.pirog.output;

import by.pirog.cli.CliOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutputManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteAndCreateFile() throws IOException {
        // given
        CliOptions options = new CliOptions();
        options.setOutputDir(tempDir);
        options.setPrefix("test_");

        //when
        FileOutputManager manager = new FileOutputManager(options);

        manager.write(DataType.INTEGER, "123");
        manager.write(DataType.FLOAT, "1.32");
        manager.write(DataType.STRING, "abc");

        manager.close();

        // then
        Path intFile = tempDir.resolve("test_integers.txt");
        Path floatFile = tempDir.resolve("test_floats.txt");
        Path stringFile = tempDir.resolve("test_strings.txt");

        assertTrue(Files.exists(intFile));
        assertTrue(Files.exists(floatFile));
        assertTrue(Files.exists(stringFile));

        List<String> intLines = Files.readAllLines(intFile);
        assertEquals(List.of("123"), intLines);

        List<String> floatLines = Files.readAllLines(floatFile);
        assertEquals(List.of("1.32"), floatLines);

        List<String> stringLines = Files.readAllLines(stringFile);
        assertEquals(List.of("abc"), stringLines);
    }


    @Test
    void testAppendMode() throws IOException {
        // given
        CliOptions options = new CliOptions();
        options.setOutputDir(tempDir);
        options.setPrefix("test_");
        options.setAppend(true);


        // when
        FileOutputManager manager = new FileOutputManager(options);

        manager.write(DataType.INTEGER, "123");
        manager.close();

        manager = new FileOutputManager(options);
        manager.write(DataType.INTEGER, "12");
        manager.close();

        // then

        Path file =  tempDir.resolve("test_integers.txt");
        assertTrue(Files.exists(file));

        List<String> lines = Files.readAllLines(file);
        assertEquals(List.of("123", "12"), lines);

    }
}

