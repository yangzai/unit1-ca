package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class TransactionManager {
	
	private int transactionID;
	private List<TransactionItem> item ;
	private List<Transaction> transaction;
	HashMap<Product, Integer> list;
	
	public TransactionManager(HashMap<Product, Integer> list)
	{
		this.list = list;
		transaction = new ArrayList<Transaction>();
		item = new ArrayList<TransactionItem>();
	}
	
	public void addTransaction(String productId,String memberID, int quantity)
	{
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		Date dateobj = new Date();
		String date = df.format(dateobj);
		Date currentdate = df.parse(date);
		
		Iterator<Transaction> iter = transaction.iterator();
		while(iter.hasNext())
		{
			Transaction tran = iter.next();
			if((tran.getDate().toString()).equals(date))
					{
						transactionID = tran.getTransactionID();
					}
		}	
		transactionID++;
		Transaction tran = new Transaction(transactionID, productId, memberID, quantity ,currentdate);
		transaction.add(tran);
	}
	
	public float calculateTotalPrice(int discount)
	{
		String id = 0;
		int quantityPurchased = 0;
		float price =0;
		List<Product> products = list.getKeySet();
		
		Iterator<Product> iter = products.iterator();
		while(iter.hasNext())
		{
			Product pro = iter.next(); 
			int proprice = pro.getPrice();
			quantityPurchased = list.getValue(pro);
			price = price + proprice*quantityPurchased;		
		}
		
		price = price - (discount/100)*price; 
		return price;
	}
	
	public void storeItems(float price , int discount)
	{
		Iterator<Transaction> iter = transaction.iterator();
		while(iter.hasNext())
		{
			Transaction tran = iter.next(); 

			item.add(new TransactionItem(tran.getQuantity(),tran.getProductId(),tran.getMemberID(),price,discount,tran.getTransactionID(),tran.getDate()));
		}
	}

}
