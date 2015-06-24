package junitsample.model;

import org.junit.Before;
import org.junit.Test;

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
}
