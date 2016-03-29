package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.DiscountManager;

public class DiscountManagerTest {

	private DiscountManager discountManager = null;
	private Discount discount = null;

	@Before
	public void setUp() throws Exception {
		discountManager = new DiscountManager("data/Discounts.dat");
		discount = new Discount("TEST10", "TEST 2 Desc", null, -1, 30, true);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInit() {
		assertNotNull(discountManager);
		assertNotNull(discountManager.getDiscountList());
		assertTrue(discountManager.getDiscountList().size() > 0);
	}

	@Test
	public void testAddDiscount() {
		int lastSize = discountManager.getDiscountList().size();
		assertNotNull(discount);
		assertTrue(discountManager.addDiscount(discount) == true);
		assertTrue(discountManager.getDiscountList().size() == lastSize + 1);
		Discount addedDiscount = discountManager.getDiscountList().get(lastSize);
		assertTrue(addedDiscount.getCode().equals(discount.getRequestedCode()));
		assertTrue(discountManager.addDiscount(discount) == false);

		
	}

}
