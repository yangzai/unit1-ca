package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/* created by Navy Gao on 3/27 */

public class StoreKeeperManagerTest extends TestCase {
    // test fixtures
    private StoreKeeper sk1 = null;
    private StoreKeeper sk2 = null;
    private StoreKeeperManager storeKeeperManager = null;

    @Before
    public void setUp() throws Exception {
        sk1 = new StoreKeeper("Gao Haijun", "123456");
        sk2 = new StoreKeeper("Navy", null); // permit no password for input by
                                             // default
    }

    @After
    public void tearDown() throws Exception {
        sk1 = null;
        sk2 = null;
        storeKeeperManager = null;
    }

    // Test for store keeper manager
    @Test
    public void testStoreKeeperManager() {
        assertNull(storeKeeperManager);
        storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");
        assertTrue(storeKeeperManager != null);
    }

    @Test
    public void testInitData() {

    }

    @Test
    public void testLogin() {
        assertNull(storeKeeperManager);
        storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");

        // reflect the member storeKeeperMap
        Field field;
        try {
            field = storeKeeperManager.getClass().getDeclaredField("storeKeeperMap");
            field.setAccessible(true);
            Object value = (Object) field.get(storeKeeperManager);

            assertTrue(value instanceof Map<?, ?>);

            StoreKeeper storeKeeper = new StoreKeeper("Gao Haijun", "123456");
            ((Map<String, StoreKeeper>) value).put("Gao Haijun", storeKeeper);
            field.setAccessible(false);

        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception thrown!");

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception thrown!");

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception thrown!");
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception thrown!");
        }

        assertTrue(storeKeeperManager != null);
        assertTrue(storeKeeperManager.login("Gao Haijun", "123456"));
        assertFalse(storeKeeperManager.login("none", null));
    }
}
