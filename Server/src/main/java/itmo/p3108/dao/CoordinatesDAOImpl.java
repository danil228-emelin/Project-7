package itmo.p3108.dao;

import itmo.p3108.model.Coordinates;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CoordinatesDAOImpl implements DAO<Coordinates> {


    @Override
    public void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {

            String createTableCoordinates = "create  table IF NOT EXISTS coordinates(\n" +
                    " coordinate_x int,\n" +
                    "coordinate_y double precision,\n" +
                    "coordinates_id serial primary key\n" +
                    ");\n" +
                    "\n";
            statement.execute(createTableCoordinates);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<Integer> insert(Coordinates element, Connection connection) {
        String saveCoordinates = "insert into coordinates (coordinate_x, coordinate_y)\n" +
                "    values (?, ?)\n" +
                "     returning coordinates_id as answer;";

        try (PreparedStatement coordinates = connection.prepareStatement(saveCoordinates)) {

            coordinates.setInt(1, element.getCoordinatesX());
            coordinates.setDouble(2, element.getCoordinatesY());
            try (ResultSet resultCoordinates = coordinates.executeQuery();) {
                if (!resultCoordinates.next()) {
                    log.error("coordinates doesn't save,can't continue saving");
                    return Optional.empty();
                }
                log.info("coordinates  saved in db");
                return Optional.of(resultCoordinates.getInt("answer"));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Coordinates>> getAllElements(Connection connection) {
        return Optional.empty();
    }

    @Override
    public String tableName() {
        return "coordinates";
    }
}
