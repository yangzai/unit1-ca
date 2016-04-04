package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;

// ** Created by Srishti ** // 

public abstract class InventoryPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 20;
    JTable table;
    JScrollPane scrollPane;
    private List<InventoryPanelListener> inventoryPanelListenerList;

    public InventoryPanel() {
        super(new GridBagLayout());

        table = new JTable() {
            @Override
            public TableCellEditor getCellEditor(int row, int col) {
                // TODO Auto-generated method stub
                return getTableCellEditor(row, col);
            }
            
        };
        inventoryPanelListenerList = new ArrayList<>();
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = gc.gridy = 0;
        add(new JLabel("Inventory Below Threshold"), gc);

        gc.gridy++;
        gc.gridheight++;
        gc.weightx = gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        scrollPane = new JScrollPane(table);
        add(scrollPane, gc);

        gc.gridx++;
        gc.gridheight--;
        gc.weightx = gc.weighty = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        JButton purchaseOrderButton = new JButton("Generate Purchase Order");
        purchaseOrderButton.addActionListener(e -> {
            //TODO: vendor selection?
            List<Integer> selectedRowList = Arrays.stream(table.getSelectedRows())
                    .boxed().collect(Collectors.toList());
            if (selectedRowList.isEmpty()) {
                new JOptionPane().showMessageDialog(this, "Please elect at least one product understocked");
                return;
            } 

            JFrame frame = new JFrame();
            int response = JOptionPane.showConfirmDialog(
                    frame,
                    "Sure to place an order for selected items?",
                    "An Inane Question",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION)
                inventoryPanelListenerList.forEach(l -> l.purchaseOrderRequested(selectedRowList));
            else frame.dispose();
        });
        add(purchaseOrderButton,gc);

//        gc.gridy++;
//        JButton backButton = new JButton("Back");
//        backButton.addActionListener(this::backActionPerformed);
//        add(backButton,gc);
    }

    public void addInventoryPanelListener(InventoryPanelListener l ) {
        inventoryPanelListenerList.add(l);
    }

    public void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);
        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width,table.getRowHeight()*VISIBLE_ROW+1));
    }
    
    abstract protected TableCellEditor getTableCellEditor(int row, int col);
}