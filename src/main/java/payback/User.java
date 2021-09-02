package payback;

import payback.database.DataBase;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class User {
    private String name;
    private String phone;
    private String bank;
    private String codeParty;
    private Boolean alcohol;
    private String idUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCodeParty() {
        return codeParty;
    }

    public void setCodeParty(String codeParty) {
        this.codeParty = codeParty;
    }

    public Boolean getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Boolean alcohol) {
        this.alcohol = alcohol;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", bank='" + bank + '\'' +
                ", codeParty='" + codeParty + '\'' +
                ", alcohol=" + alcohol +
                '}';
    }

    public int createInDataBase() throws URISyntaxException, SQLException {
        DataBase dataBase = new DataBase();
        return dataBase.createUser(this);
    }
}
