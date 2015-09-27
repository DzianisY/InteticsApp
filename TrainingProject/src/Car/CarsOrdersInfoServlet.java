package Car;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.cmd.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CarsOrdersInfoServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String number = request.getParameter("number");
        JSONArray jsonArr = new JSONArray();

        factory().register(Order.AddOrderServlet.Orders.class);

        Query<Order.AddOrderServlet.Orders> q = ofy().load().type(Order.AddOrderServlet.Orders.class).filter("linkPar ==", number);
        for (Order.AddOrderServlet.Orders order: q) {
            String ord=order.getOrder();
            String amount=order.getAmount();
            String status=order.getStatus();
            long id=order.getId();

            JSONObject json = new JSONObject();
            try{
                json.put("order",ord);
                json.put("amount",amount);
                json.put("status",status);
                json.put("id",id);
            } catch(JSONException ex) {ex.printStackTrace();}
            jsonArr.put(json);
        }
        PrintWriter out = response.getWriter();
        out.print(jsonArr.toString());

    }
}
