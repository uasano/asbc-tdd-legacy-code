package junitsample.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import junitsample.model.Cart;
import junitsample.model.CartItem;

public interface CartDao {

	Cart find(int userId);

	List<CartItem> findAllItems(int userId);

	void save(int userId,Cart cart);
	
	void save(String userId,List<CartItem> cartItems);

	void save(String userId,CartItem cartItem);
}
