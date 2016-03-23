package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;

public class CategoryTest {

	@Test
	public void testCategoryManager() {
		CategoryManager categoryManager = null;
		try {
			categoryManager = new CategoryManager("data/Category.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(categoryManager != null);
	}

	@Test
	public void testGetCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCategoryList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTableModel() {
		fail("Not yet implemented");
	}

}
