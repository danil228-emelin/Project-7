import itmo.p3108.PersonReadingBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateIdTest {
    @Test
    void test_create_id(){
        Long test1= PersonReadingBuilder.getInstance().createId();
        Long test2= PersonReadingBuilder.getInstance().createId();
        Assertions.assertNotEquals(test2,test1);

    }
}
