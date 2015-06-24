package junitsample.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junitsample.dao.OrderDao;
import junitsample.model.Item;
import junitsample.model.Order;
import junitsample.model.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoImpl implements OrderDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void save(Order order) {
		jdbcTemplate.update("insert into order_tbl (user_Id) values (?)",order.userId);
		order.id = jdbcTemplate.queryForObject("select id from order_tbl where user_id = ?", Integer.class,order.userId);
	}

	@Override
	public void save(OrderItem orderItem) {
		jdbcTemplate.update("insert into order_item_tbl (order_Id,item_id,amount,quantity) values (?,?,?,?)",orderItem.orderId,orderItem.item.id,orderItem.amount,orderItem.quantity);
	}

	@Override
	public List<OrderItem> findAllItems(int userId) {
		List<OrderItem> items = jdbcTemplate.query("select * from order_item_tbl where order_id in (select id from order_tbl where user_id = ?)",new RowMapper<OrderItem>(){
			@Override
			public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderItem orderItem = new OrderItem();
				orderItem.id = rs.getInt("id");
				orderItem.orderId = rs.getInt("order_id");
				orderItem.amount = rs.getBigDecimal("amount");
				orderItem.quantity = rs.getInt("quantity");
				
				int itemId = rs.getInt("item_id");
				orderItem.item = findItem(itemId);
				return orderItem;
			}

			private Item findItem(int itemId) {
				return jdbcTemplate.queryForObject("select * from item_tbl where id = ?",new RowMapper<Item>(){
					@Override
					public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
						Item item = new Item();
						item.id = rs.getInt("id");
						item.name = rs.getString("name");
						item.kindId = rs.getInt("kind_id");
						item.makerId = rs.getInt("maker_id");
						item.amount = rs.getBigDecimal("amount");
						
						return item;
					}
					
				},itemId);
			}
			
		},userId);
		
		return items;
	}

}
