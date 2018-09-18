package xml;

import java.util.Map;
import java.util.Scanner;

public interface ReaderWriter {
    Map<String, Object> read(Scanner sc);

    void write();
}
