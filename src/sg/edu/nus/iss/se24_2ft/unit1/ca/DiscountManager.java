package sg.edu.nus.iss.se24_2ft.unit1.ca;

//@author: Nguyen Trung
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVWriter;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

public class DiscountManager {
	private String filename;
	private List<Discount> discountList = null;

	public DiscountManager(String fileaname) {
		this.filename = fileaname;
		discountList = new ArrayList<>();
		initData();
	}

	private void initData() {
		List<ArrayList<String>> _list = new ArrayList<>();

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

			boolean memberOnly = params.get(5).toUpperCase().equals("M");
			String code = params.get(0), description = params.get(1);
			Date start = Utils.parseDateOrDefault(params.get(2), null);
			int period = Utils.parseIntOrDefault(params.get(3), -1);
			double percent = Utils.parseDoubleOrDefault(params.get(4), 0);

			Discount discount = new Discount(code, description, start, period, percent, memberOnly);
			discount.setCode();

			discountList.add(discount);
		}
	}

	public Discount getMaxDiscount(Customer customer) {
		return discountList.stream()
				.filter(d -> {
					if (!d.isDiscountAvailable()) return false;
					if (!d.isMemeberOnly()) return true;
					if (customer instanceof Member) {
						int loyaltyPoint = ((Member) customer).getLoyaltyPoint();
						String code = d.getCode().toUpperCase();
						if (code.equals("MEMBER_FIRST") && loyaltyPoint == -1)
							return true;
						if (code.equals("MEMBER_SUBSEQ") && loyaltyPoint != -1)
							return true;
					}
					return false;
				}).max((d1, d2) -> Double.compare(d1.getPercent(), d2.getPercent()))
				.get();
	}

	public List<Discount> getDiscountList() {
		return discountList;
	}

	public void addDiscount(Discount discount) {
		discountList.add(discount);
		writeToFile();
	}

	private void writeToFile() {
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(filename);
			Iterator<Discount> i = discountList.iterator();
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
