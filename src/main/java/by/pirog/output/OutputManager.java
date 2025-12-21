package by.pirog.output;

import java.io.IOException;

public interface OutputManager extends AutoCloseable {

    void write(DataType type, String value) throws IOException;

    @Override
    void close() throws IOException;
}
