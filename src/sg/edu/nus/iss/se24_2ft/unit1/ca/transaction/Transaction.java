package sg.edu.nus.iss.se24_2ft.unit1.ca.transaction;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.*;
import java.util.stream.Stream;

public class Transaction {
    private Integer id;
    private Customer customer;
    private Date date;

    //derived
    private double subtotal;

    //not persisted
    private Discount discount;
    private int redeemPoint;
    private double payment;
    private int creditPoint;

    private List<TransactionItem> transactionItemList;
    private Map<String, TransactionItem> transactionItemMap;
    private AbstractTableModel tableModel;


    /**
     * created by Srishti
     */

    public Transaction() {
        id = null;
        customer = null;
        date = null;
        subtotal = 0;
        discount = null;
        redeemPoint = 0;
        payment = 0;
        creditPoint = 0;

        tableModel = null;

        transactionItemList = new ArrayList<>();
        transactionItemMap = new HashMap<>();
    }

    public int getId() { return id; }

    public Customer getCustomer() { return customer; }

    public Date getDate() { return date; }

    public Discount getDiscount() { return discount; }

    public int getRedeemPoint() { return redeemPoint; }

    public double getPayment() { return payment; }

    public int getCreditPoint() { return creditPoint; }

    public double getSubtotal() { return subtotal; }

    public double getDiscountAmount() {
        if (discount == null) return 0;

        return (discount.getPercent() / 100.0) * subtotal;
    }

    public double getSubtotalAfterDiscount() {
        return subtotal - getDiscountAmount();
    }

    public double getBalance() {
        return getSubtotalAfterDiscount() - payment - getRedeemPointValue();
    }

    public double getRedeemPointValue() {
        return redeemPoint * TransactionManager.POINT_TO_DOLLAR;
    }

    public List<TransactionItem> getTransactionItemList() { return transactionItemList; }

    public TableModel getTableModel() {
        if (tableModel != null) return tableModel;

        return tableModel = new AbstractTableModel() {
            private final String[] COLUMN_NAMES =
                    { "Qty", "ID", "Description", "Unit Price" };

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
                switch (columnIndex) {
                    case 0: return item.getQuantity();
                    case 1: return item.getProduct().getId();
                    case 2: return item.getProduct().getDescription();
                    case 3: return Util.formatDollar(item.getProduct().getPrice());
                    default: return null;
                }
            }
        };
    }

    public Stream<String> toStringStream() {
        return transactionItemList.stream()
                .sorted(Comparator.comparing(i -> {
                    Product product = i.getProduct();
                    return product != null ? product.getId() : null;
                }))
                .map(TransactionItem::toString);
    }

    //setters
    public void addTransactionItem(TransactionItem transactionItem) {
        addTransactionItem(transactionItem, false);
    }

    /*package*/ void addTransactionItem(TransactionItem transactionItem, boolean isLoading) {
        //allow adding only before id is set
        if (id != null)
            throw new IllegalArgumentException("Unauthorised.");

        Product product = transactionItem.getProduct();

        if (product == null)
            throw new IllegalArgumentException("No product in transaction item.");

        String productId = product.getId();
        if (productId == null)
            throw new IllegalArgumentException("No such product.");

        int quantity = transactionItem.getQuantity();
        if (!isLoading && quantity > product.getQuantity())
            throw new IllegalArgumentException("Exceeded inventory quantity.");

        TransactionItem existingItem = transactionItemMap.get(productId);

        if (existingItem == null) {
            transactionItem.setParent(this);
            transactionItemList.add(transactionItem);
            transactionItemMap.put(productId, transactionItem);

            if (tableModel != null) {
                int rowIndex = transactionItemList.size() - 1;
                tableModel.fireTableRowsInserted(rowIndex, rowIndex);
            }
        } else {
            quantity += existingItem.getQuantity();
            if (!isLoading && quantity > product.getQuantity())
                throw new IllegalArgumentException("Exceeded inventory quantity.");

            existingItem.addQuantity(transactionItem.getQuantity());

            if (tableModel != null) {
                int rowIndex = transactionItemList.indexOf(existingItem);
                if (rowIndex >= 0)
                    tableModel.fireTableCellUpdated(rowIndex, 0);
            }
        }

        subtotal += transactionItem.getQuantity() * product.getPrice();
    }

    public boolean setCustomer(Customer customer) {
        //only allow before id is set
        if (id != null) return false;

        this.customer = customer;
        return true;
    }

    public boolean setDate(Date date) {
        //only allow before id is set
        if (id != null) return false;

        this.date = date;
        return true;
    }

    public boolean setDiscount(Discount discount) {
        //only allow before id is set
        if (id != null) return false;

        this.discount = discount;

        double total = getSubtotalAfterDiscount();
        if (redeemPoint > total) redeemPoint = (int) total;

        return true;
    }

    public boolean setRedeemPoint(int loyaltyPoint) {
        //only allow before id is set
        if (id != null || loyaltyPoint < 0) return false;

        this.redeemPoint = loyaltyPoint;
        return true;
    }

    public boolean setPayment(double payment) {
        //only allow before id is set
        if (id != null || payment < 0) return false;

        this.payment = payment;
        return true;
    }

    /*package*/ void setId(int id) { this.id = id; }

    /*package*/ boolean setCreditPoint(int creditPoint) {
        //only allow before id is set
        if (id != null || redeemPoint < 0) return false;

        this.creditPoint = creditPoint;
        return true;
    }
}