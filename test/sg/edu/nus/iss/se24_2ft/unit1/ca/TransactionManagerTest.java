package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.MemberManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionItem;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionManager;

/**
 * Created by chenyao on 29/3/2016
 *
 */

public class TransactionManagerTest extends TestCase {
    // Test fixtures
    private TransactionItem ti1 = null;
    private TransactionItem ti2 = null;
    private Category c1 = null;
    private Category c2 = null;
    private Product p1 = null;
    private Product p2 = null;
    private TransactionManager tm = null;
    private CategoryManager cm = null;
    private ProductManager pm = null;
    private MemberManager mm = null;
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
        cm = null;
        pm = null;
        mm = null;
        tm = null;
        t1 = null;
    }

    @Test
    public void testTransactionManager() {
        assertNull(mm);
        try {
            mm = new MemberManager("data/Members.dat");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(mm != null);

        assertNull(cm);
        try {
            cm = new CategoryManager("data/Category.dat");
            cm.addCategory(c1);
            cm.addCategory(c2);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(cm != null);

        assertNull(pm);
        try {
            pm = new ProductManager("data/Products.dat", cm);
            pm.addProduct(c1, p1);
            pm.addProduct(c2, p2);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(pm != null);

        // non-existed file name
        assertNull(tm);
        try {
            tm = new TransactionManager("data/non-existed_file.dat", pm, mm);
            fail("error path");
        } catch (IOException e) {
        }

        if (null != tm)
            tm = null;

        // correct file name
        assertNull(tm);
        try {
            tm = new TransactionManager("data/Transactions.dat", pm, mm);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(tm != null);
    }

    @Test
    public void testAddTransaction() {
        assertNull(mm);
        try {
            mm = new MemberManager("data/Members.dat");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(mm != null);

        assertNull(cm);
        try {
            cm = new CategoryManager("data/Category.dat");
            cm.addCategory(c1);
            cm.addCategory(c2);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(cm != null);

        assertNull(pm);
        try {
            pm = new ProductManager("data/Products.dat", cm);
            pm.addProduct(c1, p1);
            pm.addProduct(c2, p2);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(pm != null);

        assertNull(tm);
        try {
            tm = new TransactionManager("data/Transactions.dat", pm, mm);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        t1 = new Transaction();
        assertTrue(tm.addTransaction(t1));
    }

}
