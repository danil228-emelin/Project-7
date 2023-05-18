package itmo.p3108.chain;

import itmo.p3108.command.type.Command;
import lombok.Data;

/**
 * Container which use Invoker for communicate with Handlers
 */
@Data
public class WrapperArgument {
    private Command command;
    private String[] argument;
}
