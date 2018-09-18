package xml;

import java.util.Map;

public interface ReaderWriter {
    Map<String, Object> read(String filename);

    void write();
}
