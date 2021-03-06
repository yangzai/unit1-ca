package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.storekeeper.StoreKeeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/* created by Navy Gao on 3/27 */

public class StoreKeeperTest {
    private StoreKeeper sk1;
    private StoreKeeper sk2;

    @Before
    public void setUp() {
        sk1 = new StoreKeeper("Gao Haijun", "123456");
        sk2 = new StoreKeeper("Navy", null); // permit no password for input by
                                             // default
    }

    @After
    public void tearDown() {
    }

    // test for store keeper class
    @Test
    public void testStoreKeeper() {
        assertEquals("Gao Haijun", sk1.getName());
        assertEquals("123456", sk1.getPassword());

        assertEquals("Navy", sk2.getName());
        assertNull(sk2.getPassword());
    }

    @Test
    public void testGetName() {
        assertEquals("Gao Haijun", sk1.getName());
        assertEquals("Navy", sk2.getName());
    }

    @Test
    public void testGetPassword() {
        assertEquals("Gao Haijun", sk1.getName());
        assertNull(sk2.getPassword());
    }
}
