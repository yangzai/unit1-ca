package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.text.SimpleDateFormat;
import java.util.Date;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

/**
 * Created by Nguyen Trung on 27/2/16.
 */
public class Discount {
	protected String code, description;
	protected Date startDate; // Null for ALWAYS applicable
	protected int period; // -1 for ALWAYS applicable
	protected double percent; // or restrict to int

	public Discount() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String toString() {
		String strDate;
		if (this.startDate == null) {
			strDate = "ALWAYS";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			strDate = sdf.format(this.startDate);
		}
		String period = this.period == -1 ? "ALWAYS" : String.valueOf(this.period);
		return code + "," + description + "," + strDate + "," + period + "," + percent;
	}

	public boolean isDiscountAvailable() {
		if (startDate != null) {
			Date today = new Date(System.currentTimeMillis());
			if (startDate.after(today)) {
				return false;
			}
			if (period != -1) {
				Date endDate = Utils.addDate(startDate, period);
				if (endDate.before(today)) {
					return false;
				}
			}
		}
		return true;
	}

}
