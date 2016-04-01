package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.vendor.Vendor;

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

        assertTrue(isEqual(v1, new Vendor("MUG", "Office Sovenirs", "One and only Office Sovenirs")));
        assertTrue(isEqual(v2, new Vendor("MUF", "ArtWorks Stationary Store", null)));

        assertFalse(isEqual(v1, v2));
        assertFalse(isEqual(v2, v1));

        Vendor v3 = new Vendor("MUF", "ArtWorks Stationary Store", "All kinds of Stationary and Gifts");
        assertFalse(isEqual(v2, v3));
        assertFalse(isEqual(v3, v2));
    }
    
	private boolean isEqual(Vendor vendor1, Vendor vendor2) {
		if (vendor1 == vendor2) return true;
		if (!vendor1.getCategoryId().equals(vendor2.getCategoryId())) return false;
		if (vendor1.getName()==null){
			if (vendor2.getName()!=null) return false;
		} else {
			if (!vendor1.getName().equals(vendor2.getName())) return false;
		}
		if (vendor1.getDescription()==null){
			if (vendor2.getDescription()!=null) return false;
		} else {
			if (!vendor1.getDescription().equals(vendor2.getDescription())) return false;
		}
		return true;
	}
}
