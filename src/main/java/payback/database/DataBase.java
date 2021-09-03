package payback.database;

import payback.Party;
import payback.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import static payback.Log.LOGGER;
public class DataBase {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public void connect() throws SQLException, URISyntaxException {
        LOGGER.info("Подключение к базе данных");
        URI dbUri = new URI("postgres://gyqdavjdmxqlmm:f356e0e1aa2bd94f9487778199133bc0de1efe1c5313b7700d4f4374b33e4100@ec2-54-154-101-45.eu-west-1.compute.amazonaws.com:5432/dbm0srl4egvboe");
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        connection = DriverManager.getConnection(dbUrl, username, password);
        statement = connection.createStatement();
    }

    private void checkTableParties() throws SQLException, URISyntaxException {
        connect();
        LOGGER.info("Проверка существования таблицы public.Parties");
        String createDatabase = "CREATE TABLE IF NOT EXISTS public.Parties" +
                "(" +
                "PartyID serial," +
                "NameParty text NOT NULL," +
                "CodeParty text," +
                "DateStartParty timestamp DEFAULT 0," +
                "DateEndParty timestamp DEFAULT 0," +
                "PRIMARY KEY (CodeParty)" +
                ")";
        statement.executeUpdate(createDatabase);
        disconnect();
    }

    public void createParty(String nameParty, String codeParty, String startTimeParty, String endTimeParty) throws SQLException, URISyntaxException {
        checkTableParties();
        connect();
        LOGGER.info(String.format("Создание мероприятия %s в базе данных", nameParty));
        String insertParty;
        if (startTimeParty == null ){
            insertParty = String.format("INSERT INTO public.Parties" +
                            " (NameParty, CodeParty) VALUES ('%s', '%s')", nameParty,
                    codeParty);
        }else {
            insertParty = String.format("INSERT INTO public.Parties" +
                            " (NameParty, CodeParty, DateStartParty, DateEndParty) VALUES ('%s', '%s', '%s', '%s')", nameParty,
                    codeParty, startTimeParty, endTimeParty);
        }
        statement.executeUpdate(insertParty);
        disconnect();
    }

    private void checkTableUsers() throws SQLException, URISyntaxException {
        connect();
        LOGGER.info("Проверка существования таблицы public.users");
        String createDatabase = "CREATE TABLE IF NOT EXISTS public.users" +
                "(" +
                "user_id serial," +
                "codeparty text references public.parties ON DELETE CASCADE," +
                "name text NOT NULL," +
                "bank text," +
                "phone text," +
                "alcohol boolean," +
                "PRIMARY KEY (user_id, codeparty)" +
                ")";
        statement.executeUpdate(createDatabase);
        disconnect();
    }

    public int createUser(User user) throws SQLException, URISyntaxException {
        int idUser;
        checkTableUsers();
        connect();
        LOGGER.info(String.format("Создание пользователя %s в базе данных", user.getName()));
        String insertUser = String.format("INSERT INTO public.users" +
                " (codeparty, name, bank, phone, alcohol) VALUES ('%s', '%s', '%s', '%s', %b) RETURNING user_id", user.getCodeParty(), user.getName(),
                user.getBank(), user.getPhone(), user.getAlcohol());
        PreparedStatement stmt = connection.prepareStatement(insertUser);
        stmt.execute();
        ResultSet last_updated_person = stmt.getResultSet();
        last_updated_person.next();
        idUser = last_updated_person.getInt(1);
        LOGGER.info(String.format("ID пользователя %s = %d", user.getName(), idUser));
        disconnect();
        return idUser;
    }

    public void disconnect() throws SQLException {
        LOGGER.info("Отключение от базы данных");
        statement.close();
        connection.close();
    }

    public void getParty(Party party) throws SQLException, URISyntaxException {
        connect();
        LOGGER.info("Взять из public.parties данные мероприятия ");
        resultSet = statement.executeQuery(String.format("SELECT nameparty, datestartparty, dateendparty FROM public.parties WHERE codeparty = '%s'", party.getCodeParty()));
        while (resultSet.next()){
            party.setNameParty(resultSet.getString("nameparty"));
            party.setDateStart(resultSet.getString("datestartparty"));
            party.setDateEnd(resultSet.getString("dateendparty"));
        }
        disconnect();
    }
}
