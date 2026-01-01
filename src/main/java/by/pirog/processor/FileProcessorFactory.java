package by.pirog.processor;

import by.pirog.cli.CliOptions;

public class FileProcessorFactory {

    public static FileProcessor createFileProcessor(CliOptions options) {
        SingleFileProcess singleFileProcess = new SingleFileProcess();
        if (options.isAsync()) {
            AsyncFileProcessor asyncFileProcessor = new AsyncFileProcessor(singleFileProcess);
            if (options.getThreadCount() != null){
                asyncFileProcessor.setThreadCount(options.getThreadCount());
            }
            return asyncFileProcessor;
        } else {
            return new SyncFileProcessor(singleFileProcess);
        }
    }
}
