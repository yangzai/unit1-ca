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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.security.auth.Destroyable;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;



public class LoginPanel extends JFrame {
	
	private JTextField username,password;
	private JButton login;
	private static final String USER_NAME = "Username";
	private static final String Password_ = "Password";
	private LoginPanelListener l;

	public LoginPanel() {
		
		MouseAdapter adapter = new java.awt.event.MouseAdapter() {
	        public void mousePressed(java.awt.event.MouseEvent evt) {
	            textFieldMousePressed(evt);
	        }
	        public void mouseExited(MouseEvent e)
	        {
	        	textfieldFocusLost(e);
	        }
	   };
		// TODO Auto-generated constructor stub
		JPanel mainPanel = new JPanel(new BorderLayout());
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 128));
		panel.setPreferredSize(new Dimension(50,50));
		add(panel, BorderLayout.NORTH);
		System.out.println("after adding");
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		
		JLabel lblUniversitySouvenir = new JLabel("Welcome to the University Souviner");
		lblUniversitySouvenir.setForeground(Color.WHITE);
		lblUniversitySouvenir.setHorizontalAlignment(SwingConstants.CENTER);
		lblUniversitySouvenir.setFont(new Font("Tahoma", Font.PLAIN, 31));
		panel.add(lblUniversitySouvenir, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(100,80));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(128, 128, 128));
		panel_1.setPreferredSize(new Dimension(300,80));
		add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(128, 128, 128));
		panel_2.setPreferredSize(new Dimension(300,80));
		add(panel_2, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(128, 128, 128));
		panel_3.setPreferredSize(new Dimension(300,80));
		add(panel_3, BorderLayout.SOUTH);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(192, 192, 192));
		//panel_4.setBorder();
		TitledBorder titled = BorderFactory.createTitledBorder(
				new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)), "Login to Continue");
		titled.setTitleFont(new Font("SansSerif", Font.BOLD, 17));
		panel_4.setBorder(titled);
		panel_4.setPreferredSize(new Dimension(200,200));
		panel_4.setBackground(new Color(192, 192, 192));
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		/*gbl_panel_4.columnWidths = new int[]{0};
		gbl_panel_4.rowHeights = new int[]{0};
		gbl_panel_4.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{Double.MIN_VALUE};*/
		panel_4.setLayout(gbl_panel_4);
		panel_4.setPreferredSize(new Dimension(200,200));
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(3,3,3,3);
		
		JPanel label_panel = new JPanel(new BorderLayout());
		//label_panel.setBorder(new LineBorder(Color.BLACK));
		
		gc.gridx= 0;
		gc.gridy =0 ;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 0.0;
		//gc.anchor=GridBagConstraints.LAST_LINE_END;
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		panel_4.add(label_panel,gc);
	
		JLabel username_ = new JLabel("Username:");
		gc.gridx= 0;
		gc.gridy =1 ;
		gc.anchor = GridBagConstraints.CENTER;
		//gc.insets = new Insets(3,3,3,3);
		panel_4.add(username_, gc);
		
			
		gc.gridx= 1;
		gc.gridy =1 ;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(3,3,3,3);
		username = new JTextField("Username");
		username.addMouseListener(adapter);
		username.addKeyListener(new KeyAdapter()
	    {
	        public void keyPressed(KeyEvent ke)
	        {
	            if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
	            {
	                username.removeMouseListener(adapter);
	            }
	        }
	    });
		username.setPreferredSize(new Dimension(200,30));
		username.setForeground(Color.LIGHT_GRAY);
		//addListener(username,USER_NAME);
		
		panel_4.add(username,gc);
		
		JLabel password_ = new JLabel("Password:");
		gc.gridx= 0;
		gc.gridy =2 ;
		gc.anchor = GridBagConstraints.CENTER;
	//	gc.insets = new Insets(3,3,3,3);
		panel_4.add(password_, gc);
		
		gc.gridx= 1;
		gc.gridy =2 ;
		gc.anchor = GridBagConstraints.CENTER;
		password = new JPasswordField(18);
		password.setPreferredSize(new Dimension(200,30));
		password.setForeground(Color.LIGHT_GRAY);
		password.setText("Password:");
		password.addMouseListener(adapter);
		password.addKeyListener(new KeyAdapter()
	    {
	        public void keyPressed(KeyEvent ke)
	        {
	            if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
	            {
	            	password.removeMouseListener(adapter);
	            }
	        }
	    });
		//addListener(password,Password_);
		//password.addActionListener(l);
		panel_4.add(password,gc);
		
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
		gc.gridx= 1;
		gc.gridy =3 ;
		gc.anchor = GridBagConstraints.CENTER;
		panel_4.add(buttonPanel,gc);
		
		add(panel_4, BorderLayout.CENTER);
		setVisible(true);
		setSize(900,400);
		
	}
	
	private void textFieldMousePressed(java.awt.event.MouseEvent evt) {
	     
		if(evt.getSource() == username){
		username.setText("");}else
		{
			password.setText("");
		}
	}
	
	public void textfieldFocusLost(java.awt.event.MouseEvent evt) {    
		if(evt.getSource() == username){
			username.setText("Username");;}else
			{
				password.setText("Password");
			}
	
	}

	public void addLoginListener(LoginPanelListener l)
	{
		this.l = l;
	}
}
	