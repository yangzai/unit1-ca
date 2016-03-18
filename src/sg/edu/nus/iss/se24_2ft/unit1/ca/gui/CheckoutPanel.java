package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.TableModel;

import javax.swing.table.AbstractTableModel;

public class CheckoutPanel extends FeaturePanel {
	private static final int VISIBLE_ROW = 10;
	private final String FORMAT = "%.2f";

	private JTable table;
	private JScrollPane scrollPane;
	private JButton addItemButton, setMemberButton, proceedPaymentButton, backButton;
	private JTextField productField;
	private JLabel memberField, subTotalField;
	private JFormattedTextField quantityField;
	private Double subTotal, totalAmount, discountAmount, redeemAmount, paidAmount, balanceAmount;
	private int loyaltyPts, redeemPts, discountRate;
	private ArrayList<TransItem> itemList = new ArrayList<TransItem>();
	private HashMap<String, TransItem> itemMap = new HashMap<String, TransItem>();

	public CheckoutPanel() {
		// Initial setting
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = gbc.weighty = 0;
		gbc.gridwidth = 1;

		// Item Panel: To key in Item details and Add into table
		JPanel itemPanel = new JPanel(new GridBagLayout());
		gbc.gridx = gbc.gridy = 0;
		add(itemPanel, gbc);
		setItemPanel(itemPanel);

		// Table to display all current records
		table = new JTable();
		scrollPane = new JScrollPane(table);
		setTableModel(getItemTableModel());

		gbc.gridy++;
		gbc.weightx = gbc.weighty = 0;
		add(scrollPane, gbc);

		// Additional Info: Membership, Subtotal
		JPanel infoPanel = new JPanel(new GridBagLayout());
		gbc.gridy++;
		add(infoPanel, gbc);
		setInfoPanel(infoPanel);

		// Menu Button Panel
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		gbc.gridy++;
		gbc.gridheight = 2;
		add(buttonPanel, gbc);
		setButtonPanel(buttonPanel);

	}

	private void setItemPanel(JPanel itemPanel) {
		// Setting
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Add Product ID input field
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		itemPanel.add(new JLabel("Enter Product ID:"), gbc);

		gbc.gridy++;
		productField = new JTextField();
		itemPanel.add(productField, gbc);

		// Add Quantity input field
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		itemPanel.add(new JLabel("Quantity:"), gbc);

		gbc.gridy++;
		quantityField = new JFormattedTextField(NumberFormat.getNumberInstance());
		quantityField.setValue(1);
		itemPanel.add(quantityField, gbc);

		// Add Add Item button
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		addItemButton = new JButton("Add Item");
		addItemButton.addActionListener(e -> {
			String productId = productField.getText();
			if ((productId == null) || (productId.isEmpty())) {
				System.out.println("Product ID is empty");
				return;
			}
			;
			int quantity = ((Number) quantityField.getValue()).intValue();
			TransItem item = new TransItem(productId, quantity);
			addItemRequested(item);
			productField.setText(null);
			quantityField.setValue(1);
		});
		itemPanel.add(addItemButton, gbc);

	}

	private void setButtonPanel(JPanel buttonPanel) {
		// Setting
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.BOTH;

		// Membership Details
		gbc.gridy = 0;
		gbc.gridx = 2;
		gbc.weightx = 0.5;
		setMemberButton = new JButton("Membership");
		setMemberButton.addActionListener(e -> {
			// TODO: Replace the idList with list of existing member and one
			// option for PUBLIC
			// TODO: Refactor this logic into Action Listener
			String[] idList = { "PUBLIC", "ID1", "ID2", "ID3", "ID4", "ID5", "ID6", "ID7", "ID8", "ID9", "ID10", "ID11",
					"ID12" };
			String id = (String) JOptionPane.showInputDialog(this, "Membership ID", "Customer Detail",
					JOptionPane.PLAIN_MESSAGE, null, idList, idList[0]);

			// TODO: Get member object based on id
			if (id == idList[0]) {
				// Set customer as Public customer
			} else {
				// TODO: Get Member details
			}
			memberField.setText(id);
		});
		buttonPanel.add(setMemberButton, gbc);

		// Proceed to Payment
		gbc.gridx++;
		gbc.weightx = 0.5;
		proceedPaymentButton = new JButton("Proceed to Payment");
		proceedPaymentButton.addActionListener(e -> {
			getTransactionData();
			new ConfirmPaymentDialog();
		});
		buttonPanel.add(proceedPaymentButton, gbc);

		// Back button
		gbc.gridx++;
		gbc.weightx = 0.5;
		backButton = new JButton("Back");
		backButton.addActionListener(e -> backActionPerformed(e));
		buttonPanel.add(backButton, gbc);

	}

