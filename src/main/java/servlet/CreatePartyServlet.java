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
public class CreatePartyServlet extends HttpServlet {
    Party party;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String request = req.getParameter("TAG");
        JSONObject json = new JSONObject(request);
        party = new Party();
        party.setNameParty((String)json.getJSONArray("name_party").get(0));
        party.generateCodeParty();
        JSONArray jsonDataStart = json.getJSONArray("data_start");
        party.setDateStart(jsonDataStart.get(0) + " " + jsonDataStart.get(1));
        JSONArray jsonDataEnd = json.getJSONArray("data_end");
        party.setDateEnd(jsonDataEnd.get(0) + " " + jsonDataEnd.get(1));

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

