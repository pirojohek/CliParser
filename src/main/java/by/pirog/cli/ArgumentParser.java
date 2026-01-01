package by.pirog.cli;

import by.pirog.exception.ArgumentParserException;

import java.nio.file.Paths;

public class ArgumentParser {

    public static CliOptions parse(String[] args) throws ArgumentParserException {
        CliOptions options = new CliOptions();

        int i = 0;
        while (i < args.length) {
            switch (args[i]) {
                case "-a":
                    options.setAppend(true);
                    i++;
                    break;
                case "-s":
                    options.setShortStats(true);
                    i++;
                    break;
                case "-f":
                    options.setFullStats(true);
                    i++;
                    break;
                case "--full":
                    options.setFullCustomStats(true);
                    i++;
                    break;
                case "-o":
                    i++;
                    if (i >= args.length) {
                        throw new ArgumentParserException("Опция -о требует аргумент(путь)");
                    }
                    options.setOutputDir(Paths.get(args[i]));
                    i++;
                    break;
                case "-p":
                    i++;
                    if (i >= args.length) {
                        throw new ArgumentParserException("Опция -p требует аргумент (префикс)");
                    }
                    options.setPrefix(args[i]);
                    i++;
                    break;
                case "--async":
                    options.setAsync(true);
                    i++;
                    break;
                case "--threads":
                    i++;
                    if (i >= args.length) {
                        throw new ArgumentParserException("Опция --threads требует аргумент (число потоков)");
                    }
                    try {
                        options.setThreadCount(Integer.parseInt(args[i]));
                    } catch (NumberFormatException e) {
                        throw new ArgumentParserException("Аргумент для опции --threads должен быть числом");
                    }

                    i++;
                    break;
                case "--time":
                    options.setTimeStatistics(true);
                    i++;
                    break;
                case "--json-stats":
                    options.setJsonStats(true);
                    i++;
                    break;
                default:
                    if (args[i].startsWith("-")) {
                        throw new ArgumentParserException("Неизвестная опция: "  + args[i]);
                    }
                    options.getInputFiles().add(Paths.get(args[i]));
                    i++;
                    break;
            }
        }

        return options;
    }
}
