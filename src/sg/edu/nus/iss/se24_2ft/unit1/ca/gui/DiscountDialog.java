package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.OkCancelDialog;

public abstract class DiscountDialog extends OkCancelDialog {
	
	private JTextField text1,text2,text3;
	//private static final JFrame frame = getFrameInstance();
	private static final long serialVersionUID = 1L;
	private CheckoutPanel checkoutPanel;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_1;
	private JTextField textField_5;
	private String[] discountDetails;

	public DiscountDialog(JFrame frame)
	{
        super (frame, "Apply Discount");
	}
	
	public JPanel createFormPanel()
	{	

		JPanel panel = new JPanel();
		discountDetails = getDiscountDetails();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("Member ID:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		System.out.println(discountDetails[0]);
		textField.setText(discountDetails[0]);
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 2;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);		
		
		JLabel lblNewLabel_3 = new JLabel("Maximum Discount:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 4;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		
		textField_3 = new JTextField();
		textField_3.setText(discountDetails[1]+"%");
		System.out.println(discountDetails[1]);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 5;
		gbc_textField_3.gridy = 4;
		panel.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		return panel;
		
	
	}
	
	  protected abstract boolean performOkAction();
	  
	  protected abstract boolean performCancel();
	  
	  protected abstract String[] getDiscountDetails();
}
