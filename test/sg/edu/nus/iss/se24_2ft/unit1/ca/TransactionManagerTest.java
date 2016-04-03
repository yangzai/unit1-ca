package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.MemberManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionManager;
import util.TestUtil;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by chenyao on 29/3/2016
 *
 */

//TODO: txItem, member
public class TransactionManagerTest {
    private Category c1;
    private Category c2;
    private Product p1;
    private Product p2;
    private TransactionManager tm;
    private CategoryManager cm;
    private ProductManager pm;
    private MemberManager mm;

    @Before
    public void setUp() {
        TestUtil.putData(TestUtil.CATEGORY_FILENAME, TestUtil.CATEGORY_STRING_LIST);
        TestUtil.putData(TestUtil.PRODUCT_FILENAME, TestUtil.PRODUCT_STRING_LIST);
        TestUtil.putData(TestUtil.MEMBER_FILENAME, TestUtil.MEMBER_STRING_LIST);
        TestUtil.putData(TestUtil.TRANSACTION_FILENAME, TestUtil.TRANSACTION_STRING_LIST);

        tm = null;
        pm = null;
        cm = null;
        mm = null;

        c1 = new Category("PEN", "Pen");
        c2 = new Category("BOK", "Book");
        p1 = new Product("Pen", "cheap", 100, 1.50, 12345, 5, 20);
        p2 = new Product("Book", "expensive", 50, 20.00, 1798, 10, 50);
    }

    @After
    public void tearDown() {
        TestUtil.deleteData(TestUtil.CATEGORY_FILENAME);
        TestUtil.deleteData(TestUtil.PRODUCT_FILENAME);
        TestUtil.deleteData(TestUtil.MEMBER_FILENAME);
        TestUtil.deleteData(TestUtil.TRANSACTION_FILENAME);
    }

    @Test
    public void testAddTransaction() {
        mm = new MemberManager(TestUtil.MEMBER_FILENAME);

        assertNull(cm);
        cm = new CategoryManager(TestUtil.CATEGORY_FILENAME);
        cm.addCategory(c1);
        cm.addCategory(c2);
        assertTrue(cm != null);

        assertNull(pm);
        pm = new ProductManager(TestUtil.PRODUCT_FILENAME, cm);
        pm.addProduct(c1, p1);
        pm.addProduct(c2, p2);
        assertTrue(pm != null);

        assertNull(tm);
        tm = new TransactionManager(TestUtil.TRANSACTION_FILENAME, pm, mm);

        Transaction t1 = new Transaction();
        try {
            tm.addTransaction(t1);
        } catch (IllegalArgumentException e) {
            fail("Fail to add transaction.");
        }
    }
}
