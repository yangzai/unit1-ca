package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

/*
 * created by Srishti
 */

public class TransactionManager {
	
	private int transactionID;
	private List<TransactionItem> item ;
	private List<Transaction> transaction;
	private String fileName;
	
	
	
	public TransactionManager(String fileName)  throws IOException
	{
		transaction = new ArrayList<Transaction>();
		item = new ArrayList<TransactionItem>();
		
		this.fileName = fileName;
		initData();
	}
	
	public void addTransaction(String productId,String memberID, int quantity)
	{
		
		Date dateobj = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(dateobj);
			
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
		Transaction tran = new Transaction(transactionID, productId, memberID, quantity ,date);
		transaction.add(tran);
	}
	
	public float calculateTotalPrice(HashMap<Product, Integer> list,int discount)
	{
		String id = null;
		int quantityPurchased = 0;
		float price =0;
		Set<Product> products = list.keySet();
		
		Iterator<Product> iter = products.iterator();
		while(iter.hasNext())
		{
			Product pro = iter.next(); 
			int proprice = pro.getPrice();
			quantityPurchased = list.get(pro);
			price = price + proprice*quantityPurchased;		
		}
		
		price = price - (discount/100)*price; 
		return price;
	}
	
	private void initData() throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader(fileName);
            
            while(reader.readRecord()) {
                Object[] transactions = reader.getValues().toArray();
                
                int transactionID = Integer.parseInt(transactions[0].toString());
                String productId = transactions[1].toString();
                String memberID = transactions[2].toString();
                int quantity = Integer.parseInt(transactions[3].toString());
                String date = transactions[4].toString();   
                
                Transaction tran = new Transaction(transactionID, productId, memberID, quantity ,date);
        		transaction.add(tran);
        		
            }
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (reader != null) reader.close();
        }
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
