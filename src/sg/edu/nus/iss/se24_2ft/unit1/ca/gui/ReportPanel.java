package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ReportPanel extends FeaturePanel {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	private static final int VISIBLE_ROW = 5;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private JTable categoryTable, productTable, transactionTable, memberTable;
    private Date startDate, endDate;
    
	public ReportPanel() {
		
		// Initial setting
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = gbc.weighty = 0;
		gbc.gridwidth = 2;
		
		// Create Tab Pane for Multiple Report
		JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //enables to use scrolling tabs.
		
		JPanel categoryReportPanel = createCategorReportyPanel();
		tabbedPane.add("Category Report", categoryReportPanel);
		
		JPanel productReportPanel = createProductReportPanel();
		tabbedPane.add("Product Report", productReportPanel);
		
		JPanel transactionReportPanel = createTransactionReportPanel();
		tabbedPane.add("Transaction Report", transactionReportPanel);
		
		JPanel memberReportPanel = createMemberReportPanel();
		tabbedPane.add("Member Report", memberReportPanel);
		
		gbc.gridx = gbc.gridy = 0;
		tabbedPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(tabbedPane, gbc);
		
		//Add button
		gbc.gridy++;
		gbc.gridx++;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> backActionPerformed(e));
		add(backButton, gbc);
	}

	private JPanel createCategorReportyPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		categoryTable = new JTable();
		panel.add(new JScrollPane(categoryTable));
		
		return panel;
	}
	
	private JPanel createProductReportPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		productTable = new JTable();
		panel.add(new JScrollPane(productTable));
		
		return panel;
	}
	
	private JPanel createTransactionReportPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// Selection field for Start Date/ End Date and button Get
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = gbc.gridy = 0;
		panel.add(new JLabel("Start Date: "), gbc);
		
		gbc.gridy++;
		startDate = new Date();
		JFormattedTextField startDateField = new JFormattedTextField(DATE_FORMAT);
		startDateField.setValue(startDate);
		panel.add(startDateField, gbc);
		
		gbc.gridx++;
		gbc.gridy--;
		panel.add(new JLabel("End Date: "), gbc);
		
		gbc.gridy++;
		endDate = new Date();
		JFormattedTextField endDateField = new JFormattedTextField(DATE_FORMAT);
		endDateField.setValue(endDate);
		panel.add(endDateField, gbc);
		
		gbc.gridy--;
		gbc.gridx++;
		gbc.weightx = 0.1;
		gbc.gridheight = 2;
		JButton getButton = new JButton("Get Report");
		getButton.addActionListener(l -> {
			startDate = (Date) startDateField.getValue();
			endDate = (Date) endDateField.getValue();
			if (startDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "End Date is before Start Date, please correct");
			} else {
				getTransactionBasedOnDate(startDate, endDate);
			}
		});
		panel.add(getButton, gbc);	
		
		// Table to display data
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.weightx = 0;
		transactionTable = new JTable();
		JScrollPane scrollPane= new JScrollPane(transactionTable);
		scrollPane.setPreferredSize(new Dimension(WIDTH-5, transactionTable.getRowHeight() * VISIBLE_ROW));
		panel.add(scrollPane, gbc);
		
		return panel;
	}
	
	private void getTransactionBasedOnDate(Date start, Date end) {
		// TODO Fire Event to get Transaction data based on the startDate and endDate
		System.out.println("Start Date: " + start.toString() + ", End Date: " + end.toString());
	}

	private JPanel createMemberReportPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		memberTable = new JTable();
		panel.add(new JScrollPane(memberTable));

		return panel;
	}

    public void setCategoryTableModel(TableModel tableModel) {
        categoryTable.setModel(tableModel);
        categoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        categoryTable.setPreferredSize(new Dimension(WIDTH, categoryTable.getRowHeight() * VISIBLE_ROW ));
        categoryTable.getColumnModel().getColumn(0).setPreferredWidth((int) (WIDTH*0.2));
        categoryTable.getColumnModel().getColumn(1).setPreferredWidth((int) (WIDTH*0.8));
    }
    
    public void setMemberTableModel(TableModel tableModel) {
    	memberTable.setModel(tableModel);
    }
    
    public void setProductTableModel(TableModel tableModel) {
    	productTable.setModel(tableModel);
    }
    
    public void setTransactionTableModel(TableModel tableModel) {
    	transactionTable.setModel(tableModel);
    }
    
    
}
