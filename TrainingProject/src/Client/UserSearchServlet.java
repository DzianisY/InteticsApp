package Client;

import com.googlecode.objectify.cmd.Query;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserSearchServlet extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long idn=0;
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        String birthDate="",adress="",phone="",email="";

        factory().register(CreateUserServlet.Clients.class);

        Query<CreateUserServlet.Clients> q = ofy().load().type(CreateUserServlet.Clients.class).filter("name ==", name).filter("surname ==",surname);
        for (CreateUserServlet.Clients client: q) {
            name=client.name.toString();
            surname=client.surname.toString();
            birthDate=client.birthDate.toString();
            adress=client.adress.toString();
            phone=client.phone.toString();
            email=client.email.toString();
            idn=client.id;
        }

        JSONObject json = new JSONObject();
        try{
            json.put("name",name);
            json.put("surname",surname);
            json.put("birthDate",birthDate);
            json.put("adress",adress);
            json.put("phone",phone);
            json.put("email",email);
            json.put("message",idn);
        } catch(JSONException ex) {ex.printStackTrace();}

        PrintWriter out = response.getWriter();
        out.print(json);

    }

}
