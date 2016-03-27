package sg.edu.nus.iss.se24_2ft.unit1.ca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;

public class MemberTest extends TestCase {
    // Test Fixtures
    private Member member1 = null;
    private Member member2 = null;

    @Before
    public void setUp() throws Exception {
        member1 = new Member("E0015280", null);
        member2 = new Member("E0015270", "Chen Yao");
    }

    @After
    public void tearDown() throws Exception {
        member1 = null;
        member2 = null;
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

        assertEquals(member1, new Member("E0015280", null));
        assertEquals(member2, new Member("E0015270", "Chen Yao"));

        assertFalse(member1.equals(member2));
        assertFalse(member2.equals(member1));

        Member member3 = new Member("E0015270", "Gao Haijun");
        assertFalse(member3.equals(member2));
        assertFalse(member2.equals(member3));
    }
}
