package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

/**
 * @author Srishti Miglani
 *  
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class LoginPanel extends JFrame {

	private JTextField username,password;
	private JButton login;
	private static final String USER_NAME = "Username";
	private static final String Password_ = "Password";
	private LoginPanelListener l;
	private MouseAdapter adapter;

	public LoginPanel() {

		adapter = new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				textFieldMousePressed(evt);
			}
			public void mouseExited(MouseEvent e)
			{
				textfieldFocusLost(e);
			}
		};
		// TODO Auto-generated constructor stub
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = gbc.weighty = 0;
		gbc.gridx = gbc.gridy = 0;

		//*****Adding Main heading **//
		JLabel lblUniversitySouvenir = new JLabel("Welcome to the University Souviner");
		lblUniversitySouvenir.setForeground(Color.BLACK);
		lblUniversitySouvenir.setHorizontalAlignment(SwingConstants.CENTER);
		lblUniversitySouvenir.setFont(new Font("Tahoma", Font.PLAIN, 31));
		add(lblUniversitySouvenir, gbc);

		// *** Adding username- password panel ***////
		gbc.gridy++;
		add(getMainPanel(), gbc);

		// *** Adding login button panel ***////
		gbc.gridy++;
		gbc.gridheight = 2;
		add(getButtonPanel(), gbc);

		setVisible(true);
		setSize(600,400);
	}   


	public JPanel getMainPanel(){ 
		JPanel panel_4 = new JPanel();

		// *** Adding border to the username-password panel *** /
		panel_4.setBackground(new Color(192, 192, 192));

		TitledBorder titled = BorderFactory.createTitledBorder(
				new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)), "Login to Continue");
		titled.setTitleFont(new Font("SansSerif", Font.BOLD, 17));
		panel_4.setBorder(titled);
		panel_4.setPreferredSize(new Dimension(200,200));
		panel_4.setBackground(new Color(192, 192, 192));

		GridBagLayout gbl_panel_4 = new GridBagLayout();
		panel_4.setLayout(gbl_panel_4);
		panel_4.setPreferredSize(new Dimension(200,200));
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(3,3,3,3);

		// *** Adding border to the username-password labels and textfields *** /
		JLabel username_ = new JLabel(USER_NAME +":");
		gc.gridx= 0;
		gc.gridy =1 ;
		gc.anchor = GridBagConstraints.CENTER;
		panel_4.add(username_, gc);


		gc.gridx= gc.gridy =1;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(3,3,3,3);
		username = new JTextField(USER_NAME);
		username.addMouseListener(adapter);
		username.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
				{
					username.removeMouseListener(adapter);
					username.setForeground(Color.black);
				}
			}
		});
		username.setPreferredSize(new Dimension(200,30));
		username.setForeground(Color.LIGHT_GRAY);

		panel_4.add(username,gc);

		JLabel password_ = new JLabel(Password_+":");
		gc.gridx= 0;
		gc.gridy =2 ;
		gc.anchor = GridBagConstraints.CENTER;
		panel_4.add(password_, gc);

		gc.gridx= 1;
		gc.gridy =2 ;
		gc.anchor = GridBagConstraints.CENTER;
		password = new JPasswordField(18);
		password.setPreferredSize(new Dimension(200,30));
		password.setForeground(Color.LIGHT_GRAY);
		password.setText(Password_);
		password.addMouseListener(adapter);
		password.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
				{
					password.removeMouseListener(adapter);
					password.setForeground(Color.black);
				}
			}
		});
		panel_4.add(password,gc);

		return panel_4;

	}

	public JPanel getButtonPanel(){
		JPanel buttonPanel  = new JPanel(new BorderLayout());
		login = new JButton("LOGIN");
		buttonPanel.add(login,BorderLayout.NORTH);		

		login.setPreferredSize(new Dimension(200,35));
		getRootPane().setDefaultButton(login);
		login.setSelected(true);
		login.addActionListener(e ->{boolean success = false; 
		success = l.login(username.getText(),password.getText());	
		if(success== false)
		{
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Incorrect Username/Password.");  
		}
		else{
			l.setSuccess(true);
			dispose();
		}

		});
		return buttonPanel;
	}


	private void textFieldMousePressed(java.awt.event.MouseEvent evt) {

		if(evt.getSource() == username)
			username.setText("");else
				password.setText("");

	}

	public void textfieldFocusLost(java.awt.event.MouseEvent evt) {    
		if(evt.getSource() == username)
			username.setText(USER_NAME);else
				password.setText(Password_);

	}

	public void addLoginListener(LoginPanelListener l)
	{
		this.l = l;
	}
}
