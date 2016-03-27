package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.PublicCustomer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.MemberManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

/**
 * Created by Srishti
 */

public class TransactionManager {
	private static final int VALUE_TO_LOYALITY_RATE = 20;
	private String filename;
	private ProductManager productManager;
	private MemberManager memberManager;
	private int maxId;
	private List<Transaction> transactionList;
	private Map<Integer, Transaction> transactionMap;

	public TransactionManager(String filename, ProductManager productManager, MemberManager memberManager) throws IOException {
		maxId = 0;

		this.filename = filename;
		this.productManager = productManager;
		this.memberManager = memberManager;

		transactionList = new ArrayList<>();
		transactionMap = new HashMap<>();

		initData();
	}

	private void initData() throws IOException {
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			stream.map(Utils::splitCsv).forEach(a -> {
				int id = Utils.parseIntOrDefault(a[0], 0),
						quantity = Utils.parseIntOrDefault(a[3], 0);
				String productId = a[1];

				Product product = productManager.getProduct(productId);
				TransactionItem transactionItem = new TransactionItem(product, quantity);

				Transaction transaction = transactionMap.get(id);
				if (transaction != null) {
					//same transaction, assume no conflict in tx details
					transaction.addTransactionItem(transactionItem);
					return;
				}

				String memberId = a[2];
				Date date = Utils.parseDateOrDefault(a[4], null);

				Customer customer;
				if (memberId.toUpperCase().equals("PUBLIC"))
					customer = memberManager.getMember(memberId);
				else customer = PublicCustomer.getInstance();

				//do not setId yet or it will lock addTransactionItem
				transaction = new Transaction();
				transaction.setCustomer(customer);
				transaction.setDate(date);
				transaction.addTransactionItem(transactionItem);

				if (id > maxId) maxId = id;

				transactionList.add(transaction);
				transactionMap.put(id, transaction);
			});

			transactionMap.forEach((id, t) -> t.setId(id));
		}
	}

	public boolean addTransaction(Transaction transaction) {
		Customer customer = transaction.getCustomer();
//

		int debitPoint = transaction.getLoyaltyPoint();
		if (debitPoint < 0) debitPoint = 0;
		double subtotalAfterDiscount = transaction.getSubtotalAfterDiscount();

		//to prevent converting points to change
		if (debitPoint > subtotalAfterDiscount)
			transaction.setLoyaltyPoint((int) subtotalAfterDiscount);

		//insufficient
		if (transaction.getPayment() + debitPoint < subtotalAfterDiscount) return false;

		if (customer instanceof Member) {
			String memberId = customer.getId();
			if (!memberManager.debitLoyaltyPoint(memberId, debitPoint)) return false;

			//floor
			int creditPoint = (int) (transaction.getSubtotal() / VALUE_TO_LOYALITY_RATE);
			memberManager.creditLoyaltyPoint(memberId, creditPoint);
		}

		transaction.setId(++maxId);
		transactionList.add(transaction);
		transactionMap.put(transaction.getId(), transaction);

		//TODO: consider append
		try {
			store();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private void store() throws IOException {
		Stream<String> stream = transactionList.stream()
				.sorted(Comparator.comparing(Transaction::getId))
				.flatMap(Transaction::toStringStream);

		Files.write(Paths.get(filename), (Iterable<String>) stream::iterator,
				StandardOpenOption.CREATE);
	}
}