package by.pirog;

import by.pirog.cli.CliOptions;
import by.pirog.output.AsyncFileOutputManager;
import by.pirog.output.FileOutputManager;
import by.pirog.output.OutputManager;
import by.pirog.statistics.NumberStatistics;
import by.pirog.statistics.ProcessingStatistics;
import by.pirog.statistics.StringStatistics;

public class ApplicationContext {
    private final CliOptions options;
    private final OutputManager outputManager;
    private final NumberStatistics numberStatistics;
    private final StringStatistics stringStatistics;
    private final ProcessingStatistics processingStatistics;

    public ApplicationContext(CliOptions options) {
        this.options = options;
        this.numberStatistics = new NumberStatistics();
        this.stringStatistics = new StringStatistics();
        this.processingStatistics = new ProcessingStatistics();
        this.outputManager = createOutputManager();
    }

    private OutputManager createOutputManager() {
        OutputManager baseManager = new FileOutputManager(options);
        return options.isAsync()
                ? new AsyncFileOutputManager(baseManager)
                : baseManager;
    }

    public CliOptions getOptions() {
        return options;
    }

    public OutputManager getOutputManager() {
        return outputManager;
    }

    public NumberStatistics getNumberStatistics() {
        return numberStatistics;
    }

    public StringStatistics getStringStatistics() {
        return stringStatistics;
    }

    public ProcessingStatistics getProcessingStatistics() {
        return processingStatistics;
    }
}

