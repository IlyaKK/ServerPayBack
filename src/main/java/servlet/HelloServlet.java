package servlet;

import payback.database.DataBase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/hello"}
)
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        DataBase dataBase = new DataBase();
        try {
            dataBase.connect();
            dataBase.checkTableParties();
            dataBase.disconnect();
        } catch (SQLException | ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        out.write("hello heroku".getBytes());
        out.flush();
        out.close();
    }

}
