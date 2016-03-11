/**
 * @author: Tran Ngoc Hieu
 */
package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.Member;

public class MemberPanel extends JPanel {
    private static final int VISIBLE_ROW = 5;
    private JTable table;
    private ArrayList<Member> memberList = new ArrayList<Member>();
    
	public MemberPanel() {
        super(new GridBagLayout());
        
        //init data for testing purpose. TO BE REMOVED in final version
        initdata();

        table = new JTable(getMemberTableModel());
        Dimension d = table.getPreferredSize();
        
        //Initial setting
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;

        //Table to display all current records
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(d.width,table.getRowHeight()*VISIBLE_ROW+1));
        add(scrollPane, c);
        
        //Input field for Member ID
        c.gridy++;
        c.gridwidth--;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("Member ID"), c);

        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField idTextField = new JTextField();
        add(idTextField, c);
        
        //Input field for Member Name
        c.gridx--;
        c.gridy++;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("Name"), c);

        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField nameTextField = new JTextField();
        add(nameTextField, c);
        
        //Add Button
        c.gridx--;
        c.gridy++;
        c.gridwidth++;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Member member = new Member(idTextField.getText(), nameTextField.getText());
            addActionPerformed(member);
            idTextField.setText(null);
            nameTextField.setText(null);
        });
        add(addButton, c);

        //Back Button - Logic to be included later
		c.gridy++;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
//            backActionPerformed(e);
        });
        add(backButton, c);
	}
	
    public TableModel getMemberTableModel(){
        return new AbstractTableModel() {
        	private final String[] COLUMN_NAMES = {"Name", "Member ID", "Loyalty Point"};
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
                    case 0: return member.getName();
                    case 1: return member.getMemberID();
                    case 2: return member.getLoyaltyPoint();
                    default: return null;
                }
            }
        };
    };

    public void addActionPerformed(Member member){
    	memberList.add(member);
    	int insertedRowIndex = memberList.size() - 1;
    	((AbstractTableModel)table.getModel()).fireTableRowsInserted(insertedRowIndex, insertedRowIndex);
    };
    
//	  //Uncomment when combine with controller
//    public abstract void backActionPerformed(ActionEvent e);
//	  public abstract void addActionPerformed(Member member);
    
    private void initdata(){
    	memberList.add(new Member("F42563743156", "Yan Martel", 150));
    	memberList.add(new Member("X437F356", "Suraj Sharma", 250));
    	memberList.add(new Member("R64565FG4", "Ang Lee"));
    }

}
