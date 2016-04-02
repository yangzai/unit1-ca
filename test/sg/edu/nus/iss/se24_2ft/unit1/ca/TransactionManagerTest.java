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
	private final String FILENAME_MEMBER = "test/data/Members.dat";
	private final String FILENAME_CATEGORY = "test/data/Category.dat";
	private final String FILENAME_PRODUCT = "test/data/Products.dat";
	private final String FILENAME_TRANSACTION = "test/data/Transactions.dat";
	private List<String> stringListMember, stringListCategory, stringListProduct, stringListTransaction;

    @Before
    public void setUp() throws Exception {
        c1 = new Category("PEN", "Pen");
        c2 = new Category("BOK", "Book");
        p1 = new Product("Pen", "cheap", 100, 1.50, 12345, 5, 20);
        p2 = new Product("Book", "expensive", 50, 20.00, 1798, 10, 50);
        ti1 = new TransactionItem(p1, 3);
        ti2 = new TransactionItem(p2, 1);
        stringListMember = preserveData(FILENAME_MEMBER);
        stringListCategory = preserveData(FILENAME_CATEGORY);
        stringListProduct = preserveData(FILENAME_PRODUCT);
        stringListTransaction = preserveData(FILENAME_TRANSACTION);
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
        for (String string : stringListMember) System.out.println(string);
        restoreData(FILENAME_MEMBER, stringListMember);
        restoreData(FILENAME_CATEGORY, stringListCategory);
        restoreData(FILENAME_PRODUCT, stringListProduct);
        restoreData(FILENAME_TRANSACTION, stringListTransaction);
    }

    @Test
    public void testTransactionManager() {
        assertNull(mm);
        mm = new MemberManager(FILENAME_MEMBER);
        assertTrue(mm != null);

        assertNull(cm);
        cm = new CategoryManager(FILENAME_CATEGORY);
        cm.addCategory(c1);
        cm.addCategory(c2);

        assertTrue(cm != null);

        assertNull(pm);
        pm = new ProductManager(FILENAME_PRODUCT, cm);
        pm.addProduct(c1, p1);
        pm.addProduct(c2, p2);
        assertTrue(pm != null);

        // correct file name
        assertNull(tm);
        tm = new TransactionManager(FILENAME_TRANSACTION, pm, mm);
        assertTrue(tm != null);
    }

    @Test
    public void testAddTransaction() {
        assertNull(mm);
        mm = new MemberManager(FILENAME_MEMBER);
        assertTrue(mm != null);

        assertNull(cm);
        cm = new CategoryManager(FILENAME_CATEGORY);
        cm.addCategory(c1);
        cm.addCategory(c2);
        assertTrue(cm != null);

        assertNull(pm);
        pm = new ProductManager(FILENAME_PRODUCT, cm);
        pm.addProduct(c1, p1);
        pm.addProduct(c2, p2);
        assertTrue(pm != null);

        assertNull(tm);
        tm = new TransactionManager(FILENAME_TRANSACTION, pm, mm);

        t1 = new Transaction();
        try {
            tm.addTransaction(t1);
        } catch (IllegalArgumentException e) {
            fail("Fail to add transaction.");
        }
    }
    
    private List<String> preserveData(String fileName){
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			return stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    private void restoreData(String fileName, List<String> stringList){
		try {
			Files.write(Paths.get(fileName), stringList);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
