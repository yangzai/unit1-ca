package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;

import javax.swing.table.AbstractTableModel;

public class CheckoutPanel extends FeaturePanel {
	private static final int VISIBLE_ROW = 5;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 200;
	private JTable table;
    private JScrollPane scrollPane;
    private ArrayList<TransItem> itemList = new ArrayList<TransItem>();

	public CheckoutPanel() {	
        // Initial setting
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
		//Add Product ID input field	
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		add(new JLabel("Enter Product ID:"), gbc);
		
		gbc.gridy++;
		JTextField productTextField = new JTextField();
		add(productTextField, gbc);
		
		//Add Quantity input field
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		add(new JLabel("Quantity:"), gbc);
		
		gbc.gridy++;
		JFormattedTextField quantityField = new JFormattedTextField(NumberFormat.getNumberInstance());
		quantityField.setValue(1);
		add(quantityField, gbc);
		
		//Add Add Item button
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
        	String productId = productTextField.getText();
        	int quantity = ((Number) quantityField.getValue()).intValue();
            TransItem item = new TransItem(productId, quantity);
            addItemRequested(item);
            productTextField.setText(null);
            quantityField.setValue(1);
        });
        add(addItemButton, gbc);
		
        // Table to display all current records 
		table = new JTable();
		scrollPane = new JScrollPane(table);
		setTableModel(getItemTableModel());
		
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = gbc.weighty = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(scrollPane, gbc);
        
        
        
	}
	
	private void addItemRequested(TransItem item) {
		itemList.add(item);
		int insertedRowIndex = itemList.size() - 1;
		((AbstractTableModel) table.getModel()).fireTableRowsInserted(insertedRowIndex, insertedRowIndex);
		
	}

	public TableModel getItemTableModel(){
		return new AbstractTableModel() {
			private final String[] COLUMN_NAMES = { "Product", "Quantity" };

			@Override
			public String getColumnName(int column) {
				return COLUMN_NAMES[column];
			}

			@Override
			public int getRowCount() {
				return itemList.size();
			}

			@Override
			public int getColumnCount() {
				return COLUMN_NAMES.length;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				TransItem item = itemList.get(rowIndex);
				switch (columnIndex) {
				case 0:
					return item.getProductID();
				case 1:
					return item.getQuantity();
				default:
					return null;
				}
			}
		};
	}
	
    public void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);
//        Dimension d = table.getPreferredSize();
//        scrollPane.setPreferredSize(new Dimension(d.width,table.getRowHeight()*VISIBLE_ROW+1));
    }
	
    //TODO: Refactoring this class, suggest to use TransactionItem instead.
    class TransItem{
    	String productID;
    	int quantity;
    	
    	TransItem(String productID, int quantity){
    		this.productID = productID;
    		this.quantity = quantity;
    	}

		public String getProductID() {
			return productID;
		}

		public int getQuantity() {
			return quantity;
		}
    }
}
