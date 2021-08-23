package payback.initialise;

public interface InitialiseService {
    String getNamePartyByCodeParty(String codeParty);
    void createParty(String nameParty, String startTimeParty, String endTimeParty);
}
