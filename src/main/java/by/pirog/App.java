package by.pirog;

import by.pirog.cli.ArgumentParser;
import by.pirog.cli.CliOptionsValidator;
import by.pirog.cli.CliOptions;
import by.pirog.exception.ApplicationException;
import by.pirog.exception.ArgumentParserException;
import by.pirog.exception.FileProcessingException;
import by.pirog.exception.OutputException;
import by.pirog.exception.ValidationException;

public class App {

    public static void main(String[] args) {
        int exitCode = 0;

        try {
            CliOptions options = parseAndValidateArguments(args);
            ApplicationContext context = new ApplicationContext(options);
            ApplicationRunner runner = new ApplicationRunner(context);

            runner.run();

            System.out.println("\n✓ Обработка успешно завершена");

        } catch (ArgumentParserException | ValidationException e) {
            System.err.println("Ошибка аргументов: " + e.getMessage());
            printUsageHint();
            exitCode = e.getExitCode();

        } catch (FileProcessingException e) {
            // Ошибки обработки файлов (возможен частичный успех)
            System.err.println("Ошибка обработки: " + e.getMessage());

            if (e.isPartialSuccess()) {
                System.err.println("  Частичные данные были успешно обработаны");
                System.err.println("  Статистика может быть неполной");
            }

            exitCode = e.getExitCode();

        } catch (OutputException e) {
            // Ошибки записи в файлы
            System.err.println("Ошибка записи: " + e.getMessage());
            exitCode = e.getExitCode();

        } catch (Exception e) {
            // Неожиданные ошибки
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace(System.err);
            exitCode = 99;
        }

        System.exit(exitCode);
    }

    private static CliOptions parseAndValidateArguments(String[] args)
            throws ValidationException, ArgumentParserException {
        CliOptions options = ArgumentParser.parse(args);
        CliOptionsValidator.validate(options);
        return options;
    }

    private static void printUsageHint() {
        System.err.println("\nИспользование:");
        System.err.println("  java -jar app.jar [опции] <входные_файлы>");
        System.err.println("\nОпции:");
        System.err.println("  -o <путь>      : директория для выходных файлов");
        System.err.println("  -p <префикс>   : префикс для выходных файлов");
        System.err.println("  -a             : режим добавления (append)");
        System.err.println("  -s             : краткая статистика");
        System.err.println("  -f             : полная статистика");
        System.err.println("  --async        : асинхронная обработка");
        System.err.println("  --json-stats   : вывод статистики в JSON");
        System.err.println("  --time         : показать время выполнения");
    }
}
