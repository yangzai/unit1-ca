package sg.edu.nus.iss.se24_2ft.unit1.ca.printer;

/**
 * Created by yangzai on 28/2/16.
 */
public abstract class Printer {
    private static int count = 0;
    private int id;

    public Printer() {
        id = ++count;
    }

    public abstract String getTag();

    public static void print(String message) {
//        System.out.println(getTag() + ' ' + id + ": " + message);
    	System.out.println(message);
    }
}
