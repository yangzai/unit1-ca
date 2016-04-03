package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;

import static org.junit.Assert.assertEquals;

public class ProductTest {
    private Product p1;

    @Before
    public void setUp() {
        p1 = new Product("Test1", "NUS test", 525, 10.26, 5555, 50, 200);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetName() {
        assertEquals(p1.getName(), "Test1");
    }

    @Test
    public void testGetDescription() {
        assertEquals(p1.getDescription(), "NUS test");
    }

    @Test
    public void testGetQuantity() {
        assertEquals(p1.getQuantity(), 525);
    }

    @Test
    public void testGetBarCode() {
        assertEquals(p1.getBarcode(), 5555);
    }

    @Test
    public void testGetThreshold() {
        assertEquals(p1.getThreshold(), 50);
    }

    @Test
    public void testGetOrderQuantity() {
        assertEquals(p1.getOrderQuantity(), 200);
    }
}
