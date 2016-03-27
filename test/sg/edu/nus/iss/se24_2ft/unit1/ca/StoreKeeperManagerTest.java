package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.Map;
import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.*;

/* created by Navy Gao on 3/27 */

public class StoreKeeperManagerTest extends TestCase {
	//test fixtures
	private StoreKeeper sk1 = null;
	private StoreKeeper sk2 = null;
	private StoreKeeperManager storeKeeperManager = null;

	
	@Before
	public void setUp() throws Exception {
		sk1 = new StoreKeeper("Gao Haijun", "123456");
		sk2 = new StoreKeeper("Navy", null); //permit no password for input by default
		//storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");
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
		//non-existed file name
		assertNull(storeKeeperManager);
		try {
			storeKeeperManager = new StoreKeeperManager("data/non-existed_file.dat");
			fail("error execution path");
		} catch (IOException e) { }
		
		if (null != storeKeeperManager)
			storeKeeperManager = null;
		
		//correct file name
		assertNull(storeKeeperManager);
		try {
			storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception thrown!");
		}
		assertTrue(storeKeeperManager != null);
		
	}

	@Test
	public void testInitData() {
		
	}
	
	@Test
	public void testLogin() {
		
		assertNull(storeKeeperManager);
		try {
			storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception thrown!");
		}
		
		//reflect the member storeKeeperMap
		Field field;
        try {
        	field = storeKeeperManager.getClass().getDeclaredField("storeKeeperMap");
        	field.setAccessible(true);
            Object value = (Object)field.get(storeKeeperManager);
            
            assertTrue(value instanceof Map<?,?>);
            
            StoreKeeper storeKeeper = new StoreKeeper("Gao Haijun", "123456");
            ((Map<String, StoreKeeper>)value).put("Gao Haijun", storeKeeper);
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
