package junitsample.service;

import java.util.List;

import junitsample.model.Cart;
import junitsample.model.CartItem;

public interface CartDao {

	Cart find(int userId);

	List<CartItem> findAllItems(int userId);

}
