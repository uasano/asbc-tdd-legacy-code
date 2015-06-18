package junitsample.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import junitsample.model.Order;
import junitsample.model.OrderItem;

public interface OrderDao {
	public void save(Order order);

	public void save(OrderItem orderItem);

	public List<OrderItem> findAllItems(int userId);
}
