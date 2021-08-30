package payback;

import payback.database.DataBase;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class Party {
    private String nameParty;
    private String dateStart;
    private String dateEnd;
    private String codeParty;

    public Party() {
    }

    public String getNameParty() {
        return nameParty;
    }

    public void setNameParty(String nameParty) {
        this.nameParty = nameParty;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }


    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCodeParty() {
        return codeParty;
    }

    public void setCodeParty(String codeParty) {
        this.codeParty = codeParty;
    }

    public void generateCodeParty() {
        codeParty = Long.toString(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE));
    }

    public void createInDataBase() throws SQLException, URISyntaxException {
        DataBase dataBase = new DataBase();
        dataBase.createParty(nameParty, codeParty, dateStart, dateEnd);
    }

    @Override
    public String toString() {
        return "Party{" +
                "nameParty='" + nameParty + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", codeParty='" + codeParty + '\'' +
                '}';
    }
}
