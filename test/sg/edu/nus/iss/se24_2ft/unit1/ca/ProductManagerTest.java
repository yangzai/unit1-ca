package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;
import util.TestUtil;

import static org.junit.Assert.*;

public class ProductManagerTest {
    private CategoryManager categoryManager;
    private ProductManager productManager;

    @Before
    public void setUp() {
        TestUtil.putData(TestUtil.PRODUCT_FILENAME, TestUtil.PRODUCT_STRING_LIST);
        TestUtil.putData(TestUtil.CATEGORY_FILENAME, TestUtil.CATEGORY_STRING_LIST);

        categoryManager = null;
        productManager = null;
    }

    @After
    public void tearDown() {
        TestUtil.deleteData(TestUtil.PRODUCT_FILENAME);
        TestUtil.deleteData(TestUtil.CATEGORY_FILENAME);
    }

    @Test
    public void testGetProduct() {
        initTestData();
        assertNull(productManager.getProduct("NON/1"));
        assertNull(productManager.getProduct("TST/1"));

        initTestData();
        Category category = new Category("TST", "Testing");
        categoryManager.addCategory(category);

        Product product = new Product("TST", "test description", 525, 10.26, 5555, 50, 200);
        productManager.addProduct(category, product);
        assertTrue(null != productManager.getProduct("TST/1"));
    }

    @Test
    public void testGetProductList() {
        initTestData();
        assertTrue(productManager.getProductList().size() > 0);

    }

    @Test
    public void testAddProduct() {
        initTestData();
        int count = productManager.getProductList().size();
        Category category = new Category("TST", "Testing");
        categoryManager.addCategory(category);

        Product product1 = new Product("Test1", "NUS test", 525, 10.26, 5555, 50, 200);

        try {
            productManager.addProduct(category, product1);
        } catch (IllegalArgumentException e) {
            fail("Fail to add product.");
        }

        assertEquals(product1, productManager.getProduct("TST/1"));

        Product product2 = new Product("Test2", "NUS test", 600, 10.26, 6666, 50, 200);
        try {
            productManager.addProduct(category, product2);
        } catch (IllegalArgumentException e) {
            fail("Fail to add product.");
        }
        assertEquals(product2, productManager.getProduct("TST/2"));

        Product product3 = new Product("Test3", "NUS test", 600, 20, 7890, 30, 200);
        try {
            productManager.addProduct(category, product3);
        } catch (IllegalArgumentException e) {
            fail("Fail to add product.");
        }
        assertTrue(productManager.getProductList().size() == count + 3);
    }

    @Test
    public void testGeneratePurchaseOrder() {
        initTestData();
        Category category = new Category("TST", "Testing");
        categoryManager.addCategory(category);

        Product product1 = new Product("Test1", "NUS test", 20, 10.26, 5555, 50, 200);
        try {
            productManager.addProduct(category, product1);
        } catch (IllegalArgumentException e) {
            fail("Fail to add product.");
        }

        List<Integer> understockIndexList = null;
        productManager.generatePurchaseOrder(understockIndexList);
        // reflect the member storeKeeperMap
        Field field;
        try {
            field = productManager.getClass().getDeclaredField("understockProductList");
            field.setAccessible(true);
            Object value = field.get(productManager);
            assertTrue(value instanceof List<?>);

            assertTrue(((List<Product>) value).contains(product1));

            field.setAccessible(false);

        } catch (SecurityException | NoSuchFieldException
                | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
    }

    private void initTestData() {
        categoryManager = new CategoryManager(TestUtil.CATEGORY_FILENAME);
        productManager = new ProductManager(TestUtil.PRODUCT_FILENAME, categoryManager);
    }
}