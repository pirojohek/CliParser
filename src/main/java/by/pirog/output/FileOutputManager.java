package by.pirog.output;

import by.pirog.cli.CliOptions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.EnumMap;
import java.util.Map;


public class FileOutputManager implements OutputManager {

    private final Path outputDir;
    private final String prefix;
    private final boolean append;

    private final Map<DataType, BufferedWriter> writers =
            new EnumMap<>(DataType.class);

    public FileOutputManager(CliOptions options) {
        this.outputDir = options.getOutputDir() != null ?
                options.getOutputDir() : Paths.get(".");
        this.prefix = options.getPrefix();
        this.append = options.isAppend();

    }

    @Override
    public void write(DataType type, String value) throws IOException {
        BufferedWriter writer = writers.get(type);

        if (writer == null) {
            writer = createWriter(type);
            writers.put(type, writer);
        }
        writer.write(value);
        writer.newLine();

    }

    private BufferedWriter createWriter(DataType type) throws IOException {
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        String fileName = prefix + type.getDefaultFileName();
        Path filePath = outputDir.resolve(fileName);

        OpenOption[] options = append
                ? new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND}
                : new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};

        return Files.newBufferedWriter(filePath, options);
    }

    @Override
    public void close() throws IOException {
        for (BufferedWriter writer : writers.values()) {
            writer.close();
        }
    }
}
