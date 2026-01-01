package by.pirog;

import by.pirog.classifier.ClassificationResult;
import by.pirog.classifier.LineClassifier;
import by.pirog.output.DataType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineClassifierTest {

    private LineClassifier classifier = new LineClassifier();

    @ParameterizedTest
    @ValueSource(strings = {"123", "-42", "0"})
    void testInteger(String input) {
        var result = classifier.classifyWithValue(input);

        assertEquals(DataType.INTEGER, result.getType());
        assertEquals(Long.parseLong(input), result.getAsLong());
    }

    @ParameterizedTest
    @ValueSource(strings = { "3.14", "-0.001", "1.5E3" })
    void testFloat(String input) {
        var result = classifier.classifyWithValue(input);

        assertEquals(DataType.FLOAT, result.getType());
        assertEquals(Double.parseDouble(input), result.getAsDouble());
    }

    @ParameterizedTest
    @ValueSource(strings = { "hello", "world", "123abc", " ", "", "null"})
    void testString(String input) {
        var result = classifier.classifyWithValue(input);

        assertEquals(DataType.STRING, result.getType());
        assertEquals(input, result.getParsedValue());

    }



}
