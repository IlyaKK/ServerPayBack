package payback;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.*;

public class Log {
    public static final Logger LOGGER = Logger.getLogger("");
    public Log() {
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
}
