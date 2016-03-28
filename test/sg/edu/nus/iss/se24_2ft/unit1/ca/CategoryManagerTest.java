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
    @Test
    public void testCategoryManager() {
        // non-existed file name
        assertNull(categoryManager);
        try {
            categoryManager = new CategoryManager("data/non-existed_file.dat");
            fail("error path");
        } catch (IOException e) {
        }

        if (null != categoryManager)
            categoryManager = null;

        // correct file name
        assertNull(categoryManager);
        try {
            categoryManager = new CategoryManager("data/Category.dat");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(categoryManager != null);
    }

    @Test
    public void testGetCategory() {
        assertNull(categoryManager);
        try {
            categoryManager = new CategoryManager("data/Category.dat");

        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown!");
        }
        assertTrue(categoryManager != null);
        assertEquals(category3.getRequestedId(), categoryManager.getCategory("CLO").getRequestedId());
        // System.out.println(categoryManager.getCategory("CLO").getRequestedId());
        // System.out.println(categoryManager.getCategory("CLO").getName());
        // System.out.println(category3.getName());
        assertEquals(category3.getName(), categoryManager.getCategory("CLO").getName());
    }

    @Test
    public void testAddCategory() {
        assertNull(categoryManager);
        try {
            categoryManager = new CategoryManager("data/Category.dat");
            categoryManager.addCategory(category1);
            categoryManager.addCategory(category2);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown");
        }
        assertTrue(categoryManager != null);
        assertEquals(category1, categoryManager.getCategory("BOK"));
        assertEquals(category2, categoryManager.getCategory("COM"));
    }

    @Test
    public void testGetCategoryList() {
        assertNull(categoryManager);
        try {
            categoryManager = new CategoryManager("data/Category.dat");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown");
        }
        List<Category> categoryList = categoryManager.getCategoryList();
        assertTrue(categoryManager != null);
        assertEquals(category3.getRequestedId(), categoryManager.getCategory("CLO").getRequestedId());
        assertEquals(category3.getName(), categoryManager.getCategory("CLO").getName());
        assertEquals(category1, categoryList.get(4));
        assertEquals(category2, categoryList.get(5));
    }
}
