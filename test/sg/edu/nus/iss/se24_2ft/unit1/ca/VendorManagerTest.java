package sg.edu.nus.iss.se24_2ft.unit1.ca;

/* created by Navy Gao on 3/26 */

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class VendorManagerTest extends TestCase {
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
    public void testVendorManager() {
        VendorManager vm = new VendorManager("data");
            vm.getVendorListByCategoryId("MUG").stream()
                    .map(v -> v.getCategoryId() + ',' + v.getName() + ',' + v.getDescription())
                    .forEach(System.out::println);
        //TODO: test else something here
    }

    @Test
    public void testGetVendorListByCategoryId() {
        // load data from mug file
        VendorManager vm = new VendorManager("data");
        List<Vendor> listVendor = vm.getVendorListByCategoryId("MUG");
        assertTrue(listVendor != null);
    }
}
