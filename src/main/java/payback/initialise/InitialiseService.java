package payback.initialise;

public interface InitialiseService {
    String getNamePartyByCodeParty(String codeParty);
    void createPartyInDatabase(String nameParty, String codeParty, String startTimeParty, String endTimeParty);
}
