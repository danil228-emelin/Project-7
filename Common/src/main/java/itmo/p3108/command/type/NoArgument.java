package itmo.p3108.command.type;

import java.util.Optional;

/**
 * interface-marker,implements by no argument command
 */
public interface NoArgument<T> extends Command {
     T execute(String string);
     Optional<Command> prepare();


}
