package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import sg.edu.nus.iss.se24_2ft.unit1.ca.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.CategoryManager;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzai on 29/2/16.
 */
public abstract class MainFrame extends JFrame {
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
    private Map<String, JPanel> featurePanelMap;

    public MainFrame() {
        super("University Souvenir Store");

        featurePanelMap = new HashMap<>();

        CardLayout cardLayout = new CardLayout();
        JPanel contentPane = new JPanel(cardLayout);
        setContentPane(contentPane);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        contentPane.add(mainPanel, "Main");

        featurePanelMap.put(NEW_CATEGORY, new CategoryPanel() {
            @Override
            public void addActionPerformed(Category category) {
                addCategoryActionPerformed(category);
            }

            @Override
            public void backActionPerformed() {
                //TODO: refactor
                cardLayout.show(contentPane, "Main");
                contentPane.setPreferredSize(mainPanel.getPreferredSize());
                pack();
            }

            @Override
            public TableModel getTableModel() {
                return getCategoryTableModel();
            }
        });
        //TODO: @everyone
        featurePanelMap.put(NEW_MEMBER, new MemberPanel());
        featurePanelMap.put(NEW_PRODUCT, new ProductPanel());
        featurePanelMap.put(INVENTORY, new InventoryPanel());
        featurePanelMap.put(DISCOUNT, new DiscountPanel());
        featurePanelMap.put(CHECK_OUT, new CheckoutPanel());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        int i = 0;
        for (String feature : FEATURE_LIST) {
            c.gridx = i % 2;
            c.gridy = i++/2 % 4;

            JPanel panel = featurePanelMap.getOrDefault(feature, new JPanel() {
                {
                    JButton backButton = new JButton("Back");
                    backButton.addActionListener(e -> {
                        cardLayout.show(contentPane, "Main");
                        contentPane.setPreferredSize(mainPanel.getPreferredSize());
                        pack();
                    });
                    add(backButton);
                    add(new JLabel(feature));
                }
            });
            contentPane.add(panel, feature);

            JButton button = new JButton(feature);
            button.addActionListener(e -> {
                cardLayout.show(contentPane, feature);
                contentPane.setPreferredSize(panel.getPreferredSize());
                pack();
            });
            mainPanel.add(button, c);
        }

        contentPane.setPreferredSize(mainPanel.getPreferredSize());
        pack();
    }

    public abstract void addCategoryActionPerformed(Category category);

    public abstract TableModel getCategoryTableModel();
}
