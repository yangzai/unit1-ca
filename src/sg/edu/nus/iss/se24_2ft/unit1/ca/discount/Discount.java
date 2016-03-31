package sg.edu.nus.iss.se24_2ft.unit1.ca.discount;

import java.util.Date;
import java.util.StringJoiner;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

/**
 * Created by Nguyen Trung on 27/2/16.
 */
public class Discount {
    private String code, requestedCode, description;
    private Date start; // Null for ALWAYS applicable
    private int period; // -1 for ALWAYS applicable
    private double percent; // or restrict to int
    private boolean memberOnly;

    public Discount(String requestedCode, String description,
                    Date start, int period, double percent, boolean memberOnly) {
        code = null;

        this.requestedCode = requestedCode.trim().toUpperCase();
        this.description = description.trim();
        this.start = start;
        this.period = period;
        this.percent = percent;
        this.memberOnly = memberOnly;
    }

    public String getCode() { return code; }

    public String getRequestedCode() { return requestedCode; }

    public String getDescription() { return description; }

    public Date getStart() { return start; }

    public int getPeriod() { return period; }

    public double getPercent() { return percent; }

    public boolean isMemberOnly() { return memberOnly; }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(",");
        return stringJoiner.add(code)
                .add(description)
                .add(Util.formatDateOrDefault(start, "ALWAYS"))
                .add(period > -1 ? Integer.toString(period) : "ALWAYS")
                .add(Double.toString(percent))
                .add(memberOnly ? "M" : "A")
                .toString();
    }

    public boolean isDiscountAvailable() {
        if (start == null) return true;

        Date today = new Date();
        if (start.after(today)) return false;

        if (period < 0) return true;

        Date end = Util.addDate(start, period);
        return !end.before(today);
    }

    //setters
    /*package*/ void setCode() { code = requestedCode; }
}
