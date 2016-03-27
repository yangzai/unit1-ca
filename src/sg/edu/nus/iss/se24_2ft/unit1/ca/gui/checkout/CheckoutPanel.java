package sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout;

import sg.edu.nus.iss.se24_2ft.unit1.ca.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.TransactionItem;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.gui.FeaturePanel;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

import java.awt.*;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public abstract class CheckoutPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 10;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField productField;
    private JLabel memberField, subTotalField;
    private JFormattedTextField quantityField;
    private JButton addItemButton;

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
        quantityField.setValue(1);
        quantityField.addActionListener(e -> addItemButton.doClick());
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

            int quantity = Utils.parseIntOrDefault(quantityField.getText(), 0);
            if (quantity <= 0) return;

            Product product = getProduct(productId);
            if (product == null) return;

            TransactionItem transactionItem = new TransactionItem(product, quantity);
            transaction.addTransactionItem(transactionItem);
            subTotalField.setText(Utils.formatDollar(transaction.getSubtotal()));

            productField.setText(null);
            quantityField.setValue(1);
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

            if (id == null || id.equals(memberFieldText)) return;

            Member member = getMember(id);
            if (!id.isEmpty() && member == null) {
                JOptionPane.showMessageDialog(this, "No such member.");
                return;
            }

            transaction.setCustomer(member);
            memberField.setText(member != null ? member.getId() : null);
        });
        panel.add(setMemberButton, gbc);

        // Proceed to Payment
        gbc.gridx++;
        JButton proceedPaymentButton = new JButton("Proceed to Payment");
        proceedPaymentButton.addActionListener(e -> {
            transaction.setDiscount(getDiscount(transaction.getCustomer()));
            ConfirmPaymentDialog cpd = new ConfirmPaymentDialog(this, transaction);
            cpd.setVisible(true);

            if (!cpd.isConfirmed()) return;

            transaction.setDate(new Date());
            checkoutPanelListenerList.forEach(l -> l.addTransactionRequested(transaction));

            String[] selectionList= {"Print Receipt and Return", "Return"};
            int option = JOptionPane.showOptionDialog(this, "Transaction Completed", "Transaction Completed",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, selectionList, selectionList[0]);

            if (option == 0) {
                //TODO: print receipt here
                System.out.println("Print receipt");
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
        subTotalField = new JLabel(Utils.formatDollar(transaction.getSubtotal()));
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
        subTotalField.setText(Utils.formatDollar(0));
    }

    abstract protected Product getProduct(String id);

    abstract protected Member getMember(String id);

    abstract protected Discount getDiscount(Customer customer);
}