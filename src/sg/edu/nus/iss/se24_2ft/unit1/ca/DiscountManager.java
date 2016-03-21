package sg.edu.nus.iss.se24_2ft.unit1.ca;

//@author: Nguyen Trung
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVWriter;

public class DiscountManager {
	private static DiscountManager _instance = null;
	private String filename;
	private List<Discount> discountList = null;
	private List<String> storingList = null;
	private double FIRST_TIME_MEMBER_DISCOUNT;

	private DiscountManager() {
		// TODO Auto-generated constructor stub
		discountList = new ArrayList<Discount>();
		storingList = new ArrayList<String>();
		discountList.size();
		filename = "data/Discounts.dat";
		this.initData();
	}

	public static DiscountManager getInstance() {
		if (_instance == null) {
			_instance = new DiscountManager();
		}
		return _instance;
	}

	private void initData() {
		List<ArrayList<String>> _list = new ArrayList<ArrayList<String>>(); // This
																			// list
																			// gets
																			// from
																			// CSV
		CSVReader reader = null;
		try {
			reader = new CSVReader(filename);
			while (reader.readRecord()) {
				ArrayList<String> discountStrList = reader.getValues();
				_list.add(discountStrList);
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			if (reader != null)
				reader.close();
		}

		for (int i = 0; i < _list.size(); i++) {
			ArrayList<String> params = _list.get(i);
			Date startDate = null;
			if (!params.get(2).equals("ALWAYS")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					startDate = sdf.parse(params.get(2));
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
			if (params.get(0).equals("MEMBER_FIRST")) {
				this.FIRST_TIME_MEMBER_DISCOUNT = Double.parseDouble(params.get(4));
			}
			Discount discount = null;
			if (params.get(5).equals("M")) {
				discount = new MemberDiscount();
			} else {
				discount = new CustomerDiscount();
			}
			discount.setCode(params.get(0));
			discount.setDescription(params.get(1));
			discount.setStartDate(startDate);
			discount.setPeriod(params.get(3).equals("ALWAYS") ? -1 : Integer.parseInt(params.get(3)));
			discount.setPercent(Float.parseFloat(params.get(4)));
			discountList.add(discount);
		}
	}

	public double getDiscountForCustomer() {
		double maxDiscount = 0;
		for (Discount discount : discountList) {
			boolean customerDiscount = discount instanceof CustomerDiscount;
			if (discount instanceof CustomerDiscount && discount.isDiscountAvailable()
					&& discount.getPercent() > maxDiscount) {
				maxDiscount = discount.getPercent();
			}
		}
		return maxDiscount;
	}

	public double getDiscountForMember(boolean firstTimePurchase) {
		double normalDiscount = 0;
		if (firstTimePurchase) {
			normalDiscount = this.FIRST_TIME_MEMBER_DISCOUNT;
		}
		for (Discount discount : discountList) {
			if (discount.isDiscountAvailable()) {
				if (discount.getPercent() > normalDiscount) {
					normalDiscount = discount.getPercent();
				}
			}
		}
		return normalDiscount;
	}

	public List<Discount> getDiscountList() {
		return this.discountList;
	}

	public void addDiscount(Discount discount) {
		this.discountList.add(discount);
		writeToFile();
	}

	private void writeToFile() {
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(this.filename);
			Iterator<Discount> i = this.discountList.iterator();
			while (i.hasNext()) {
				Discount discount = i.next();
				writer.writeRecord(discount.toString().split(","));
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			if (writer != null)
				writer.close();
		}

	}

}
