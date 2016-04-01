package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.MemberManager;

/**
 * Created by chenyao on 27/3/16
 */
public class MemberManagerTest extends TestCase {
    // test fixtures
    private Member member1 = null;
    private Member member2 = null;
    private Member member3 = null;
    private MemberManager memberManager = null;
	private final String FILENAME = "test/data/Members.dat";
	private List<String> stringList;
	
    @Before
    public void setUp() throws Exception {
        member1 = new Member("E0015280", "Gao Haijun");
        member2 = new Member("E0015270", "Chen Yao");
        member3 = new Member("R64565FG4", "Ang Lee");
		// Preserved Test Data
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			stringList = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @After
    public void tearDown() throws Exception {
        member1 = null;
        member2 = null;
        memberManager = null;
		try {
			Files.write(Paths.get(FILENAME), stringList);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // Test for MemberManger
    @Test
    public void testMemberManager() {
        // correct file name
        assertNull(memberManager);
        memberManager = new MemberManager(FILENAME);
        assertTrue(memberManager != null);
    }

    @Test
    public void testGetMember() {
        assertNull(memberManager);
        memberManager = new MemberManager(FILENAME);
        assertTrue(memberManager != null);
        assertTrue(isEqual(member3, memberManager.getMember("R64565FG4")));
    }

    @Test
    public void testAddMember() {
        assertNull(memberManager);
        memberManager = new MemberManager(FILENAME);
        memberManager.addMember(member1);
        memberManager.addMember(member2);
        assertTrue(memberManager != null);
        assertTrue(isEqual(member1, memberManager.getMember("E0015280")));
        assertTrue(isEqual(member2,  memberManager.getMember("E0015270")));
    }

    @Test
    public void testGetMemberList() {
        assertNull(memberManager);
        memberManager = new MemberManager(FILENAME);
        memberManager.addMember(member1);
        memberManager.addMember(member2);
        List<Member> memberList = memberManager.getMemberList();
        assertTrue(memberManager != null);
        assertTrue(memberList.contains(member1));
        assertTrue(memberList.contains(member2));
    }
    
	private boolean isEqual(Member member1, Member member2) {
		if (member1 == member2) return true;
		if (!member1.getRequestedId().equals(member2.getRequestedId())) return false;
		if (member1.getName()==null){
			if (member2.getName()!=null) return false;
		} else {
			if (!member1.getName().equals(member2.getName())) return false;
		}
		if (member1.getLoyaltyPoint()!= member2.getLoyaltyPoint()) return false;
		return true;
	}
}
