package sg.edu.nus.iss.se24_2ft.unit1.ca;

/* created by Navy Gao on 3/26 */

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.*;
public class VendorTest extends TestCase {
	//test fixtures
	private Vendor v1 = null;
	private Vendor v2 = null;
	
	@Before
	public void setUp() throws Exception {
		v1 = new Vendor("MUG","Office Sovenirs","One and only Office Sovenirs");
		v2 = new Vendor("MUF","ArtWorks Stationary Store",null);
	}
	
	@After
	public void tearDown() throws Exception {
		v1 = null;
		v2 = null;
	}

	@Test
	public void testGetCategoryId() {
		
		assertEquals("MUG", v1.getCategoryId());
		assertEquals("MUF", v2.getCategoryId());
	}
	
	@Test
	public void testGetName() {
		assertEquals("Office Sovenirs", v1.getName());
		assertEquals("ArtWorks Stationary Store", v2.getName());
	}
	
	@Test
	public void testGetDescription() {
		assertEquals("One and only Office Sovenirs", v1.getDescription());
		assertNull(v1.getDescription());
	}
}
