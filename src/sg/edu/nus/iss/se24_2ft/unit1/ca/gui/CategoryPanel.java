package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by yangzai on 2/3/16.
 */
public class CategoryPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 5;
    private JTable table;
    private JScrollPane scrollPane;
    private List<CategoryPanelListener> categoryPanelListenerList;

    public CategoryPanel() {
        super(new GridBagLayout());

        table = new JTable();
        scrollPane = new JScrollPane(table);
        categoryPanelListenerList = new ArrayList<>();
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = c.gridy = 0;
        c.weightx = c.weighty = 0.5;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        add(scrollPane, c);

        c.gridy++;
        c.gridwidth = 1;
        c.weighty = 0;
        c.weightx = 0.2;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("Category ID"), c);

        c.gridx++;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField idTextField = new JTextField();
        add(idTextField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.weightx = 0.2;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("Name"), c);

        c.gridx++;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField nameTextField = new JTextField();
        add(nameTextField, c);

        c.gridy--;
        c.gridx++;
        c.gridwidth = 1;
        c.weightx = 0;
        JButton addCategoryButton = new JButton("Add");
        addCategoryButton.addActionListener(e -> {
            Category category = new Category(idTextField.getText(), nameTextField.getText());
            //TODO: validate category
            categoryPanelListenerList.forEach(l -> {
                try { l.addCategoryRequested(category); }
                catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            idTextField.setText(null);
            nameTextField.setText(null);
        });
        add(addCategoryButton, c);

//        c.gridx++;
//        JButton backButton = new JButton("Back");
//        backButton.addActionListener(this::backActionPerformed);
//        add(backButton, c);
        
        c.gridy++;
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
        	idTextField.setText(null);
        	nameTextField.setText(null);
        });
        add(resetBtn, c);
    }

    public void addCategoryPanelListener(CategoryPanelListener l) {
        categoryPanelListenerList.add(l);
    }

    public void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);
        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width,table.getRowHeight()*VISIBLE_ROW+1));
    }
}