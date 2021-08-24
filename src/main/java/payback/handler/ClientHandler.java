package payback.handler;

import payback.ServerApp;
import payback.initialise.InitialiseService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private final ServerApp serverApp;
    private final Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    private String nameParty;
    private String codeParty;

    private static final String CREATE_CMD_PREFIX = "/create_party"; // + nameParty + codeParty + startTimeParty
    // + endTimeParty
    private static final String LOG_IN_CMD_PREFIX = "/log_in_party"; // + codeParty + name_user
    private static final String CREATE_OK_CMD_PREFIX = "/create_party_ok";

    public ClientHandler(ServerApp serverApp, Socket socket) {
        this.serverApp = serverApp;
        this.clientSocket = socket;
    }

    public void handle() throws IOException {
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
        new Thread(() -> {
            try {
                authentication();
            } catch (IOException e) {
                e.getStackTrace();
            }
        });
    }

    private void authentication() throws IOException {
        while (true) {
            String message = in.readUTF();
            if(message.startsWith(CREATE_CMD_PREFIX)){
                boolean isCreatedParty = processCreateParty(message);
                if(isCreatedParty){
                    out.writeUTF(CREATE_OK_CMD_PREFIX);
                }
            }else if(message.startsWith(LOG_IN_CMD_PREFIX)){
                boolean isLogged = processRegistrationUser(message);
                if (isLogged){
                    break;
                }
            }
        }
    }

    private boolean processCreateParty(String message) {
        String[] party = message.split("\\s+",5);
        String nameParty = party[1];
        String codeParty = party[2];
        String startTimeParty = party[3];
        String endTimeParty = party[4];

        InitialiseService initialiseService = serverApp.getInitialiseService();
        return initialiseService.createPartyInDatabase(nameParty, codeParty, startTimeParty, endTimeParty);
    }

    private boolean processRegistrationUser(String message) {
        String[] personInfo = message.split("\\s+", 3);
        String codeParty = personInfo[1];
        String nameUser = personInfo[2];
        InitialiseService initialiseService = serverApp.getInitialiseService();
        return initialiseService.createUserByCodeParty(codeParty, nameUser);
    }
}
