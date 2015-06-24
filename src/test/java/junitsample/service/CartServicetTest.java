package junitsample.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junitsample.Repository;
import junitsample.dao.CartDao;
import junitsample.dao.OrderDao;
import junitsample.exception.ApplicationException;
import junitsample.model.Cart;
import junitsample.model.CartItem;
import junitsample.model.Item;
import junitsample.model.Order;
import junitsample.model.OrderItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class CartServicetTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CartDao cartDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	CartService sut;
	
	@Before
	public void setup(){
		remakeTable();
		
		final Repository<Integer, Cart> cartRepository = new Repository<Integer, Cart>();
		final Repository<String, CartItem> cartItemRepository = new Repository<String, CartItem>();
		cartDao =  new CartDao() {
			
			@Override
			public List<CartItem> findAllItems(int userId) {
				return cartItemRepository.findAll();
			}
			
			@Override
			public Cart find(int userId) {
				return cartRepository.find(userId);
			}

			@Override
			public void save(int userId, Cart cart) {
				cartRepository.save(userId, cart);
			}

			@Override
			public void save(String userId, List<CartItem> cartItems) {
				for (CartItem cartItem : cartItems) {
					cartItemRepository.save(userId, cartItem);
				}
			}

			@Override
			public void save(String userId, CartItem cartItem) {
				cartItemRepository.save(userId, cartItem);
			}
		};
		sut.cartDao = cartDao;
	}

	private void remakeTable() {
		remakeItemTable();
		remakeCartTable();
		remakeOrderTable();
	}
	private void remakeItemTable() {
		jdbcTemplate.execute("drop table item_Tbl if exists");
		jdbcTemplate
				.execute("create table item_Tbl("
						+ "id integer, name varchar,kind_Id integer,maker_Id integer,amount integer)");
	}

	private void remakeCartTable() {
		jdbcTemplate.execute("drop table cart_Tbl if exists");
		jdbcTemplate
				.execute("create table cart_Tbl("
						+ "id serial, user_id integer)");

		jdbcTemplate.execute("drop table cart_Item_Tbl if exists");
		jdbcTemplate
				.execute("create table cart_Item_Tbl("
						+ "id serial, cart_id integer,item_id integer,amount integer,quantity integer)");
	}

	private void remakeOrderTable() {
		jdbcTemplate.execute("drop table order_Tbl if exists");
		jdbcTemplate
				.execute("create table order_Tbl("
						+ "id serial, user_id integer)");

		jdbcTemplate.execute("drop table order_Item_Tbl if exists");
		jdbcTemplate
				.execute("create table order_Item_Tbl("
						+ "id serial, order_id integer,item_id integer,amount integer,quantity integer)");
	}

	@Test(expected=ApplicationException.class)
	public void testカート明細が０件のときに注文するとApplicationExceptionが発生するべき() {
		int userId = 1;

		Cart cart = new Cart();
		cart.items = new ArrayList<>();

		cartDao.save(userId, cart);

		sut.order(userId);
	}

	@Test
	public void testカートの明細が１件のときに注文すると明細１件の注文を返すべき() {
		int userId = 1;

		Cart cart = new Cart();
		cart.items = new ArrayList<>();
		CartItem cartItem = create(1,"ねんどろいど 島風",3500,100);
		
		cartDao.save(userId, cart);
		cartDao.save(String.valueOf(userId), cartItem);

		cart.items.add(cartItem);

		Order order = sut.order(userId);

		List<OrderItem> list = orderDao.findAllItems(order.id);
		assertThat(list.size(), is(1));
		assertThat(list.get(0).id, is(1));
		assertThat(list.get(0).item.name, is("ねんどろいど 島風"));
		assertThat(Integer.parseInt(list.get(0).amount.toString()), is(3500));
		assertThat(list.get(0).quantity, is(100));
	}

	private CartItem create(){
		return create(1,"ねんどろいど 島風",3500,100);
	}
	private CartItem create(int id,String name,int amount,int quantity){
		CartItem cartItem = new CartItem();
		cartItem.item = createItem(id,name,amount);
		cartItem.amount = new BigDecimal(amount);
		cartItem.quantity = quantity;
		return cartItem;
	}

	private Item createItem(int id,String name, int amount) {
		Item item = new Item();
		item.id = id;
		item.name = name;
		item.makerId = 1;
		item.kindId = 1;
		item.amount = new BigDecimal(amount);
		jdbcTemplate.update("insert into item_tbl(id,name,maker_id,kind_id,amount) values (?,?,?,?,?)",
		item.id,
		item.name,
		item.makerId,
		item.kindId,
		item.amount);
		return item;
	}
}
