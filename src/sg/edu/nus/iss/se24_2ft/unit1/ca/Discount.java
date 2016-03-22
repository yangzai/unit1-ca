package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.text.SimpleDateFormat;
import java.util.Date;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

/**
 * Created by Nguyen Trung on 27/2/16.
 */
public class Discount {
	private String code, requestedCode, description;
	private Date start; // Null for ALWAYS applicable
	private int period; // -1 for ALWAYS applicable
	private double percent; // or restrict to int
	private boolean memberOnly;

	public Discount(String requestedCode, String description, Date start, int period, double percent, boolean memberOnly) {
		code = null;
		this.requestedCode = requestedCode;
		this.description = description;
		this.start = start;
		this.period = period;
		this.percent = percent;
		this.memberOnly = memberOnly;
	}

	public String getCode() {
		return code;
	}

	public String getRequestedCode() {
		return requestedCode;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return start;
	}

	public int getPeriod() {
		return period;
	}

	public double getPercent() {
		return percent;
	}

	public boolean isMemeberOnly() { return memberOnly; }

	public String toString() { //TODO: refactor
		String strDate;
		if (this.start == null) {
			strDate = "ALWAYS";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			strDate = sdf.format(this.start);
		}
		String period = this.period == -1 ? "ALWAYS" : String.valueOf(this.period);
		return code + "," + description + "," + strDate + "," + period + "," + percent;
	}

	public boolean isDiscountAvailable() { //TODO: move to manager
		if (start != null) {
			Date today = new Date(System.currentTimeMillis());
			if (start.after(today)) {
				return false;
			}
			if (period != -1) {
				Date endDate = Utils.addDate(start, period);
				if (endDate.before(today)) {
					return false;
				}
			}
		}
		return true;
	}

	protected void setCode() { code = requestedCode; }
}
