package itmo.p3108.command.type;

import lombok.NonNull;

import java.util.Optional;

public interface OneArgument<T> extends Command {
    String execute(String string);

    T getParameter();

    void setParameter(@NonNull T parameter);

    Optional<Command> prepare(String argument);
}
