package by.pirog.cli;

import java.nio.file.Paths;

public class ArgumentParser {

    public static CliOptions parse(String[] args) throws CliException {
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
                case "-o":
                    i++;
                    if (i >= args.length) {
                        throw new CliException("Опция -о требует аргумент(путь)");
                    }
                    options.setOutputDir(Paths.get(args[i]));
                    i++;
                    break;
                case "-p":
                    i++;
                    if (i >= args.length) {
                        throw new CliException("Опция -p требует аргумент (префикс)");
                    }
                    options.setPrefix(args[i]);
                    i++;
                    break;
                case "--async":
                    options.setAsync(true);
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
                        throw new CliException("Неизвестная опция: "  + args[i]);
                    }
                    options.getInputFiles().add(Paths.get(args[i]));
                    i++;
                    break;
            }
        }

        return options;
    }
}
