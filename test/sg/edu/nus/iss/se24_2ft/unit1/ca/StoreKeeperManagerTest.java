package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.storekeeper.StoreKeeper;
import sg.edu.nus.iss.se24_2ft.unit1.ca.storekeeper.StoreKeeperManager;
import util.TestUtil;

import static org.junit.Assert.*;

/* created by Navy Gao on 3/27 */

public class StoreKeeperManagerTest {
    private StoreKeeperManager storeKeeperManager;

    @Before
    public void setUp() {
        TestUtil.putData(TestUtil.STOREKEEPER_FILENAME, TestUtil.STOREKEEPER_STRING_LIST);

        storeKeeperManager = null;
    }

    @After
    public void tearDown() {
        TestUtil.deleteData(TestUtil.STOREKEEPER_FILENAME);
    }

    @Test
    public void testInitData() {
    }

    @Test
    public void testLogin() {
        assertNull(storeKeeperManager);
        storeKeeperManager = new StoreKeeperManager(TestUtil.STOREKEEPER_FILENAME);

        // reflect the member storeKeeperMap
        Field field;
        try {
            field = storeKeeperManager.getClass().getDeclaredField("storeKeeperMap");
            field.setAccessible(true);
            Object value = field.get(storeKeeperManager);

            assertTrue(value instanceof Map<?, ?>);

            StoreKeeper storeKeeper = new StoreKeeper("Gao Haijun", "123456");
            ((Map<String, StoreKeeper>) value).put("Gao Haijun", storeKeeper);
            field.setAccessible(false);

        } catch (SecurityException | NoSuchFieldException
                | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Exception thrown!");

        }

        assertTrue(storeKeeperManager != null);
        assertTrue(storeKeeperManager.login("Gao Haijun", "123456"));
        assertFalse(storeKeeperManager.login("none", null));
    }
}
