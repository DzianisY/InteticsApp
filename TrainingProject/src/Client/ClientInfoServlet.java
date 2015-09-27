package Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import Car.AddCarServlet;
import com.googlecode.objectify.cmd.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClientInfoServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String number = request.getParameter("number");
        JSONArray jsonArr = new JSONArray();

        factory().register(AddCarServlet.Cars.class);

        Query<AddCarServlet.Cars> q = ofy().load().type(AddCarServlet.Cars.class).filter("linkPar ==", number);
        for (AddCarServlet.Cars car: q) {
            String make=car.getMake();
            String model=car.getModel();
            String year=car.getYear();
            String vin=car.getVIN();
            long id=car.getId();

            JSONObject json = new JSONObject();
            try{
                json.put("make",make);
                json.put("model",model);
                json.put("year",year);
                json.put("vin",vin);
                json.put("id",id);
            } catch(JSONException ex) {ex.printStackTrace();}

            jsonArr.put(json);

        }

        PrintWriter out = response.getWriter();
        out.print(jsonArr.toString());

    }
}
