package servlet;

import org.json.JSONObject;
import payback.Log;
import payback.Party;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import static payback.Log.LOGGER;


@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/party_create"}
)
public class CreatePartyServlet extends HttpServlet{
    private Party party;
    private final Log log = new Log();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("Receive http req: " + req.getPathInfo());
        party = new Party();
        party.setNameParty(req.getParameter("name_party"));
        party.generateCodeParty();
        party.setDateStart(req.getParameter("data_start_party") + " " + req.getParameter("time_start_party"));
        party.setDateEnd(req.getParameter("data_end_party") + " " + req.getParameter("time_end_party"));
        LOGGER.info("Создался объект Party: " + party);
        try {
            party.createInDataBase();
        } catch (SQLException | URISyntaxException e) {
            LOGGER.warning("Ошибка создания мероприятия в базе данных");
            e.printStackTrace();
        }

        resp.setContentType("application/json");
        HashMap<String, String> map;
        map = new HashMap<>();
        map.put("code_party",party.getCodeParty());
        JSONObject replyJSON = new JSONObject(map);
        PrintWriter printW;
        try {
            printW = resp.getWriter();
            printW.println(replyJSON);
        } catch (IOException e) {
            LOGGER.warning("Ошибка при ответе на запрос POST: " + replyJSON);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }
}

