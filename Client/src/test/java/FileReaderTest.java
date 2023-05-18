import itmo.p3108.util.FileWorker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileReaderTest {


    @Test
    public void check_execute_script() throws IOException {
       String s= FileWorker.read("D:\\IntelliJ IDEA 2022.2.3\\Lab6\\Client\\src\\test\\resources\\test_script");
        Assertions.assertEquals(1, s.split("\n").length);
    }
}