package wanna_shop.persistence;

import lombok.extern.slf4j.Slf4j;
import wanna_shop.model.OrderLine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderLineDaoImpl extends MySQLDao implements OrderLineDao{
    public OrderLineDaoImpl(){
        super();
    }

    public OrderLineDaoImpl(Connection conn){
        super(conn);
    }

    public OrderLineDaoImpl(String propertiesFilename){
        super(propertiesFilename);
    }

    @Override
    public List<OrderLine> getOrderLinesForOrder(int orderNumber) {
        if(orderNumber <= 0){
            return List.of();
        }

        List<OrderLine> orderlines = new ArrayList<>();

        Connection conn = super.getConnection();
        String sql = "SELECT * FROM orderlines WHERE ordernumber = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, orderNumber);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    OrderLine ol = mapRow(rs);
                    orderlines.add(ol);
                }
            }
        }catch(SQLException ex){
            log.error("Exception occurred when retrieving all order lines for order #" + orderNumber, ex);
        }

        return orderlines;
    }

    private static OrderLine mapRow(ResultSet rs) throws SQLException {
        return OrderLine.builder()
                .orderNumber(rs.getInt("orderNumber"))
                .productCode(rs.getString("productCode"))
                .quantityOrdered(rs.getInt("quantityOrdered"))
                .priceEach(rs.getDouble("priceEach"))
                .orderLineNumber(rs.getInt("orderLineNumber"))
                .build();
    }

    public static void main(String[] args) {
        OrderLineDao olDao = new OrderLineDaoImpl("database.properties");
        List<OrderLine> orderLines = olDao.getOrderLinesForOrder(2);
        for(OrderLine ol : orderLines){
            System.out.println(ol);
        }
    }
}
