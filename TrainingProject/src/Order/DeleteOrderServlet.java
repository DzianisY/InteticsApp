package Order;

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

public class DeleteOrderServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        factory().register(AddOrderServlet.Orders.class);

        String number = request.getParameter("number");
        String result="";

        Long num=Long.parseLong(number);

        Query<AddOrderServlet.Orders> q = ofy().load().type(AddOrderServlet.Orders.class).filter("keyPar == ",num);
        for (AddOrderServlet.Orders order: q) {
            result=order.getLinkPar();
            ofy().delete().entity(order);
        }

        JSONObject json = new JSONObject();
        try{
            json.put("message",result);
        } catch(JSONException ex) {ex.printStackTrace();}

        PrintWriter out = response.getWriter();
        out.print(json.toString());
    }
}
