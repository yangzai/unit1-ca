package sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.printer.ReceiptPrinter;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionItem;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

/**
 * 
 * @author Srishti Miglani
 *
 */
/*package*/ class ReceiptDialog extends JDialog {
    private Transaction transaction;
    private Discount discount;
    private Member member;
    private JTable table;

    /*package*/ ReceiptDialog(Component parentComponent, Transaction transaction) {
        super((JFrame) SwingUtilities.windowForComponent(parentComponent),
                "Order Receipt", true);
        this.transaction = transaction;
        this.discount = transaction != null ? transaction.getDiscount() : null;
        Customer customer = transaction != null ? transaction.getCustomer() : null;
        member = customer != null && customer instanceof Member ? (Member) customer : null;
        setContentPane(getReceiptPanel());
        pack();
    }

    private JPanel getReceiptPanel() {
        JPanel receiptPanel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(7, 0, 0, 0);

        gc.gridx = gc.gridy = 0;
        labelPanel.add(Box.createVerticalGlue(), gc);

        gc.gridx = gc.gridy = 1;
        Integer id = transaction != null ? transaction.getId() : null;
        JLabel tranLabel = new JLabel("Transaction ID:  " + id);
        tranLabel.setFont(new Font("Serif", Font.BOLD, 17));
        labelPanel.add(tranLabel, gc);

        gc.gridy++;
        Date date = transaction != null ? transaction.getDate() : null;
        JLabel dateLabel = new JLabel("Issue Date:  " + date);
        dateLabel.setFont(new Font("Serif", Font.PLAIN, 17));
        labelPanel.add(dateLabel, gc);
        receiptPanel.add(labelPanel,BorderLayout.NORTH);

        table = new JTable();
        table.setModel(transaction.getTableModel());
        JScrollPane scroller = new JScrollPane();
        scroller.setViewportView(table);
        scroller.setPreferredSize(new Dimension(200, 200));

        receiptPanel.add(scroller, BorderLayout.CENTER);
        receiptPanel.add(getAdditionalInfo(), BorderLayout.SOUTH);

        return receiptPanel;
    }

    private JPanel getAdditionalInfo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.ipadx = table.getWidth() / 3;
        infoPanel.add(new JLabel("Subtotal: "), gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        double subtotal = transaction != null ? transaction.getSubtotal() : 0;
        infoPanel.add(new JLabel(Util.formatDollar(subtotal)), gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        double discountPercent = discount != null ? discount.getPercent() : 0;
        infoPanel.add(new JLabel("Discount (" + discountPercent + "%):"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        double discountAmount = transaction != null ? transaction.getDiscountAmount() : 0;
        infoPanel.add(new JLabel(Util.formatDollar(discountAmount)), gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel redeemValueTextLabel = new JLabel("Redeemed Value:");
        redeemValueTextLabel.setVisible(member != null);
        infoPanel.add(redeemValueTextLabel, gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        double redeemValue = transaction != null ? transaction.getRedeemPointValue() : 0;
        JLabel redeemValueLabel = new JLabel(Util.formatDollar(redeemValue));
        redeemValueLabel.setVisible(member != null);
        infoPanel.add(redeemValueLabel, gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        infoPanel.add(new JLabel("Cash:"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        double payment = transaction != null ? transaction.getPayment() : 0;
        infoPanel.add(new JLabel(Util.formatDollar(payment)), gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        infoPanel.add(new JLabel("Change:"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        double change = transaction != null ? transaction.getBalance() * -1 : 0;
        infoPanel.add(new JLabel(Util.formatDollar(change)), gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel redeemPointTextLabel = new JLabel("Redeemed (1 point = $"+ TransactionManager.POINT_TO_DOLLAR + "):");
        redeemPointTextLabel.setVisible(member != null);
        infoPanel.add(redeemPointTextLabel, gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        int redeemPoint = transaction != null ? transaction.getRedeemPoint() : 0;
        JLabel redeemPointLabel = new JLabel(Integer.toString(redeemPoint));
        redeemPointLabel.setVisible(member != null);
        infoPanel.add(redeemPointLabel, gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel creditPointTextLabel = new JLabel("Credited ($" + TransactionManager.DOLLAR_TO_POINT + " = "
                 + " 1 point): ");
        creditPointTextLabel.setVisible(member != null);
        infoPanel.add(creditPointTextLabel, gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        int creditPoint = transaction != null ? transaction.getCreditPoint() : 0;
        JLabel creditPointLabel = new JLabel(Integer.toString(creditPoint));
        creditPointLabel.setVisible(member != null);
        infoPanel.add(creditPointLabel, gbc);
        p.add(infoPanel, BorderLayout.EAST);

        gbc.gridx--;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel balancePointTextLabel = new JLabel("Point Balance:");
        balancePointTextLabel.setVisible(member != null);
        infoPanel.add(balancePointTextLabel, gbc);

        gbc.gridx++;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        int balancePoint = member != null ? member.getLoyaltyPoint() : 0;
        JLabel balancePointLabel = new JLabel(Integer.toString(balancePoint));
        balancePointLabel.setVisible(member != null);
        infoPanel.add(balancePointLabel, gbc);
        p.add(infoPanel, BorderLayout.EAST);

        return p;
    }
    
    @Override
    public void setVisible(boolean visible) {
        if (visible) ReceiptPrinter.print(getReceiptText());
        super.setVisible(visible);
    }

    private String getReceiptText(){
        String strTableFormat = "%-6.6s  %-7.7s %-30.30s  %-10.10s %n";
        String strTotalFormat = "%-30.30s %-10.10s %n";

        //Init data for Receipt:
        Integer id = transaction != null ? transaction.getId() : null;
        Date date = transaction != null ? transaction.getDate() : null;
        double subtotal = transaction != null ? transaction.getSubtotal() : 0;
        double discountPercent = discount != null ? discount.getPercent() : 0;
        String discountLabel = "Discount (" + discountPercent + "%):";
        double discountAmount = transaction != null ? transaction.getDiscountAmount() : 0;
        double redeemValue = transaction != null ? transaction.getRedeemPointValue() : 0;
        double payment = transaction != null ? transaction.getPayment() : 0;
        double change = transaction != null ? transaction.getBalance() * -1 : 0;
        String redeemPointLabel = "Redeemed (1 point = $" + TransactionManager.POINT_TO_DOLLAR + "):";
        int redeemPoint = transaction != null ? transaction.getRedeemPoint() : 0;
        String creditPointLabel = "Credited ($" + TransactionManager.DOLLAR_TO_POINT + " = 1 point):";
        int creditPoint = transaction != null ? transaction.getCreditPoint() : 0;
        int balancePoint = member != null ? member.getLoyaltyPoint() : 0;

        //Formatted Text
        String string = "********* Transaction ID: " + id + " **************" + "\n";
        string += "Issue Date: " + (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")).format(date) + "\n\n";
        string += String.format(strTableFormat, "Qty", "ID", "Description", "Unit Price");
        for (TransactionItem item : transaction.getTransactionItemList()) {
            Product product = item.getProduct();
            string += String.format(strTableFormat, item.getQuantity(), product.getId(), product.getDescription(), "$"+product.getPrice());
        }
        string += "\n";
        string += String.format(strTotalFormat, "Subtotal: ", Util.formatDollar(subtotal));
        string += String.format(strTotalFormat, discountLabel, Util.formatDollar(discountAmount));
        if (member!=null) {
            string += String.format(strTotalFormat, "Redeem Value:", Util.formatDollar(redeemValue));
        }
        string += "\n";
        string += String.format(strTotalFormat, "Cash:", Util.formatDollar(payment));
        string += "\n";
        string += String.format(strTotalFormat, "Change:", Util.formatDollar(change));
        string += "\n";
        if (member!=null) {
            string += String.format(strTotalFormat, redeemPointLabel, redeemPoint);
            string += String.format(strTotalFormat, creditPointLabel, creditPoint);
            string += String.format(strTotalFormat, "Point Balance:", balancePoint);
        }
        return string;
    }
    
    
}