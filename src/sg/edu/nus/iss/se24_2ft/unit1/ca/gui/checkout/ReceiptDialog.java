package sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.Transaction;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

/**
 * 
 * @author Srishti Miglani
 *
 */
public class ReceiptDialog extends JDialog {
	private Transaction transaction;
	private JTable table;

	public ReceiptDialog(Component parentComponent,Transaction transaction)
	{
		super((JFrame) SwingUtilities.windowForComponent(parentComponent),
				"Order Receipt", true);
		this.transaction = transaction;
		setContentPane(getReceiptPanel());
		pack();
	}

	public JPanel getReceiptPanel(){

		JPanel receiptPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(7, 0, 0, 0);

		gc.gridx = gc.gridy = 0;
		labelPanel.add(new JLabel(),gc);

		gc.gridx = gc.gridy = 1;
		JLabel tranLabel = new JLabel("Transaction ID:  " +transaction.getId());
		tranLabel.setFont(new Font("Serif", Font.BOLD, 17));
		labelPanel.add(tranLabel, gc);


		gc.gridy++;
		JLabel dateLabel = new JLabel("Issue Date:  " +transaction.getDate());
		dateLabel.setFont(new Font("Serif", Font.PLAIN, 17));
		labelPanel.add(dateLabel, gc);
		receiptPanel.add(labelPanel,BorderLayout.NORTH);

		table = new JTable();
		table.setModel(transaction.getTableModel());
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(table);
		scroller.setPreferredSize(new Dimension(200,200));


		receiptPanel.add(scroller,BorderLayout.CENTER);
		receiptPanel.add(getAdditionalInfo(),BorderLayout.SOUTH);

		return receiptPanel;


	}


	public JPanel getAdditionalInfo(){

		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JPanel infoPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(7,7,7,7);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.ipadx = table.getWidth() / 3;
		infoPanel.add(new JLabel("Subtotal: "), gbc);

		gbc.gridx=3;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel subTotalField = new JLabel(Utils.formatDollar(transaction.getSubtotal()));
		infoPanel.add(subTotalField, gbc);

		gbc.gridy = 1;
		gbc.gridx = 2;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		infoPanel.add(new JLabel("Discount: (" +transaction.getDiscount().getPercent()+"%)"),gbc);

		gbc.gridx =3;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel discountField = new JLabel(Utils.formatDollar(transaction.getDiscountAmount()));
		infoPanel.add(discountField,gbc);


		gbc.gridy = 4;
		gbc.gridx = 2;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		infoPanel.add(new JLabel("Cash:"),gbc);

		gbc.gridx =3;
		gbc.gridy = 4;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel cash = new JLabel(Utils.formatDollar(transaction.getPayment()));
		infoPanel.add(cash,gbc);

		gbc.gridy = 5;
		gbc.gridx = 2;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		infoPanel.add(new JLabel("Balance:"),gbc);

		gbc.gridx =3;
		gbc.gridy = 5;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel balance = new JLabel(Utils.formatDollar(transaction.getBalance()));
		infoPanel.add(balance,gbc);

		p.add(infoPanel,BorderLayout.EAST);

		return p;

	}

}
