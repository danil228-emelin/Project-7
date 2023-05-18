import itmo.p3108.chain.NoArgumentHandler;
import itmo.p3108.chain.WrapperArgument;
import itmo.p3108.command.Info;
import itmo.p3108.command.type.Command;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class NoArgumentHandlerTest {
    NoArgumentHandler noArgumentHandler;
    WrapperArgument wrapperArgument;


    @Test
    public void check_execute_script() {
        noArgumentHandler = new NoArgumentHandler();

        wrapperArgument = new WrapperArgument();
        wrapperArgument.setCommand(new Info());
        wrapperArgument.setArgument(new String[]{"info"});
        Optional<Command> command = noArgumentHandler.processRequest(wrapperArgument);
        Assertions
                .assertThat(command)
                .as("NoArgumentHandler can't find command")
                .isPresent();
    }

}
