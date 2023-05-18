package itmo.p3108.command;

import itmo.p3108.LineParameter;
import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.exception.ValidationException;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.Optional;

/**
 * Command Add,add element in collection
 * <p>
 * User enters data,but  while script executing Add take arguments from script file
 * Next line is treated as arguments
 */
@Slf4j
@LineParameter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Add implements OneArgument<Person> {
    @Serial
    private static final long serialVersionUID = 589988001L;
    @Setter
    private Person person;


    @Override
    public String description() {
        return "add {element} : добавить новый элемент в коллекцию";
    }


    @Override
    public String name() {
        return "add";
    }


    @Override
    public String execute(String s) {
        CollectionController.getInstance().add(person);
        log.info("Add executed successfully");
        return String.format("object  with id %d added", person.getPersonId());

    }

    public Optional<Command> prepare(String argument) {

        if (argument != null) {
            throw new ValidationException("Add doesn't have argument");
        }
        person = CreatePerson.createPerson();
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
