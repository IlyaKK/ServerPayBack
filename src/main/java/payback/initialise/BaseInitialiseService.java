package payback.initialise;

import payback.database.DataBase;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class BaseInitialiseService implements InitialiseService{
    @Override
    public String getNamePartyByCodeParty(String codeParty) {
        return null;
    }

    @Override
    public boolean createPartyInDatabase(String nameParty, String codeParty, String startTimeParty, String endTimeParty) {
        DataBase dataBase = new DataBase();
        try{
            dataBase.connect();
            if(dataBase.createParty(nameParty, codeParty, startTimeParty, endTimeParty))
                try {
                    dataBase.disconnect();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
        } catch (SQLException | ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createUserByCodeParty(String codeParty, String nameUser) {
        DataBase dataBase = new DataBase();
        try {
            dataBase.connect();
        } catch (SQLException | ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        if(dataBase.createUser(codeParty, nameUser)) {
            try {
                dataBase.disconnect();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
