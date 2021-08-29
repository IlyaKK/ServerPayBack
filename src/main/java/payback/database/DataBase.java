package payback.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class DataBase {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public void connect() throws SQLException, URISyntaxException {
        URI dbUri = new URI("postgres://gyqdavjdmxqlmm:f356e0e1aa2bd94f9487778199133bc0de1efe1c5313b7700d4f4374b33e4100@ec2-54-154-101-45.eu-west-1.compute.amazonaws.com:5432/dbm0srl4egvboe");
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        connection = DriverManager.getConnection(dbUrl, username, password);
        statement = connection.createStatement();
    }

    public void checkTableParties() throws SQLException, URISyntaxException {
        connect();
        String createDatabase = "CREATE TABLE IF NOT EXISTS public.Parties" +
                "(" +
                "PartyID serial PRIMARY KEY," +
                "NameParty TEXT NOT NULL," +
                "CodeParty TEXT NOT NULL," +
                "DateStartParty TEXT NOT NULL," +
                "DateEndParty TEXT NOT NULL" +
                ")";
        statement.executeUpdate(createDatabase);
        disconnect();
    }

    public void createParty(String nameParty, String codeParty, String startTimeParty, String endTimeParty) throws SQLException, URISyntaxException {
        checkTableParties();
        connect();
        String insertParty = String.format("INSERT INTO public.Parties" +
                " (PartyID, NameParty, CodeParty, DateStartParty, DateEndParty) VALUES (%d, %s, %s, %s, %s)", 1, nameParty, codeParty, startTimeParty, endTimeParty);
        statement.executeUpdate(insertParty);
        disconnect();
    }

    public void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

    public boolean createUser(String codeParty, String nameUser) {
        return true;
    }
}
