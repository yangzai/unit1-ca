package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.MemberManager;
import util.TestUtil;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by chenyao on 27/3/16
 */
public class MemberManagerTest {
    private Member member1;
    private Member member2;
    private Member member3;
    private MemberManager memberManager;
	
    @Before
    public void setUp() {
        TestUtil.putData(TestUtil.MEMBER_FILENAME, TestUtil.MEMBER_STRING_LIST);

        memberManager = null;

        member1 = new Member("E0015280", "Gao Haijun");
        member2 = new Member("E0015270", "Chen Yao");
        member3 = new Member("R64565FG4", "Ang Lee");
    }

    @After
    public void tearDown() {
        TestUtil.deleteData(TestUtil.MEMBER_FILENAME);
    }

    @Test
    public void testGetMember() {
        assertNull(memberManager);
        memberManager = new MemberManager(TestUtil.MEMBER_FILENAME);
        assertTrue(isEqual(member3, memberManager.getMember("R64565FG4")));
    }

    @Test
    public void testAddMember() {
        assertNull(memberManager);
        memberManager = new MemberManager(TestUtil.MEMBER_FILENAME);
        memberManager.addMember(member1);
        memberManager.addMember(member2);
        assertTrue(memberManager != null);
        assertTrue(isEqual(member1, memberManager.getMember("E0015280")));
        assertTrue(isEqual(member2, memberManager.getMember("E0015270")));
    }

    @Test
    public void testGetMemberList() {
        assertNull(memberManager);
        memberManager = new MemberManager(TestUtil.MEMBER_FILENAME);
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
        return member1.getLoyaltyPoint() == member2.getLoyaltyPoint();
    }
}
