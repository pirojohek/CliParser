package by.pirog;

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
        assertEquals(DataType.INTEGER, classifier.classify(input));
    }

    @ParameterizedTest
    @ValueSource(strings = { "3.14", "-0.001", "1.5E3" })
    void testFloat(String input) {
        assertEquals(DataType.FLOAT, classifier.classify(input));
    }

    @ParameterizedTest
    @ValueSource(strings = { "hello", "world", "123abc", " ", "", "null"})
    void testString(String input) {
        assertEquals(DataType.STRING, classifier.classify(input));
    }

}
