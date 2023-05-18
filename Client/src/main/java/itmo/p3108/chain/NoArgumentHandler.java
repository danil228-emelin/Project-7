package itmo.p3108.chain;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.NoArgument;
import itmo.p3108.exception.CommandException;
import itmo.p3108.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Process request for No argument commands
 */
@Slf4j
public class NoArgumentHandler implements Handler<Command> {

    @Override
    public Optional<Command> processRequest(WrapperArgument wrapperArgument) {
        Command command = wrapperArgument.getCommand();
        String[] commandLine = wrapperArgument.getArgument();
        if (command instanceof NoArgument<?> noArgument) {
            if (commandLine.length > 1) {
                log.error("Error during execution command " + command.name() + " doesn't have arguments");
                throw new CommandException("Error during execution command " + command.name() + " doesn't have arguments");
            }

            return noArgument.prepare();

        }
        throw new ValidationException("Command doesn't implement NoArgument and OneArgument interfaces");
    }
}