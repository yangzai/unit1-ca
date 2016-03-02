package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yangzai on 29/2/16.
 */
public class MainFrame extends JFrame {
    private static final String
            CHECK_OUT = "Check Out", DISCOUNT = "Discount",
            INVENTORY = "Inventory", NEW_MEMBER = "New Member",
            NEW_PRODUCT = "New Product", NEW_CATEGORY = "New Category",
            REPORTS = "Reports", NULL = "NULL";
    private static final String[] FEATURE_ARRAY =
            {CHECK_OUT, DISCOUNT, INVENTORY, NEW_MEMBER,
            NEW_PRODUCT, NEW_CATEGORY, REPORTS, NULL};
    private static final ArrayList<String> FEATURE_LIST =
            new ArrayList<>(Arrays.asList(FEATURE_ARRAY));

    public MainFrame() {
        super("University Souvenir Store");

        CardLayout cardLayout = new CardLayout();
        JPanel contentPane = new JPanel(cardLayout);
        setContentPane(contentPane);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        contentPane.add(mainPanel, "Main");

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        int i = 0;
        for (String feature : FEATURE_LIST) {
            c.gridx = i % 2;
            c.gridy = i++/2 % 4;

            JButton button = new JButton(feature);
            button.addActionListener(
                    e->cardLayout.show(contentPane, feature)
            );
            mainPanel.add(button, c);

            //TODO: FeaturePanel abstract class
            JPanel panel = new JPanel();
            panel.add(new JLabel(feature));

            JButton backButton = new JButton("Back");
            backButton.addActionListener(
                    e->cardLayout.show(contentPane, "Main")
            );
            panel.add(backButton);

            contentPane.add(panel, feature);
        }

        pack();
    }
}
