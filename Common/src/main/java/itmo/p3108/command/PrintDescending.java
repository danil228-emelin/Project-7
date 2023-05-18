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
import java.util.stream.Collectors;

/**
 * Command Print Descending, print in descending order
 * provided with default comparator,compare by id
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PrintDescending implements NoArgument<String> {
    @Serial
    private static final long serialVersionUID = 547998001L;
    @Setter
    @NonNull
    transient
    private Comparator<Person> naturalComparatorOrder = (Comparator.comparing(Person::getPersonId));
  
    @Override
    public String execute(String token) {
        if (CollectionController.getInstance().isEmpty()) {
            throw new ValidationException("Collection is empty");
        }
               log.info("print_descending executed successfully");

        naturalComparatorOrder = naturalComparatorOrder.reversed();
        return CollectionController.getInstance()
                .personStream()
                .sorted(naturalComparatorOrder)
                .parallel()
                .map(Person::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String description() {
        return "print_descending : вывести элементы коллекции в порядке убывания";
    }
    @Override
    public Optional<Command> prepare() {
        return Optional.of(this);
    }
    @Override
    public String name() {
        return "print_descending";
    }
}
