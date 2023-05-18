package itmo.p3108.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class FileWorker,class for reading and writing data from,to  the file
 */

public class FileWorker {
    private FileWorker() {
    }

    /**
     * @param path source fail
     * @return fail content
     * @throws IOException happen when  problems  with file occur
     */
    public static String read(String path) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path))) {
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedInputStream.available() > 0) {
                stringBuilder.append((char) bufferedInputStream.read());
            }
            return stringBuilder.toString();
        }
    }

}
