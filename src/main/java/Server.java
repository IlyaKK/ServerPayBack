import payback.ServerApp;

import java.io.IOException;

public class Server {
    private static final int DEFAULT_PORT = 8082;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }

        try {
            new ServerApp(port).start();
        } catch (IOException e) {
            System.out.println("Ошибка");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
