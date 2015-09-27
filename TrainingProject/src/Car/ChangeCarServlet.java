package Car;

import com.googlecode.objectify.cmd.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class ChangeCarServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        String year = request.getParameter("year");
        String vin = request.getParameter("vin");

        String number=request.getParameter("number");
        Long num= Long.parseLong(number);

        Query<AddCarServlet.Cars> q = ofy().load().type(AddCarServlet.Cars.class).filter("keyPar ==", num);
        for (AddCarServlet.Cars car: q) {
            car.setMake(make);
            car.setModel(model);
            car.setYear(year);
            car.setVin(vin);
            ofy().save().entity(car).now();
        }
    }
}


