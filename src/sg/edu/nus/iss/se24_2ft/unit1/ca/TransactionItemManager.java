package sg.edu.nus.iss.se24_2ft.unit1.ca;

/**
 * Created by Srishti
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;

public class TransactionItemManager {

	private TransactionItem transactionItems;	
	private List<TransactionItem> transactionlist ;
	private AbstractTableModel tableModel; 
	private static final String COLUMN_NAMES [] = {"Qty.", "Item", "Description" , "Unit Price", "Discount", "Total"};

	public TransactionItemManager()
	{
		transactionlist = new ArrayList<>();
	}

	public TableModel getTableModel() {
		if (tableModel != null) return tableModel;

		tableModel  = new AbstractTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getColumnName(int column) {
				return COLUMN_NAMES[column];
			}

			@Override
			public int getRowCount() {
				return transactionlist.size();
			}

			@Override
			public int getColumnCount() {
				return COLUMN_NAMES.length;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				TransactionItem item = transactionlist.get(rowIndex);
				System.out.println(item);
				switch (columnIndex) {
				case 0: return item.getQuantityPurchased();
				case 1: return item.getProductId();
				case 2: return item.getDescription();
				case 3: return item.getUnitPrice();
				case 4: return "";
				case 5: return item.getTotalPrice();
				default: return null;
				}
			}
		};
		return tableModel;

	}
	
	public void storeTransactionItem(Product p)
	{	
		Iterator<TransactionItem> iter = transactionlist.iterator();
		int rowIndex = transactionlist.size()-1;
		while(iter.hasNext())
		{
			TransactionItem tran = iter.next();
			if(tran.getProductId() == p.getId())
			{
				// ** Increasing quantity if product ID is same as added before **//
				int quantity = tran.getQuantityPurchased(); 
				quantity = quantity+1; 
				
				// **calculating total price for each product id **//
				double price = tran.getUnitPrice();
				price = price*quantity;
				
				// ** updating the quantity purchased and total price for each product in TransactionItem class ** //
				tran.setQuantityPurchased(quantity);
				tran.setTotalPrice(price);
				
				// ** updating the table to show the changed values **// 
				tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
				
				return;
			}
			
		}
		// *** creating and adding new transaction item object for the newly added products in the cart ** //
		transactionItems = new TransactionItem(p.getId(),p.getDescription(),p.getPrice());
		transactionlist.add(transactionItems);
		tableModel.fireTableRowsInserted(rowIndex, rowIndex);
		}
	
	public List<TransactionItem> getTransactionItems()
	{
		return transactionlist;
	}
	
	public void refreshTable()
	{	
		transactionlist.clear();
		tableModel.fireTableDataChanged();
	}
	
	public double calculateTotalPrice()
	{
		double subTotal = 0;
		Iterator<TransactionItem> iter = transactionlist.iterator();
		while(iter.hasNext())
		{
			TransactionItem transactionITem = iter.next();
			subTotal += transactionITem.getTotalPrice();	
		}
		return subTotal;
	}
	
	} 

