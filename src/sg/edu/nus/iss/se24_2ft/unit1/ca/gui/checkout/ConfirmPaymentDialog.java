package sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout;

import sg.edu.nus.iss.se24_2ft.unit1.ca.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

import javax.swing.*;
import java.awt.*;

/*package*/ class ConfirmPaymentDialog extends JDialog {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 210;
    private Transaction transaction;
    private double balance;
    private Member member;
    private Discount discount;

    private JLabel balanceLabel;
    private JTextArea balanceTA;
    private JButton confirmButton;
    private boolean isConfirmed;

    /*package*/ ConfirmPaymentDialog(Component parentComponent, Transaction transaction) {
        super((JFrame) SwingUtilities.windowForComponent(parentComponent),
                "Payment Details", true);

        isConfirmed = false;
        this.transaction = transaction;
        balance = transaction.getBalance();
        Customer customer = transaction.getCustomer();
        member = customer != null && customer instanceof Member ?
                (Member) customer : null;
        discount = transaction.getDiscount();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
        JTextArea subtotalTA = new JTextArea(Utils.formatDollar(transaction.getSubtotal()));
        subtotalTA.setEditable(false);
        panel.add(subtotalTA, gbc);

        // Redeem Value
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        JLabel redeemLabel = new JLabel("Redeem: ");
        redeemLabel.setVisible(member != null);
        panel.add(redeemLabel, gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea redeemTA = new JTextArea(Integer.toString(transaction.getLoyaltyPoint()));
        redeemTA.setEditable(false);
        redeemTA.setVisible(member != null);
        panel.add(redeemTA, gbc);

        // Discount Amount
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        String lblDiscount = discountLabelFormat(discount.getPercent());
        panel.add(new JLabel(lblDiscount), gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea discountTA = new JTextArea(Utils.formatDollar(transaction.getDiscountAmount()));
        discountTA.setEditable(false);
        panel.add(discountTA, gbc);

        // Total Price - Using top padding;
        gbc.insets = top20Insets;
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Total:"), gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea totalTA = new JTextArea(Utils.formatDollar(
                transaction.getSubtotalAfterDiscount() /*- transaction.getLoyaltyPoint()*/
        ));
        totalTA.setEditable(false);
        panel.add(totalTA, gbc);

        // Amount Paid
        gbc.insets = defaultInsets;
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Amount Paid:"), gbc);

        gbc.gridx++;
        gbc.gridwidth = 3;
        JTextArea payTA = new JTextArea(Utils.formatDollar(transaction.getPayment()));
        payTA.setEditable(false);
        panel.add(payTA, gbc);

        // Balance
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
            int maxPoint = member.getLoyaltyPoint();
            if (maxPoint < 0) maxPoint = 0;
            String message = "Enter Redeem Point (Max. "
                    + String.valueOf(maxPoint) + "):";
            String point = (String) JOptionPane.showInputDialog(this, message, "Redeem",
                    JOptionPane.PLAIN_MESSAGE, null, null, redeemText);

            if (point == null || redeemText.equals(point)) return;

            int redeemPoint = 0;
            try {
                redeemPoint = Integer.parseInt(point);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Not a valid integer");
                return;
            }

            if (redeemPoint < 0 || redeemPoint > maxPoint) {
                JOptionPane.showMessageDialog(this, "Out of range.");
                return;
            }

            double total = transaction.getSubtotalAfterDiscount();
            if (redeemPoint > total) redeemPoint = (int) total;

            transaction.setLoyaltyPoint(redeemPoint);
            redeemTA.setText(Integer.toString(redeemPoint));
            double currentBalance = transaction.getBalance();
            if (currentBalance != balance) balanceChanged(currentBalance);
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
            double currentBalance = transaction.getBalance();
            if (currentBalance != balance) balanceChanged(currentBalance);
        });
        panel.add(paymentButton, gbc);

        gbc.gridx++;
        confirmButton = new JButton("Confirm");
        confirmButton.setEnabled(balance <= 0);
        confirmButton.addActionListener(l -> {
            isConfirmed = true;
            this.setVisible(false);
        });
        panel.add(confirmButton, gbc);

        return panel;
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
