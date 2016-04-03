package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionItem;
import util.TestUtil;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by chenyao on 29/3/2016
 *
 */

public class TransactionTest {
    private TransactionItem ti1;
    private TransactionItem ti2;
    private Category c1;
    private Category c2;
    private Product p1;
    private Product p2;
    private ProductManager pm1;
    private CategoryManager cm1;
    private Transaction t1;

    @Before
    public void setUp() {
        pm1 = null;
        cm1 = null;
        t1 = null;

        c1 = new Category("PEN", "Pen");
        c2 = new Category("BOK", "Book");
        p1 = new Product("Pen", "cheap", 100, 1.50, 12346, 5, 20);
        p2 = new Product("Book", "expensive", 50, 20.00, 1789, 10, 50);
        ti1 = new TransactionItem(p1, 3);
        ti2 = new TransactionItem(p2, 1);

        TestUtil.putData(TestUtil.CATEGORY_FILENAME, TestUtil.CATEGORY_STRING_LIST);
        TestUtil.putData(TestUtil.PRODUCT_FILENAME, TestUtil.PRODUCT_STRING_LIST);
    }

    @After
    public void tearDown() {
        TestUtil.deleteData(TestUtil.CATEGORY_FILENAME);
        TestUtil.deleteData(TestUtil.PRODUCT_FILENAME);
    }

    @Test
    public void testAddTransactionItem() {
        assertNull(cm1);
        cm1 = new CategoryManager(TestUtil.CATEGORY_FILENAME);
        cm1.addCategory(c1);
        cm1.addCategory(c2);
        assertTrue(null != cm1);

        assertNull(pm1);
        pm1 = new ProductManager(TestUtil.PRODUCT_FILENAME, cm1);
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
