package servlet;

import org.json.JSONObject;
import payback.Log;
import payback.Party;
import payback.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;

import static payback.Log.LOGGER;

@WebServlet(
        name = "Servlet Create User",
        urlPatterns = {"/log_in_party"}
)
public class CreateUserServlet extends HttpServlet {
    private User user;
    private Party party;
    private final Log log = new Log();
    private HashMap<String, String> map = new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("Receive http req: " + req.getRequestURI());
        user = new User();
        party = new Party();
        JSONObject jsonRequest = new JSONObject(getBody(req));
        user.setName(jsonRequest.getString("name"));
        user.setPhone(jsonRequest.getString("phone"));
        user.setBank(jsonRequest.getString("bank"));
        user.setCodeParty(jsonRequest.getString("codeParty"));
        user.setAlcohol(jsonRequest.getBoolean("alcohol"));
        LOGGER.info("Создался объект User: " + user);
        try {
            user.setIdUser(String.valueOf(user.createInDataBase()));
            party.setCodeParty(user.getCodeParty());
            party.selectDataBase();
            map.put("id_user", user.getIdUser());
            map.put("name_party",party.getNameParty());
            map.put("data_start",party.getDateStart().split(" ", 2)[0]);
            map.put("data_end",party.getDateEnd().split(" ", 2)[0]);
            map.put("time_start",party.getDateStart().split(" ",2)[1]);
            map.put("time_end",party.getDateEnd().split(" ",2)[1]);
        } catch (URISyntaxException | SQLException e) {
            map.put("id_user", "error");
            LOGGER.warning("Ошибка создания пользователя в базе данных");
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

    public static String getBody(HttpServletRequest request)  {
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                stringBuilder.append(responseLine.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*String body;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            // throw ex;
            return "";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {

                }
            }
        }

        body = stringBuilder.toString();*/
        return stringBuilder.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
