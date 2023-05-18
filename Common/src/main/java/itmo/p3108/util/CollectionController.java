package itmo.p3108.util;

import itmo.p3108.model.Person;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * CollectionController class.Contain Collection of elements.
 * Contain the root element of xml file,it needs for creating certain xml format(tree format)
 */
@Slf4j
public final class CollectionController {
    private static CollectionController controller = new CollectionController();
    private final LocalDate localDate;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private volatile ArrayList<Person> personList;

    public CollectionController() {
        this.personList = new ArrayList<>();
        this.localDate = LocalDate.now();
    }

    public static void setController(CollectionController controller) {
        CollectionController.controller = controller;
    }

    public static CollectionController getInstance() {

        return controller;
    }

    public void add(Person person) {
        lock.writeLock().lock();
        personList.add(person);
        lock.writeLock().unlock();
    }

    public Stream<Person> personStream() {
        return personList.stream();
    }

    public String info() {
        lock.readLock().lock();
        String result = "Тип:ArrayList\n" + "Дата инициализации:" +
                localDate + "\n"
                + "Количество элементов:" + personList.size();
        lock.readLock().unlock();
        return result;
    }

    public boolean isEmpty() {

        lock.readLock().lock();
        boolean res = personList.isEmpty();
        lock.readLock().unlock();
        return res;
    }


    public String removeById(Long id, String token) {
        lock.writeLock().lock();
        String result = "";
        ListIterator<Person> iterator = personList.listIterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getPersonId().equals(id) && person.getToken().equals(token)) {
                iterator.remove();
                log.info(String.format("element with id %d deleted", id));
                result = String.format("element with id %d deleted", id);
                break;
            }
        }
        if (result.equals("")) {
            result = String.format("element with id %d  wasn't deleted", id);
        }
        lock.writeLock().unlock();
        return result;
    }

    public boolean updatePerson(Person person, String token) {
        lock.writeLock().lock();
        boolean result = false;
        ListIterator<Person> iterator = personList.listIterator();
        while (iterator.hasNext()) {
            Person personFromList = iterator.next();
            if (personFromList.getPersonId().equals(person.getPersonId()) && personFromList.getToken().equals(token)) {
                iterator.set(person);
                log.info(String.format("element with id %d updated", person.getPersonId()));
                result = true;
                break;
            }
        }
        lock.writeLock().unlock();
        return result;
    }

    public boolean removeAll(Collection<Person> collection) {
        lock.writeLock().lock();
        boolean res = personList.removeAll(collection);
        lock.writeLock().unlock();
        return res;
    }

    public void sort(Comparator<Person> comparator) {
        lock.writeLock().lock();
        personList.sort(comparator);
        lock.writeLock().unlock();
    }

    public void forEach(Consumer<Person> consumer) {
        lock.writeLock().lock();
        personList.forEach(consumer);
        lock.writeLock().unlock();
    }

    public void addAll(Collection<Person> collection) {
        lock.writeLock().lock();
        personList.addAll(collection);
        lock.writeLock().unlock();
    }
}
