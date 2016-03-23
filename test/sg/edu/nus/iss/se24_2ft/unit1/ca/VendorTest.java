package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class VendorTest {

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
      }
      assertTrue(vm!=null);
	}

	@Test
	public void testGetVendorListByCategoryId() {
		fail("Not yet implemented");
	}

}
