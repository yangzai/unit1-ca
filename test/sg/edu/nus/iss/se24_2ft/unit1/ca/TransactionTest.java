package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionItem;

/**
 * Created by chenyao on 29/3/2016
 *
 */

public class TransactionTest extends TestCase {
    // Test fixtures
    private TransactionItem ti1 = null;
    private TransactionItem ti2 = null;
    private Category c1 = null;
    private Category c2 = null;
    private Product p1 = null;
    private Product p2 = null;
    private ProductManager pm1 = null;
    private CategoryManager cm1 = null;
    private Transaction t1 = null;

    @Before
    public void setUp() throws Exception {
        c1 = new Category("PEN/1", "Pen");
        c2 = new Category("BOK", "Book");
        p1 = new Product("Pen", "cheap", 100, 1.50, 1234, 5, 20);
        p2 = new Product("Book", "expensive", 50, 20.00, 178, 10, 50);
        ti1 = new TransactionItem(p1, 3);
        ti2 = new TransactionItem(p2, 1);
    }

    @After
    public void tearDown() throws Exception {
        c1 = null;
        c2 = null;
        p1 = null;
        p2 = null;
        ti1 = null;
        ti2 = null;
        pm1 = null;
        cm1 = null;
        t1 = null;
    }

    @Test
    public void testAddTransactionItem() {
        assertNull(cm1);
        cm1 = new CategoryManager("data/Category.dat");
        cm1.addCategory(c1);
        cm1.addCategory(c2);
        assertTrue(null != cm1);

        assertNull(pm1);
        pm1 = new ProductManager("data/Products.dat", cm1);
        pm1.addProduct(c1, p1);
        pm1.addProduct(c2, p2);
        // for testing the setID of the product
        // System.out.println(p1.getId());
        // System.out.println(p2.getId());

        assertNull(t1);
        t1 = new Transaction();
        assertTrue(t1.addTransactionItem(ti1));
        assertTrue(t1.addTransactionItem(ti2));
        assertTrue(t1 != null);
    }

}
