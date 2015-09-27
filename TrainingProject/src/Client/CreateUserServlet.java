package Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;


public class CreateUserServlet extends HttpServlet {

    @Entity
    public static class Clients {
        @Id Long id;
        @Index String name;
        @Index String surname;
        @Index String birthDate;
        @Index String adress;
        @Index String phone;
        @Index String email;

        private Clients() {}

        private Clients(String name, String surname, String birthDate, String adress, String phone, String email){
            this.name = name;
            this.surname=surname;
            this.birthDate=birthDate;
            this.adress=adress;
            this.phone=phone;
            this.email=email;
        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        factory().register(Clients.class);

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthDate = request.getParameter("birthDate");
        String adress = request.getParameter("adress");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        Clients testOne = new Clients(name,surname,birthDate,adress,phone,email);
        ofy().save().entity(testOne).now();

    }
}