	private void setInfoPanel(JPanel infoPanel) {
		// Setting
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Membership Details
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		infoPanel.add(new JLabel("Membership: "), gbc);

		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		memberField = new JLabel("PUBLIC");
		infoPanel.add(memberField, gbc);

		// Subtotal Details
		gbc.gridx++;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.ipadx = table.getWidth() / 3;
		infoPanel.add(new JLabel("Subtotal: "), gbc);

		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		subTotal = 0d;
		subTotalField = new JLabel("$" + String.format(FORMAT, subTotal));
		infoPanel.add(subTotalField, gbc);
	}

	private void addItemRequested(TransItem item) {
		// Check if Product ID already exist
		TransItem i = itemMap.get(item.getProductID());
		if (i == null) {
			itemMap.put(item.getProductID(), item);
		} else {
			i.addQuantity(item.getQuantity());
		}

		// Convert HashMap to arrayList and update JTable
		itemList = new ArrayList<TransItem>(itemMap.values());
		((AbstractTableModel) table.getModel()).fireTableDataChanged();

		// TODO: Recalculate the Subtotal here and remove the Sample code
		subTotal += item.quantity * 1.2;
		subTotalField.setText("$" + String.format(FORMAT, subTotal));
	}

	public TableModel getItemTableModel() {
		return new AbstractTableModel() {
			private final String[] COLUMN_NAMES = { "Qty", "ID", "Description", "Unit Price" };

			@Override
			public String getColumnName(int column) {
				return COLUMN_NAMES[column];
			}

			@Override
			public int getRowCount() {
				return itemList.size();
			}

			@Override
			public int getColumnCount() {
				return COLUMN_NAMES.length;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				TransItem item = itemList.get(rowIndex);
				switch (columnIndex) {
				case 0:
					return item.getQuantity();
				case 1:
					return item.getProductID();
				case 2:
					return item.getDescription();
				case 3:
					return "$" + String.format(FORMAT, item.getUnitPrice());
				default:
					return null;
				}
			}
		};
	}

	// TODO Manager to be included to get data
	private void getTransactionData() {
		// Mock data
		loyaltyPts = 100;
		discountRate = 15;
		
		// Get discountPrice
		discountAmount = subTotal * discountRate / 100;

		// Get Initial value
		redeemAmount = 0d;
		paidAmount = 0d;
		totalAmount = subTotal - discountAmount - redeemAmount;
		balanceAmount = paidAmount - totalAmount; 

	}

