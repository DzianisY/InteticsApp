package Order;

import com.googlecode.objectify.cmd.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ChangeOrderServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter("number");
        String ord = request.getParameter("order");
        String amount = request.getParameter("amount");
        String status = request.getParameter("status");

        log(status);

        Long num= Long.parseLong(number);

        Query<AddOrderServlet.Orders> q = ofy().load().type(AddOrderServlet.Orders.class).filter("keyPar ==", num);
        for (AddOrderServlet.Orders order: q) {
            order.setAmount(amount);
            order.setOrder(ord);
            order.setStatus(status);
            ofy().save().entity(order).now();
        }
    }
}

