package by.pirog.processor;

import by.pirog.cli.CliException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileScanner {

    public static List<Path> resolve(List<Path> inputFiles) {
        List<Path> result = new ArrayList<>();
        Queue<Path> queue = new ArrayDeque<>(inputFiles);
        while (!queue.isEmpty()) {
            Path currentPath = queue.poll();

            if (Files.isDirectory(currentPath)) {
                try(DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath)){
                    stream.forEach(queue::add);
                } catch (IOException e){
                    System.err.println("Невозможно извлечь данные из папки: " + currentPath.toString());
                }
            }else if (Files.isRegularFile(currentPath)) {
                result.add(currentPath);
            }
        }
        return result;
    }

    public static List<Path> resolveAndValidate(List<Path> inputFiles) throws CliException {
        List<Path> resolvedFiles = resolve(inputFiles);
        if (resolvedFiles.isEmpty()) {
            throw new CliException("Не найдено ни одного входного файла для обработки. " +
                    "Проверьте, что указанные пути существуют и содержат файлы.");
        }
        return resolvedFiles;
    }
}
