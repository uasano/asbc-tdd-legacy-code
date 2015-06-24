package junitsample.model;

import java.util.List;

public class Cart {
	public int userId;
	public List<CartItem> items;

	public boolean hasNoItems() {
		return items.isEmpty();
	}
}
