import itmo.p3108.command.type.Command;
import itmo.p3108.util.Invoker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class InvokerTest {


    @Test
    public void check_execute_script() {
        Invoker invoker = Invoker.getInstance();
        Optional<Command> command = invoker.invoke("info");
        Assertions
                .assertThat(command)
                .as("invoker doesn't find command")
                .isPresent();

    }
}
