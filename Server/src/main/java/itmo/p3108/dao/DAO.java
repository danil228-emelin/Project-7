package itmo.p3108.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 *
 * DAO-Each table has their representatives.
 * representatives are responsible for encapsulate work with db
 */
public interface DAO<T> {
    void createTable(Connection connection);

    Optional<Integer> insert(T element, Connection connection);

    Optional<List<T>> getAllElements(Connection connection);
    String tableName();
}
