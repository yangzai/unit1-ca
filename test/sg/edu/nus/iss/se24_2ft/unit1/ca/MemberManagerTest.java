package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
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

    @Before
    public void setUp() throws Exception {
        member1 = new Member("E0015280", "Gao Haijun");
        member2 = new Member("E0015270", "Chen Yao");
        member3 = new Member("R64565FG4", "Ang Lee");
    }

    @After
    public void tearDown() throws Exception {
        member1 = null;
        member2 = null;
        memberManager = null;
    }

    // Test for MemberManger
    @Test
    public void testMemberManager() {
        // correct file name
        assertNull(memberManager);
        memberManager = new MemberManager("data/Members.dat");
        assertTrue(memberManager != null);
    }

    @Test
    public void testGetMember() {
        assertNull(memberManager);
        memberManager = new MemberManager("data/Members.dat");
        assertTrue(memberManager != null);
        assertEquals(member3, memberManager.getMember("R64565FG4"));
    }

    @Test
    public void testAddMember() {
        assertNull(memberManager);
        memberManager = new MemberManager("data/Members.dat");
        memberManager.addMember(member1);
        memberManager.addMember(member2);
        assertTrue(memberManager != null);
        assertEquals(member1, memberManager.getMember("E0015280"));
        assertEquals(member2, memberManager.getMember("E0015270"));
    }

    @Test
    public void testGetMemberList() {
        assertNull(memberManager);
        memberManager = new MemberManager("data/Members.dat");
        memberManager.addMember(member1);
        memberManager.addMember(member2);
        List<Member> memberList = memberManager.getMemberList();
        assertTrue(memberManager != null);
        assertTrue(memberList.contains(member1));
        assertTrue(memberList.contains(member2));
        assertTrue(memberList.contains(member3));
    }
}
