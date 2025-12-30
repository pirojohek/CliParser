package by.pirog;

import by.pirog.cli.ArgumentParser;
import by.pirog.cli.CliException;
import by.pirog.cli.CliOptions;
import by.pirog.cli.CliOptionsValidator;

public class App {

    public static void main(String[] args) {
        try {
            CliOptions options = parseAndValidateArguments(args);
            ApplicationContext context = new ApplicationContext(options);
            ApplicationRunner runner = new ApplicationRunner(context);

            runner.run();

            System.exit(0);
        } catch (CliException e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static CliOptions parseAndValidateArguments(String[] args) throws CliException {
        CliOptions options = ArgumentParser.parse(args);
        CliOptionsValidator.validate(options);
        return options;
    }
}
