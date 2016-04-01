package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.PublicCustomer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.MemberManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.DiscountManager;

public class DiscountManagerTest {

	private DiscountManager discountManager = null;
	private Discount discount = null;
	private Member member = null;
	private final String FILENAME = "test/data/Discounts.dat";
	private List<String> stringList;


	@Before
	public void setUp() throws Exception {
		// Preserved Test Data
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			stringList = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		discountManager = new DiscountManager(FILENAME);
		discount = new Discount("TEST11", "TEST 2 Desc", null, -1, 30, true);
	}

	@After
	public void tearDown() throws Exception {
		try {
			Files.write(Paths.get(FILENAME), stringList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInit() {
		assertNotNull(discountManager);
		assertNotNull(discountManager.getDiscountList());
		assertTrue(discountManager.getDiscountList().size() > 0);
	}

	@Test
	public void testGetList() {
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

	@Test
	public void testGetDiscount(){
		Customer customer = new Member("TESTMEMBERID1", "NGUYEN TRUNG");
		Customer publicCustomer = PublicCustomer.getInstance();
		Discount d = discountManager.getMaxDiscount(publicCustomer);
		assertTrue(!d.isMemberOnly());
		assertTrue(d.isDiscountAvailable());

		System.out.println(d.toString());
		d = discountManager.getMaxDiscount(customer);
		assertTrue(d.getCode().equals("MEMBER_FIRST"));
		assertTrue(d.isMemberOnly());
		assertTrue(d.isDiscountAvailable());

		System.out.println(d.toString());
	}
}
