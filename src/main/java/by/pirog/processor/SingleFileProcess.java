package by.pirog.processor;

import by.pirog.classifier.LineClassifier;
import by.pirog.output.DataType;
import by.pirog.output.OutputManager;
import by.pirog.statistics.numberStatistics.NumberStatistics;
import by.pirog.statistics.processingStatistics.ProcessingStatistics;
import by.pirog.statistics.stringStatistics.StringStatistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SingleFileProcess {

    public void processSingleFile(
            Path path,
            LineClassifier classifier,
            OutputManager output,
            NumberStatistics numberStatistics,
            StringStatistics stringStatistics,
            ProcessingStatistics processingStatistics) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                processLine(line, lineNumber, path, classifier, output, numberStatistics, stringStatistics, processingStatistics);
            }
            processingStatistics.incrementFilesSucceeded();
        } catch (IOException e) {
            System.err.println("⚠ Ошибка чтения файла " + path + ": " + e.getMessage());
            processingStatistics.incrementFilesFailed();
        }
    }

    private void processLine(String line,
                             int lineNumber,
                             Path filePath,
                             LineClassifier classifier,
                             OutputManager output,
                             NumberStatistics numberStatistics,
                             StringStatistics stringStatistics,
                             ProcessingStatistics  processingStatistics) {
        if (line.isEmpty()) {
            return;
        }

        processingStatistics.incrementLines();

        try{
            var result = classifier.classifyWithValue(line);
            DataType type = result.getType();

            switch (type) {
                case INTEGER -> {
                    output.write(type, line);
                    numberStatistics.accept(result.getAsLong());
                }
                case STRING -> {
                    output.write(type, line);
                    stringStatistics.accept(line);
                }
                case FLOAT -> {
                    output.write(type, line);
                    numberStatistics.accept(result.getAsDouble());
                }
            }

            processingStatistics.incrementValidLines();
        } catch (IOException e) {
            System.err.println(String.format("⚠ Ошибка записи [%s:%d]: %s",
                filePath.getFileName(), lineNumber, e.getMessage()));
            processingStatistics.incrementInvalidLines();
        } catch (Exception e) {
            System.err.println(String.format("⚠ Ошибка обработки [%s:%d]: \"%s\" — %s",
                filePath.getFileName(), lineNumber, truncate(line, 50), e.getMessage()));
            processingStatistics.incrementInvalidLines();
        }
    }

    private String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }
}
