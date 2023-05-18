package itmo.p3108.command;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Command FilterStartsWithName, print all elements,which name start with given argument.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class FilterStartsWithName implements OneArgument<String> {

    @Serial
    private static final long serialVersionUID = 589988002L;
    private String substring;

    /**
     * @return the result of command
     */


    @Override
    public String description() {
        return "filter_start_with_name name:выводит элементы чье имя начинается с name";
    }

    @Override
    public String name() {
        return "filter_starts_with_name";
    }

    /**
     * set the argument
     */

    @Override
    public String execute(String token) {
        return
                CollectionController.getInstance()
                        .personStream()
                        .filter(x -> x.getPersonName().startsWith(substring))
                        .parallel()
                        .map(Person::toString)
                        .collect(Collectors.joining("\n"));
    }

    @Override
    public Optional<Command> prepare(@NonNull String argument) {
        substring = argument;
        return Optional.of(this);
    }

    @Override
    public String getParameter() {
        return substring;
    }

    @Override
    public void setParameter(String parameter) {
        substring = parameter;
    }
}
