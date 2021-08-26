package payback;

import payback.handler.ClientHandler;
import payback.initialise.BaseInitialiseService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.*;

public class ServerApp {
    private final ServerSocket serverSocket;
    public static final Logger LOGGER = Logger.getLogger("");
    private final BaseInitialiseService initialiseService;

    private void parametersLogger() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Handler handler;
            handler = new FileHandler("src/main/resources/logs/serverLog.log");
            LOGGER.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.getHandlers()[0].setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getLevel() + "\t" + record.getMessage() + "\t" + dateFormat.format(record.getMillis()) +
                        "\n";
            }
        });
        LOGGER.getHandlers()[1].setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getLevel() + "\t" + record.getMessage() + "\t" + dateFormat.format(record.getMillis()) +
                        "\n";
            }
        });
    }

    public ServerApp(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.initialiseService = new BaseInitialiseService();
    }

    public void start() throws UnknownHostException {
        parametersLogger();
        LOGGER.info("Сервер запущен!");
        LOGGER.info("Имя хоста: " + InetAddress.getLocalHost().getHostName());

        try {
            while (true) {
                waitAndProcessNewClientConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitAndProcessNewClientConnection() throws IOException {
        LOGGER.info("Ожидание пользователя...");
        Socket socket = serverSocket.accept();
        LOGGER.info("Клиент подключился");
        processClientConnection(socket);
    }

    private void processClientConnection(Socket socket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, socket);
        clientHandler.handle();
    }

    public BaseInitialiseService getInitialiseService() {
        return initialiseService;
    }
}
