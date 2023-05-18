package itmo.p3108.command;

import itmo.p3108.bd.PsqlStorage;
import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.NoArgument;
import itmo.p3108.dao.DAO;
import itmo.p3108.dao.DAOFactory;
import itmo.p3108.dao.UsersDAOImpl;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import itmo.p3108.util.Users;
import itmo.p3108.util.UsersStorage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Command  save,save elements of collection in db
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Save implements NoArgument<String> {

    @Override
    public String execute(String string) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        try {
            log.info("try to save elements");
            PsqlStorage psqlStorage = PsqlStorage.getInstance();
            Optional<DAO<Person>> optionalDAO = DAOFactory.getInstance().getDao("person");
            if (optionalDAO.isEmpty()) {
                log.error("Can't find person dao in daoFactory");
                return "";
            }
            ForkJoinTask<?> forkJoinTask1 = forkJoinPool.submit(() -> CollectionController
                    .getInstance()
                    .personStream()
                    .parallel()
                    .forEach(x -> optionalDAO.get().insert(x, psqlStorage.getConnection())));
            ForkJoinTask<?> forkJoinTask2 = forkJoinPool.submit(() -> {
                UsersDAOImpl usersDAO = new UsersDAOImpl();
                for (Map.Entry<String, Users> entry : UsersStorage.elements()) {
                    usersDAO.insert(entry.getKey(), PsqlStorage.getInstance().getConnection());
                }
            });

                forkJoinTask1.get();
                forkJoinTask2.get();


            log.info("all elements loaded in dataBase");
            return "";
        } catch (InterruptedException | ExecutionException e) {
            log.error("Can't load elements in database");
            log.error(e.toString());
        } finally {
            forkJoinPool.shutdown();
        }

        return "";
    }

    @Override
    public Optional<Command> prepare() {
        return Optional.empty();
    }

    @Override
    public String description() {
        return "save : сохранить коллекцию в файл";
    }

    @Override
    public String name() {
        return "save";
    }
}
