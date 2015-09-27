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


public class DeleteCarServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        factory().register(AddCarServlet.Cars.class);
        factory().register(AddOrderServlet.Orders.class);

        String number = request.getParameter("number");
        String result="";

        Long num=Long.parseLong(number);
        int ordersQuant = 0;

        Query<AddCarServlet.Cars> q = ofy().load().type(AddCarServlet.Cars.class).filter("keyPar == ",num);
        for (AddCarServlet.Cars car: q) {


            String link = car.getId().toString();

            Query<AddOrderServlet.Orders> r = ofy().load().type(AddOrderServlet.Orders.class).filter("linkPar == ",link);
            for (AddOrderServlet.Orders order: r) {ordersQuant++;}

            if (ordersQuant<1) {
                ofy().delete().entity(car);
                result = car.getLinkPar();
            }

        }

        JSONObject json = new JSONObject();
        try{
            json.put("message",result);
        } catch(JSONException ex) {ex.printStackTrace();}

        PrintWriter out = response.getWriter();
        out.print(json.toString());
    }
}
