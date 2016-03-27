package sg.edu.nus.iss.se24_2ft.unit1.ca;

/* created by Navy Gao on 3/26 */

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;
import java.util.List;
import org.junit.Test;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.*;
public class VendorManagerTest extends TestCase {
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
	public void testVendorManager() {
      VendorManager vm = null;
      try {
          vm = new VendorManager("data");
          vm.getVendorListByCategoryId("MUG")
                  .stream()
                  .map(v -> v.getCategoryId()+','+v.getName()+','+v.getDescription())
                  .forEach(System.out::println);
      } catch (IOException e) {
          e.printStackTrace();
          fail();
      }
      assertTrue(vm!=null);
      vm = null;
      //possible memory leak here
	}

	@Test
	public void testGetVendorListByCategoryId() {
		//load data from mug file
	      VendorManager vm = null;
	      List<Vendor> listVendor = null;
	      try {
	          vm = new VendorManager("data");
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	      assertTrue(vm!=null);
	      
	      listVendor = vm.getVendorListByCategoryId("MUG");
	      assertTrue(listVendor!=null);
	}


}
