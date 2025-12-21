package by.pirog.cli;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CliOptionsValidatorTest {


    @Test
    public void testValidate_OptionFAndO_throwsException(){
        CliOptions options = new CliOptions();
        options.setFullStats(true);
        options.setShortStats(true);

        Exception ex = assertThrows(CliException.class, () -> {
            CliOptionsValidator.validate(options);
        });
        assertEquals("Нельзя использовать -f и -s одновременно",  ex.getMessage());
    }

    @Test
    public void testValidate_inputFilesDoesNotExists_throwsException(){
        CliOptions options = new CliOptions();

        Exception ex = assertThrows(CliException.class, () -> {
            CliOptionsValidator.validate(options);
        });
        assertEquals("Не указаны входные файлы", ex.getMessage());
    }

    @Test
    public void testValidate_validTest() throws CliException {
        CliOptions options = new CliOptions();
        options.setFullStats(true);
        options.getInputFiles().add(Paths.get("in1.txt"));

        CliOptionsValidator.validate(options);
    }
}
