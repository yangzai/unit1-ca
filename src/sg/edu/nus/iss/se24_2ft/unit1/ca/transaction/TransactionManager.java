package sg.edu.nus.iss.se24_2ft.unit1.ca.transaction;

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

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Created by Srishti
 */

public class TransactionManager {
    private static final int VALUE_TO_LOYALTY_RATE = 20;
    private String filename;
    private ProductManager productManager;
    private MemberManager memberManager;
    private int maxId;
    private List<Transaction> transactionList;
    private Map<Integer, Transaction> transactionMap;
    private List<TransactionItem> transactionItemList;
    private AbstractTableModel tableModel;

    public TransactionManager(String filename, ProductManager productManager, MemberManager memberManager) throws IOException {
        maxId = 0;
        tableModel = null;

        this.filename = filename;
        this.productManager = productManager;
        this.memberManager = memberManager;

        transactionList = new ArrayList<>();
        transactionMap = new HashMap<>();
        transactionItemList = new ArrayList<>();

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

                transactionItemList.addAll(transaction.getTransactionItemList());
            });

            transactionMap.forEach((id, t) -> t.setId(id));
        }
    }

    public TableModel getTableModel() {
        if (tableModel != null) return tableModel;

        return tableModel = new AbstractTableModel() {
            private final String[] COLUMN_NAMES =
                    { "ID", "Product ID", "Product Name", "Product Description", "Member ID",  "Qty", "Date" };

            @Override
            public String getColumnName(int column) {
                return COLUMN_NAMES[column];
            }

            @Override
            public int getRowCount() {
                return transactionItemList.size();
            }

            @Override
            public int getColumnCount() {
                return COLUMN_NAMES.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                TransactionItem item = transactionItemList.get(rowIndex);
                Product product = item.getProduct();
                Transaction parent = item.getParent();
                Customer customer = parent.getCustomer();
                if (customer == null) customer = PublicCustomer.getInstance();

                switch (columnIndex) {
                    case 0: return parent.getId();
                    case 1: return product.getId();
                    case 2: return product.getName();
                    case 3: return product.getDescription();
                    case 4: return customer.getId();
                    case 5: return item.getQuantity();
                    case 6: return parent.getDate();
                    default: return null;
                }
            }
        };
    }

    public void addTransaction(Transaction transaction) {
        Customer customer = transaction.getCustomer();

        int debitPoint = transaction.getLoyaltyPoint();
        if (debitPoint < 0) debitPoint = 0;
        double subtotalAfterDiscount = transaction.getSubtotalAfterDiscount();

        //to prevent converting points to change
        if (debitPoint > subtotalAfterDiscount)
            transaction.setLoyaltyPoint((int) subtotalAfterDiscount);

        //insufficient
        if (transaction.getPayment() + debitPoint < subtotalAfterDiscount)
            throw new IllegalArgumentException("Insufficient Amount");

        if (customer instanceof Member) {
            String memberId = customer.getId();
            //throws IllegalArgumentException
            memberManager.debitLoyaltyPoint(memberId, debitPoint);

            //floor
            int creditPoint = (int) (transaction.getSubtotal() / VALUE_TO_LOYALTY_RATE);
            memberManager.creditLoyaltyPoint(memberId, creditPoint);
        }

        transaction.getTransactionItemList().forEach(i -> {
            Product p = i.getProduct();
            if (p == null) return;
            productManager.deductQuantity(p.getId(), i.getQuantity());
            //TODO: Add logic to update Product file here;
        });

        transaction.setId(++maxId);
        transactionList.add(transaction);
        transactionMap.put(transaction.getId(), transaction);

        int firstRowIndex = transactionItemList.size();
        transactionItemList.addAll(transaction.getTransactionItemList());

        if (tableModel != null) {
            int lastRowIndex = transactionItemList.size() - 1;
            tableModel.fireTableRowsInserted(firstRowIndex, lastRowIndex);
        }

        //TODO: consider append
        try {
            store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void store() throws IOException {
        //assume list is already chronologically sorted
        Stream<String> stream = transactionList.stream()
                .flatMap(Transaction::toStringStream);

        Files.write(Paths.get(filename), (Iterable<String>) stream::iterator,
                StandardOpenOption.CREATE);
    }
}