/**
 * The Member Panel is used when Storekeeper want to add new Member into the
 * system. The list of all current member in the system will also be displayed. 
 * @author: Tran Ngoc Hieu
 */
package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.Member;

public class MemberPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 5;
    private JTable table;
    private JTextField idTextField = new JTextField();
    private JTextField nameTextField = new JTextField();
    private ArrayList<Member> memberList = new ArrayList<Member>();

    public MemberPanel() {
        super(new GridBagLayout());

        // init data for testing purpose. TO BE REMOVED in final version
        initdata();

        table = new JTable(getMemberTableModel());
        Dimension d = table.getPreferredSize();

        // Initial setting
        GridBagConstraints gbc = new GridBagConstraints();

        // Table to display all current records
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 3;

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * VISIBLE_ROW + 1));
        add(scrollPane, gbc);

        // Input field for Member ID
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        gbc.gridy++;

        add(new JLabel("Member ID"), gbc);

        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.gridx++;
        add(idTextField, gbc);

        // Input field for Member Name
        gbc.gridx--;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        add(new JLabel("Name"), gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        add(nameTextField, gbc);

//		JPanel detailPanel = new JPanel();
//		gbc.gridx = 0;
//		gbc.gridy++;
//		gbc.weightx = 0;
//		gbc.gridwidth = 3;
//		gbc.gridheight = 2;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
//		add(detailPanel, gbc);
//		setDetailPanelLayout(detailPanel);

        // Panel for Button
        JPanel buttonPanel = new JPanel();
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);
        setButtonPanelLayout(buttonPanel);
    }

    private void setButtonPanelLayout(JPanel buttonPanel) {
        GridBagConstraints gbc = new GridBagConstraints();

        // Add Button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth++;
        gbc.fill = GridBagConstraints.NONE;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Member member = new Member(idTextField.getText(), nameTextField.getText());
            addActionPerformed(member);
            idTextField.setText(null);
            nameTextField.setText(null);
        });
        buttonPanel.add(addButton, gbc);

        // Back Button - Logic to be included later
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // backActionPerformed(e);
        });
        buttonPanel.add(backButton, gbc);

    }

//	private void setDetailPanelLayout(JPanel detailPanel) {
//		GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = gbc.gridy = 0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        
//		// Input field for Member ID
//        gbc.weightx = 0.5;
//        gbc.gridwidth = 1;
//		detailPanel.add(new JLabel("Member ID"), gbc);
//
//		gbc.gridx++;
//		gbc.weightx = 1;
//		gbc.gridwidth = 2;
//		detailPanel.add(idTextField, gbc);
//
//		// Input field for Member Name
//		gbc.gridx--;
//		gbc.gridy++;
//		gbc.weightx = 0.5;
//        gbc.gridwidth = 1;
//		detailPanel.add(new JLabel("Name"), gbc);
//
//		gbc.gridx++;
//		gbc.weightx = 1;
//		gbc.gridwidth = 2;
//		detailPanel.add(nameTextField, gbc);
//	}

    public TableModel getMemberTableModel() {
        return new AbstractTableModel() {
            private final String[] COLUMN_NAMES = { "Name", "Member ID", "Loyalty Point" };

            @Override
            public String getColumnName(int column) {
                return COLUMN_NAMES[column];
            }

            @Override
            public int getRowCount() {
                return memberList.size();
            }

            @Override
            public int getColumnCount() {
                return COLUMN_NAMES.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Member member = memberList.get(rowIndex);
                switch (columnIndex) {
                case 0:
                    return member.getName();
                case 1:
                    return member.getMemberID();
                case 2:
                    return member.getLoyaltyPoint();
                default:
                    return null;
                }
            }
        };
    }

    public void addActionPerformed(Member member) {
        memberList.add(member);
        int insertedRowIndex = memberList.size() - 1;
        ((AbstractTableModel) table.getModel()).fireTableRowsInserted(insertedRowIndex, insertedRowIndex);
    }

    // //Uncomment when combine with controller
    // public abstract void backActionPerformed(ActionEvent e);
    // public abstract void addActionPerformed(Member member);

    private void initdata() {
        memberList.add(new Member("F42563743156", "Yan Martel", 150));
        memberList.add(new Member("X437F356", "Suraj Sharma", 250));
        memberList.add(new Member("R64565FG4", "Ang Lee"));
    }
}