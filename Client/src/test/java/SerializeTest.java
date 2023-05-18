import itmo.p3108.command.Show;
import itmo.p3108.command.type.Command;
import itmo.p3108.util.SerializeObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SerializeTest {


    @Test
    public void check_serialization() {
        Command command = new Show();

        Assertions
                .assertThat(SerializeObject.serialize(command, 1))
                .as("Serializer doesn't serialize command")
                .isTrue();

    }
}
