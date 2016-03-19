package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class TransactionItem {
	private int quantityPurchased;
	private String productId;
	private double price, totalPrice;
	private String Description;
	
	/**
	 * created by Srishti
	 */
	
	public TransactionItem(String productId,String Description, double price) {
		super();
		this.quantityPurchased = 1;
		this.productId = productId;
		this.price = price;
		this.totalPrice = getUnitPrice();
		this.Description = Description;
	}
	

	public int getQuantityPurchased() {
		return quantityPurchased;
	}

	public String getProductId() {
		return productId;
	}

	public double getUnitPrice() {
		return price;
	}
	
	public double getTotalPrice()
	{
		return totalPrice;
	}
	 
	public String getDescription()
	{
		return Description;
	}

	public void setQuantityPurchased(int quantityPurchased)
	{
		this.quantityPurchased = quantityPurchased;
	}
	
	public void setTotalPrice(double totalPrice)
	{
		this.totalPrice = totalPrice;
	}
}
