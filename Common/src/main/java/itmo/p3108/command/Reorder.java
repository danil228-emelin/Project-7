package itmo.p3108.command;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.NoArgument;
import itmo.p3108.exception.ValidationException;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.Comparator;
import java.util.Optional;

/**
 * Command reorder,reorder collection in reverse order
 * provided with default comparator,compare by id
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Reorder implements NoArgument<String> {
    @Serial
    private static final long serialVersionUID = 547248001L;
    private final static String SUCCESS = "Command reorder:collection reordered ";
    @Setter
    @NonNull
    transient
    private Comparator<Person> naturalComparatorOrder = Comparator.comparing(Person::getPersonId);

    /**
     * if collection has been already reversed,
     * using natural order to return to initial order
     */

    @Override
    public String execute(String token) {
        if (CollectionController.getInstance().isEmpty()) {
            throw new ValidationException("Collection is empty");
        }
        naturalComparatorOrder = naturalComparatorOrder.reversed();
        CollectionController.getInstance().sort(naturalComparatorOrder);
        log.info(String.format("%s executed successfully", this.name()));

        return SUCCESS;
    }

    @Override
    public String description() {
        return "reorder:сортировать коллекцию в обратном порядке";
    }

    @Override
    public Optional<Command> prepare() {
        return Optional.of(this);
    }

    @Override
    public String name() {
        return "reorder";
    }
}
