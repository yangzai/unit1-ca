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
	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;
	private JTable table;
    private JScrollPane scrollPane;
    private JButton addItemButton, setMemberButton, proceedPaymentButton, backButton;
    private JTextField productField;
    private JLabel memberField, subTotalField;
    private JFormattedTextField quantityField;
    private Double subTotal, total;
    private ArrayList<TransItem> itemList = new ArrayList<TransItem>();

	public CheckoutPanel() {	
        // Initial setting
//		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = gbc.weighty = 0;
        gbc.gridwidth = 1;
        
        //Item Panel: To key in Item details and Add into table
        JPanel itemPanel = new JPanel(new GridBagLayout());
        gbc.gridx = gbc.gridy = 0;
        add(itemPanel, gbc);
        setItemPanel(itemPanel);
        		
        // Table to display all current records 
		table = new JTable();
		scrollPane = new JScrollPane(table);
		setTableModel(getItemTableModel());
		
		gbc.gridy++;
        gbc.weightx = gbc.weighty = 0;
        add(scrollPane, gbc);
        
        //Additional Info: Membership, Subtotal
        JPanel infoPanel = new JPanel(new GridBagLayout());
        gbc.gridy++;
        add(infoPanel, gbc);
        setInfoPanel(infoPanel);
        
        
        //Menu Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridy++;
        gbc.gridheight = 2;
        add(buttonPanel, gbc);
        setButtonPanel(buttonPanel);

	}
	
	private void setItemPanel(JPanel itemPanel) {
		//Setting
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		//Add Product ID input field	
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		itemPanel.add(new JLabel("Enter Product ID:"), gbc);
		
		gbc.gridy++;
		productField = new JTextField();
		itemPanel.add(productField, gbc);
		
		//Add Quantity input field
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		itemPanel.add(new JLabel("Quantity:"), gbc);
		
		gbc.gridy++;
		quantityField = new JFormattedTextField(NumberFormat.getNumberInstance());
		quantityField.setValue(1);
		itemPanel.add(quantityField, gbc);
		
		//Add Add Item button
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
        addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
        	String productId = productField.getText();
        	if ((productId == null) || (productId.isEmpty())) { System.out.println("Product ID is empty");return; };
        	int quantity = ((Number) quantityField.getValue()).intValue();
            TransItem item = new TransItem(productId, quantity);
            addItemRequested(item);
            productField.setText(null);
            quantityField.setValue(1);
        });
        itemPanel.add(addItemButton, gbc);
		
	}
	
	private void setButtonPanel(JPanel buttonPanel) {
		//Setting
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		
		//Membership Details
		gbc.gridy = 0;
		gbc.gridx = 2;
		gbc.weightx = 0.5;
		setMemberButton = new JButton("Membership");
        setMemberButton.addActionListener(e -> {
        	//TODO: Replace the idList with list of existing member and one option for PUBLIC
        	//TODO: Refactor this logic into Action Listener
        	String[] idList = { "PUBLIC", "ID1", "ID2", "ID3", "ID4", "ID5", "ID6", "ID7", "ID8", "ID9", "ID10", "ID11", "ID12" };
        	String id = (String)JOptionPane.showInputDialog(
        	                    this,
        	                    "Membership ID",
        	                    "Customer Detail",
        	                    JOptionPane.PLAIN_MESSAGE,
        	                    null,
        	                    idList,
        	                    idList[0]);

        	//TODO: Get member object based on id
        	if (id == idList[0]) {
				//Set customer as Public customer 
			} else {
				//TODO: Get Member details
			}
        	memberField.setText(id);
        });
		buttonPanel.add(setMemberButton, gbc);
		
		//Proceed to Payment
		gbc.gridx++;
		gbc.weightx = 0.5;
		proceedPaymentButton = new JButton("Proceed to Payment");
//		proceedPaymentButton.addActionListener(e -> {
//        	
//        });
		buttonPanel.add(proceedPaymentButton, gbc);
		
		//Back button
		gbc.gridx++;
		gbc.weightx = 0.5;
		backButton = new JButton("Back");
		backButton.addActionListener(e -> backActionPerformed(e));
		buttonPanel.add(backButton, gbc);
		
	}
	
	private void setInfoPanel(JPanel infoPanel){
		//Setting
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		
		
		//Membership Details
		gbc.gridx = gbc.gridy = 0;	
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		infoPanel.add(new JLabel("Membership: "), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		memberField = new JLabel("PUBLIC");
		infoPanel.add(memberField, gbc);
		
		//Subtotal Details
		gbc.gridx++;
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.ipadx = table.getWidth() / 3;
		infoPanel.add(new JLabel("Subtotal: "), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		subTotal = 0d;
		subTotalField = new JLabel(subTotal.toString());
		infoPanel.add(subTotalField, gbc);
	}

	private void addItemRequested(TransItem item) {
		itemList.add(item);
		int insertedRowIndex = itemList.size() - 1;
		((AbstractTableModel) table.getModel()).fireTableRowsInserted(insertedRowIndex, insertedRowIndex);
		
		//TODO: Recalculate the Subtotal here and remove the Sample code
		subTotal += item.quantity * 1.3; 
		subTotalField.setText(subTotal.toString());
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
        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width,table.getRowHeight()*VISIBLE_ROW+1));
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
