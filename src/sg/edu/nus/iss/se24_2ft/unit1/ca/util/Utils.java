package sg.edu.nus.iss.se24_2ft.unit1.ca.util;

import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static Date addDate(Date date, int period) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.DATE, period);
        return calendar.getTime();
    }

    public static int parseIntOrDefault(String string, int defaultValue) {
        try { return Integer.parseInt(string); }
        catch (NumberFormatException e) { return defaultValue; }
    }

    public static double parseDoubleOrDefault(String string, double defaultValue) {
        try { return Double.parseDouble(string); }
        catch (NumberFormatException e) { return defaultValue; }
    }

    public static String[] splitCsv(String string) {
        if (string == null) return new String[0];
        return string.split(",");
    }
}