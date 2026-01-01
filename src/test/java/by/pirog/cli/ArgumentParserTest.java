package by.pirog.cli;

import by.pirog.exception.ArgumentParserException;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ArgumentParserTest {

    @Test
    void testOutputDirOption() throws ArgumentParserException {
        String[] args = {"-o", "/tmp"};
        CliOptions cliOptions = ArgumentParser.parse(args);

        assertEquals(Paths.get("/tmp").toString(), cliOptions.getOutputDir().toString());

        assertFalse(cliOptions.isShortStats());
        assertFalse(cliOptions.isAppend());
        assertFalse(cliOptions.isAppend());
        assertFalse(cliOptions.isAsync());
        assertFalse(cliOptions.isFullCustomStats());
        assertFalse(cliOptions.isTimeStatistics());
        assertEquals("", cliOptions.getPrefix());
    }


    @Test
    void testPrefixOption() throws ArgumentParserException {
        String[] args = {"-p", "tmp-"};
        CliOptions cliOptions = ArgumentParser.parse(args);
        assertEquals("tmp-", cliOptions.getPrefix());

        assertFalse(cliOptions.isShortStats());
        assertFalse(cliOptions.isAppend());
        assertFalse(cliOptions.isAppend());
        assertFalse(cliOptions.isAsync());
        assertFalse(cliOptions.isFullCustomStats());
        assertFalse(cliOptions.isTimeStatistics());
        assertEquals(".", cliOptions.getOutputDir().toString());
    }

    @Test
    void testMissingArgumentForO_throwsException() {
        Exception e = assertThrows(ArgumentParserException.class, () -> {
            ArgumentParser.parse(new String[]{"-o"});
        });
    }

    @Test
    void testMissingArgumentForP_throwsException()  {
        Exception e = assertThrows(ArgumentParserException.class, () -> {
            ArgumentParser.parse(new String[]{"-p"});
        });
    }

    @Test
    void unknownOption_throwsException(){
        Exception e = assertThrows(ArgumentParserException.class, () -> {
            ArgumentParser.parse(new String[]{"-y", "-x"});
        });
    }

    @Test
    void inputFiles_addedInputFiles() throws ArgumentParserException {
        String[] args = {"input.txt", "file.txt"};

        CliOptions cliOptions = ArgumentParser.parse(args);

        assertEquals(2, cliOptions.getInputFiles().size());
        assertEquals(Paths.get("input.txt"), cliOptions.getInputFiles().get(0));
        assertEquals(Paths.get("file.txt"), cliOptions.getInputFiles().get(1));
    }


    @Test
    void testAddToFileOption() throws ArgumentParserException {
        String[] args = {"-a"};

        CliOptions options = ArgumentParser.parse(args);

        assertTrue(options.isAppend());
        assertFalse(options.isShortStats());
        assertFalse(options.isFullStats());
        assertFalse(options.isAsync());
        assertFalse(options.isFullCustomStats());
        assertFalse(options.isTimeStatistics());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }


    @Test
    void testShortStateOption() throws ArgumentParserException {
        String[] args = {"-s"};
        CliOptions options = ArgumentParser.parse(args);

        assertFalse(options.isAppend());
        assertTrue(options.isShortStats());
        assertFalse(options.isFullStats());
        assertFalse(options.isAsync());
        assertFalse(options.isFullCustomStats());
        assertFalse(options.isTimeStatistics());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }

    @Test
    void testFullStateOption() throws ArgumentParserException {
        String[] args = {"-f"};
        CliOptions options = ArgumentParser.parse(args);

        assertFalse(options.isAppend());
        assertFalse(options.isShortStats());
        assertTrue(options.isFullStats());
        assertFalse(options.isAsync());
        assertFalse(options.isFullCustomStats());
        assertFalse(options.isTimeStatistics());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }

    @Test
    void testAsyncOption() throws ArgumentParserException {
        String[] args = {"--async"};
        CliOptions options = ArgumentParser.parse(args);

        assertFalse(options.isAppend());
        assertFalse(options.isShortStats());
        assertFalse(options.isFullStats());
        assertTrue(options.isAsync());
        assertFalse(options.isFullCustomStats());
        assertFalse(options.isTimeStatistics());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }

    @Test
    void testTimeStatisticsOption() throws ArgumentParserException {
        String[] args = {"--time"};
        CliOptions options = ArgumentParser.parse(args);

        assertFalse(options.isAppend());
        assertFalse(options.isShortStats());
        assertFalse(options.isFullStats());
        assertFalse(options.isAsync());
        assertFalse(options.isFullCustomStats());
        assertTrue(options.isTimeStatistics());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }

    @Test
    void testFullCustomStatsOption() throws ArgumentParserException {
        String[] args = {"--full"};
        CliOptions options = ArgumentParser.parse(args);

        assertFalse(options.isAppend());
        assertFalse(options.isShortStats());
        assertFalse(options.isFullStats());
        assertTrue(options.isFullCustomStats());
        assertFalse(options.isAsync());
        assertFalse(options.isTimeStatistics());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }

    @Test
    void testJsonStatsOption() throws ArgumentParserException {
        String[] args = {"--json-stats"};
        CliOptions options = ArgumentParser.parse(args);

        assertFalse(options.isAppend());
        assertFalse(options.isShortStats());
        assertFalse(options.isFullStats());
        assertFalse(options.isAsync());
        assertFalse(options.isFullCustomStats());
        assertFalse(options.isTimeStatistics());
        assertTrue(options.isJsonStats());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }

    @Test
    void testThreadsOption() throws ArgumentParserException {
        String[] args = {"--threads", "4"};
        CliOptions options = ArgumentParser.parse(args);

        assertEquals(4, options.getThreadCount());
        assertFalse(options.isAppend());
        assertFalse(options.isShortStats());
        assertFalse(options.isFullStats());
        assertFalse(options.isAsync());
        assertFalse(options.isFullCustomStats());
        assertFalse(options.isTimeStatistics());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }

    @Test
    void testMissingArgumentForThreads_throwsException() {
        Exception e = assertThrows(ArgumentParserException.class, () -> {
            ArgumentParser.parse(new String[]{"--threads"});
        });
    }

    @Test
    void testInvalidArgumentForThreads_throwsException() {
        Exception e = assertThrows(ArgumentParserException.class, () -> {
            ArgumentParser.parse(new String[]{"--threads", "abc"});
        });
    }
}
