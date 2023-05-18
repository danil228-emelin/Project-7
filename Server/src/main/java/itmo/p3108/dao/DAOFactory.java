package itmo.p3108.dao;


import itmo.p3108.exception.ValidationException;
import itmo.p3108.util.Reflection;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/**
 * Class generated all DAO
 * and control that only one instance of each DAO was created
 */
@Slf4j
public class DAOFactory {
    private static final DAOFactory FLY_WEIGHT_DAO = new DAOFactory();
    private final HashMap<String, DAO<?>> DAO_MAP = new HashMap<>();

    /**
     * in constructor  reflections is used  to find all DAO in project
     */
    private DAOFactory() {
        Optional<Set<Class<?>>> set = Reflection.findAllClasses("itmo.p3108.dao", DAO.class);
        if (set.isEmpty()) {
            throw new ValidationException("Dao not found");
        }
        for (Class<?> dao : set.get()) {
            try {
                if (dao.isInterface()) {
                    continue;
                }
                Object object = dao.getConstructor().newInstance();
                if (object instanceof DAO<?> dao1) {
                    DAO_MAP.put(dao1.tableName(), dao1);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                     InstantiationException exception) {
                log.error(exception.getMessage());
            }
        }
    }

    public static DAOFactory getInstance() {
        return FLY_WEIGHT_DAO;
    }

    public boolean contains(String name) {
        return DAO_MAP.containsKey(name);
    }

    public <T> Optional<DAO<T>> getDao(String name) {
        return Optional.of((DAO<T>) DAO_MAP.get(name.toLowerCase().trim()));
    }

    /**
     * @return all commands which contains factory
     */
    public Collection<DAO<?>> getValues() {
        return DAO_MAP.values();
    }

}

