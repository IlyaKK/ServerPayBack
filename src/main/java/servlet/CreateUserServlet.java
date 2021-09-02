package servlet;

import org.json.JSONObject;
import payback.Log;
import payback.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static payback.Log.LOGGER;

@WebServlet(
        name = "Servlet Create User",
        urlPatterns = {"/log_in_party"}
)
public class CreateUserServlet extends HttpServlet {
    private User user;
    private boolean resultAuthenticate;
    private final Log log = new Log();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Receive http req: " + req.getRequestURI());
        user = new User();
        JSONObject jsonRequest = new JSONObject(req.getParameter("user"));
        user.setName(jsonRequest.getString("name"));
        user.setPhone(jsonRequest.getString("phone"));
        user.setBank(jsonRequest.getString("bank"));
        user.setCodeParty(jsonRequest.getString("codeParty"));
        user.setAlcohol(jsonRequest.getBoolean("alcohol"));
        LOGGER.info("Создался объект User: " + user);
        try {
            resultAuthenticate = user.createInDataBase();
        } catch (SQLException | URISyntaxException e) {
            LOGGER.warning("Ошибка создания пользователя в базе данных");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
