package by.pirog.processor;

import by.pirog.exception.ValidationException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileScanner {

    public static List<Path> resolve(List<Path> inputFiles) {
        List<Path> result = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        Queue<Path> queue = new ArrayDeque<>(inputFiles);

        while (!queue.isEmpty()) {
            Path currentPath = queue.poll();

            if (Files.isDirectory(currentPath)) {
                try(DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath)){
                    stream.forEach(queue::add);
                } catch (IOException e){
                    String errorMsg = String.format("⚠ Невозможно прочитать директорию %s: %s",
                        currentPath, e.getMessage());
                    System.err.println(errorMsg);
                    errors.add(errorMsg);
                }
            } else if (Files.isRegularFile(currentPath)) {
                if (Files.isReadable(currentPath)) {
                    result.add(currentPath);
                } else {
                    String errorMsg = String.format("⚠ Файл недоступен для чтения: %s", currentPath);
                    System.err.println(errorMsg);
                    errors.add(errorMsg);
                }
            } else if (Files.exists(currentPath)) {
                String errorMsg = String.format("⚠ Путь не является файлом или директорией: %s", currentPath);
                System.err.println(errorMsg);
                errors.add(errorMsg);
            }
        }

        if (!errors.isEmpty()) {
            System.err.println(String.format("\n⚠ Обнаружено проблем при сканировании: %d", errors.size()));
        }

        return result;
    }

    public static List<Path> resolveAndValidate(List<Path> inputFiles) throws ValidationException {
        if (inputFiles.isEmpty()) {
            throw new ValidationException("Не указаны входные файлы");
        }

        List<Path> resolvedFiles = resolve(inputFiles);

        if (resolvedFiles.isEmpty()) {
            throw new ValidationException(
                "Не найдено ни одного доступного файла для обработки.\n" +
                "Проверьте, что указанные пути существуют, содержат файлы и доступны для чтения."
            );
        }

        System.out.println(String.format("✓ Найдено файлов для обработки: %d", resolvedFiles.size()));

        return resolvedFiles;
    }
}
