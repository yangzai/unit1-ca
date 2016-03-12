package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.StoreApplication;

// ** Created by Srishti ** // 

public  class InventoryPanel extends JPanel {


	JTable table;
	private List<Product> list;
	//private StoreApplication store;
	private String[] COLUMN_NAMES = {"ID","Name","Description","Quantity Avl.","Price",
			"Bar Code","Reorder Quantity","Order Quantity"};

	public InventoryPanel(/*StoreApplication store*/){
		// TODO Auto-generated constructor stub
		super(new GridBagLayout());

		//this.store= store;

		list = initData();
		table = new JTable(getTableModel());
		JScrollPane scroller = new JScrollPane(table);

		JButton back = new JButton("Back");

		GridBagConstraints gc = new GridBagConstraints();
		JLabel label = new JLabel("Inventory Below Threshold");

		gc.gridx = 0 ; 
		gc.gridy = 0 ;
		add(label,gc);


		gc.gridx = 0 ; 
		gc.gridy =1 ;
		gc.weightx =0.3;
		gc.fill = GridBagConstraints.BOTH;
		add(scroller,gc);


		JButton updateButton = new JButton("Generate Purchase Order");
		updateButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				int[] rowcount  = table.getSelectedRows();

				int col1 =0; 
				int col2 =0;

				Integer quantity =null;

				for(int a : rowcount)
				{
					for(int i=0; i< table.getColumnCount() ; i++)
					{
						if (table.getColumnName(i).equals("Quantity Avl."))
						{
							col1 = i;
							quantity = (Integer)table.getValueAt(a, col1);
						}
						if (table.getColumnName(i).equals("Order Quantity"))
						{
							col2 = i;
							quantity = quantity + (Integer)table.getValueAt(a,col2);
						}

					}

					table.setValueAt(quantity, a, col1);
					list.get(a).setQuantity(quantity);
					
				}
				JFrame frame = new JFrame();
				int response = JOptionPane.showConfirmDialog(
					    frame,
					    "Sure to place an order for selected items?",
					    "An Inane Question",
					    JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {
					((AbstractTableModel)table.getModel()).fireTableRowsUpdated(0, table.getRowCount());
				}
				else
				{
					frame.dispose();
				}

			}

		});

		JPanel buttonPanel = new JPanel();

		gc.gridx = 0; 
		gc.gridy =0 ;
		gc.weightx =0;
		buttonPanel.add(updateButton,gc);

		gc.gridx = 0; 
		gc.gridy =1 ;
		gc.weightx =0;
		buttonPanel.add(back,gc);

		gc.gridx = 1; 
		gc.gridy =1;
		gc.weightx =0;
		add(buttonPanel,gc);

	}

	public AbstractTableModel getTableModel()
	{
		AbstractTableModel tableModel = new AbstractTableModel(){
			@Override
			public String getColumnName(int column) {
				return COLUMN_NAMES[column];
			}

			@Override
			public int getRowCount() {
				return list.size();
			}

			@Override
			public int getColumnCount() {
				return COLUMN_NAMES.length;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Product product = list.get(rowIndex);
				switch (columnIndex) {
				case 0: return product.getId();
				case 1: return product.getName();
				case 2: return product.getDescription();
				case 3: return product.getQuantity();
				case 4: return product.getPrice();
				case 5: return product.getBarCode();
				case 6: return product.getTreshold();
				case 7: return product.getOrderQuantity();
				default: return null;
				}
			}

		}; 

		return tableModel;
	}
	//// ************************************** this method is for testing *****************************************////	
	public List<Product> initData()
	{
		list = (new StoreApplication()).getProductList();
		ArrayList<Product> product = new ArrayList<Product>();

		list.get(1).setQuantity(24);
		list.get(2).setQuantity(49);
		list.get(0).setQuantity(9);

		Iterator<Product> iter = list.iterator();
		while(iter.hasNext())
		{
			Product p = iter.next();
			if(p.getQuantity() < p.getTreshold())
			{
				product.add(p);
			}
		}


		return product;
	}

}

