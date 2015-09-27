package Order;

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


public class AddOrderServlet extends HttpServlet {

    @Entity
    public static class Orders {
        @Id Long id;
        @Index String order;
        @Index String amount;
        @Index String status;
        @Index String linkPar;
        @Index Long keyPar;

        private Orders() {}

        private Orders(String order, String amount, String status, String linkPar){
            this.order = order;
            this.amount=amount;
            this.status=status;
            this.linkPar=linkPar;
        }

        public String getOrder(){
            return this.order;
        }
        public String getAmount(){
            return this.amount;
        }
        public String getStatus(){
            return this.status;
        }
        public Long getId(){
            return this.id;
        }
        public String getLinkPar(){return this.linkPar;}

        public void setOrder(String ord){
            this.order=ord;
        }
        public void setAmount(String amount){
            this.amount=amount;
        }
        public void setStatus(String status){
            this.status=status;
        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        factory().register(Orders.class);

        String order = request.getParameter("order");
        String amount = request.getParameter("amount");
        String status = request.getParameter("status");
        String linkPar = request.getParameter("linkPar");

        Orders testOne = new Orders(order,amount,status,linkPar);
        ofy().save().entity(testOne).now();
        testOne.keyPar=testOne.id;
        ofy().save().entity(testOne).now();
    }
}
