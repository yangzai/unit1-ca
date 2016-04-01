/**
 * The Member Panel is used when Storekeeper want to add new Member into the
 * system. The list of all current member in the system will also be displayed. 
 * @author: Tran Ngoc Hieu
 */
package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;

public class MemberPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 5;
    private JTable table;
    JScrollPane scrollPane;
    private List<MemberPanelListener> memberPanelListenerList;

    public MemberPanel() {
        super(new GridBagLayout());

        memberPanelListenerList = new ArrayList<>();
        table = new JTable();
        scrollPane = new JScrollPane(table);

        // Initial setting
        GridBagConstraints gbc = new GridBagConstraints();

        // Table to display all current records
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Input field for Member ID
        gbc.gridy++;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Member ID"), gbc);

        gbc.weightx = 1;
        gbc.gridx++;
        JTextField idTextField = new JTextField();
        add(idTextField, gbc);

        // Input field for Member Name
        gbc.gridx--;
        gbc.gridy++;
        gbc.weightx = 0.5;
        add(new JLabel("Name"), gbc);

        gbc.gridx++;
        gbc.weightx = 1;
        JTextField nameTextField = new JTextField();
        add(nameTextField, gbc);

//        gbc.gridx--;
        gbc.gridy++; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Member member = new Member(idTextField.getText(), nameTextField.getText());
            memberPanelListenerList.forEach(l -> {
                try { l.addMemberRequested(member); }
                catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            idTextField.setText(null);
            nameTextField.setText(null);
        });
        add(addButton, gbc);

//        gbc.gridx++;
//        gbc.anchor = GridBagConstraints.WEST;
//        JButton backButton = new JButton("Back");
//        backButton.addActionListener(this::backActionPerformed);
//        add(backButton, gbc);
    }

    public void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);
        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width,table.getRowHeight()*VISIBLE_ROW+1));
    }

    public void addMemberPanelistener(MemberPanelListener l) {
        memberPanelListenerList.add(l);
    }
}