package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

public class ReportPanel extends FeaturePanel {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	private static final int VISIBLE_ROW = 5;
    private JTable categoryTable, productTable, transactionTable, memberTable;
	private JFormattedTextField startDateField, endDateField;
	private TableRowSorter<TableModel> transactionSorter;
    
	public ReportPanel() {
		transactionSorter = null;

		// Initial setting
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = gbc.weighty = 0;
		gbc.gridwidth = 2;
		
		// Create Tab Pane for Multiple Report
		JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //enables to use scrolling tabs.
		
		JPanel categoryReportPanel = createCategoryReportPanel();
		tabbedPane.add("Category Report", categoryReportPanel);
		
		JPanel productReportPanel = createProductReportPanel();
		tabbedPane.add("Product Report", productReportPanel);
		
		JPanel transactionReportPanel = createTransactionReportPanel();
		tabbedPane.add("Transaction Report", transactionReportPanel);
		
		JPanel memberReportPanel = createMemberReportPanel();
		tabbedPane.add("Member Report", memberReportPanel);
		
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		tabbedPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(tabbedPane, gbc);
		
		//Add button
		gbc.gridy++;
		gbc.weighty = 0;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this::backActionPerformed);
		add(backButton, gbc);
	}

	private JPanel createCategoryReportPanel() {
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
		startDateField = new JFormattedTextField(Utils.DATE_FORMAT);
		panel.add(startDateField, gbc);
		
		gbc.gridx++;
		gbc.gridy--;
		panel.add(new JLabel("End Date: "), gbc);
		
		gbc.gridy++;
		endDateField = new JFormattedTextField(Utils.DATE_FORMAT);
		panel.add(endDateField, gbc);
		
		gbc.gridy--;
		gbc.gridx++;
		gbc.weightx = 0.1;
		gbc.gridheight = 2;
		JButton getButton = new JButton("Get Report");
		getButton.addActionListener(l -> {
			Date startDate = (Date) startDateField.getValue();
			Date endDate = (Date) endDateField.getValue();
			if (startDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "End Date is before Start Date, please correct");
			} else if (transactionSorter != null) {
				List<RowFilter<Object, Object>> lowerBoundFilterList = new ArrayList<>(2);
				lowerBoundFilterList.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, startDate));
				lowerBoundFilterList.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, startDate));

				List<RowFilter<Object, Object>> upperBoundFilterList = new ArrayList<>(2);
				upperBoundFilterList.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, endDate));
				upperBoundFilterList.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, endDate));

				List<RowFilter<Object, Object>> filterList = new ArrayList<>(2);
				filterList.add(RowFilter.orFilter(lowerBoundFilterList));
				filterList.add(RowFilter.orFilter(upperBoundFilterList));
				transactionSorter.setRowFilter(RowFilter.andFilter(filterList));
			}
		});
		panel.add(getButton, gbc);	
		
		// Table to display data
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.weightx = gbc.weighty = 1;
		transactionTable = new JTable();
		JScrollPane scrollPane= new JScrollPane(transactionTable);
		scrollPane.setPreferredSize(new Dimension(WIDTH-5, transactionTable.getRowHeight() * VISIBLE_ROW));
		panel.add(scrollPane, gbc);
		
		return panel;
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
		TableColumnModel columnModel = categoryTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth((int) (WIDTH*0.2));
		columnModel.getColumn(1).setPreferredWidth((int) (WIDTH*0.8));
    }
    
    public void setMemberTableModel(TableModel tableModel) {
    	memberTable.setModel(tableModel);
    }
    
    public void setProductTableModel(TableModel tableModel) {
    	productTable.setModel(tableModel);
    }
    
    public void setTransactionTableModel(TableModel tableModel) {
		int dateColumnIndex = tableModel.getColumnCount() - 1;
		startDateField.setValue(tableModel.getValueAt(0, dateColumnIndex));
		endDateField.setValue(tableModel.getValueAt(tableModel.getRowCount() - 1, dateColumnIndex));
		transactionSorter = new TableRowSorter<>(tableModel);
		transactionTable.setModel(tableModel);
		transactionTable.setRowSorter(transactionSorter);
		//TODO: Consider replacing with localdate
		TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
														   boolean hasFocus, int row, int column) {
				if( value instanceof Date)
					value = Utils.formatDateOrDefault((Date) value, null);
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		transactionTable.getColumnModel().getColumn(dateColumnIndex).setCellRenderer(tableCellRenderer);
	}
}
