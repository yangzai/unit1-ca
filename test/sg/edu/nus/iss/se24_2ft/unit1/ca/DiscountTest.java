package sg.edu.nus.iss.se24_2ft.unit1.ca;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;

public class DiscountTest {
    private Discount discount1, discount2;

    @Before
    public void setUp() {
        Calendar calendar = new GregorianCalendar(2016, 2, 27);
        Date date = calendar.getTime();
        discount1 = new Discount("TEST1", "TEST 1 Desc", date, 1, 10, true);
        discount2 = new Discount("TEST2", "TEST 2 Desc", null, -1, 30, true);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetDiscountInfo() {
        assertTrue(discount1.getPeriod() == 1);
        assertTrue(!discount1.isDiscountAvailable());
        assertTrue(discount1.getPercent() == 10);

        assertTrue(discount2.getPeriod() == -1);
        assertTrue(discount2.isDiscountAvailable());
        assertTrue(discount2.getPercent() == 30);
    }

    @Test
    public void testToString() {
        String str1 = "null,TEST 1 Desc,2016-03-27,1,10.0,M";
        String str2 = "null,TEST 2 Desc,ALWAYS,ALWAYS,30.0,M";
        assertTrue(discount1.toString().trim().equals(str1));
        assertTrue(discount2.toString().trim().equals(str2));
    }
}
