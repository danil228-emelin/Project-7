package itmo.p3108.command;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.NoArgument;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.Optional;

/**
 * Command Info, put out major information of collection
 * Size,Collection Type,Initialization Date
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Info implements NoArgument<String> {
    @Serial
    private static final long serialVersionUID = 589988003L;
    @Override

    public String execute(String token) {
        log.info("info executed successfully");

        return CollectionController.getInstance().info();
    }
  
    @Override
    public String description() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
    @Override
    public Optional<Command> prepare() {
        return Optional.of(this);
    }

    @Override
    public String name() {
        return "info";
    }
}
