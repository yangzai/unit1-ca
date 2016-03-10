package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.util.Date;

/**
 * Created by Nguyen Trung on 27/2/16.
 */
public class Discount {
    private String code, description;
    private Date startDate; // Null for ALWAYS applicable
    private int period; //-1 for ALWAYS applicable
    private double percent; //or restrict to int
    private boolean isOnlyForMember;

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

	public boolean isOnlyForMember() {
		return isOnlyForMember;
	}

	public void setOnlyForMember(boolean isOnlyForMember) {
		this.isOnlyForMember = isOnlyForMember;
	}
    
    
    

}
