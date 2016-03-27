package sg.edu.nus.iss.se24_2ft.unit1.ca;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;

public class TransactionItem {
	//TODO: this might be a package/inner class
	private Product product;
	private int quantity;

	/**
	 * created by Srishti
	 */

	public TransactionItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() { return product; }

	public int getQuantity() { return quantity; }

	//setter
	/*package*/ void addQuantity(int quantity) {
		this.quantity += quantity;
	}
}