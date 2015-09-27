package Car;

import Order.AddOrderServlet;
import com.googlecode.objectify.cmd.Query;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class GetCarServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String number = request.getParameter("number");
        Long num=Long.parseLong(number);
        JSONObject json = new JSONObject();
        factory().register(AddOrderServlet.Orders.class);

        Query<AddCarServlet.Cars> q = ofy().load().type(AddCarServlet.Cars.class).filter("keyPar ==", num);
        for (AddCarServlet.Cars car: q) {
            String make=car.getMake();
            String model=car.getModel();
            String year=car.getYear();
            String vin=car.getVIN();
            String backwards=car.getLinkPar();
            try{
                json.put("make",make);
                json.put("model",model);
                json.put("year",year);
                json.put("vin",vin);
                json.put("backwards",backwards);
            } catch(JSONException ex) {ex.printStackTrace();}
        }

        PrintWriter out = response.getWriter();
        out.print(json.toString());

    }
}
