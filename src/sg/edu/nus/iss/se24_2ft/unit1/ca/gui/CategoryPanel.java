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

        c.weightx = c.weighty = 1;
        c.gridx = c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        add(scrollPane, c);

        c.gridy++;
        c.gridwidth--;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("ID"), c);

        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField idTextField = new JTextField();
        add(idTextField, c);

        c.gridx--;
        c.gridy++;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("Name"), c);

        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField nameTextField = new JTextField();
        add(nameTextField, c);

        c.gridx--;
        c.gridy++;
        c.gridwidth++;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        JButton addCategoryButton = new JButton("Add");
        addCategoryButton.addActionListener(e -> {
            Category category = new Category(idTextField.getText(), nameTextField.getText());
            //TODO: validate category
            categoryPanelListenerList.forEach(l -> l.addCategoryRequested(category));
            idTextField.setText(null);
            nameTextField.setText(null);
        });
        add(addCategoryButton, c);

        c.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> backActionPerformed(e));
        add(backButton, c);
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