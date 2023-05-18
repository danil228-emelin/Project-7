package itmo.p3108.chain;

import itmo.p3108.command.FlyWeightCommandFactory;
import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.NoArgument;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
/**
 * Handler for NoArgument commands
 */
@Slf4j
public class HandlerNoArgument implements Handler {

    @Override
    public String processRequest(Command command, String token) {
        if (command instanceof NoArgument) {
            Optional<Command> OptionalServerCommand = FlyWeightCommandFactory.getInstance().getCommand(command.name());
            if (OptionalServerCommand.isPresent()) {
                NoArgument serverCommand = (NoArgument) OptionalServerCommand.get();
                log.info(String.format("Try to execute %s", command.name()));
                return (String) serverCommand.execute(token);
            } else {
                log.error(String.format("can't find %s", command.name()));
                return String.format("can't find %s", command.name());
            }
        }

        return String.format("can't find %s", command.name());
    }
}
