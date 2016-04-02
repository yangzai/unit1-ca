package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import javax.swing.*;

/**
 * Created by yangzai on 29/2/16.
 */
public class MainFrame extends JFrame {
    private JTabbedPane contentPane;

    public MainFrame() {
        super("University Souvenir Store");
        contentPane = new JTabbedPane(JTabbedPane.LEFT);
        setContentPane(contentPane);
    }

    public void addFeaturePanel(String name, FeaturePanel featurePanel) {
        contentPane.add(featurePanel, name);
    }
}
