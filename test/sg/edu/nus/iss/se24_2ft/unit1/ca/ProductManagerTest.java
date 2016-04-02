package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.lang.reflect.Field;
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
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;;

public class ProductManagerTest extends TestCase {
    // test fixtures
    private CategoryManager categoryManager = null;
    private ProductManager productManager = null;
	private final String FILENAME_PRODUCT = "test/data/Products.dat";
	private final String FILENAME_CATEGORY = "test/data/Category.dat";
	private List<String> stringListProduct, stringListCategory;

    @Before
    public void setUp() throws Exception {
        // initTestData();
		// Preserved Test Data
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME_PRODUCT))) {
			stringListProduct = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME_CATEGORY))) {
			stringListCategory = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @After
    public void tearDown() throws Exception {
        categoryManager = null;
        productManager = null;
		try {
			Files.write(Paths.get(FILENAME_PRODUCT), stringListProduct);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Files.write(Paths.get(FILENAME_CATEGORY), stringListCategory);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Test
    public void testProductManager() {
        CategoryManager categoryManager = new CategoryManager(FILENAME_CATEGORY);
        assertTrue(categoryManager != null);

        ProductManager productManager = new ProductManager(FILENAME_PRODUCT, categoryManager);
        assertTrue(productManager != null);
    }

    @Test
    public void testGetProduct() {
        assertTrue(initTestData());
        // non-existed product
        assertNull(productManager.getProduct("NON/1"));
        assertNull(productManager.getProduct("TST/1"));
        //
        assertTrue(initTestData());
        // int count = productManager.getProductList().size();
        Category category = new Category("TST", "Testing");
        // category.setId();
        categoryManager.addCategory(category);

        // Product product1 = new Product("Test1", "NUS test", 525, 10.26, 5555,
        // 50, 200);

        Product product = new Product("TST", "test description", 525, 10.26, 5555, 50, 200);
        productManager.addProduct(category, product);
        assertTrue(null != productManager.getProduct("TST/1"));
    }

    @Test
    public void testGetProductList() {
        assertTrue(initTestData());
        assertTrue(productManager.getProductList().size() > 0);

    }

    @Test
    public void testAddProduct() {
        assertTrue(initTestData());
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
        assertTrue(initTestData());
        Category category = new Category("TST", "Testing");
        // category.setId();
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
            Object value = (Object) field.get(productManager);
            assertTrue(value instanceof List<?>);

            assertTrue(((List<Product>) value).contains(product1));

            field.setAccessible(false);

        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception thrown!");

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception thrown!");

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception thrown!");
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception thrown!");
        }
    }

    public boolean initTestData() {
        categoryManager = new CategoryManager(FILENAME_CATEGORY);
        productManager = new ProductManager(FILENAME_PRODUCT, categoryManager);
        if (null == productManager) return false;

        return true;
    }
}