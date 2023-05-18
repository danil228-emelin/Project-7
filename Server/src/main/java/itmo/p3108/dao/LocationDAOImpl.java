package itmo.p3108.dao;

import itmo.p3108.model.Location;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
public class LocationDAOImpl implements DAO<Location> {
    @Override
    public void createTable(Connection connection) {
        String createTableLocation = "create  table IF NOT EXISTS location(\n" +
                " location_x double precision,\n" +
                "location_y double precision,\n" +
                "location_z double precision,\n" +
                "location_name varchar(63),\n" +
                "location_id serial primary key\n" +
                ");";
        try(Statement statement = connection.createStatement();) {
            statement.execute(createTableLocation);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<Integer> insert(Location element, Connection connection) {
        String saveLocation = "insert into location (location_x, location_y, location_z, location_name)\n" +
                "\n" +
                "values (?, ?, ?, ?)\n" +
                "returning location_id as answer;";

        try ( PreparedStatement location = connection.prepareStatement(saveLocation)){
            location.setDouble(1, element.getLocationX());
            location.setDouble(2, element.getLocationY());
            location.setDouble(3, element.getLocationZ());
            location.setString(4, element.getLocationName());
            ResultSet resultLocation = location.executeQuery();
            if (!resultLocation.next()) {
                log.error("location doesn't save,can't continue saving");
                return Optional.empty();
            }
           log.info("location saved in db");
            return Optional.of(resultLocation.getInt("answer"));

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        log.error("location doesn't save,can't continue saving");
        return Optional.empty();
    }

    @Override
    public Optional<List<Location>> getAllElements(Connection connection) {
        return Optional.empty();
    }

    @Override
    public String tableName() {
        return "location";
    }
}
