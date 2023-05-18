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
import java.util.Comparator;
import java.util.Optional;

/**
 * AddIfMax,add element in collection if it is bigger than the max element in collection
 * If collection is empty add element
 * default comparator compared by name and them by id
 */
@Slf4j
@LineParameter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AddIfMax implements OneArgument<Person> {
    @Serial
    private static final long serialVersionUID = 579988001L;
    private final static String SUCCESS = "Command AddIf: new element  added ";
    private final static String FAIL = "Command AddIf: new element didn't added ";
    @Setter
    transient
    private Comparator<Person> comparator = Comparator.comparing(Person::getPersonName).thenComparing(Person::getPersonHeight);

    private Person person;


    @Override
    public String description() {
        return "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String name() {
        return "add_if_max";
    }


    @Override
    public String execute(String s) {
        if (CollectionController.getInstance().isEmpty()) {
            CollectionController.getInstance().add(person);
            log.info("add_if_max executed successfully");

            return SUCCESS;
        }
        Optional<Person> other =
                CollectionController
                        .getInstance()
                        .personStream()
                        .parallel()
                        .max(comparator);
        if (other.isEmpty()) {
            CollectionController.getInstance().add(person);
            return SUCCESS;
        }
        if (comparator.compare(person, other.get()) > 0) {
            CollectionController.getInstance().add(person);
            log.info("add_if_max executed successfully");
            return SUCCESS;
        }
        log.info("add_if_max executed successfully");

        return FAIL;
    }


    @Override
    public Optional<Command> prepare(String object) {

        if (object != null) {
            throw new ValidationException("AddIfMax doesn't have argument");
        }
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