	public void setTableModel(TableModel tableModel) {
		table.setModel(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(75);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);
		Dimension d = table.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width + 5, table.getRowHeight() * VISIBLE_ROW + 1));
	}

	// TODO: Refactoring this class, suggest to use TransactionItem instead.
	class TransItem {
		String productID;
		int quantity;

		TransItem(String productID, int quantity) {
			this.productID = productID;
			this.quantity = quantity;
		}

		public String getProductID() {
			return productID;
		}

		public int getQuantity() {
			return quantity;
		}

		public String getDescription() {
			return "Sample Description";
		}

		public double getUnitPrice() {
			// Sample Unit Price
			return (double) quantity * 1.2;
		}

		public void addQuantity(int quantity) {
			this.quantity += quantity;
		}
	}

	class ConfirmPaymentDialog extends JDialog {
		private final int WIDTH = 600;
		private final int HEIGHT = 210;

		ConfirmPaymentDialog() {
			setTitle("Payment Details");
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setResizable(false);

			// Panel for Payment Dialog
			JPanel paymentPanel = new JPanel(new GridBagLayout());
			paymentPanel.setPreferredSize(this.getPreferredSize());
			setPaymentPanel(paymentPanel);
			add(paymentPanel);

			this.validate();
			this.pack();
			this.setVisible(true);
		}
	}

	private void setPaymentPanel(JPanel panel) {
		// Setting
		GridBagConstraints gbc = new GridBagConstraints();
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
		JTextArea subtotalTA = new JTextArea("$" + String.format(FORMAT, subTotal));
		subtotalTA.setEditable(false);
		panel.add(subtotalTA, gbc);

		// Redeem Value
		gbc.gridy++;
		gbc.gridx--;
		gbc.gridwidth = 1;
		panel.add(new JLabel("Redeem: "), gbc);

		gbc.gridx++;
		gbc.weightx = 1;
		gbc.gridwidth = 3;
		JTextArea redeemTA = new JTextArea("$" + String.format(FORMAT, redeemAmount));
		redeemTA.setEditable(false);
		panel.add(redeemTA, gbc);

		// Discount Amount
		gbc.gridy++;
		gbc.gridx--;
		gbc.gridwidth = 1;
		String lblDiscount = "Discount (";
		lblDiscount += String.valueOf(discountRate) + "%):";
		panel.add(new JLabel(lblDiscount), gbc);

		gbc.gridx++;
		gbc.weightx = 1;
		gbc.gridwidth = 3;
		JTextArea discountTA = new JTextArea("$" + String.format(FORMAT, discountAmount));
		discountTA.setEditable(false);
		panel.add(discountTA, gbc);

		// Total Price - Using top padding;	
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridy++;
		gbc.gridx--;
		gbc.gridwidth = 1;
		panel.add(new JLabel("Total:"), gbc);

		gbc.gridx++;
		gbc.weightx = 1;
		gbc.gridwidth = 3;
		JTextArea totalTA = new JTextArea("$" + String.format(FORMAT, totalAmount));
		totalTA.setEditable(false);
		panel.add(totalTA, gbc);

		// Amount Paid
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy++;
		gbc.gridx--;
		gbc.gridwidth = 1;
		panel.add(new JLabel("Amount Paid:"), gbc);

		gbc.gridx++;
		gbc.weightx = 1;
		gbc.gridwidth = 3;
		JTextArea payTA = new JTextArea("$" + String.format(FORMAT, paidAmount));
		payTA.setEditable(false);
		panel.add(payTA, gbc);

		// Balance
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridy++;
		gbc.gridx--;
		gbc.gridwidth = 1;
		panel.add(new JLabel("Balance:"), gbc);

		gbc.gridx++;
		gbc.weightx = 1;
		gbc.gridwidth = 3;
		JTextArea balanceTA = new JTextArea("$" + String.format(FORMAT, balanceAmount));
		panel.add(balanceTA, gbc);

		// Button
		gbc.gridy++;
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;
		JButton redeemButton = new JButton("Redeem");
		redeemButton.addActionListener(l -> {
			String message = "Enter Redeem Point (Max. ";
			message += String.valueOf(loyaltyPts) + "): ";
			String point = (String) JOptionPane.showInputDialog(this, message, "Redeem",
					JOptionPane.PLAIN_MESSAGE, null, null, "0");
			try {
			    redeemPts = Integer.parseInt(point);
			} catch (NumberFormatException e) {
				redeemPts = 0;
			    // Not a number, display error message...
				System.out.println("Redeem Points Input is not a valid number");
			}
			//TODO Get the Redeem Price based on the redeem Point input
			updatePaymentData();
			redeemTA.setText("$" + String.format(FORMAT, redeemAmount));
			totalTA.setText("$" + String.format(FORMAT, totalAmount));
			balanceTA.setText("$" + String.format(FORMAT, balanceAmount));
			
		});
		panel.add(redeemButton, gbc);

		gbc.gridx++;
		JButton paymentButton = new JButton("Payment");
		paymentButton.addActionListener(l -> {
			String message = "Enter Amount Paid by Customer ($): ";
			String input = (String) JOptionPane.showInputDialog(this, message, "Payment",
					JOptionPane.PLAIN_MESSAGE, null, null, "0");
			try {
			    paidAmount = Double.parseDouble(input);
			} catch (NumberFormatException e) {
				paidAmount = 0d;
			    // Not a number, display error message...
				System.out.println("Amount Paid Input is not a valid number");
			}
			//TODO Get the Redeem Price based on the redeem Point input
			updatePaymentData();
			payTA.setText("$" + String.format(FORMAT, paidAmount));
			balanceTA.setText("$" + String.format(FORMAT, balanceAmount));
		});
		panel.add(paymentButton, gbc);
		
		gbc.gridx++;
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(l -> {
			if (balanceAmount < 0) {
				JOptionPane.showMessageDialog(this, "Amount paid is not enough, please retry");
			} else {
				String msg = "Transaction Completed! Balance is: $" + String.format(FORMAT, balanceAmount);
				int result = JOptionPane.showConfirmDialog(this, msg, "Confirm Payment", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					//TODO Show Receipt here
					
					//TODO Update Transaction data to Database and back to Main Menu
				}
			}
			
		});
		panel.add(confirmButton, gbc);
	}
	
	private void updatePaymentData(){
		//TODO replace w logic to get redeemAmount based on Redeem Point input
		redeemAmount = (double)(redeemPts / 5);
		
		//Recalculate balance
		totalAmount = subTotal - discountAmount - redeemAmount;
		balanceAmount = paidAmount - totalAmount;	
	}
}
