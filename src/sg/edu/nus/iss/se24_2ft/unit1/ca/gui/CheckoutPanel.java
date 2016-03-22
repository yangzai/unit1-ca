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
import java.awt.event.ActionEvent;

import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 * Created by Srishti
 */


public class CheckoutPanel extends FeaturePanel {

	private static final long serialVersionUID = 1L;

	private JPanel top,top1_right_panel,top1_left,top1,bottom, memberId_panel, enterItem_panel;
	private GridBagConstraints gc;
	private JScrollPane scroller,scroll;
	private JTextPane area;
	private JTextArea text, memberID_text;
	private JLabel label,itemlabel, memberId_label;
	private JButton select,entry,addItem,logOff,btn_qty,view_receipt, pay, new_tran,discount;
	private JTable table;
	private Component rigidArea;
	private  Border loweredetched,
	raisedetched;
	private CheckOutPanelListener l;
	private double subTotal=0,discount_amount = 0,total =0.0;
	private JFrame frame;
	private String member_ID;

	public CheckoutPanel() {
		// TODO Auto-generated constructor stub
		super(new GridBagLayout());
		area = new JTextPane();
		area.setBackground(Color.pink);
		area.setText("Total: $         " + " \n" +"Discount:       " +"\n\n" + "Subtotal: $       ");


		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

		gc = new GridBagConstraints();
		gc.insets = new Insets(15,15,15,15);
		///***************************** Adding Top Panel *************************************************///////
		top = new JPanel(new BorderLayout());
		top.setBackground(Color.LIGHT_GRAY);
		top.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched));
		label = new JLabel("Salesperson: 	Srishti");
		top.add(label,BorderLayout.WEST);

		logOff = new JButton("Back");
		logOff.addActionListener(e -> {
			refreshFields(e);
			l.refreshTable();
			backActionPerformed(e);		
		});
		logOff.setFont(new Font("Tahoma", Font.BOLD, 10));
		top.add(logOff,BorderLayout.EAST);

		gc.gridx=0;
		gc.gridy =0;
		gc.weightx =0.3;
		gc.fill = GridBagConstraints.HORIZONTAL;	
		add(top,gc);

		//***************************************** Adding second Panel **********************************////		

		top1 = new JPanel(new BorderLayout());
		top1.setPreferredSize(new Dimension(60,60));
		top1.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched));
		top1_left = new JPanel(new GridLayout(1, 0,5,5));
		top1_right_panel = new JPanel(new GridLayout(1, 0,5,5));


		enterItem_panel = new JPanel(new GridLayout(2, 1));		
		//*** Adding Item code textField *********////	
		itemlabel = new JLabel("Enter Item Code");
		enterItem_panel.add(itemlabel);

		/// *********Adding text area***////
		text = new JTextArea(0,0);
		text.setBorder(BorderFactory.createLineBorder(Color.BLACK));	
		enterItem_panel.add(text);
		top1_left.add(enterItem_panel);


		//*** Adding Add item button*********////
		addItem = new JButton("<html><Center>" +"Add to" +"<br>" +"Cart" +"</center></html>");
		addItem.addActionListener(e -> {
			String item_code = text.getText();
			if(item_code.equals("")){
				JOptionPane.showMessageDialog(frame, "Enter Item code"); 
			}else
			{
				l.getTransactionDetails(item_code);
			}

		});

		addItem.setFont(new Font("Tahoma", Font.BOLD, 10));
		top1_left.add(addItem);

		// *********** adding rigid area between buttons *******************// 
		rigidArea = Box.createRigidArea(new Dimension(30,30));
		top1_right_panel.add(rigidArea);	

		///**and select item button **//
		select = new JButton();
		select.setText("<html><Center>" +"Select" +"<br>" +"Item" +"</center></html>");
		select.setPreferredSize(new Dimension(60,15));
		select.setFont(new Font("Tahoma", Font.BOLD, 10));
		top1_right_panel.add(select);

		///**and quantity button **//
		btn_qty = new JButton();
		btn_qty.setText("<html><Center>" +"Adjust" +"<br>" +"Quantity" +"</center></html>");
		btn_qty.addActionListener(e ->{
			if(table.getRowCount()==0)
			{
				
			}else{
				
			}
		});
		btn_qty.setFont(new Font("Tahoma", Font.BOLD, 10));
		top1_right_panel.add(btn_qty);

		top1.add(top1_right_panel,BorderLayout.EAST);
		top1.add(top1_left,BorderLayout.WEST);

		gc.gridx=0;
		gc.gridy =1;
		gc.weightx =0.3;
		gc.insets = new Insets(5,5,5,5);
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(top1,gc); 

		////////////********************************* Adding table *******************************************////////

		table = new JTable();
		//table.getColumnModel().getColumn(2).setMinWidth(600);
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

		/// *************Adding TextPane *************************/////	
		////// *****************Styling text *************************/////
		StyledDocument doc = area.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		doc.setParagraphAttributes(0, doc.getLength(), right, false);

		area.setFont(new Font("Tahoma", Font.BOLD, 13));
		area.setEditable(false);
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

		///////////////// ****************** Creating bottom panel ***************************************//////////		

		bottom =new JPanel(new GridLayout(1,0));

		// ******************************** Adding member ID panel **********/////////
		memberId_panel = new JPanel(new GridLayout(2, 1));

		memberId_label = new JLabel("Enter Member ID:");
		memberId_panel.add(memberId_label);

		memberID_text = new JTextArea();

		memberID_text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		memberId_panel.add(memberID_text);

		bottom.add(memberId_panel);

		/// *** Adding check discount available *** /// 
		discount = new JButton("<html><Center>" +"Apply" +"<br>" +"Discount" +"</center></html>");
		discount.addActionListener(e -> {
			if((memberID_text.getText()).equals("") || (memberID_text.getText()).equals("PUBLIC")  ){
				member_ID = "PUBLIC";}else{
				member_ID = memberID_text.getText();}
			discount_amount = l.getDiscount(member_ID);
			frame = l.getMainFrame();
	
			DiscountDialog d = new DiscountDialog(frame){
				public boolean performOkAction()
				{
					total =l.getDiscountedAmount(subTotal,discount_amount);
					refreshFields(e);
					return true;
				}
				public boolean performCancel()
				{
					discount_amount = 0.0;
					return true;
				}
				
				public String[] getDiscountDetails()
				{
					String[] detail = {member_ID,Double.toString(discount_amount)};
					
					return detail;
				}
			};	
			d.setModal(true);
			d.setLocationRelativeTo(this);
			d.setSize(300, 200);
			d.setVisible(true);
		});

		bottom.add(discount);

		//	 ************ adding rigid area ********************//
		rigidArea = Box.createRigidArea(new Dimension(90,30));
		bottom.add(rigidArea);		



		//*************** Pay Button *********************//	
		pay = new JButton("Pay");
		pay.addActionListener(e ->{
			if(table.getRowCount() == 0){
				JOptionPane.showMessageDialog(frame, "No items selected");  
			}else if(view_receipt.isEnabled() == true){
				JOptionPane.showMessageDialog(frame, "Confirm your selection before proceding");  
			}
				else{
			
				PaymentDialog pay = new PaymentDialog(frame){
					public double getSubTotal(){
						return total;
					}
					public double getDiscountAmount(){
						return discount_amount;
					}
				};
				pay.setModal(true);
				pay.setSize(400, 400);
				pay.setVisible(true);
			}
		});
		bottom.add(pay);


		// ************************ Confirm Selection Button ***************/// 		
		view_receipt = new JButton("Confirm Selection");
		view_receipt.addActionListener(e -> {
			if(table.getRowCount() == 0){
				JOptionPane.showMessageDialog(frame, "No items to confirm");                 
			}else
			{
				subTotal = l.calculateSubtotal();
				total=subTotal;
				refreshFields(e);
			}
		});
		bottom.add(view_receipt);

		// **************New Transaction Button ************* // 
		new_tran = new JButton("New Transaction");
		new_tran.addActionListener(e -> {
			refreshFields(e);
			l.refreshTable();
		});
		bottom.add(new_tran);

		gc.gridx=0;
		gc.gridy =4;
		gc.weightx =0;
		gc.weighty =0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(bottom,gc);


	}

	public void addCheckOutListener(CheckOutPanelListener l)
	{
		this.l = l; 
	}

	public void setTableModel(TableModel model)
	{
		table.setModel(model);
	}
	public JFrame getFrame(){
		JFrame frame = new JFrame();
		return frame;
	}


	public void refreshFields(ActionEvent ae)
	{
		if(ae.getSource() == new_tran || ae.getSource() == logOff )
		{
			subTotal =0;
			total=0.0;
			discount_amount =0;
			view_receipt.setEnabled(true);	
		}
		else if(ae.getSource() != discount){
			view_receipt.setEnabled(false);	
		}
		area.setText("Total: $" +subTotal+" \n" +"Discount: " +discount_amount+ "%"  +"\n\n" + "Subtotal: $" +total);
		text.setText("");		
	}



}