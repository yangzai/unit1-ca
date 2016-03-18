package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class CheckoutPanel extends FeaturePanel {
	
	private static final String COLUMN_NAMES [] = {"Qty.", "Item", "Description" , "Unit Price", "Discount", "tax", "Total"};
	private JPanel top,top1_right_panel,top1_left,top1,bottom;
	private GridBagConstraints gc;

	//private Container c;
	private JScrollPane scroller,scroll;
	private JTextPane area;
	private JLabel label,itemlabel;
	private JButton select,entry,addItem,logOff,btn_qty,view_receipt, pay, new_tran;
	private JTable table;
	private  Border loweredetched,
	raisedetched;

	public CheckoutPanel() {
		// TODO Auto-generated constructor stub
		super(new GridBagLayout());
		
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

		gc = new GridBagConstraints();
		gc.insets = new Insets(15,15,15,15);

		top = new JPanel(new BorderLayout());
		top.setBackground(Color.LIGHT_GRAY);
		top.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched));
		// Populte this with the storkeeper name **//
		label = new JLabel("Salesperson: 	Srishti");
		top.add(label,BorderLayout.WEST);

		logOff = new JButton("Back");
		logOff.setFont(new Font("Tahoma", Font.BOLD, 10));
		System.out.println(logOff.getSize());
		top.add(logOff,BorderLayout.EAST);

		gc.gridx=0;
		gc.gridy =0;
		gc.weightx =0.3;
		gc.fill = GridBagConstraints.HORIZONTAL;	
		add(top,gc);

		top1 = new JPanel(new BorderLayout());
		top1.setPreferredSize(new Dimension(60,60));


		top1.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched));

		top1_left = new JPanel(new GridBagLayout());
		top1_right_panel = new JPanel(new GridLayout(1, 0,10,10));

		itemlabel = new JLabel("Enter Item Code");
		JTextArea text = new JTextArea(0,0);
		text.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		addItem = new JButton("Add Item");

		select = new JButton();
		select.setText("<html><Center>" +"Select" +"<br>" +"Item" +"</center></html>");
		btn_qty = new JButton();
		btn_qty.setText("<html><Center>" +"Adjust" +"<br>" +"Quantity" +"</center></html>");
		entry = new JButton();
		entry.setPreferredSize(new Dimension(60,15));
		select.setPreferredSize(new Dimension(60,15));
		select.setFont(new Font("Tahoma", Font.BOLD, 10));
		entry.setFont(new Font("Tahoma", Font.BOLD, 10));
		addItem.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_qty.setFont(new Font("Tahoma", Font.BOLD, 10));
		entry.setPreferredSize(new Dimension(60, 15));
		entry.setText("<html><center>"+"Manual"+"<br>"+"Item Entry"+"</center></html>");

		gc.gridx=0;
		gc.gridy =0;
		gc.weightx =0;
		gc.fill = GridBagConstraints.HORIZONTAL;	
		gc.insets = new Insets(0,0,0,0);
		top1_left.add(itemlabel,gc);

		gc.gridx=0;
		gc.gridy =1;
		gc.weightx =0;
		gc.insets = new Insets(10,10,10,10);
		gc.fill = GridBagConstraints.HORIZONTAL;	
		top1_left.add(text,gc);

		gc.gridx= 2;
		gc.gridy =1;
		gc.weightx =1;
		gc.fill = GridBagConstraints.VERTICAL;	
		gc.insets = new Insets(0,10,10,0);
		top1_left.add(addItem,gc);

		top1_right_panel.add(select);

		top1_right_panel.add(entry);	
		top1_right_panel.add(btn_qty);
		top1.add(top1_right_panel,BorderLayout.EAST);
		top1.add(top1_left,BorderLayout.WEST);


		gc.gridx=0;
		gc.gridy =1;
		gc.weightx =0.3;
		gc.insets = new Insets(5,5,5,5);
		gc.fill = GridBagConstraints.HORIZONTAL;

		add(top1,gc); 

		table = new JTable();
		table.setModel(new AbstractTableModel() {
			@Override
			public String getColumnName(int column) {
				return COLUMN_NAMES[column];
			}

			@Override
			public int getRowCount() {
				return 7;
			}

			@Override
			public int getColumnCount() {
				return COLUMN_NAMES.length;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// Category category = categoryList.get(rowIndex);
				switch (columnIndex) {
				case 0: return null;
				case 1: return null;
				default: return null;
				}
			}
		});

		table.getColumnModel().getColumn(2).setMinWidth(600);

		scroller = new JScrollPane(table);
		scroller.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched));
		scroller.setPreferredSize(new Dimension(200,200));
		gc.gridx=0;
		gc.gridy =2;
		gc.weightx =0.3;
		gc.weighty =0.3;
		gc.fill = GridBagConstraints.BOTH;
		add(scroller,gc);

		area = new JTextPane();
		int total = 0;
		area.setBackground(Color.pink);
		area.setText("Total:    $" +total + "      ");
		
		StyledDocument doc = area.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		doc.setParagraphAttributes(0, doc.getLength(), right, false);
		
		area.setFont(new Font("Tahoma", Font.BOLD, 13));

		area.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched));
		scroll = new JScrollPane(area);

		gc.gridx=0;
		gc.gridy =3;
		gc.weightx =0.3;
		gc.weighty =0;
		gc.fill = GridBagConstraints.BOTH;
		scroll.setPreferredSize(new Dimension(120,120));
		add(scroll,gc);
		
		bottom =new JPanel(new GridLayout(1,0));
		Component rigidArea = Box.createRigidArea(new Dimension(30,30));
		bottom.add(rigidArea);

		view_receipt = new JButton("View Reciept");
		//view_receipt.setPreferredSize(new Dimension(60,15));
		bottom.add(view_receipt);
		pay = new JButton("Pay");
		//pay.setPreferredSize(new Dimension(30,30));
		bottom.add(pay);
		new_tran = new JButton("New Transaction");
		//new_tran.setPreferredSize(new Dimension(60,15));
		bottom.add(new_tran);
		
		gc.gridx=0;
		gc.gridy =4;
		gc.weightx =0;
		gc.weighty =0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(bottom,gc);
	
		
	}

}
