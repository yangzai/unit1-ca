package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import util.TestUtil;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by chenyao on 27/3/16
 */
public class CategoryManagerTest {

	private Category category1;
	private Category category2;
	private Category category3;
	private CategoryManager categoryManager;

	@Before
	public void setUp() {
		TestUtil.putData(TestUtil.CATEGORY_FILENAME, TestUtil.CATEGORY_STRING_LIST);

		categoryManager = null;

		category1 = new Category("BOK", "Book");
		category2 = new Category("COM", "Computer");
		category3 = new Category("CLO", "Clothing");
	}

	@After
	public void tearDown() {
		TestUtil.deleteData(TestUtil.CATEGORY_FILENAME);
	}

	@Test
	public void testGetCategory() {
		assertNull(categoryManager);
		categoryManager = new CategoryManager(TestUtil.CATEGORY_FILENAME);
		assertTrue(isEqual(category3, categoryManager.getCategory("CLO")));
	}

	@Test
	public void testAddCategory() {
		assertNull(categoryManager);
		categoryManager = new CategoryManager(TestUtil.CATEGORY_FILENAME);
		categoryManager.addCategory(category1);
		categoryManager.addCategory(category2);
		assertTrue(isEqual(category1, categoryManager.getCategory("BOK")));
		assertTrue(isEqual(category2, categoryManager.getCategory("COM")));
	}

	@Test
	public void testGetCategoryList() {
		assertNull(categoryManager);
		categoryManager = new CategoryManager(TestUtil.CATEGORY_FILENAME);
		categoryManager.addCategory(category1);
		categoryManager.addCategory(category2);
		List<Category> categoryList = categoryManager.getCategoryList();
		assertTrue(categoryList.contains(category1));
		assertTrue(categoryList.contains(category2));
	}

	private boolean isEqual(Category category1, Category category2) {
		if (category1 == category2) return true;
		if (!category1.getRequestedId().equals(category2.getRequestedId())) return false;
		if (category1.getName()==null){
			if (category2.getName()!=null) return false;
		} else {
			if (!category1.getName().equals(category2.getName())) return false;
		}
		return true;
	}
}
