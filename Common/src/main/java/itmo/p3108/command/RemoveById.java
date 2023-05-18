package itmo.p3108.command;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.exception.ValidationException;
import itmo.p3108.util.CheckData;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.Optional;

/**
 * Command RemoveByID,remove element with certain id,
 * has int number as parameter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RemoveById implements OneArgument<Long> {
    @Serial
    private static final long serialVersionUID = 547968001L;
    private Long id;

    @Override
    public String description() {
        return "remove_by_id id:удалить из коллекции элемент с заданным id";
    }

    @Override
    public String name() {
        return "remove_by_id";
    }


    @Override
    public String execute(String token) {
        if (CollectionController.getInstance().isEmpty()) {
            throw new ValidationException("Collection is empty");
        }
        if (id <= 0) {
            log.error(" RemoveById error id<=0");
            throw new ValidationException("RemoveById error id<=0");
        }

        return CollectionController
                .getInstance()
                .removeById(id, token);


    }

    @Override
    public Optional<Command> prepare(@NonNull String id) {
        boolean validation = new CheckData().checkPersonId(id);
        if (!validation) {
            throw new ValidationException("id isn't positive number");
        }
        this.id = Long.valueOf(id);
        return Optional.of(this);
    }

    @Override
    public Long getParameter() {
        return id;
    }

    @Override
    public void setParameter(Long parameter) {
        id = parameter;
    }
}
