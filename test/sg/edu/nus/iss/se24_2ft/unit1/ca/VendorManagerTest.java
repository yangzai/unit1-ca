package sg.edu.nus.iss.se24_2ft.unit1.ca;

/* created by Navy Gao on 3/26 */

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.vendor.Vendor;
import sg.edu.nus.iss.se24_2ft.unit1.ca.vendor.VendorManager;
import util.TestUtil;

import static org.junit.Assert.assertTrue;

public class VendorManagerTest {
    private CategoryManager cm;
    @Before
    public void setUp() {
        TestUtil.putData(TestUtil.CATEGORY_FILENAME, TestUtil.CATEGORY_STRING_LIST);
        TestUtil.putData(TestUtil.VENDOR_MUG_FILENAME, TestUtil.VENDOR_MUG_STRING_LIST);
        TestUtil.putData(TestUtil.VENDOR_CLO_FILENAME, TestUtil.VENDOR_CLO_STRING_LIST);

        cm = new CategoryManager(TestUtil.CATEGORY_FILENAME);
    }

    @After
    public void tearDown() {
        TestUtil.deleteData(TestUtil.CATEGORY_FILENAME);
        TestUtil.deleteData(TestUtil.VENDOR_MUG_FILENAME);
        TestUtil.deleteData(TestUtil.VENDOR_CLO_FILENAME);
    }

    @Test
    public void testGetVendorListByCategoryId() {
        // load data from mug file
        VendorManager vm = new VendorManager(TestUtil.DATA_PATH, cm);
        List<Vendor> listVendorMUG = vm.getVendorListByCategoryId("MUG");
        assertTrue(listVendorMUG != null);
        List<Vendor> listVendorCLO = vm.getVendorListByCategoryId("CLO");
        assertTrue(listVendorCLO != null);
        List<Vendor> listVendorSTA = vm.getVendorListByCategoryId("STA");
        assertTrue(listVendorSTA == null);
    }
}
