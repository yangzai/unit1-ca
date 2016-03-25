package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.MemberManager;

public class MemberTest {

	@Test
	public void testMemberManager() {
		MemberManager memberManager = null;
		try {
			memberManager = new MemberManager("data/Members.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(memberManager!=null);
	}

	@Test
	public void testAddMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMemberList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTableModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testStore() {
		fail("Not yet implemented");
	}

}
