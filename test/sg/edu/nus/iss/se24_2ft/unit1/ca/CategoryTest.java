package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;

/**
 * Created by chenyao on 27/3/16
 */
public class CategoryTest extends TestCase {
    // Test Fixtures
    private Category category1 = null;
    private Category category2 = null;

    @Before
    public void setUp() throws Exception {
        category1 = new Category("BOK", "Book");
        category2 = new Category("CLO", null);
    }

    @After
    public void tearDown() throws Exception {
        category1 = null;
        category2 = null;
    }

    @Test
    public void testCategory() {
        assertEquals("BOK", category1.getRequestedId());
        assertEquals("Book", category1.getName());

        assertEquals("CLO", category2.getRequestedId());
        assertNull(category2.getName());
    }

    @Test
    public void testGetRequestedId() {
        assertEquals("BOK", category1.getRequestedId());
        assertEquals("CLO", category2.getRequestedId());
    }

    @Test
    public void testGetName() {
        assertEquals("Book", category1.getName());
        assertNull(category2.getName());
    }

    @Test
    public void testEquals() {
        assertSame(category1, category1);
        assertSame(category2, category2);

        assertTrue(isEqual(category1, new Category("BOK", "Book")));
        assertTrue(category2.getRequestedId().equals(new Category("CLO", null).getRequestedId()));

        assertFalse(isEqual(category1,category2));
        assertFalse(isEqual(category2,category1));

        Category category3 = new Category("CLO", "Book");
        assertFalse(isEqual(category3,category2));
    }
    
	private boolean isEqual(Category category1, Category category2) {
		return (category1.getRequestedId().equals(category2.getRequestedId())) && (category1.getName().equals(category2.getName()));
	}
}
