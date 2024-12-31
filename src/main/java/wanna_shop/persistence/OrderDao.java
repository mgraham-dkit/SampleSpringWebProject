package wanna_shop.persistence;

import wanna_shop.model.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();
    List<Order> getOrdersForUser(String username);
    Order getOrderById(int orderNumber);
}
