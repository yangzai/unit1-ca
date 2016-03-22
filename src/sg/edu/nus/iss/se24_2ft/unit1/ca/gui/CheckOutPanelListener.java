package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import javax.swing.JFrame;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;

/**
 * Created by Srishti
 */


public interface CheckOutPanelListener {
	
	public void getTransactionDetails(String id);
	
	public void refreshTable(); 
	
	public double calculateSubtotal();
	
	public double getDiscount(String id);
	
	public double getDiscountedAmount(double price ,double discount);
	
	public JFrame getMainFrame();

}
