package payback.database;

import java.sql.*;

public class DataBase {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public void connect() throws SQLException {
        String userName = "gyqdavjdmxqlmm";
        String password = "f356e0e1aa2bd94f9487778199133bc0de1efe1c5313b7700d4f4374b33e4100";
        String dbURL = "jdbc:postgresql://" + "ec2-54-154-101-45.eu-west-1.compute.amazonaws.com:5432" +
                "/dbm0srl4egvboe?sslmode=require";
        connection = DriverManager.getConnection(dbURL, userName, password);
        statement = connection.createStatement();
    }
}
