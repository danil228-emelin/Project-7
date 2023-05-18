package itmo.p3108.command;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import itmo.p3108.util.Users;
import itmo.p3108.util.UsersStorage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class WhoOwner implements OneArgument<Long> {

    private Long personId;


    @Override
    public String description() {
        return "tells who is owner of person with id";
    }

    @Override
    public String name() {
        return "who_owner";
    }

    @Override
    public String execute(String string) {
        Optional<Person> res =
                CollectionController
                        .getInstance()
                        .personStream()
                        .filter(x -> x.getPersonId().equals(personId) && x.getToken().equals(string))
                        .findFirst();

        if (res.isEmpty()) {
            return String.format("element with id %d doesn't exist", personId);
        }
        Optional<Users> users = UsersStorage.get(string);
        if (users.isEmpty()) {
            return String.format("can't find owner for  element with %d in UsersStorage", personId);
        }
        return String.format("%s is owner of element with %d", users.get().getLogin(), personId);


    }

    @Override
    public Long getParameter() {
        return personId;
    }

    @Override
    public void setParameter(@NonNull Long parameter) {
        personId = parameter;
    }

    @Override
    public Optional<Command> prepare(String argument) {
        try {
            personId = Long.parseLong(argument);
            return Optional.of(this);
        } catch (NumberFormatException exception) {
            log.error("argument must be digit");
            return Optional.empty();
        }
    }

}
