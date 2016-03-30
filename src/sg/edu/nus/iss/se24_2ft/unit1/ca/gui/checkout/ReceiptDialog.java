package sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import javax.swing.*;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

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
		infoPanel.add(new JLabel(Utils.formatDollar(subtotal)), gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		double discountPercent = discount != null ? discount.getPercent() : 0;
		infoPanel.add(new JLabel("Discount: (" + discountPercent + "%)"), gbc);

		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		double discountAmount = transaction != null ? transaction.getDiscountAmount() : 0;
		infoPanel.add(new JLabel(Utils.formatDollar(discountAmount)), gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		JLabel redeemPointTextLabel = new JLabel("Points Redeemed:");
		redeemPointTextLabel.setVisible(member != null);
		infoPanel.add(redeemPointTextLabel, gbc);

		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		int redeemPoint = transaction != null ? transaction.getLoyaltyPoint() : 0;
		JLabel redeemPointValueLabel = new JLabel(Integer.toString(redeemPoint));
		redeemPointValueLabel.setVisible(member != null);
		infoPanel.add(redeemPointValueLabel, gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		infoPanel.add(new JLabel("Cash:"), gbc);

		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		double payment = transaction != null ? transaction.getPayment() : 0;
		infoPanel.add(new JLabel(Utils.formatDollar(payment)), gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		infoPanel.add(new JLabel("Change:"), gbc);

		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		double change = transaction != null ? transaction.getBalance() * -1 : 0;
		infoPanel.add(new JLabel(Utils.formatDollar(change)), gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		JLabel creditPointTextLabel = new JLabel("Points Credited:");
		creditPointTextLabel.setVisible(member != null);
		infoPanel.add(creditPointTextLabel, gbc);

		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		int creditPoint = transaction != null ? transaction.getCreditPoint() : 0;
		JLabel creditPointValueLabel = new JLabel(Integer.toString(creditPoint));
		creditPointValueLabel.setVisible(member != null);
		infoPanel.add(creditPointValueLabel, gbc);
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
		JLabel balancePointValueLabel = new JLabel(Integer.toString(balancePoint));
		balancePointValueLabel.setVisible(member != null);
		infoPanel.add(balancePointValueLabel, gbc);
		p.add(infoPanel, BorderLayout.EAST);

		return p;
	}
}