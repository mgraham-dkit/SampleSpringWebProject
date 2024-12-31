package wanna_shop.persistence;

import lombok.extern.slf4j.Slf4j;
import wanna_shop.model.Order;
import wanna_shop.model.OrderLine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderDaoImpl extends MySQLDao implements OrderDao{
    public OrderDaoImpl(){
        super();
    }

    public OrderDaoImpl(Connection conn){
        super(conn);
    }

    public OrderDaoImpl(String propertiesFilename){
        super(propertiesFilename);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        Connection conn = super.getConnection();
        String sql = "SELECT * FROM orders";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Order o = mapRow(rs);
                    orders.add(o);
                }
            }
        }catch(SQLException ex){
            log.error("Exception occurred when retrieving all orders.", ex);
        }

        return orders;
    }

    private static Order mapRow(ResultSet rs) throws SQLException {
        Order o = Order.builder()
                .orderNumber(rs.getInt("orderNumber"))
                .username(rs.getString("username"))
                .orderDate(rs.getTimestamp("orderDate").toLocalDateTime()).build();
        return o;
    }

    @Override
    public List<Order> getOrdersForUser(String username) {
        if(username == null){
            throw new IllegalArgumentException("Username to be retrieved cannot be null");
        }

        List<Order> orders = new ArrayList<>();

        Connection conn = super.getConnection();
        String sql = "SELECT * FROM orders where username COLLATE utf8mb4_bin = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Order o = mapRow(rs);
                    orders.add(o);
                }
            }
        }catch(SQLException ex){
            log.error("Exception occurred when retrieving all orders for user: " + username, ex);
        }

        return orders;
    }

    public Order getOrderById(int orderNumber){
        if(orderNumber <= 0){
            return null;
        }

        Order order = null;

        Connection conn = super.getConnection();
        String sql = "SELECT * FROM orders where orderNumber = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, orderNumber);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    order = mapRow(rs);
                }
            }
        }catch(SQLException ex){
            log.error("Exception occurred when retrieving order matching ID #" + orderNumber, ex);
        }

        return order;
    }

    public static void main(String[] args) {
        OrderDao orderDao = new OrderDaoImpl("database.properties");
        List<Order> allOrders = orderDao.getAllOrders();
        System.out.println("All current orders: ");
        for(Order o: allOrders){
            System.out.println(o);
        }

        String user = "Helen2";
        List<Order> userOrders = orderDao.getOrdersForUser(user);
        if(!userOrders.isEmpty()) {
            System.out.println("All current orders for " + user + ":");
            for (Order o : userOrders) {
                System.out.println(o);
            }
        }else{
            System.out.println("No orders found for " + user + ".");
        }
    }
}
