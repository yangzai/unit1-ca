package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

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

        // Create Tab Pane for Multiple Report
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //enables to use scrolling tabs.

        JPanel categoryReportPanel = new JPanel(new BorderLayout()); 
        categoryReportPanel.add(createCategoryReportPanel(), BorderLayout.CENTER);
        tabbedPane.add("Category Report", categoryReportPanel);

        JPanel productReportPanel = createProductReportPanel();
        tabbedPane.add("Product Report", productReportPanel);

        JPanel transactionReportPanel = createTransactionReportPanel();
        tabbedPane.add("Transaction Report", transactionReportPanel);

        JPanel memberReportPanel = createMemberReportPanel();
        tabbedPane.add("Member Report", memberReportPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        tabbedPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(tabbedPane, gbc);
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
        Date today = new Date();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // Selection field for Start Date/ End Date and button Get
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gbc.gridy = 0;
        panel.add(new JLabel("Start Date: "), gbc);

        gbc.gridy++;
        startDateField = new JFormattedTextField(Util.DATE_FORMAT);
        startDateField.setValue(today);
        panel.add(startDateField, gbc);

        gbc.gridx++;
        gbc.gridy--;
        panel.add(new JLabel("End Date: "), gbc);

        gbc.gridy++;
        endDateField = new JFormattedTextField(Util.DATE_FORMAT);
        endDateField.setValue(today);
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
    }
    
    public void setMemberTableModel(TableModel tableModel) {
        memberTable.setModel(tableModel);
    }
    
    public void setProductTableModel(TableModel tableModel) {
        productTable.setModel(tableModel);
    }
    
    public void setTransactionTableModel(TableModel tableModel) {
        transactionTable.setModel(tableModel);
        transactionSorter = new TableRowSorter<>(tableModel);
        transactionTable.setRowSorter(transactionSorter);

        int dateColumnIndex = tableModel.getColumnCount() - 1;
        //TODO: Consider replacing with localdate
        TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                if( value instanceof Date)
                    value = Util.formatDateOrDefault((Date) value, null);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        transactionTable.getColumnModel().getColumn(dateColumnIndex).setCellRenderer(tableCellRenderer);

        int rowCount = tableModel.getRowCount();
        if (rowCount == 0) return;

        startDateField.setValue(tableModel.getValueAt(0, dateColumnIndex));
        endDateField.setValue(tableModel.getValueAt(tableModel.getRowCount() - 1, dateColumnIndex));
    }
}