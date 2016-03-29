package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.util.List;

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

    @Before
    public void setUp() throws Exception {
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
    }

    // Test for CategoryManager
    public void testCategoryManager() {
        assertNull(categoryManager);
        categoryManager = new CategoryManager("data/Category.dat");
        assertTrue(categoryManager != null);
    }
    @Test
    public void testGetCategory() {
        assertNull(categoryManager);
        categoryManager = new CategoryManager("data/Category.dat");
        assertEquals(category3, categoryManager.getCategory("CLO"));
    }

    @Test
    public void testAddCategory() {
        assertNull(categoryManager);
        categoryManager = new CategoryManager("data/Category.dat");
        categoryManager.addCategory(category1);
        categoryManager.addCategory(category2);
        assertEquals(category1, categoryManager.getCategory("BOK"));
        assertEquals(category2, categoryManager.getCategory("COM"));
    }

    @Test
    public void testGetCategoryList() {
        assertNull(categoryManager);
        categoryManager = new CategoryManager("data/Category.dat");
        categoryManager.addCategory(category1);
        categoryManager.addCategory(category2);
        List<Category> categoryList = categoryManager.getCategoryList();
        assertTrue(categoryList.contains(category1));
        assertTrue(categoryList.contains(category2));
        assertTrue(categoryList.contains(category3));
    }
}
