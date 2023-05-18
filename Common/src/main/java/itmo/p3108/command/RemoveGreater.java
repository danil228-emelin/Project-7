package itmo.p3108.command;

import itmo.p3108.LineParameter;
import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.exception.ValidationException;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

/**
 * Command Remove Greater,
 * remove elements witch greater than created element
 * after reading command,user creates new element.
 * if new element is greater than elements in collections will be deleted
 * provided with default comparator,compare by name and then birthday
 */
@Slf4j
@LineParameter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RemoveGreater implements OneArgument<Person> {
    @Serial
    private static final long serialVersionUID = 547268001L;
    private final static String SUCCESS = "command RemoveGreater deleted all suitable elements ";
    private final static String FAIL = "command RemoveGreater deleted nothing ";
    @Setter
    @NonNull
    transient
    private Comparator<Person> comparator = Comparator.comparing(Person::getPersonName).thenComparing(Person::getPersonBirthday);
    private Person person;

    @Override
    public String description() {
        return "remove_greater {element} : удалить из коллекции все элементы, больше, чем заданный";

    }

    @Override
    public String name() {
        return "remove_greater";
    }

    @Override
    public String execute(String token) {
        if (CollectionController.getInstance().isEmpty()) {
            throw new ValidationException("Collection is empty");
        }
        Collection<Person> collection = CollectionController
                .getInstance()
                .personStream()
                .filter(x -> comparator.compare(x, person) > 0 && x.getToken().equals(token)).toList();

        if (CollectionController.getInstance().removeAll(collection)) {
            log.info(String.format("%s executed successfully", this.name()));
            return SUCCESS;
        }
        log.info(String.format("%s executed unsuccessfully", this.name()));

        return FAIL;
    }

    @Override
    public Optional<Command> prepare(String object) {
        this.person = CreatePerson.createPerson();
        return Optional.of(this);
    }

    @Override
    public Person getParameter() {
        return person;
    }

    @Override
    public void setParameter(Person parameter) {
        person = parameter;
    }
}
