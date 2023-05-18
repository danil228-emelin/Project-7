package itmo.p3108.bd;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class PsqlStorage {


    private static final PsqlStorage psqlStorage = new PsqlStorage();
    private final String url = "jdbc:postgresql://localhost:5431/studs";
    private final String user = "s368149";
    private String password = "A4diwszNUMbJj7s9";

    @Getter
    private Connection connection;

    private PsqlStorage() {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            log.info("Connected to the PostgreSQL server successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e.toString());
            System.exit(-1);
        }
    }

    public static PsqlStorage getInstance() {
        return psqlStorage;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

}
