package servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import payback.Party;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;



@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/party_create"}
)
public class CreatePartyServlet extends HttpServlet{
    Party party;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        party = new Party();
        party.setNameParty(req.getParameter("name_party"));
        party.generateCodeParty();
        party.setDateStart(req.getParameter("data_start_party") + " " + req.getParameter("time_start_party"));
        party.setDateEnd(req.getParameter("data_end_party") + " " + req.getParameter("time_end_party"));

        resp.setContentType("application/json");
        HashMap<String, String> map;
        map = new HashMap<>();
        map.put("code_party",party.getCodeParty());
        JSONObject replyJSON = new JSONObject(map);
        PrintWriter printW = resp.getWriter();
        printW.println(replyJSON);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}

