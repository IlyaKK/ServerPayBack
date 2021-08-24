package payback.initialise;

public interface InitialiseService {
    String getNamePartyByCodeParty(String codeParty);
    boolean createPartyInDatabase(String nameParty, String codeParty, String startTimeParty, String endTimeParty);

    boolean createUserByCodeParty(String codeParty, String nameUser);
}
