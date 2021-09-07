package servlet;

import org.json.JSONObject;
import payback.Party;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;

import static payback.Log.LOGGER;


@WebServlet(
        name = "Servlet Create Party",
        urlPatterns = {"/party_create"}
)
public class CreatePartyServlet extends HttpServlet{
    private final HashMap<String, String> map = new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        JSONObject jsonRequest = new JSONObject(getBody(req));
        LOGGER.info("Receive http req: " + req.getRequestURI());
        Party party = new Party();
        party.setNameParty(jsonRequest.getString("nameParty"));
        party.generateCodeParty();
        party.setDateStart(jsonRequest.getString("timeStart") + " " + jsonRequest.getString("dateStart"));
        party.setDateEnd(jsonRequest.getString("timeEnd") + " " + jsonRequest.getString("dateEnd"));
        LOGGER.info("Создался объект Party: " + party);
        try {
            party.createInDataBase();
            map.put("code_party", party.getCodeParty());
        } catch (SQLException | URISyntaxException e) {
            map.put("code_party", "error");
            LOGGER.warning("Ошибка создания мероприятия в базе данных");
            e.printStackTrace();
        }
        resp.setContentType("application/json");
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

    public static String getBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                stringBuilder.append(responseLine.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }
}

