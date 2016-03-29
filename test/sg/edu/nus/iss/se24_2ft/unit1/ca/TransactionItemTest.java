package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionItem;

/**
 * Created by chenyao on 29/3/2016
 *
 */

public class TransactionItemTest extends TestCase {
    // Test fixtures
    private TransactionItem ti1 = null;
    private TransactionItem ti2 = null;
    private Product p1 = null;
    private Product p2 = null;

    @Before
    public void setUp() throws Exception {
        p1 = new Product("Pen", "cheap", 100, 1.50, 1234, 5, 20);
        p2 = new Product("Book", "expensive", 50, 20.00, 178, 10, 50);
        ti1 = new TransactionItem(p1, 3);
        ti2 = new TransactionItem(p2, 1);
    }

    @After
    public void tearDown() throws Exception {
        p1 = null;
        p2 = null;
        ti1 = null;
        ti2 = null;
    }

    @Test
    public void testTransactionItem() {
        assertEquals(p1, ti1.getProduct());
        assertEquals(3, ti1.getQuantity());

        assertEquals(p2, ti2.getProduct());
        assertEquals(1, ti2.getQuantity());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(p1, ti1.getProduct());
        assertEquals(p2, ti2.getProduct());
    }

    @Test
    public void testGetProduct() {
        assertEquals(3, ti1.getQuantity());
        assertEquals(1, ti2.getQuantity());
    }
}
