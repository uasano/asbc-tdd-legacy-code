package junitsample.service;

import java.util.ArrayList;
import java.util.List;

import junitsample.dao.CartDao;
import junitsample.dao.OrderDao;
import junitsample.model.Cart;
import junitsample.model.CartItem;
import junitsample.model.Order;
import junitsample.model.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartService {
	@Autowired
	private OrderDao orderDao;
	public CartDao cartDao;
	
	public Order order(int userId){
		Cart cart = cartDao.find(userId);
		List<CartItem> list = cartDao.findAllItems(userId);
		cart.items = list;

		Order order = new Order();
		order.userId = userId;
		order.items = new ArrayList<>();
		orderDao.save(order);
		for(CartItem item:cart.items){
			OrderItem orderItem = new OrderItem();
			orderItem.id = item.id;
			orderItem.item  = item.item;
			orderItem.amount = item.amount;
			orderItem.quantity = item.quantity;

			orderItem.orderId = order.id;
			order.items.add(orderItem);
			orderDao.save(orderItem);
		}
		
		return order;
	}
}
