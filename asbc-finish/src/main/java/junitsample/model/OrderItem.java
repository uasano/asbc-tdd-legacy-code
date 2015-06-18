package junitsample.model;

import java.math.BigDecimal;

public class OrderItem {
	public int id;
	public int orderId;
	public Item item;
	public BigDecimal amount;
	public int quantity;
}
