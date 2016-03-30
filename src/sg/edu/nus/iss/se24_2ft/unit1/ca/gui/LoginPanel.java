package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

/**
 * @author Srishti Miglani
 *  
 */
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public abstract class LoginPanel extends JDialog {
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private JTextField username, password;
    private MouseAdapter adapter;
    private boolean success;

    public LoginPanel() {
        setModal(true);
        setTitle("Login");

        success = false;
        adapter = new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                textFieldMousePressed(evt);
            }
            public void mouseExited(MouseEvent e)
            {
                textFieldFocusLost(e);
            }
        };
        // TODO Auto-generated constructor stub
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = gbc.weighty = 0;
        gbc.gridx = gbc.gridy = 0;

        //*****Adding Main heading **//
        JLabel lblUniversitySouvenir = new JLabel("University Souvenir Store Staff Login");
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

        setSize(600,400);
    }

    public JPanel getMainPanel() {
        JPanel panel = new JPanel();

        // *** Adding border to the username-password panel *** /
        panel.setBackground(new Color(192, 192, 192));

        TitledBorder titled = BorderFactory.createTitledBorder(
                new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)), "Login to Continue");
        titled.setTitleFont(new Font("SansSerif", Font.BOLD, 17));
        panel.setBorder(titled);
        panel.setPreferredSize(new Dimension(200,200));
        panel.setBackground(new Color(192, 192, 192));

        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(200,200));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(3,3,3,3);

        // *** Adding border to the username-password labels and textfields *** /
        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel(USERNAME +":"), gc);

        gc.gridx = gc.gridy = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(3,3,3,3);
        username = new JTextField(USERNAME);
        username.addMouseListener(adapter);
        username.addKeyListener(new KeyAdapter() {
            //this section will execute only when user is editing the JTextField
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyChar()!=27 && ke.getKeyChar()!=65535) {
                    username.removeMouseListener(adapter);
                    username.setForeground(Color.black);
                }
            }
        });
        username.setPreferredSize(new Dimension(200,30));
        username.setForeground(Color.LIGHT_GRAY);

        panel.add(username,gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel(PASSWORD +":"), gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.CENTER;
        password = new JPasswordField(18);
        password.setPreferredSize(new Dimension(200,30));
        password.setForeground(Color.LIGHT_GRAY);
        password.setText(PASSWORD);
        password.addMouseListener(adapter);
        password.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                //this section will execute only when user is editing the JTextField
                if (ke.getKeyChar()!=27 && ke.getKeyChar()!=65535) {
                    password.removeMouseListener(adapter);
                    password.setForeground(Color.black);
                }
            }
        });
        panel.add(password, gc);

        return panel;
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel  = new JPanel(new BorderLayout());
        JButton login = new JButton("LOGIN");
        buttonPanel.add(login,BorderLayout.NORTH);

        login.setPreferredSize(new Dimension(200,35));
        getRootPane().setDefaultButton(login);
        login.setSelected(true);
        login.addActionListener(e ->{
            if (!login(username.getText(),password.getText())) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Incorrect Username/Password.");
            } else {
                success = true;
                dispose();
            }
        });
        return buttonPanel;
    }

    private void textFieldMousePressed(MouseEvent evt) {
        if(evt.getSource() == username)
            username.setText("");
        else password.setText("");

    }

    private void textFieldFocusLost(MouseEvent evt) {
        if(evt.getSource() == username)
            username.setText(USERNAME);
        else password.setText(PASSWORD);
    }

    public boolean isSuccess() { return success; }

    abstract protected boolean login(String username, String password);
}
