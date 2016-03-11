package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.util.Date;

public class TransactionItem {
	private int quantityPurchased;
	private String productId;
	private String memberID;
	private double price ;
	private int discount ;
	private int tranId;
	private String date; 
	
	/**
	 * created by Srishti
	 */
	
	public TransactionItem(int quantityPurchased, String productId,String memberID, double price, int discount, int tranId, String date) {
		super();
		this.quantityPurchased = quantityPurchased;
		this.productId = productId;
		this.price = price;
		this.discount = discount;
		this.tranId = tranId;
		this.date = date;
		this.memberID = memberID;
	}
	

	public int getQuantityPurchased() {
		return quantityPurchased;
	}

	public String getProductId() {
		return productId;
	}

	public double getPrice() {
		return price;
	}

	public int getDiscount() {
		return discount;
	}

	public int getTranId() {
		return tranId;
	}



}
