package wanna_shop.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wanna_shop.model.User;
import wanna_shop.model.Order;
import wanna_shop.model.OrderLine;
import wanna_shop.persistence.OrderDao;
import wanna_shop.persistence.OrderDaoImpl;
import wanna_shop.persistence.OrderLineDao;
import wanna_shop.persistence.OrderLineDaoImpl;

import java.util.List;

@Controller
public class OrderController {
    @GetMapping("/userOrders")
    public String getUserOrders(Model model, HttpSession session){
        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser == null){
            String message = "Please log in and try again.";
            model.addAttribute("message", message);
            return "index";
        }

        OrderDao orderDao = new OrderDaoImpl("database.properties");
        List<Order> orders = orderDao.getOrdersForUser(currentUser.getUsername());

        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/getOrderDetails")
    public String getOrderDetails(int orderNumber, Model model, HttpSession session){
        if(orderNumber <= 0){
            String error = "Illegal order number supplied.";
            model.addAttribute("errMsg", error);
            return "error";
        }

        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser == null){
            String message = "Please log in and try again.";
            model.addAttribute("message", message);
            return "index";
        }

        OrderLineDao orderLineDao = new OrderLineDaoImpl("database.properties");
        List<OrderLine> orderLines = orderLineDao.getOrderLinesForOrder(orderNumber);

        if(!currentUser.isAdmin()){
            OrderDao orderDao = new OrderDaoImpl("database.properties");
            Order order = orderDao.getOrderById(orderNumber);
            if(!order.getUsername().equalsIgnoreCase(currentUser.getUsername())){
                String message = "This order does not belong to you.";
                model.addAttribute("message", message);
                return "error";
            }
        }

        model.addAttribute("orderLines", orderLines);
        return "viewOrder";
    }
}
