import itmo.p3108.chain.OneArgumentHandler;
import itmo.p3108.chain.WrapperArgument;
import itmo.p3108.command.CountByHeight;
import itmo.p3108.command.type.Command;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OneArgumentHandlerTest {
    OneArgumentHandler oneArgumentHandler;
    WrapperArgument wrapperArgument;


    @Test
    public void check_execute_script() {
        oneArgumentHandler = new OneArgumentHandler();
        wrapperArgument = new WrapperArgument();
        wrapperArgument.setCommand(new CountByHeight());
        wrapperArgument.setArgument(new String[]{"count_by_height", "1"});
        Optional<Command> command = oneArgumentHandler.processRequest(wrapperArgument);
        Assertions
                .assertThat(command)
                .as("NoArgumentHandler can't find command")
                .isPresent();
    }
}
