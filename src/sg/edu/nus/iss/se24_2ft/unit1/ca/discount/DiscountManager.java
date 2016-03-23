package sg.edu.nus.iss.se24_2ft.unit1.ca.discount;

//@author: Nguyen Trung
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

public class DiscountManager {
	private String filename;
	private List<Discount> discountList;
	private Map<String, Discount> discountMap;

	public DiscountManager(String fileaname) throws IOException {
		this.filename = fileaname;
		discountList = new ArrayList<>();
		discountMap = new HashMap<>();

		initData();
	}

	private void initData() throws IOException {
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			stream.map(Utils::splitCsv).forEach(a -> {
				String code = a[0], description = a[1];
				Date start = Utils.parseDateOrDefault(a[2], null);
				int period = Utils.parseIntOrDefault(a[3], -1);
				double percent = Utils.parseDoubleOrDefault(a[4], 0);
				boolean memberOnly = a[5].toUpperCase().equals("M");

				//TODO: refactor order of domain object instantiation for all iniitData
				Discount discount = new Discount(code, description, start, period, percent, memberOnly);

				//TODO: try filter
				//if id already exist, skip
				//TODO: conditions for other initData
				if (discountMap.containsKey(code)) return;

				discount.setCode();

				discountList.add(discount);
				discountMap.put(code, discount);
			});
		}
	}

	public Discount getMaxDiscount(Customer customer) {
		Optional<Discount> discountOptional = discountList.stream()
				.filter(d -> {
					if (!d.isDiscountAvailable()) return false;
					if (!d.isMemberOnly()) return true;
					if (customer instanceof Member) {
						int loyaltyPoint = ((Member) customer).getLoyaltyPoint();
						String code = d.getCode().toUpperCase();
						if (code.equals("MEMBER_FIRST") && loyaltyPoint == -1)
							return true;
						if (code.equals("MEMBER_SUBSEQ") && loyaltyPoint != -1)
							return true;
					}
					return false;
				}).max(Comparator.comparing(Discount::getPercent));

		return discountOptional.isPresent() ? discountOptional.get() : null;
	}

	public List<Discount> getDiscountList() {
		return discountList;
	}

	public boolean addDiscount(Discount discount) {
		String code = discount.getRequestedCode();
		//TODO: check constraint for all add
		if (discountMap.containsKey(code)) return false;

		discount.setCode();
		discountList.add(discount);
		discountMap.put(code, discount);

		//TODO: KIV try/catch for IO
		try {
			store();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private void store() throws IOException {
		Stream<String> stream = discountList.stream()
				.sorted(Comparator.comparing(Discount::getCode))
				.map(Discount::toString);

		Files.write(Paths.get(filename), (Iterable<String>) stream::iterator,
				StandardOpenOption.CREATE);
	}
}
