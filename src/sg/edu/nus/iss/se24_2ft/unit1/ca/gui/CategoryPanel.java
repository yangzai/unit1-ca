package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import sg.edu.nus.iss.se24_2ft.unit1.ca.Category;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by yangzai on 2/3/16.
 */
public abstract class CategoryPanel extends JPanel {
    private static final int VISIBLE_ROW = 5;
    private JTable table;

    public CategoryPanel() {
        super(new GridBagLayout());

        table = new JTable(getTableModel());
        Dimension d = table.getPreferredSize();

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(d.width,table.getRowHeight()*VISIBLE_ROW+1));
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



//        c.anchor = GridBagConstraints.EAST;

        c.gridx--;
        c.gridy++;
        c.gridwidth++;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Category category = new Category(idTextField.getText(), nameTextField.getText());
            //TODO: validate category
            addActionPerformed(category);
            idTextField.setText(null);
            nameTextField.setText(null);
        });
        add(addButton, c);

        c.gridy++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
//            backButtonActionListenerList.forEach(l -> l.actionPerformed(e));
            backActionPerformed();
        });
        add(backButton, c);
    }

    public abstract TableModel getTableModel();

    public abstract void addActionPerformed(Category category);

    public abstract void backActionPerformed(/*ActionEvent e*/);
}