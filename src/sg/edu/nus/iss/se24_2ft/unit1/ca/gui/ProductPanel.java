package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

/**
 * Created by chenyao on 15/3/16
 */

public class ProductPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 20;
    private JTable table;
    private JScrollPane scrollPane;
    private List<ProductPanelListener> productPanelListenerList;

    public ProductPanel() {
        super(new GridBagLayout());

        table = new JTable();
        productPanelListenerList = new ArrayList<>();
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = c.gridy = 0;
        add(new JLabel("Product"), c);

        c.gridy++;
        c.gridheight = 18;
        c.weightx = c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        scrollPane = new JScrollPane(table);
        add(scrollPane, c);

        // input field for product category
        c.gridx++;
        c.gridheight = 1;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("Category ID"), c);

        c.gridy++;
        JTextField categoryTextField = new JTextField();
        add(categoryTextField, c);

        // input field for product name
        c.gridy++;
        add(new JLabel("Name"), c);

        c.gridy++;
        JTextField nameTextField = new JTextField();
        add(nameTextField, c);

        // input field for product description
        c.gridy++;
        add(new JLabel("Description"), c);

        c.gridy++;
        JTextField desTextField = new JTextField();
        add(desTextField, c);

        // input field for product available quantity
        c.gridy++;
        add(new JLabel("Quantity"), c);

        c.gridy++;
        JTextField aqTextField = new JTextField();
        add(aqTextField, c);

        // input field for product product price
        c.gridy++;
        add(new JLabel("Price"), c);

        c.gridy++;
        JTextField priceTextField = new JTextField();
        add(priceTextField, c);

        // input field for product product bar code number
        c.gridy++;
        add(new JLabel("Barcode"), c);

        c.gridy++;
        JTextField barcodeTextField = new JTextField();
        add(barcodeTextField, c);

        // input field for product product threshold
        c.gridy++;
        add(new JLabel("Threshold"), c);

        c.gridy++;
        JTextField thresholdTextField = new JTextField();
        add(thresholdTextField, c);

        // input field for product product orderQuantity
        c.gridy++;
        add(new JLabel("Order Qty"), c);

        c.gridy++;
        JTextField orderQuantityTextField = new JTextField();
        add(orderQuantityTextField, c);

        // Add Button
        c.gridy++;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            //TODO: change to Integer.parse, catch NFE, return message dialog
            String name = nameTextField.getText(), destination = desTextField.getText(),
                    categoryId = categoryTextField.getText();
            int quantity = Utils.parseIntOrDefault(aqTextField.getText(), 0),
                    barCode = Utils.parseIntOrDefault(barcodeTextField.getText(), 0),
                    threshold = Utils.parseIntOrDefault(thresholdTextField.getText(), 0),
                    orderQuantity = Utils.parseIntOrDefault(orderQuantityTextField.getText(), 0);
            double price = Utils.parseDoubleOrDefault(priceTextField.getText(), 0);

            Product product = new Product(name, destination, quantity, price,
                    barCode, threshold, orderQuantity);

            productPanelListenerList.forEach(l -> {
                try { l.addProductRequested(categoryId, product); }
                catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            categoryTextField.setText(null);
            nameTextField.setText(null);
            desTextField.setText(null);
            aqTextField.setText(null);
            priceTextField.setText(null);
            barcodeTextField.setText(null);
            thresholdTextField.setText(null);
            orderQuantityTextField.setText(null);
        });
        add(addButton, c);

        // Back Button
        c.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::backActionPerformed);
        add(backButton, c);
    }

    public void addProductPanelListener(ProductPanelListener l) {
        productPanelListenerList.add(l);
    }

    public void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);
        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * VISIBLE_ROW + 1));
    }
}