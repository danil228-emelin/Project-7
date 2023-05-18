package itmo.p3108.chain;

import itmo.p3108.command.FlyWeightCommandFactory;
import itmo.p3108.command.LogIn;
import itmo.p3108.command.SignIn;
import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.util.Token;
import itmo.p3108.util.Users;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Handler for authorization
 */
@Slf4j
public class AuthorizeHandler implements Handler {
    private static final Handler HANDLER = new HandlerOneArgument();

    @Override
    public String processRequest(Command command,String t) {
        if (command instanceof SignIn || command instanceof LogIn) {
            OneArgument<Users> clientCommand = (OneArgument<Users>) command;
            Optional<Command> OptionalServerCommand = FlyWeightCommandFactory.getInstance().getCommand(command.name());
            if (OptionalServerCommand.isPresent()) {
                OneArgument<Users> serverCommand = (OneArgument<Users>) OptionalServerCommand.get();
                log.info(String.format("Try to register in system %s", clientCommand.getParameter().getLogin()));
                serverCommand.setParameter(clientCommand.getParameter());
                String token =
                        Token.encrypt(serverCommand.getParameter().getLogin());
                return serverCommand.execute(token);
            } else {
                log.error(String.format("can't find %s", command.name()));
                return String.format("can't find %s", command.name());
            }
        }
        return HANDLER.processRequest(command,t);
    }


}
