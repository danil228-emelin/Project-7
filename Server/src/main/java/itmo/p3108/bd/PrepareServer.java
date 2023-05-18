package itmo.p3108.bd;

import itmo.p3108.command.Save;
import itmo.p3108.dao.DAO;
import itmo.p3108.dao.DAOFactory;
import itmo.p3108.exception.ValidationException;
import itmo.p3108.model.Coordinates;
import itmo.p3108.model.Location;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import itmo.p3108.util.ShutDownThread;
import itmo.p3108.util.UserReader;
import itmo.p3108.util.Users;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Prepare server before work with clients
 */
@Slf4j

public class PrepareServer {
    private PsqlStorage psqlStorage;

    public PrepareServer() {
        psqlStorage = PsqlStorage.getInstance();
    }

    /**
     * download collection from db
     * download logins of authorized clients
     * Prepare shut down threads ,which execute when server terminated
     */
    public void prepare() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            Future<?> prepareCollection = executor.submit(this::prepareCollection);
            Future<?> prepareShutDownThreads = executor.submit(this::prepareShutDownThreads);
            Future<?> downloadUsers = executor.submit(this::downloadUsers);
            downloadUsers.get();
            prepareCollection.get();
            prepareShutDownThreads.get();
            log.info("Preparation finished");
        } catch (InterruptedException | ExecutionException exception) {
            log.error(exception.getMessage());
        } finally {
            executor.shutdown();
        }

    }

    /**
     * Return certain DAO from Dao factory
     */
    private <T> DAO<T> takeDao(String name) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        Optional<DAO<T>> dao = daoFactory.getDao(name);
        if (dao.isEmpty()) {
            log.error("can't find coordinates in daoFactory");
            throw new ValidationException("No element in daoFactory");
        }
        return dao.get();
    }
    /**
     * create all necessary tables in db
     */
    private void createTables() {
        DAO<Coordinates> coordinates = takeDao("coordinates");

        coordinates.createTable(psqlStorage.getConnection());

        DAO<Location> location = takeDao("location");

        location.createTable(psqlStorage.getConnection());

        DAO<Person> person = takeDao("person");

        person.createTable(psqlStorage.getConnection());


        DAO<Users> users = takeDao("users");

        users.createTable(psqlStorage.getConnection());

    }
    /**
     * download collection from db
     */


    private void downloadUsers() {
        DAO<Users> users = takeDao("users");
        users.getAllElements(psqlStorage.getConnection());
    }

    private void prepareCollection() {
        createTables();
        DAO<Person> personDAO = takeDao("person");
        Optional<List<Person>> list = personDAO.getAllElements(psqlStorage.getConnection());
        if (list.isEmpty()) {
            log.error("Person list is empty");
            return;
        }
        List<Person> personList = list.get();
        CollectionController controller = new CollectionController();

       controller.addAll(personList);
        CollectionController.setController(controller);
        log.info("Elements in collection-" + personList.size());
    }

    private void prepareShutDownThreads() {
        ShutDownThread.createAndAdd(() -> {
            log.info("Shut down thread started");
            Save save = new Save();
            save.execute(null);
        });

        Thread thread = new Thread(() -> {
            Save save = new Save();
            while (true) {
                String line = UserReader.readWithoutDollar();
                if (line.equalsIgnoreCase("save")) {
                    save.execute(null);
                }
            }
        }, "ThreadSave");
        thread.setDaemon(true);
        thread.start();

    }

}
