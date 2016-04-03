package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;

import static org.junit.Assert.*;

/**
 * Created by chenyao on 26/3/16
 */
public class MemberTest {
    private Member member1;
    private Member member2;

    @Before
    public void setUp() {
        member1 = new Member("E0015280", null);
        member2 = new Member("E0015270", "Chen Yao");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMember() {
        assertEquals("E0015280", member1.getRequestedId());
        assertNull(member1.getName());

        assertEquals("E0015270", member2.getRequestedId());
        assertEquals("Chen Yao", member2.getName());
    }

    @Test
    public void testGetRequestedId() {
        assertEquals("E0015280", member1.getRequestedId());
        assertEquals("E0015270", member2.getRequestedId());
    }

    @Test
    public void testGetName() {
        assertNull(member1.getName());
        assertEquals("Chen Yao", member2.getName());
    }

    @Test
    public void testEquals() {
        assertSame(member1, member1);
        assertSame(member2, member2);

        assertTrue(isEqual(member1, new Member("E0015280", null)));
        assertTrue(isEqual(member2, new Member("E0015270", "Chen Yao")));

        assertFalse(isEqual(member1, member2));
        assertFalse(isEqual(member2, member1));

        Member member3 = new Member("E0015270", "Gao Haijun");
        assertFalse(isEqual(member3, member2));
        assertFalse(isEqual(member2, member3));
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
