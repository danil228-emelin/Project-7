package itmo.p3108.chain;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.exception.CommandException;
import itmo.p3108.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
/**
 * Process request for One Argument command
 */
@Slf4j
public class OneArgumentHandler implements Handler<Command> {
    private static final Handler<Command> HANDLER = new NoArgumentHandler();

    @Override
    public Optional<Command> processRequest(WrapperArgument wrapperArgument) {
        Command command = wrapperArgument.getCommand();
        String[] commandLine = wrapperArgument.getArgument();
        if (command instanceof OneArgument<?> oneArgument) {
            if (commandLine.length > 2) {
                log.error("Error during execution command " + command.name() + " has one argument ");
                throw new CommandException("Error during execution command " + command.name() + " has one argument ");
            }
            if (commandLine.length == 1) {
                try {
                    return oneArgument.prepare(null);
                } catch (NullPointerException exception) {
                    throw new ValidationException(String.format("%s must have one argument", command.name()));
                }
            }
            return oneArgument.prepare(commandLine[1]);

        }
        return HANDLER.processRequest(wrapperArgument);
    }
}


