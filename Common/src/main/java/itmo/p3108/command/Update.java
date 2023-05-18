package itmo.p3108.command;

import itmo.p3108.PersonReadingBuilder;
import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.exception.ValidationException;
import itmo.p3108.model.Person;
import itmo.p3108.util.CheckData;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.Optional;

/**
 * Command update,update  exist element
 * has id of element  as parameter.
 * User has opportunity to stay previous state of attribute
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Update implements OneArgument<Person> {
    @Serial
    private static final long serialVersionUID = 547248024L;
    private Person person;


    @Override
    public String description() {
        return "update id:обновить элемент с заданным id";
    }

    @Override
    public String name() {
        return "update";
    }

    @Override
    public String execute(String token) {
        boolean updated = CollectionController.getInstance().updatePerson(person,token);
        if (!updated) {
            log.error(String.format("%s Person with %d wasn't updated", this.name(), person.getPersonId()));

            throw new ValidationException(String.format("Person with %d wasn't updated", person.getPersonId()));
        }
        log.info(String.format("%s executed successfully", this.name()));

        return String.format("Person with %d updated", person.getPersonId());

    }


    @Override
    public Optional<Command> prepare(@NonNull String argument) {
        boolean validation = new CheckData().checkPersonId(argument);
        if (!validation) {
            throw new ValidationException("argument isn't a positive number");
        }
        Long id = Long.valueOf(argument);
        person = CreatePerson.createPerson();
        person.setPersonId(id);
        PersonReadingBuilder.setId(PersonReadingBuilder.getId() - 1);
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
