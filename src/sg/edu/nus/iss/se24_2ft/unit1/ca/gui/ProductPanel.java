package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;

/**
 * Created by chenyao on 15/3/16
 */

public class ProductPanel extends FeaturePanel {
    // private static final int VISIBLE_ROW = 5;
    private JTable table;
    private ArrayList<Product> productList;

    public ProductPanel() {
        super(new GridBagLayout());

        // init date for testing
        // initdata();

        table = new JTable();
        productList = new ArrayList<Product>();

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = c.gridy = 0;
        add(new JLabel("Product"), c);

        c.gridy++;
        c.gridheight = 20;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, c);

        // input field for product category
        c.gridx++;
        c.gridheight = 1;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("category"), c);

        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField categoryTextField = new JTextField();
        add(categoryTextField, c);

        // input field for product name
        c.gridy++;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("name"), c);

        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField nameTextField = new JTextField();
        add(nameTextField, c);

        // input field for product description
        // c.gridx--;
        c.gridy++;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("description"), c);

        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField desTextField = new JTextField();
        add(desTextField, c);

        // input field for product available quantity
        // c.gridx--;
        c.gridy++;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("availableQuantity"), c);

        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField aqTextField = new JTextField();
        add(aqTextField, c);

        // input field for product product price
        // c.gridx--;
        c.gridy++;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("price"), c);

        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField priceTextField = new JTextField();
        add(priceTextField, c);

        // input field for product product bar code number
        // c.gridx--;
        c.gridy++;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("barcode"), c);

        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField barcodeTextField = new JTextField();
        add(barcodeTextField, c);

        // input field for product product threshold
        // c.gridx--;
        c.gridy++;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("threshold"), c);

        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField thresholdTextField = new JTextField();
        add(thresholdTextField, c);

        // input field for product product orderQuantity
        // c.gridx--;
        c.gridy++;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        add(new JLabel("orderQuantity"), c);

        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField orderQuantityTextField = new JTextField();
        add(orderQuantityTextField, c);

        // Add Button
        // c.gridx--;
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.EAST;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Product product = new Product(nameTextField.getText(), desTextField.getText(),
                    Integer.parseInt(aqTextField.getText()), Double.parseDouble(priceTextField.getText()),
                    Integer.parseInt(barcodeTextField.getText()), Integer.parseInt(thresholdTextField.getText()),
                    Integer.parseInt(orderQuantityTextField.getText()));
            addActionPerformed(product);
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
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.EAST;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // backActionPerformed(e);
        });
        add(backButton, c);
    }

    public TableModel getProductTableModel() {
        return new AbstractTableModel() {
            private final String[] COLUMN_NAMES = { "Product Id", "Name", "Description", "Available Quantity", "Price",
                    "Bar code number", "Threshold", "Order Quantity" };

            public String getColumnName(int column) {
                return COLUMN_NAMES[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Product product = productList.get(rowIndex);
                switch (columnIndex) {
                case 0:
                    return product.getName();
                case 1:
                    return product.getDescription();
                case 2:
                    return product.getQuantity();
                case 3:
                    return product.getPrice();
                case 4:
                    return product.getBarCode();
                case 5:
                    return product.getThreshold();
                case 6:
                    return product.getOrderQuantity();
                default:
                    return null;
                }
            }

            @Override
            public int getRowCount() {
                return productList.size();
            }

            @Override
            public int getColumnCount() {
                return COLUMN_NAMES.length;
            }
        };
    }

    private void addActionPerformed(Product product) {
        productList.add(product);
        int insertedRowIndex = productList.size() - 1;
        ((AbstractTableModel) table.getModel()).fireTableRowsInserted(insertedRowIndex, insertedRowIndex);
    }

    // private void initdata() {
    // productList.add(new Product("NUS Pen", "A really cute blue pen", 768,
    // 5.75, 123, 50, 250));
    // }

}
