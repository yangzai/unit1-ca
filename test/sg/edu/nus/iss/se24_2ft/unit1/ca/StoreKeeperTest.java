package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.*;

public class StoreKeeperTest extends TestCase {
	//test fixtures
	private StoreKeeper sk1 = null;
	private StoreKeeper sk2 = null;
	private StoreKeeperManager storeKeeperManager = null;

	
	@Before
	public void setUp() throws Exception {
		sk1 = new StoreKeeper("Gao Haijun", "123456");
		sk2 = new StoreKeeper("Chen Yao", null); //permit no password for input by default
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
		assertTrue(storeKeeperManager != null);
		assertTrue(storeKeeperManager.login("Gao Haijun", "123456"));
		assertFalse(storeKeeperManager.login("none", null));
	}
	
	// test for store keeper class
	@Test
	public void testStoreKeeper() {
		assertEquals("Gao Haijun", sk1.getName());
		assertEquals("123456", sk1.getPassword());
		
		assertEquals("Chen Yao", sk2.getName());
		assertNull(sk2.getPassword());
	}
	
	@Test
	public void testGetName() {
		assertEquals("Gao Haijun", sk1.getName());
		assertEquals("Chen Yao", sk2.getName());
	}
	
	@Test
	public void testGetPassword() {
		assertEquals("Gao Haijun", sk1.getName());
		assertNull(sk2.getPassword());
	}

}
