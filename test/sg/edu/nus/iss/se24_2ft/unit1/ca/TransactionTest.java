package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	private final String FILENAME_CATEGORY = "test/data/Category.dat";
	private final String FILENAME_PRODUCT = "test/data/Products.dat";
	private List<String> stringListCategory, stringListProduct;

    @Before
    public void setUp() throws Exception {
        c1 = new Category("PEN/1", "Pen");
        c2 = new Category("BOK", "Book");
        p1 = new Product("Pen", "cheap", 100, 1.50, 1234, 5, 20);
        p2 = new Product("Book", "expensive", 50, 20.00, 178, 10, 50);
        ti1 = new TransactionItem(p1, 3);
        ti2 = new TransactionItem(p2, 1);
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME_CATEGORY))) {
			stringListCategory = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME_PRODUCT))) {
			stringListProduct = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		try {
			Files.write(Paths.get(FILENAME_CATEGORY), stringListCategory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Files.write(Paths.get(FILENAME_PRODUCT), stringListProduct);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Test
    public void testAddTransactionItem() {
        assertNull(cm1);
        cm1 = new CategoryManager(FILENAME_CATEGORY);
        cm1.addCategory(c1);
        cm1.addCategory(c2);
        assertTrue(null != cm1);

        assertNull(pm1);
        pm1 = new ProductManager(FILENAME_PRODUCT, cm1);
        pm1.addProduct(c1, p1);
        pm1.addProduct(c2, p2);

        assertNull(t1);
        t1 = new Transaction();
        try {
            t1.addTransactionItem(ti1);
        } catch (IllegalArgumentException e) {
            fail("Exception thrown!");
        }
        try {
            t1.addTransactionItem(ti2);
        } catch (IllegalArgumentException e) {
            fail("Exception thrown!");
        }
        assertTrue(t1 != null);
    }

}
