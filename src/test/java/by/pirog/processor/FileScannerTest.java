package by.pirog.processor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileScannerTest {

    @TempDir
    public Path tempDir;

    @Test
    void resolve_recursiveFindsAllFiles() throws Exception {
        Path a = Files.createFile(tempDir.resolve("a.txt"));

        Path sub = Files.createDirectory(tempDir.resolve("sub"));
        Path b = Files.createFile(sub.resolve("b"));

        Path sub2 = Files.createDirectory(sub.resolve("sub2"));
        Path c = Files.createFile(sub2.resolve("c"));

        List<Path> result = FileScanner.resolve(List.of(tempDir));

        assertThat(result).containsExactlyInAnyOrder(a, b, c);
    }
}
