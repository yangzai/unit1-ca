package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

/**
 * @author Srishti Miglani
 *  
 */
import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public abstract class LoginPanel extends JDialog {
    private JTextField username, password;
    private boolean success;

    public LoginPanel() {
        setModal(true);
        setTitle("Login");

        success = false;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = gbc.weighty = 0;
        gbc.gridx = gbc.gridy = 0;

        //*****Adding Main heading **//
        JLabel lblUniversitySouvenir = new JLabel("University Souvenir Store Staff Login");
        lblUniversitySouvenir.setHorizontalAlignment(SwingConstants.CENTER);
        lblUniversitySouvenir.setFont(new Font("Tahoma", Font.PLAIN, 31));
        add(lblUniversitySouvenir, gbc);

        // *** Adding username- password panel ***////
        gbc.gridy++;
        add(getMainPanel(), gbc);

        // *** Adding login button panel ***////
        gbc.gridy++;
        add(getButtonPanel(), gbc);

        setSize(600,400);
    }

    private JPanel getMainPanel() {
        JPanel panel = new JPanel();

        // *** Adding border to the username-password panel *** /
        panel.setBackground(new Color(192, 192, 192));

        TitledBorder title = BorderFactory.createTitledBorder(
                new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)), "Login to Continue");
        title.setTitleFont(new Font("SansSerif", Font.BOLD, 17));
        panel.setBorder(title);
        panel.setPreferredSize(new Dimension(200,200));
        panel.setBackground(new Color(192, 192, 192));

        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(200,200));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(3,3,3,3);

        // *** Adding border to the username-password labels and textfields *** /
        gc.gridx = gc.gridy = 0;
        gc.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel("Username:"), gc);

        gc.gridx++;
        gc.insets = new Insets(3,3,3,3);
        username = new JTextField();
        username.setPreferredSize(new Dimension(200,30));

        panel.add(username,gc);

        gc.gridx--;
        gc.gridy++;
        panel.add(new JLabel("Password:"), gc);

        gc.gridx++;
        password = new JPasswordField(18);
        password.setPreferredSize(new Dimension(200,30));

        panel.add(password, gc);

        return panel;
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel  = new JPanel(new BorderLayout());
        JButton login = new JButton("LOGIN");
        buttonPanel.add(login,BorderLayout.NORTH);

        login.setPreferredSize(new Dimension(200,35));
        getRootPane().setDefaultButton(login);
        login.addActionListener(e ->{
            if (login(username.getText(), password.getText())) {
                success = true;
                dispose();
                return;
            }

            JOptionPane.showMessageDialog(this, "Incorrect Username/Password.");
        });
        return buttonPanel;
    }

    public boolean isSuccess() { return success; }

    abstract protected boolean login(String username, String password);
}
