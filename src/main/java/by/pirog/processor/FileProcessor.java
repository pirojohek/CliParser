package by.pirog.processor;


import by.pirog.classifier.LineClassifier;
import by.pirog.output.DataType;
import by.pirog.output.OutputManager;
import by.pirog.statistics.NumberStatistics;
import by.pirog.statistics.StringStatistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileProcessor {

    public void process(
            List<Path> inputFiles,
            LineClassifier classifier,
            OutputManager output,
            NumberStatistics numberStatistics,
            StringStatistics stringStatistics
    ) {
        for (Path path : inputFiles) {
            processSingleFile(path, classifier, output, numberStatistics, stringStatistics);
        }
    }

    private void processSingleFile(
            Path path,
            LineClassifier classifier,
            OutputManager output,
            NumberStatistics numberStatistics,
            StringStatistics stringStatistics) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, classifier, output, numberStatistics, stringStatistics);
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла " + path + ": " + e.getMessage());
        }
    }

    private void processLine(String line,
                             LineClassifier classifier,
                             OutputManager output,
                             NumberStatistics numberStatistics,
                             StringStatistics stringStatistics) {
        if (line.isEmpty()) {
            return;
        }

        try{
            DataType type = classifier.classify(line);

            switch (type) {
                case INTEGER -> {
                    output.write(type, line);
                    numberStatistics.accept(Long.parseLong(line));
                }
                case STRING -> {
                    output.write(type, line);
                    stringStatistics.accept(line);
                }
                case FLOAT -> {
                    output.write(type, line);
                    numberStatistics.accept(Float.parseFloat(line));
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка обработки строки: \"" + line + "\" — " + e.getMessage());
        }
    }
}
