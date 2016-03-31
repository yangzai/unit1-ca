package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;

/**
 * Created by chenyao on 27/3/16
 */
public class CategoryManagerTest extends TestCase {
	// test fixtures
	private Category category1 = null;
	private Category category2 = null;
	private Category category3 = null;
	private CategoryManager categoryManager = null;
	private final String FILENAME = "test/data/Category.dat";
	private List<String> stringList;

	@Before
	public void setUp() throws Exception {

		// Preserved Test Data
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			stringList = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		category1 = new Category("BOK", "Book");
		category2 = new Category("COM", "Computer");
		category3 = new Category("CLO", "Clothing");
	}

	@After
	public void tearDown() throws Exception {
		category1 = null;
		category2 = null;
		category3 = null;
		categoryManager = null;
		try {
			Files.write(Paths.get(FILENAME), stringList);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Test for CategoryManager
	public void testCategoryManager() {
		assertNull(categoryManager);
		categoryManager = new CategoryManager(FILENAME);
		assertTrue(categoryManager != null);
	}

	@Test
	public void testGetCategory() {
		assertNull(categoryManager);
		categoryManager = new CategoryManager(FILENAME);
		assertTrue(isEqual(category3, categoryManager.getCategory("CLO")));
	}

	@Test
	public void testAddCategory() {
		assertNull(categoryManager);
		categoryManager = new CategoryManager(FILENAME);
		categoryManager.addCategory(category1);
		categoryManager.addCategory(category2);
		assertTrue(isEqual(category1, categoryManager.getCategory("BOK")));
		assertTrue(isEqual(category2, categoryManager.getCategory("COM")));
	}

	@Test
	public void testGetCategoryList() {
		assertNull(categoryManager);
		categoryManager = new CategoryManager(FILENAME);
		categoryManager.addCategory(category1);
		categoryManager.addCategory(category2);
		List<Category> categoryList = categoryManager.getCategoryList();
		assertTrue(categoryList.contains(category1));
		assertTrue(categoryList.contains(category2));
	}

	private boolean isEqual(Category category1, Category category2) {
		return (category1.getId().equals(category2.getId())) && (category1.getName().equals(category2.getName()));
	}
}
