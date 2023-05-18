import itmo.p3108.util.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CryptoTest {

    @Test
    public void check_Token() {
        String test1 = Token.encrypt("Hello");
        String test2 = Token.encrypt("Hello");

        Assertions.assertEquals(test1, test2);

    }
}
