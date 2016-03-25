package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;

public class StoreKeeperTest {

	@Test
	public void testStoreKeeperManager() {
		StoreKeeperManager storeKeeperManager = null;
		try {
			storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(storeKeeperManager != null);
	}

	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}

}
