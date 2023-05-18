package itmo.p3108.dao;

import itmo.p3108.adapter.LocalDateAdapter;
import itmo.p3108.adapter.ZonedDateAdapter;
import itmo.p3108.model.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class PersonDAOImpl implements DAO<Person> {
    private DAO<Coordinates> coordinatesDAO = new CoordinatesDAOImpl();
    private DAO<Location> locationDAO = new LocationDAOImpl();

    private DAO<String> usersDAO = new UsersDAOImpl();

    @Override
    public void createTable(Connection connection) {
        String createTablePerson = "create table IF NOT EXISTS person\n" +
                "(\n" +
                "    person_id            serial primary key,\n" +
                "    person_name          varchar(63),\n" +
                "    coordinates          int references coordinates,\n" +
                "    person_creation_date varchar(63),\n" +
                "    person_height        real,\n" +
                "    person_birthday      varchar(63),\n" +
                "    person_eye_color     smallint,\n" +
                "    person_nationality   smallint,\n" +
                "    location             int references location\n" +
                ");\n" +
                "\n";
        try(Statement statement = connection.createStatement();) {
            statement.executeQuery(createTablePerson);
        } catch (SQLException e) {
            log.error("createTable:" + e.getMessage());
        }
    }

    @Override
    public Optional<Integer> insert(Person element, Connection connection) {

        String savePerson = "insert into person (person_id, person_name, coordinates, person_creation_date, person_height, person_birthday,\n" +
                "                    person_eye_color, person_nationality, location,user_id)\n" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?,?);";
        try {
            connection.setAutoCommit(false);
            Optional<Integer> locationId = locationDAO.insert(element.getLocation(), connection);
            Optional<Integer> coordinatesId = coordinatesDAO.insert(element.getCoordinates(), connection);
            Optional<Integer> user_id = usersDAO.insert(element.getToken(), connection);

            PreparedStatement persona = connection.prepareStatement(savePerson);
            persona.setLong(1, element.getPersonId());
            persona.setString(2, element.getPersonName());
            persona.setInt(3, coordinatesId.get());
            persona.setString(4, ZonedDateAdapter.getInstance().marshal(element.getPersonCreationDate()));
            persona.setDouble(5, element.getPersonHeight());
            persona.setString(6, LocalDateAdapter.getInstance().marshal(element.getPersonBirthday()));
            persona.setInt(7, Color.colorNumber(element.getPersonEyeColor()).get());
            persona.setInt(8, Country.countyNumber(element.getPersonNationality()).get());
            persona.setInt(9, locationId.get());
            persona.setInt(10, user_id.get());
            persona.execute();
            log.info(String.format("person with id %d saved", element.getPersonId()));
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | NoSuchElementException e) {
            log.error("insert:" + e.getMessage());
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    log.error(ex.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Person>> getAllElements(Connection connection) {
        List<Person> list = new ArrayList<>(100);
        try (var statement = connection.createStatement();){

            String takeAllElements = "select *\n" +
                    "from person\n" +
                    "         join coordinates c on person.coordinates = c.coordinates_id\n" +
                    "         join location l on l.location_id = person.location\n" +
                    "         join users u on person.user_id = u.user_id;";
            ResultSet result = statement.executeQuery(takeAllElements);

            Long maxId = 1L;

            while (result.next()) {

                Person person = Person
                        .builder()
                        .personId(result.getLong("person_id"))
                        .personHeight(result.getDouble("person_height"))
                        .personName(result.getString("person_name"))
                        .coordinates(Coordinates.builder()
                                .coordinatesX(result.getInt("coordinate_x"))
                                .coordinatesY(result.getFloat("coordinate_y"))
                                .build())
                        .personBirthday(LocalDateAdapter
                                .getInstance()
                                .unmarshal(result.getString("person_birthday")))
                        .personEyeColor(Color.newValue(Integer.toString(result.getInt("person_eye_color"))).get())
                        .personNationality(Country.newValue(Integer.toString(result.getInt("person_nationality"))).get())
                        .location(Location.builder()
                                .locationX(result.getDouble("location_x"))
                                .locationY(result.getFloat("location_y"))
                                .locationZ(result.getFloat("location_z"))
                                .locationName(result.getString("location_name"))
                                .build())
                        .personCreationDate(ZonedDateAdapter.getInstance().unmarshal(result.getString("person_creation_date")))
                        .token(result.getString("user_token"))
                        .build();
                list.add(person);
            }
            log.info("Elements taken from db");
            delete(connection);
            return Optional.of(list);
        } catch (SQLException exception) {
            log.error("takeAllElementFromTable:" + exception);
            return Optional.empty();
        }
    }

    @Override
    public String tableName() {
        return "person";
    }

    private void delete(Connection connection) {
        String delete = "delete from person";
        try {
            connection.createStatement().execute(delete);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
