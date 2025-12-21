package by.pirog.cli;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CliOptions {

    private boolean append;
    private boolean shortStats;
    private boolean fullStats;

    private Path outputDir = Paths.get(".");
    private String prefix = "";

    private final List<Path> inputFiles = new ArrayList<>();

    public boolean isAppend() {
        return append;
    }
    public void setAppend(boolean append) {
        this.append = append;
    }

    public boolean isShortStats() {
        return shortStats;
    }

    public void setShortStats(boolean shortStats) {
        this.shortStats = shortStats;
    }

    public boolean isFullStats() {
        return fullStats;
    }

    public void setFullStats(boolean fullStats) {
        this.fullStats = fullStats;
    }

    public Path getOutputDir() {
        return outputDir;
    }
    public void setOutputDir(Path outputDir) {
        this.outputDir = outputDir;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public List<Path> getInputFiles() {
        return inputFiles;
    }
}
