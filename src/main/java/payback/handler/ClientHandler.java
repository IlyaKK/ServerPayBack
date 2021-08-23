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

    private static final String CREATE_CMD_PREFIX = "/create_party"; // + nameParty + codeParty + startTimeParty
    // + endTimeParty
    private static final String LOG_IN_CMD_PREFIX = "/log_in_party"; // + codeParty + name_user

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
                processCreateParty(message);
            }
        }
    }

    private void processCreateParty(String message) {
        String[] party = message.split("\\s+",5);
        String nameParty = party[1];
        String codeParty = party[2];
        String startTimeParty = party[3];
        String endTimeParty = party[4];

        InitialiseService initialiseService = serverApp.getInitialiseService();
        initialiseService.createPartyInDatabase(nameParty, codeParty, startTimeParty, endTimeParty);
    }
}
