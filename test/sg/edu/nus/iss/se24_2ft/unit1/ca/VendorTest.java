package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.vendor.Vendor;
import util.TestUtil;

import java.util.List;

import static org.junit.Assert.*;

public class VendorTest {
    private Vendor v1;
    private Vendor v2;
    private CategoryManager cm;

    @Before
    public void setUp() {
        TestUtil.putData(TestUtil.CATEGORY_FILENAME, TestUtil.CATEGORY_STRING_LIST);

        cm = new CategoryManager(TestUtil.CATEGORY_FILENAME);
        v1 = new Vendor("Office Sovenirs", "One and only Office Sovenirs");
        v1.addCategory(cm.getCategory("MUG"));
        v2 = new Vendor("ArtWorks Stationary Store", null);
        v2.addCategory(cm.getCategory("STA"));
    }

    @After
    public void tearDown() {
        TestUtil.deleteData(TestUtil.CATEGORY_FILENAME);
    }

    @Test
    public void testGetCategoryId() {
        assertEquals("MUG", v1.getCategoryList().get(0).getId());
        assertEquals("STA", v2.getCategoryList().get(0).getId());
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

        Vendor v3 = new Vendor("Office Sovenirs", "One and only Office Sovenirs");
        Vendor v4 = new Vendor("ArtWorks Stationary Store", null);
        v3.addCategory(cm.getCategory("MUG"));
        v4.addCategory(cm.getCategory("STA"));
        assertTrue(isEqual(v1, v3));
        assertTrue(isEqual(v2, v4));

        assertFalse(isEqual(v1, v2));
        assertFalse(isEqual(v2, v1));

        Vendor v5 = new Vendor("ArtWorks Stationary Store", "All kinds of Stationary and Gifts");
        v5.addCategory(cm.getCategory("STA"));
        assertFalse(isEqual(v2, v5));
        assertFalse(isEqual(v5, v2));
    }
    
    private boolean isEqual(Vendor vendor1, Vendor vendor2) {
        if (vendor1 == vendor2) return true;
        List<Category> v1CategoryList = vendor1.getCategoryList();
        List<Category> v2CategoryList = vendor2.getCategoryList();
        boolean isV1inV2 = v1CategoryList.stream().allMatch(c1 ->
                v2CategoryList.stream().anyMatch(c2 -> c1 == c2)
        );

        if (!isV1inV2 || v1CategoryList.size() != v2CategoryList.size())
            return false;
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