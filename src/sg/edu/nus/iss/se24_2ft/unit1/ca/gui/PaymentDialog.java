package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
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


public abstract class PaymentDialog extends OkCancelDialog {

		private JTextField text1,text2,text3;
		private static final long serialVersionUID = 1L;
		private CheckoutPanel checkoutPanel;
		private JTextField textField;
		private JTextField textField_2;
		private JTextField textField_3;
		private JTextField textField_4;
		private JTextField textField_1;

		public PaymentDialog(JFrame frame)
		{
	        super (frame, "Receive Payment");
	        this.checkoutPanel = checkoutPanel;
		}
		
		public JPanel createFormPanel()
		{	

			JPanel panel = new JPanel();
			//getContentPane().add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			panel.setLayout(gbl_panel);
			
			JLabel lblNewLabel = new JLabel("Total amount due:");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 2;
			panel.add(lblNewLabel, gbc_lblNewLabel);
			
			textField = new JTextField();
			textField.setText("$" +Double.toString(getSubTotal()));
			textField.setEditable(false);
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 0);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 5;
			gbc_textField.gridy = 2;
			panel.add(textField, gbc_textField);
			textField.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("Payment method:");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 3;
			panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
			
			
			JLabel lblNewLabel_3 = new JLabel("Redeem Points:");
			GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
			gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_3.gridx = 1;
			gbc_lblNewLabel_3.gridy = 4;
			panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
			
			
			textField_3 = new JTextField();
			GridBagConstraints gbc_textField_3 = new GridBagConstraints();
			gbc_textField_3.insets = new Insets(0, 0, 5, 0);
			gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_3.gridx = 5;
			gbc_textField_3.gridy = 4;
			panel.add(textField_3, gbc_textField_3);
			textField_3.setColumns(10);
			
			JComboBox comboBox = new JComboBox();
			comboBox.addActionListener(e -> {
				if(comboBox.getSelectedItem().equals("Cash")){
					textField_3.setEditable(false);
					lblNewLabel_3.setEnabled(false);
				}
				else{
					textField_3.setEditable(true);
					lblNewLabel_3.setEnabled(true);
				}
			});
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			
			comboBox.addItem("Cash");
			comboBox.addItem("Reedem Loyalty Points");
			
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 5;
			gbc_comboBox.gridy = 3;
			panel.add(comboBox, gbc_comboBox);
			
			JLabel lblNewLabel_4 = new JLabel("Eligible Discount:");
			lblNewLabel_4.setEnabled(false);
			GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
			gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_4.gridx = 1;
			gbc_lblNewLabel_4.gridy = 5;
			panel.add(lblNewLabel_4, gbc_lblNewLabel_4);
			
			textField_4 = new JTextField();
			textField_4.setText(Double.toString(getDiscountAmount()) +"%");
			textField_4.setEditable(false);
			GridBagConstraints gbc_textField_4 = new GridBagConstraints();
			gbc_textField_4.insets = new Insets(0, 0, 5, 0);
			gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_4.gridx = 5;
			gbc_textField_4.gridy = 5;
			panel.add(textField_4, gbc_textField_4);
			textField_4.setColumns(10);
			
			JLabel lblNewLabel_5 = new JLabel("Amount Tendered:");
			GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
			gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_5.gridx = 1;
			gbc_lblNewLabel_5.gridy = 6;
			panel.add(lblNewLabel_5, gbc_lblNewLabel_5);
			
			textField_1 = new JTextField();
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 5, 0);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 5;
			gbc_textField_1.gridy = 6;
			panel.add(textField_1, gbc_textField_1);
			textField_1.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Change:");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.anchor = GridBagConstraints.BELOW_BASELINE;
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = 1;
			gbc_lblNewLabel_2.gridy = 7;
			panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
			
			textField_2 = new JTextField();
			GridBagConstraints gbc_textField_2 = new GridBagConstraints();
			gbc_textField_2.insets = new Insets(0, 0, 5, 0);
			gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2.gridx = 5;
			gbc_textField_2.gridy = 7;
			panel.add(textField_2, gbc_textField_2);
			textField_2.setColumns(10);
			return panel;
		}
		
		  protected boolean performOkAction()
		    {
		    	return true;
		    }
		  
		  protected boolean performCancel()
		  {
			  return true;
		  }
		  
		  public abstract double getSubTotal();
		  
		  public abstract double getDiscountAmount();
	}




