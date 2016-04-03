package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;;

public class ProductTest extends TestCase {
    // test fixtures
    private Product p1 = null;
    private Product p2 = null;

    @Before
    public void setUp() throws Exception {
        // initTestData();
        p1 = new Product("Test1", "NUS test", 525, 10.26, 5555, 50, 200);
        p2 = new Product("Test2", "NUS test", 20, 10.26, 6666, 50, 200);
    }

    @After
    public void tearDown() throws Exception {
        p1 = null;
        p2 = null;
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
