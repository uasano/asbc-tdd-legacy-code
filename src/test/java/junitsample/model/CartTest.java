package junitsample.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CartTest {

    private Cart sut;

    @Before
    public void setUp() throws Exception {
        sut = new Cart();
    }

    @Test
    public void testカート内の商品が０件の時にhasNoItemsがtrueを返すべき() throws Exception {
        assertThat(sut.hasNoItems(), is(true));
    }

    @Test
    public void testカート内の商品が１件の時にhasNoItemsがfalseを返すべき() throws Exception {
        CartItem item = new CartItem();

        List<CartItem> items = new ArrayList<>();
        items.add(item);

        sut.items = items;

        assertThat(sut.hasNoItems(), is(false));

    }
}
