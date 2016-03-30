package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class VendorTest extends TestCase {
    // test fixtures
    private Vendor v1 = null;
    private Vendor v2 = null;

    @Before
    public void setUp() throws Exception {
        v1 = new Vendor("MUG", "Office Sovenirs", "One and only Office Sovenirs");
        v2 = new Vendor("MUF", "ArtWorks Stationary Store", null);
    }

    @After
    public void tearDown() throws Exception {
        v1 = null;
        v2 = null;
    }

    @Test
    public void testGetCategoryId() {

        assertEquals("MUG", v1.getCategoryId());
        assertEquals("MUF", v2.getCategoryId());
    }

    @Test
    public void testGetName() {
        assertEquals("Office Sovenirs", v1.getName());
        assertEquals("ArtWorks Stationary Store", v2.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("One and only Office Sovenirs", v1.getDescription());
        assertNull(v2.getDescription());
    }

    @Test
    public void testEquals() {
        assertSame(v1, v1);
        assertSame(v2, v2);

        assertEquals(v1, new Vendor("MUG", "Office Sovenirs", "One and only Office Sovenirs"));
        assertEquals(v2, new Vendor("MUF", "ArtWorks Stationary Store", null));

        assertFalse(v1.equals(v2));
        assertFalse(v2.equals(v1));

        Vendor v3 = new Vendor("MUF", "ArtWorks Stationary Store", "All kinds of Stationary and Gifts");
        assertFalse(v2.equals(v3));
        assertFalse(v3.equals(v2));
    }
}
