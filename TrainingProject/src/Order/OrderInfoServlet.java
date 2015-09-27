package Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.cmd.Query;
import org.json.JSONException;
import org.json.JSONObject;


public class OrderInfoServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String number = request.getParameter("number");
        Long num=Long.parseLong(number);
        JSONObject json = new JSONObject();
        factory().register(AddOrderServlet.Orders.class);

        Query<AddOrderServlet.Orders> q = ofy().load().type(AddOrderServlet.Orders.class).filter("keyPar ==", num);
        for (AddOrderServlet.Orders order: q) {
            String ord=order.order;
            String amount=order.amount;
            String status=order.status;
            String backwards=order.linkPar;
            try{
                json.put("order",ord);
                json.put("amount",amount);
                json.put("status",status);
                json.put("backwards",backwards);
            } catch(JSONException ex) {ex.printStackTrace();}
        }

        PrintWriter out = response.getWriter();
        out.print(json.toString());

    }
}
