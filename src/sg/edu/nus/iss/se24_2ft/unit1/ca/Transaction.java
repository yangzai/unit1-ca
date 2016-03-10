package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.util.Date;

public class Transaction {
	
	private int transactionID;
	private String productId;
	private String memberID;
	private int quantity;
	private Date date;
	
	public Transaction(int transactionID, String productId,String memberID, int quantity,Date date)
	{
		this.transactionID = transactionID;
		this.productId = productId;
		this.memberID = memberID;
		this.quantity = quantity;
		date = new Date();
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getProductId() {
		return productId;
	}
	public String getMemberID() {
		return memberID;
	}
	public int getQuantity() {
		return quantity;
	}
	public Date getDate() {
		return date;
	}
	
	

	
}
