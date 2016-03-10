package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

public class DiscountManager {
	private DiscountManager _instance = null;
	private String filename;
	private List<Discount> discountList = null;

	private DiscountManager() {
		// TODO Auto-generated constructor stub
		discountList = new ArrayList<Discount>();
		filename = "Discounts.dat";
	}

	public DiscountManager getInstance() {
		if (_instance == null) {
			_instance = new DiscountManager();
		}
		return _instance;
	}

	public void initData() {
		List<List<String>> _list = new ArrayList(); // This list gets from CSV
													// File
		for (List<String> params : _list) {
			Date startDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				startDate = sdf.parse(params.get(3));
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

			Discount discount = new Discount();
			discount.setCode(params.get(0));
			discount.setDescription(params.get(1));
			discount.setStartDate(startDate);
			discount.setPeriod(params.get(4).equals("ALWAYS") ? -1 : Integer.parseInt(params.get(3)));
			discount.setPercent(Float.parseFloat(params.get(5)));
			discount.setOnlyForMember(params.get(6).equals("M"));
			discountList.add(discount);
		}
	}

	
	public double getDiscountForNonMember() {
		double maxDiscount = 0;
		for (Discount discount : discountList) {
			if (!discount.isOnlyForMember() && discount.isDiscountAvailable() && discount.getPercent() > maxDiscount) {
				maxDiscount = discount.getPercent();
			}
		}
		return maxDiscount;
	}

	public double getDiscountForMember(boolean firstTimePurchase) {
		double firsttimeDiscount = 0;
		double normalDiscount = 0;
		for (Discount discount : discountList) {
			if (discount.isOnlyForMember() && discount.isDiscountAvailable()) {
				if (discount.getPercent() > firsttimeDiscount) {
					firsttimeDiscount = discount.getPercent();
				}
			}
		}
		for (Discount discount : discountList) {
			if (discount.isOnlyForMember() && discount.isDiscountAvailable()) {
				if (discount.getPercent() < firsttimeDiscount) {
					normalDiscount = discount.getPercent();
				}
			}
		}
		if (firstTimePurchase)
			return firsttimeDiscount;
		else
			return normalDiscount;
	}

}
