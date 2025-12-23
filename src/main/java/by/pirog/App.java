package by.pirog;

import by.pirog.classifier.LineClassifier;
import by.pirog.cli.ArgumentParser;
import by.pirog.cli.CliException;
import by.pirog.cli.CliOptions;
import by.pirog.cli.CliOptionsValidator;
import by.pirog.output.FileOutputManager;
import by.pirog.output.OutputManager;
import by.pirog.processor.FileProcessor;
import by.pirog.statistics.NumberStatistics;
import by.pirog.statistics.StringStatistics;


public class App 
{
    public static void main( String[] args )
    {
        try{
            CliOptions options = ArgumentParser.parse(args);
            CliOptionsValidator.validate(options);

            OutputManager outputManager = new FileOutputManager(options);
            LineClassifier classifier = new LineClassifier();

            NumberStatistics numberStatistics = new NumberStatistics();
            StringStatistics stringStatistics = new StringStatistics();

            FileProcessor fileProcessor = new FileProcessor();
            fileProcessor.process(
                    options.getInputFiles(),
                    classifier,
                    outputManager,
                    numberStatistics,
                    stringStatistics
            );

            if (options.isShortStats() || options.isFullStats()) {
                boolean full =  options.isFullStats();
                numberStatistics.print(full);
                stringStatistics.print(full);
            }

            outputManager.close();
        } catch (CliException e){
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e){
            System.err.println("Неожиданная ошибка:" +  e.getMessage());
        }
    }
}
