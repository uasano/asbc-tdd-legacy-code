package junitsample.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	public int userId;
	public List<CartItem> items;

	public Cart() {
		items = new ArrayList<>();
	}

	public boolean hasNoItems() {
		return items.isEmpty();
	}
}
