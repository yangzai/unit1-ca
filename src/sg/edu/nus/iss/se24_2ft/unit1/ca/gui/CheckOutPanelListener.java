package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import sg.edu.nus.iss.se24_2ft.unit1.ca.TransactionItem;

//Created by Srishti 
public interface CheckOutPanelListener {
	
	public void getTransactionDetails(String id);
	
	public void refreshTable(); 
	
	public double calculateSubtotal();

}
