package Car;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddCarServlet extends HttpServlet {

    @Entity
    public static class Cars {
        @Id Long id;
        @Index String make;
        @Index String model;
        @Index String year;
        @Index String vin;
        @Index String linkPar;
        @Index Long keyPar;

        private Cars() {}

        private Cars(String make, String model, String year, String vin, String linkPar){
            this.make = make;
            this.model=model;
            this.year=year;
            this.vin=vin;
            this.linkPar=linkPar;
        }

        public String getMake(){return this.make;}
        public String getModel(){return this.model;}
        public String getYear(){return this.year;}
        public String getVIN(){return this.vin;}
        public Long getId(){return this.id;        }
        public String getLinkPar(){return this.linkPar;}

        public void setMake(String make){this.make=make;}
        public void setModel(String model){this.model=model;}
        public void setYear(String year){this.year=year;}
        public void setVin(String vin){this.vin=vin;}
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        factory().register(Cars.class);

        String make = request.getParameter("make");
        String model = request.getParameter("model");
        String year = request.getParameter("year");
        String vin = request.getParameter("vin");
        String linkPar = request.getParameter("linkPar");

        Cars testOne = new Cars(make,model,year,vin,linkPar);
        ofy().save().entity(testOne).now();
        testOne.keyPar=testOne.id;
        ofy().save().entity(testOne).now();
    }
}
