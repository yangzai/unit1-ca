package sg.edu.nus.iss.se24_2ft.unit1.ca;

/* created by Navy Gao on 3/28 */

import static org.junit.Assert.*;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;;

public class ProductManagerTest extends TestCase {
	//test fixtures
	private CategoryManager categoryManager = null;
	private ProductManager	productManager = null;
	
	@Before
	public void setUp() throws Exception {
		//initTestData();
	}
	
	@After
	public void tearDown() throws Exception {
		categoryManager = null;
		productManager = null;
	}
	@Test
	public void testProductManager() {
		CategoryManager categoryManager = null;
		try {
			categoryManager = new CategoryManager("data/Category.dat");
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(categoryManager != null);

		ProductManager productManager = null;
		try {
			productManager = new ProductManager("data/Products.dat", categoryManager);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(productManager != null);
	}

	@Test
	public void testGetProduct() {
		assertTrue(initTestData());
		//non-existed product
		assertNull(productManager.getProduct("NON/1"));
		assertNull(productManager.getProduct("TST/1"));
		//
		assertTrue(initTestData());
		int count = productManager.getProductList().size();
		Category category = new Category("TST", "Testing");
		category.setId();
		categoryManager.addCategory(category);

        Product product1 = new Product("Test1", "NUS test", 525,
                10.26, 5555, 50, 200);
                
        assertTrue(null != productManager.getProduct("TST/1"));
	}

	@Test
	public void testGetProductList() {
		
		assertTrue(initTestData());
		assertTrue(productManager.getProductList().size()>0);

	}

	@Test
	public void testAddProduct() {
		assertTrue(initTestData());
		int count = productManager.getProductList().size();
		Category category = new Category("TST", "Testing");
		category.setId();
		categoryManager.addCategory(category);

        Product product1 = new Product("Test1", "NUS test", 525,
                10.26, 5555, 50, 200);
        
        assertTrue(productManager.addProduct(category, product1));
        
        assertEquals(product1, productManager.getProduct("TST/1"));
		
        Product product2 = new Product("Test2", "NUS test", 600,
                10.26, 6666, 50, 200);
        assertTrue(productManager.addProduct(category, product2));
        assertEquals(product2, productManager.getProduct("TST/2"));

        Product product3 = new Product("Test3", "NUS test", 600,
        		20, 7890, 30, 200);
        assertTrue(productManager.addProduct(category, product3));
        assertTrue(productManager.getProductList().size() == count +3);
	}

	@Test
	public void testGeneratePurchaseOrder() {
		assertTrue(initTestData());
		Category category = new Category("TST", "Testing");
		category.setId();
		categoryManager.addCategory(category);

        Product product1 = new Product("Test1", "NUS test", 20,
                10.26, 5555, 50, 200);
        
        assertTrue(productManager.addProduct(category, product1));

        List<Integer> understockIndexList = null;
        productManager.generatePurchaseOrder(understockIndexList);
		//reflect the member storeKeeperMap
		Field field;
        try {
        	field = productManager.getClass().getDeclaredField("understockProductList");
        	field.setAccessible(true);
            Object value = (Object)field.get(productManager);
            assertTrue(value instanceof List<?>);
                        
            assertTrue(((List<Product>)value).contains(product1));
            
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

	@Test
	public void testGetTableModel() {
		assertTrue(initTestData());

		Category category = new Category("TST", "Testing");
		category.setId();
		categoryManager.addCategory(category);

        Product product1 = new Product("Test1", "NUS test", 525,
                10.26, 5555, 50, 200);
        
        assertTrue(productManager.addProduct(category, product1));
        
        assertTrue(productManager.getTableModel().getRowCount() == productManager.getProductList().size());
        assertEquals(productManager.getTableModel().getValueAt
        		((productManager.getProductList().size()-1), 0), "TST/1");
        assertEquals(productManager.getTableModel().getValueAt
        		((productManager.getProductList().size()-1), 1), "Test1");
        assertEquals(productManager.getTableModel().getValueAt
        		((productManager.getProductList().size()-1), 2), "NUS test");
        assertEquals(productManager.getTableModel().getValueAt
        		((productManager.getProductList().size()-1), 3), 525);
        assertEquals(productManager.getTableModel().getValueAt
        		((productManager.getProductList().size()-1), 4), 10.26);
        assertEquals(productManager.getTableModel().getValueAt
        		((productManager.getProductList().size()-1), 5), 5555);
        assertEquals(productManager.getTableModel().getValueAt
        		((productManager.getProductList().size()-1), 6), 50);
        assertEquals(productManager.getTableModel().getValueAt
        		((productManager.getProductList().size()-1), 6), 200);
	}

	@Test
	public void testGetUnderstockTableModel() {
		assertTrue(initTestData());
		Category category = new Category("TST", "Testing");
		category.setId();
		categoryManager.addCategory(category);

        Product product1 = new Product("Test1", "NUS test", 20,
                10.26, 5555, 50, 200);
        
        assertTrue(productManager.addProduct(category, product1));

        List<Integer> understockIndexList = null;
        productManager.generatePurchaseOrder(understockIndexList);
        
        //assertTrue(productManager.getUnderstockTableModel().getRowCount() == 1);
        Field field;
        try {
        	field = productManager.getClass().getDeclaredField("understockProductList");
        	field.setAccessible(true);
            Object value = (Object)field.get(productManager);
            assertTrue(value instanceof List<?>);
                        
           // assertTrue(((List<Product>)value).contains(product1));
            assertTrue(productManager.getUnderstockTableModel().getRowCount() == ((List<Product>)value).size());
            assertEquals(productManager.getUnderstockTableModel().getValueAt
            		((((List<Product>)value).size()-1), 0), "TST/1");
            assertEquals(productManager.getUnderstockTableModel().getValueAt
            		((((List<Product>)value).size()-1), 1), "Test1");
            assertEquals(productManager.getUnderstockTableModel().getValueAt
            		((((List<Product>)value).size()-1), 2), "NUS test");
            assertEquals(productManager.getUnderstockTableModel().getValueAt
            		((((List<Product>)value).size()-1), 3), 20);
            assertEquals(productManager.getUnderstockTableModel().getValueAt
            		((((List<Product>)value).size()-1), 4), 10.26);
            assertEquals(productManager.getUnderstockTableModel().getValueAt
            		((((List<Product>)value).size()-1), 5), 5555);
            assertEquals(productManager.getUnderstockTableModel().getValueAt
            		((((List<Product>)value).size()-1), 6), 50);
            assertEquals(productManager.getUnderstockTableModel().getValueAt
            		((((List<Product>)value).size()-1), 6), 200);

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
		categoryManager = null;
		try {
			categoryManager = new CategoryManager("data/Category.dat");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if (null == categoryManager) return false;
		productManager = null;
		try {
			productManager = new ProductManager("data/Products.dat", categoryManager);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (null == productManager) return false;
		
		return true;
	}
}
