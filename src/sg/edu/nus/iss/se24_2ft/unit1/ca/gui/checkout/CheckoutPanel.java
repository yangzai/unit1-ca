package sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout;

import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionItem;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.gui.FeaturePanel;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.Format;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public abstract class CheckoutPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 10;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField productField;
    private JLabel memberField, subTotalField;
    private JFormattedTextField quantityField;
    private JButton addItemButton, proceedPaymentButton;

    private java.util.List<CheckoutPanelListener> checkoutPanelListenerList;

    private Transaction transaction;

    protected CheckoutPanel() {
        transaction = new Transaction();
        checkoutPanelListenerList = new ArrayList<>();

         // Initial setting
        setLayout(new BorderLayout());

        // Header Menu
        add(getItemPanel(), BorderLayout.NORTH);

        // Table to display all current records
        table = new JTable();
        scrollPane = new JScrollPane(table);
        setTableModel(transaction.getTableModel());
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Menu
        add(getBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel getBottomPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx = gbc.weighty = 0;

        // Membership Details
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Membership: "), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        memberField = new JLabel();
        panel.add(memberField, gbc);

        // Subtotal Details
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.ipadx = table.getWidth() / 3;
        panel.add(new JLabel("Subtotal: "), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        subTotalField = new JLabel(Util.formatDollar(transaction.getSubtotal()));
        panel.add(subTotalField, gbc);

        // Membership Button
        gbc.gridx++;
        gbc.gridy--;
        gbc.weightx = 0;
        JButton setMemberButton = new JButton("Membership");
        setMemberButton.addActionListener(e -> {
            String memberFieldText = memberField.getText();
            String id = (String) JOptionPane.showInputDialog(this, "Membership ID", "Customer Detail",
                    JOptionPane.PLAIN_MESSAGE, null, null, memberFieldText);

            //if cancel or no change, break
            if (id == null || id.equals(memberFieldText))
                return;

            Member member = getMember(id);
            if (!id.isEmpty() && member == null) {
                JOptionPane.showMessageDialog(this, "Member ID is not valid");
                return;
            }

            //empty string => PUBLIC => valid
            transaction.setCustomer(member);
            memberField.setText(member != null ? member.getId() : null);
        });
        panel.add(setMemberButton, gbc);

        // Proceed to Payment Button
        gbc.gridy++;
        proceedPaymentButton = new JButton("Proceed to Payment");
        proceedPaymentButton.setEnabled(false);
        proceedPaymentButton.addActionListener(e -> {
            transaction.setDiscount(getDiscount(transaction.getCustomer()));
            ConfirmPaymentDialog cpd = new ConfirmPaymentDialog(this, transaction);
            cpd.setVisible(true);

            if (!cpd.isConfirmed())
                return;

            transaction.setDate(new Date());
            checkoutPanelListenerList.forEach(l -> l.addTransactionRequested(transaction));

            String[] selectionList = { "Print Receipt and Return", "Return" };
            int option = JOptionPane.showOptionDialog(this, "Transaction Completed", "Transaction Completed",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, selectionList, selectionList[0]);

            if (option == 0) {
                ReceiptDialog receipt = new ReceiptDialog(this, transaction);
                receipt.setVisible(true);
            }

            // Display Alert and list of product understock
            DefaultTableModel model = new DefaultTableModel(new Object[] { "Product", "Quantity Available" }, 0);
            transaction.getTransactionItemList().stream()
                    .map(TransactionItem::getProduct)
                    .filter(Product::isUnderstock)
                    .forEach(p -> model.addRow(new Object[] { p.getId(), p.getQuantity() }));
            if (model.getRowCount() > 0) {
                JScrollPane scroll = new JScrollPane(new JTable(model));
                JOptionPane.showMessageDialog(this, scroll, "Alert! Product Understock", JOptionPane.WARNING_MESSAGE);
            }

            newTransactionRequested();
        });
        panel.add(proceedPaymentButton, gbc);

        return panel;
    }

    private JPanel getItemPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        // Setting
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add Product ID input field
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = 0.5;
        panel.add(new JLabel("Enter Product ID:"), gbc);

        gbc.gridy++;
        productField = new JTextField();
        productField.addActionListener(e -> addItemButton.doClick());
        panel.add(productField, gbc);

        // Add Quantity input field
        gbc.gridx++;
        gbc.gridy--;
        panel.add(new JLabel("Quantity:"), gbc);

        gbc.gridy++;
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        format.setMinimumIntegerDigits(1);
        format.setMaximumIntegerDigits(4);
        quantityField = new JFormattedTextField(format);
        // workaround for
        // http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6256502:
        // 1. use setText to trigger change
        quantityField.setText("1");
        quantityField.setValue(1);
        quantityField.addActionListener(e -> addItemButton.doClick());
        quantityField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                quantityField.setText(quantityField.getText());
            }
        });
        panel.add(quantityField, gbc);

        // Add Add Item button
        gbc.gridx++;
        gbc.weightx = 0;
        addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
            String productId = productField.getText().toUpperCase();
            if (productId.isEmpty()) {
                System.out.println("Product ID is empty");
                return;
            }

            int quantity = ((Number) quantityField.getValue()).intValue();
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product = getProduct(productId);
            if (product == null) {
                JOptionPane.showMessageDialog(this, "Product ID is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TransactionItem transactionItem = new TransactionItem(product, quantity);
            try {
                transaction.addTransactionItem(transactionItem);
            } catch (IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            subTotalField.setText(Util.formatDollar(transaction.getSubtotal()));

            productField.setText(null);
            quantityField.setText("1");
            if (transaction.getTransactionItemList().size() == 1)
                proceedPaymentButton.setEnabled(true);
        });
        panel.add(addItemButton, gbc);

        // Reset Button
        gbc.gridx++;
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> newTransactionRequested());
        panel.add(resetButton, gbc);

        return panel;
    }

    private void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);

        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * VISIBLE_ROW + 1));
    }

    public void addCheckoutPanelListener(CheckoutPanelListener l) {
        checkoutPanelListenerList.add(l);
    }

    private void newTransactionRequested() {
        transaction = new Transaction();
        setTableModel(transaction.getTableModel());
        memberField.setText(null);
        subTotalField.setText(Util.formatDollar(0));
        proceedPaymentButton.setEnabled(false);
    }

    abstract protected Product getProduct(String id);

    abstract protected Member getMember(String id);

    abstract protected Discount getDiscount(Customer customer);
}
