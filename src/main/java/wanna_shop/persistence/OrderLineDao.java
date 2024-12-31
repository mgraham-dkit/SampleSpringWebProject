package wanna_shop.persistence;

import wanna_shop.model.OrderLine;

import java.util.List;

public interface OrderLineDao {
    List<OrderLine> getOrderLinesForOrder(int orderNumber);
}
