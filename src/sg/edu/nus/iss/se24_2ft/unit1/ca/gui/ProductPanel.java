package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

/**
 * Created by chenyao on 15/3/16
 */

public class ProductPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 20;
    private JTable table;
    private JScrollPane scrollPane;
    private List<ProductPanelListener> productPanelListenerList;
    private JTextField categoryTextField, nameTextField, desTextField, aqTextField,
            orderQuantityTextField, priceTextField, barcodeTextField, thresholdTextField;

    public ProductPanel() {
        super(new BorderLayout());

        table = new JTable();
        productPanelListenerList = new ArrayList<>();
        add(detailPanel(), BorderLayout.CENTER);
        add(newProductPanel(), BorderLayout.SOUTH);
    }

    public void addProductPanelListener(ProductPanelListener l) {
        productPanelListenerList.add(l);
    }

    public void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);
        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * VISIBLE_ROW + 1));
    }

    private JPanel detailPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = c.gridy = 0;
        panel.add(new JLabel("Product"), c);

        c.gridy++;
        c.gridwidth = 1;
        c.weightx = c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane, c);

        // Back Button
        c.gridx++;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::backActionPerformed);
        panel.add(backButton, c);

        return panel;
    }

    private JPanel newProductPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // input field for product categoryId
        c.gridy = c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 0;
        panel.add(new JLabel("Category ID"), c);

        c.gridx++;
        categoryTextField = new JTextField();
        panel.add(categoryTextField, c);

        // input field for product name
        c.gridx++;
        panel.add(new JLabel("Name"), c);

        c.gridx++;
        nameTextField = new JTextField();
        panel.add(nameTextField, c);

        // input field for product description
        c.gridx = 0;
        c.gridy++;
        panel.add(new JLabel("Description"), c);

        c.gridx++;
        desTextField = new JTextField();
        panel.add(desTextField, c);

        // input field for product available quantity
        c.gridx++;
        panel.add(new JLabel("Quantity"), c);

        c.gridx++;
        aqTextField = new JTextField();
        panel.add(aqTextField, c);

        // input field for product product price
        c.gridx = 0;
        c.gridy++;
        panel.add(new JLabel("Price"), c);

        c.gridx++;
        priceTextField = new JTextField();
        panel.add(priceTextField, c);

        // input field for product product bar code number
        c.gridx++;
        panel.add(new JLabel("Barcode"), c);

        c.gridx++;
        barcodeTextField = new JTextField();
        panel.add(barcodeTextField, c);

        // input field for product product threshold
        c.gridx = 0;
        c.gridy++;
        panel.add(new JLabel("Threshold"), c);

        c.gridx++;
        thresholdTextField = new JTextField();
        panel.add(thresholdTextField, c);

        // input field for product product orderQuantity
        c.gridx++;
        panel.add(new JLabel("Order Qty"), c);

        c.gridx++;
        orderQuantityTextField = new JTextField();
        panel.add(orderQuantityTextField, c);

        // Add Button
        c.gridx++;
        c.gridy = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            // TODO: change to Integer.parse, catch NFE, return message dialog
            String name = nameTextField.getText(), destination = desTextField.getText(),
                    categoryId = categoryTextField.getText();
            int quantity = Util.parseIntOrDefault(aqTextField.getText(), 0),
                    barCode = Util.parseIntOrDefault(barcodeTextField.getText(), 0),
                    threshold = Util.parseIntOrDefault(thresholdTextField.getText(), 0),
                    orderQuantity = Util.parseIntOrDefault(orderQuantityTextField.getText(), 0);
            double price = Util.parseDoubleOrDefault(priceTextField.getText(), 0);

            Product product = new Product(name, destination, quantity, price, barCode, threshold, orderQuantity);

            productPanelListenerList.forEach(l -> {
                try {
                    l.addProductRequested(categoryId, product);
                } catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            resetFields();
        });
        panel.add(addButton, c);

        // reset button
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetFields());
        panel.add(resetButton, c);

        return panel;
    }

    private void resetFields() {
        categoryTextField.setText(null);
        nameTextField.setText(null);
        desTextField.setText(null);
        aqTextField.setText(null);
        priceTextField.setText(null);
        barcodeTextField.setText(null);
        thresholdTextField.setText(null);
        orderQuantityTextField.setText(null);
    }
}