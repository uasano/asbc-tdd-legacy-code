package junitsample.dao.impl;

import java.util.List;

import junitsample.dao.CartDao;
import junitsample.model.Cart;
import junitsample.model.CartItem;

import org.springframework.stereotype.Component;

@Component
public class CartDaoImpl implements CartDao{

	@Override
	public Cart find(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartItem> findAllItems(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(int userId, Cart cart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String userId, List<CartItem> cartItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String userId, CartItem cartItem) {
		// TODO Auto-generated method stub
		
	}

}
