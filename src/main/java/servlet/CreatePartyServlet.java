package servlet;

import org.json.JSONException;
import org.json.JSONObject;
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

@WebServlet("/createparty")
public class CreatePartyServlet extends HttpServlet {
    Party party = new Party();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //parametersLogger();

        System.out.println("In do Get");
        try {
            System.out.println(request.getParameter("TAG"));
            JSONObject jsonRequest = new JSONObject(request.getParameter("TAG"));//from url in app
            System.out.println(jsonRequest);
            party.setNameParty(jsonRequest.getString("name_party"));
            party.setDateStart(jsonRequest.getString("data_start"));
            party.setDateEnd(jsonRequest.getString("data_end"));
            party.generateCodeParty();

            System.out.println("PartyName: " + party.getNameParty());

            //send back to app
            HashMap<String, String> map;
            map = new HashMap<>();
            map.put("code_party", party.getCodeParty());

            JSONObject replyJSON = new JSONObject(map);
            response.setContentType("application/json");
            PrintWriter printW = response.getWriter();
            printW.println(replyJSON);
            party.createInDataBase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
