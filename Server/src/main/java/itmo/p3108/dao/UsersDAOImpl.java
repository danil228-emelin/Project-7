package itmo.p3108.dao;

import itmo.p3108.util.Users;
import itmo.p3108.util.UsersStorage;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UsersDAOImpl implements DAO<String> {
    @Override
    public void createTable(Connection connection) {
        String create = "CREATE TABLE IF NOT EXISTS users\n" +
                "( user_id serial primary key,\n" +
                "    user_login varchar(63),\n" +
                "    user_password varchar(63),\n" +
                "    user_token text\n" +
                ")";
        try (var statement = connection.createStatement()) {
            statement.execute(create);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<Integer> insert(String element, Connection connection) {

        Optional<Users> optional =
                UsersStorage.get(element);
        if (optional.isEmpty()) {
            log.error(String.format("Can't find user in user storage with token %s", element));
            return Optional.empty();
        }
        log.info(String.format("Find %s in user storage", optional.get().getLogin()));
        Users user = optional.get();
        if (user.getIsSaved()) {
            log.info(String.format(" %s was already saved in db", optional.get().getLogin()));
            return searchElement(element, connection);
        }

        String insert = "insert into users (user_login, user_password, user_token)\n" +
                "values (?, ?, ?)\n" +
                "returning user_id as answer;";
        try (PreparedStatement location = connection.prepareStatement(insert)) {
            location.setString(1, user.getLogin());
            location.setString(2, user.getPassword());
            location.setString(3, user.getToken());
            ResultSet resultLocation = location.executeQuery();
            if (!resultLocation.next()) {
                log.error("user doesn't save,can't continue saving");
                return Optional.empty();
            }
            log.info(String.format("Download user in db  with login %s", user.getLogin()));
            user.setIsSaved(true);
            return Optional.of(resultLocation.getInt("answer"));

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<String>> getAllElements(Connection connection) {
        try (var statement = connection.createStatement();) {
            String takeAllElements = "select *\n" +
                    "from users;";
            ResultSet result = statement.executeQuery(takeAllElements);

            while (result.next()) {
                Users users = new Users();
                users.setPassword(result.getString("user_password"));
                users.setLogin(result.getString("user_login"));
                users.setToken(result.getString("user_token"));
                users.setIsSaved(true);
                UsersStorage.add(users.getToken(), users);
            }
            log.info(String.format("Users taken from db-%d", UsersStorage.length()));
            return Optional.empty();
        } catch (SQLException exception) {
            log.error("takeAllElementFromTable:" + exception);
            return Optional.empty();
        }

    }

    @Override
    public String tableName() {
        return "users";
    }

    private Optional<Integer> searchElement(String token, Connection connection) {
        String search = "select user_id from users \n" +
                "where user_token=?; ";
        try (PreparedStatement searchStatement = connection.prepareStatement(search);) {
            searchStatement.setString(1, token);
            ResultSet result = searchStatement.executeQuery();
            if (result.next()) {
                log.info("find owner in db");
                return Optional.of(result.getInt("user_id"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        log.error("search method failed to find user ib db");
        return Optional.empty();
    }

    private void deleteElements(Connection connection) {
        String delete = "delete\n" +
                "from users;";
        try (var statement = connection.createStatement()) {
            statement.execute(delete);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

}
