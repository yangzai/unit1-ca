package sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout;

import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

import javax.swing.*;
import java.awt.*;

/*package*/ class ConfirmPaymentDialog extends JDialog {
    private Transaction transaction;
    private Member member;
    private Discount discount;

    private JLabel balanceLabel;
    private JTextArea balanceTA;
    private JButton confirmButton;
    private boolean isConfirmed;

    /*package*/ ConfirmPaymentDialog(Component parentComponent, Transaction transaction) {
        super((JFrame) SwingUtilities.windowForComponent(parentComponent),
                "Payment Details", true);

        this.transaction = transaction;
        isConfirmed = false;

        Customer customer = transaction != null ? transaction.getCustomer() : null;
        member = customer != null && customer instanceof Member ?
                (Member) customer : null;
        discount = transaction != null ? transaction.getDiscount() : null;

        setResizable(false);
        setContentPane(getPaymentPanel());

        this.pack();
    }

    private JPanel getPaymentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        // Setting
        GridBagConstraints gbc = new GridBagConstraints();
        Insets defaultInsets = gbc.insets;
        Insets top20Insets = new Insets(20, 0, 0, 0);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;

        // Subtotal field
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        panel.add(new JLabel("Subtotal: "), gbc);

        gbc.gridx++;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        JTextArea subtotalTA = new JTextArea(Utils.formatDollar(getSubtotal()));
        subtotalTA.setEditable(false);
        panel.add(subtotalTA, gbc);

        // Discount Amount
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        String lblDiscount = discountLabelFormat(discount != null ? getDiscountPercent() : 0);
        panel.add(new JLabel(lblDiscount), gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea discountTA = new JTextArea(Utils.formatDollar(getDiscountAmount()));
        discountTA.setEditable(false);
        panel.add(discountTA, gbc);

        // Total Price - Using top padding;
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Total:"), gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea totalTA = new JTextArea(Utils.formatDollar(getSubtotalAfterDiscount()));
        totalTA.setEditable(false);
        panel.add(totalTA, gbc);

        // Redeem Value
        gbc.insets = top20Insets;
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        JLabel redeemLabel = new JLabel("Redeem (1 point = $"+
                TransactionManager.POINT_TO_DOLLAR + "): ");
        redeemLabel.setVisible(member != null);
        panel.add(redeemLabel, gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea redeemTA = new JTextArea(Integer.toString(getRedeemPoint()));
        redeemTA.setEditable(false);
        redeemTA.setVisible(member != null);
        panel.add(redeemTA, gbc);

        gbc.insets = defaultInsets;
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        JLabel redeemValueLabel = new JLabel("Redeem Value: ");
        redeemLabel.setVisible(member != null);
        panel.add(redeemValueLabel, gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea redeemValueTA = new JTextArea(Utils.formatDollar(getRedeemPointValue()));
        redeemTA.setEditable(false);
        redeemTA.setVisible(member != null);
        panel.add(redeemValueTA, gbc);

        // Amount Paid
        gbc.insets = defaultInsets;
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Amount Paid:"), gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea payTA = new JTextArea(Utils.formatDollar(getPayment()));
        payTA.setEditable(false);
        panel.add(payTA, gbc);

        // Balance
        double balance = getBalance();
        gbc.insets = top20Insets;
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        balanceLabel = new JLabel(balance < 0 ? "Change:" : "Balance:");
        panel.add(balanceLabel, gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        balanceTA = new JTextArea(Utils.formatDollar(Math.abs(balance)));
        panel.add(balanceTA, gbc);

        // Button
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        JButton redeemButton = new JButton("Redeem");
        redeemButton.setVisible(member != null);
        redeemButton.addActionListener(l -> {
            if (member == null) return;

            String redeemText = redeemTA.getText();
            int maxPoint = (int) (transaction.getSubtotalAfterDiscount() / TransactionManager.POINT_TO_DOLLAR);
            int memberPoint = member != null ? member.getLoyaltyPoint() : 0;
            if (memberPoint < 0) memberPoint = 0;
            if (memberPoint < maxPoint) maxPoint = memberPoint;
            String[] messages = { "Member Point Balance: " + memberPoint,
                    "Redeem Point (Max. "
                    + String.valueOf(maxPoint) + "):"};
            String point = (String) JOptionPane.showInputDialog(this, messages, "Redeem",
                    JOptionPane.PLAIN_MESSAGE, null, null, redeemText);

            if (point == null || redeemText.equals(point)) return;

            int redeemPoint = 0;
            try {
                redeemPoint = Integer.parseInt(point);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Not a valid integer");
                return;
            }

            if (redeemPoint < 0 || redeemPoint > memberPoint) {
                JOptionPane.showMessageDialog(this, "Out of range.");
                return;
            }

            double total = getSubtotalAfterDiscount();
            if (redeemPoint > total) redeemPoint = (int) total;

            if (transaction != null)
                transaction.setRedeemPoint(redeemPoint);
            redeemTA.setText(Integer.toString(redeemPoint));
            redeemValueTA.setText(Utils.formatDollar(getRedeemPointValue()));
            double balanceLabelValue = Utils.parseDoubleOrDefault(balanceLabel.getText(), 0);
            double currentBalance = getBalance();
            if (currentBalance != balanceLabelValue) balanceChanged(currentBalance);
        });
        panel.add(redeemButton, gbc);

        gbc.gridx++;
        JButton paymentButton = new JButton("Payment");
        paymentButton.addActionListener(l -> {
            String paymentText = payTA.getText().replace("$", "");
            String message = "Enter Amount Paid by Customer ($): ";
            String input = (String) JOptionPane.showInputDialog(this, message, "Payment",
                    JOptionPane.PLAIN_MESSAGE, null, null, paymentText);

            if (input == null || input.equals(paymentText)) return;

            double paidAmount = 0;
            try {
                paidAmount = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Not a valid number.");
                return;
            }
            if (paidAmount < 0) {
                JOptionPane.showMessageDialog(this, "Value cannot be negative.");
                return;
            }

            int decimalIndex = input.indexOf('.');
            int decimalPlace = input.length() - decimalIndex - 1;
            if (decimalIndex >= 0 && decimalPlace > 2) {
                JOptionPane.showMessageDialog(this, "Value cannot be in more that 2 decimal places.");
                return;
            }

            transaction.setPayment(paidAmount);
            payTA.setText(Utils.formatDollar(paidAmount));
            double currentBalance = getBalance();
            if (currentBalance != balance) balanceChanged(currentBalance);
        });
        panel.add(paymentButton, gbc);

        gbc.gridx++;
        confirmButton = new JButton("Confirm");
        confirmButton.setEnabled(balance <= 0);
        confirmButton.addActionListener(l -> {
            isConfirmed = true;
            dispose();
        });
        panel.add(confirmButton, gbc);

        return panel;
    }
    private double getBalance() {
        return transaction != null ? transaction.getBalance() : 0;
    }

    private double getPayment() {
        return transaction != null ? transaction.getPayment() : 0;
    }

    private double getSubtotal() {
        return transaction != null ? transaction.getSubtotal() : 0;
    }

    private double getSubtotalAfterDiscount() {
        return transaction != null ? transaction.getSubtotalAfterDiscount() : 0;
    }

    private double getDiscountPercent() {
        return discount != null ? discount.getPercent() : 0;
    }

    private double getDiscountAmount() {
        return transaction != null ? transaction.getDiscountAmount() : 0;
    }

    private int getRedeemPoint() {
        return transaction != null ? transaction.getRedeemPoint() : 0;
    }
    private double getRedeemPointValue() {
        return transaction != null ? transaction.getRedeemPointValue() : 0;
    }

    /*package*/ boolean isConfirmed() {
        return isConfirmed;
    }

    private String discountLabelFormat(double percent) {
        return "Discount (" + percent + "%):";
    }

    private void balanceChanged(double balance) {
        balanceLabel.setText(balance < 0 ? "Change:" : "Balance:");
        balanceTA.setText(Utils.formatDollar(Math.abs(balance)));
        confirmButton.setEnabled(balance <= 0);
    }
}