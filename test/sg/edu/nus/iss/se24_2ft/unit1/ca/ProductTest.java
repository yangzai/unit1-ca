package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;

public class ProductTest {

	@Test
	public void testProductManager() {
		CategoryManager categoryManager = null;
		try {
			categoryManager = new CategoryManager("data/Category.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(categoryManager != null);

		ProductManager productManager = null;
		try {
			productManager = new ProductManager("data/Products.dat", categoryManager);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(productManager != null);
	}

	@Test
	public void testInitData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProductList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testGeneratePurchaseOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTableModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUnderstockTableModel() {
		fail("Not yet implemented");
	}

}
