package itmo.p3108.command;

import itmo.p3108.PersonReadingBuilder;
import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.NoArgument;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.List;
import java.util.Optional;

/**
 * Command Clear,clear collection
 * don't save elements before cleaning
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Clear implements NoArgument<String> {
    @Serial
    private static final long serialVersionUID = 569988001L;
    private final static String SUCCESS = "Command Clear deleted elements ";

    /**
     * Command clear,clear collection  after execution size=0
     */
    @Override

    public String execute(String token) {


        List<Person> newList = CollectionController
                .getInstance()
                .personStream()
                .filter(x -> x.getToken().equals(token)).toList();
        CollectionController.getInstance().removeAll(newList);

        log.info("clear executed successfully");

        return SUCCESS;
    }

    @Override
    public String description() {
        return "clear : очистить коллекцию";
    }

    @Override
    public String name() {
        return "clear";
    }

    @Override
    public Optional<Command> prepare() {
        PersonReadingBuilder.setId(1L);
        return Optional.of(this);
    }
}
