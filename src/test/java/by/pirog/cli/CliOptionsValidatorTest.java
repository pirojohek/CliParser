package by.pirog.cli;

import by.pirog.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CliOptionsValidatorTest {

    @Test
    void testValidate_validOptions_noException() throws ValidationException {
        CliOptions options = new CliOptions();
        options.setFullStats(true);
        options.setAsync(true);
        options.setThreadCount(4);
        options.getInputFiles().add(Paths.get("input1.txt"));

        CliOptionsValidator.validate(options);
    }


    @Test
    void testValidate_moreThenOnePrintOption_throwsException(){
        CliOptions options = new CliOptions();
        options.setFullStats(true);
        options.setShortStats(true);

        Exception ex = assertThrows(ValidationException.class, () -> {
            CliOptionsValidator.validate(options);
        });
    }

    @Test
    void testValidate_noPrintOption_throwsException(){
        CliOptions options = new CliOptions();

        Exception ex = assertThrows(ValidationException.class, () -> {
            CliOptionsValidator.validate(options);
        });
    }

    @Test
    void testValidate_inputFilesDoesNotExists_throwsException(){
        CliOptions options = new CliOptions();

        Exception ex = assertThrows(ValidationException.class, () -> {
            CliOptionsValidator.validate(options);
        });
    }

    @Test
    void testValidate_outputFilesIsEmpty_throwsException(){
        CliOptions options = new CliOptions();
        options.setShortStats(true);

        Exception ex = assertThrows(ValidationException.class, () -> {
            CliOptionsValidator.validate(options);
        });
    }

    @Test
    void testValidate_threadsOptionWithoutAsync_throwsException(){
        CliOptions options = new CliOptions();
        options.setShortStats(true);
        options.getInputFiles().add(Paths.get("input1.txt"));
        options.setThreadCount(4);

        Exception ex = assertThrows(ValidationException.class, () -> {
            CliOptionsValidator.validate(options);
        });
    }

    @Test
    void testValidate_negativeThreadCount_throwsException() {
        CliOptions options = new CliOptions();
        options.setShortStats(true);
        options.getInputFiles().add(Paths.get("input1.txt"));
        options.setAsync(true);
        options.setThreadCount(-2);
        Exception ex = assertThrows(ValidationException.class, () -> {
            CliOptionsValidator.validate(options);
        });
    }
}
