package by.pirog.cli;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ArgumentParserTest {

    @Test
    void testOutputDirOption() throws CliException {
        String[] args = {"-o", "/tmp"};
        CliOptions cliOptions = ArgumentParser.parse(args);

        assertEquals(Paths.get("/tmp").toString(), cliOptions.getOutputDir().toString());

        assertFalse(cliOptions.isShortStats());
        assertFalse(cliOptions.isAppend());
        assertFalse(cliOptions.isAppend());
        assertEquals("", cliOptions.getPrefix());
    }


    @Test
    void testPrefixOption() throws CliException {
        String[] args = {"-p", "tmp-"};
        CliOptions cliOptions = ArgumentParser.parse(args);
        assertEquals("tmp-", cliOptions.getPrefix());

        assertFalse(cliOptions.isShortStats());
        assertFalse(cliOptions.isAppend());
        assertFalse(cliOptions.isAppend());
        assertEquals(".", cliOptions.getOutputDir().toString());
    }

    @Test
    void testMissingArgumentForO_throwsException() {
        Exception e = assertThrows(CliException.class, () -> {
            ArgumentParser.parse(new String[]{"-o"});
        });
    }

    @Test
    void testMissingArgumentForP_throwsException()  {
        Exception e = assertThrows(CliException.class, () -> {
            ArgumentParser.parse(new String[]{"-p"});
        });
    }

    @Test
    void unknownOption_throwsException(){
        Exception e = assertThrows(CliException.class, () -> {
            ArgumentParser.parse(new String[]{"-y", "-x"});
        });
    }

    @Test
    void inputFiles_addedInputFiles() throws CliException {
        String[] args = {"input.txt", "file.txt"};

        CliOptions cliOptions = ArgumentParser.parse(args);

        assertEquals(2, cliOptions.getInputFiles().size());
        assertEquals(Paths.get("input.txt"), cliOptions.getInputFiles().get(0));
        assertEquals(Paths.get("file.txt"), cliOptions.getInputFiles().get(1));
    }


    @Test
    void testAddToFileOption() throws CliException {
        String[] args = {"-a"};

        CliOptions options = ArgumentParser.parse(args);

        assertTrue(options.isAppend());
        assertFalse(options.isShortStats());
        assertFalse(options.isFullStats());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }


    @Test
    void testShortStateOption() throws CliException {
        String[] args = {"-s"};
        CliOptions options = ArgumentParser.parse(args);

        assertFalse(options.isAppend());
        assertTrue(options.isShortStats());
        assertFalse(options.isFullStats());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }

    @Test
    void testFullStateOption() throws CliException {
        String[] args = {"-f"};
        CliOptions options = ArgumentParser.parse(args);

        assertFalse(options.isAppend());
        assertFalse(options.isShortStats());
        assertTrue(options.isFullStats());
        assertEquals("", options.getPrefix());
        assertEquals(Paths.get("."), options.getOutputDir());
    }
}
