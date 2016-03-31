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
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = gbc.weighty = 0;

        // Item Panel: To key in Item details and Add into table
        gbc.gridx = gbc.gridy = 0;
        add(getItemPanel(), gbc);

        // Table to display all current records
        table = new JTable();
        scrollPane = new JScrollPane(table);
        setTableModel(transaction.getTableModel());

        gbc.gridy++;
        add(scrollPane, gbc);

        // Additional Info: Membership, Subtotal
        gbc.gridy++;
        add(getInfoPanel(), gbc);

        // Menu Button Panel
        gbc.gridy++;
        gbc.gridheight = 2;
        add(getButtonPanel(), gbc);
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
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(new JLabel("Quantity:"), gbc);

        gbc.gridy++;
        quantityField = new JFormattedTextField(NumberFormat.getNumberInstance());
        //workaround for http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6256502:
        //1. use setText instead of setValue
        //2. reset text on focus gained
        quantityField.setText("1");
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
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        gbc.gridheight = 2;
        addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
            String productId = productField.getText().toUpperCase();
            if (productId.isEmpty()) {
                System.out.println("Product ID is empty");
                return;
            }

            int quantity = Util.parseIntOrDefault(quantityField.getText(), 0);
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
            transaction.addTransactionItem(transactionItem);
            subTotalField.setText(Util.formatDollar(transaction.getSubtotal()));

            productField.setText(null);
            quantityField.setText("1");
            if (transaction.getTransactionItemList().size() == 1)
                proceedPaymentButton.setEnabled(true);
        });
        panel.add(addItemButton, gbc);

        return panel;
    }

    private JPanel getButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        // Setting
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.BOTH;

        // Membership Details
        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.weightx = 0.5;
        JButton setMemberButton = new JButton("Membership");
        setMemberButton.addActionListener(e -> {
            String memberFieldText = memberField.getText();
            String id = (String) JOptionPane.showInputDialog(this, "Membership ID", "Customer Detail",
                    JOptionPane.PLAIN_MESSAGE, null, null, memberFieldText);

            if (id == null) {
                JOptionPane.showMessageDialog(this, "Member ID is not valid");
                return;
            }
            if (id.equals(memberFieldText)) return;

            Member member = getMember(id);
            if (!id.isEmpty() && member == null) {
                JOptionPane.showMessageDialog(this, "Member ID is not valid");
                return;
            }

            transaction.setCustomer(member);
            memberField.setText(member != null ? member.getId() : null);
        });
        panel.add(setMemberButton, gbc);

        // Proceed to Payment
        gbc.gridx++;
        proceedPaymentButton = new JButton("Proceed to Payment");
        proceedPaymentButton.setEnabled(false);
        proceedPaymentButton.addActionListener(e -> {
            transaction.setDiscount(getDiscount(transaction.getCustomer()));
            ConfirmPaymentDialog cpd = new ConfirmPaymentDialog(this, transaction);
            cpd.setVisible(true);

            if (!cpd.isConfirmed()) return;

            transaction.setDate(new Date());
            checkoutPanelListenerList.forEach(l -> l.addTransactionRequested(transaction));

            String[] selectionList = {"Print Receipt and Return", "Return"};
            int option = JOptionPane.showOptionDialog(this, "Transaction Completed", "Transaction Completed",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, selectionList, selectionList[0]);

            if (option == 0) {
                ReceiptDialog receipt = new ReceiptDialog(this, transaction);
                receipt.setVisible(true);
            }
            
            //Display Alert and list of product understock
            DefaultTableModel model
                    = new DefaultTableModel(new Object[] {"Product", "Quantity Available"}, 0);
            transaction.getTransactionItemList().stream()
                    .map(TransactionItem::getProduct)
                    .forEach(p -> {
                        if (p.isUnderstock()) model.addRow(new Object[] {p.getId(), p.getQuantity()});    
                    });
            if (model.getRowCount() > 0) {
                JScrollPane scroll = new JScrollPane(new JTable(model));
                JOptionPane.showMessageDialog(this, scroll, "Alert! Product Understock", JOptionPane.WARNING_MESSAGE);
            }

            newTransactionRequested();
        });
        panel.add(proceedPaymentButton, gbc);

        // Back button
        gbc.gridx++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::backActionPerformed);
        panel.add(backButton, gbc);

        return panel;
    }


    private JPanel getInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        // Setting
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Membership Details
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Membership: "), gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        memberField = new JLabel();
        panel.add(memberField, gbc);

        // Subtotal Details
        gbc.gridx++;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.ipadx = table.getWidth() / 3;
        panel.add(new JLabel("Subtotal: "), gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        subTotalField = new JLabel(Util.formatDollar(transaction.getSubtotal()));
        panel.add(subTotalField, gbc);

        return panel;
    }

    private void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(75);
        tableColumnModel.getColumn(1).setPreferredWidth(75);
        tableColumnModel.getColumn(2).setPreferredWidth(300);
        tableColumnModel.getColumn(3).setPreferredWidth(75);
        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width + 5, table.getRowHeight() * VISIBLE_ROW + 1));
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
