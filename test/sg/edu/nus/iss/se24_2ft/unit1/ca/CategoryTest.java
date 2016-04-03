package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;

import static org.junit.Assert.*;

/**
 * Created by chenyao on 27/3/16
 */
public class CategoryTest {
    private Category category1;
    private Category category2;

    @Before
    public void setUp() {
        category1 = new Category("BOK", "Book");
        category2 = new Category("CLO", null);
    }

    @After
    public void tearDown() {
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
        assertTrue(isEqual(category2, new Category("CLO", null)));

        assertFalse(isEqual(category1,category2));
        assertFalse(isEqual(category2,category1));

        Category category3 = new Category("CLO", "Book");
        assertFalse(isEqual(category3,category2));
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
