package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

/*
 * created by Srishti
 */

public class TransactionManager {
	
	private int transactionID;
	private List<TransactionItem> item ;
	private List<Transaction> transaction;
	private String fileName;
	//private TransactionItemManager transactionItems;	
	
	
	public TransactionManager(String fileName, List<TransactionItem> item)  throws IOException
	{
		transaction = new ArrayList<Transaction>();
		this.item = item;
		this.fileName = fileName;
		initData();
	}
	
	public void addTransaction(String memberID)
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
		for(TransactionItem transactionItem : item)
		{
		Transaction tran = new Transaction(transactionID, transactionItem.getProductId(), memberID, transactionItem.getQuantityPurchased() ,date);
		transaction.add(tran);
		}
	}
	
	public double calculateTotalPrice(int discount)
	{
		double price =0;
		
		Iterator<TransactionItem> iter = item.iterator();
		while(iter.hasNext())
		{
			price += (iter.next()).getTotalPrice();
			System.out.println(price +"Priceeeeeeee");
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
}
	
