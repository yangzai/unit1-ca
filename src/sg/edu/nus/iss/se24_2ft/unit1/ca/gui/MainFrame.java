package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzai on 29/2/16.
 */
public class MainFrame extends JFrame {

    private static final int ROW_BUTTON_COUNT = 2;
    private Map<String, FeaturePanel> featurePanelMap;

    private JPanel mainPanel;
    GridBagConstraints c;

    public MainFrame() {
        super("University Souvenir Store");

        featurePanelMap = new HashMap<>();

        CardLayout cardLayout = new CardLayout();
        JPanel contentPane = new JPanel(cardLayout);
        setContentPane(contentPane);

        mainPanel = new JPanel(new GridBagLayout());
        contentPane.add(mainPanel, "Main");

        c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
    }

    public void addFeaturePanel(String name, FeaturePanel featurePanel) {
        Container contentPane = getContentPane();
        CardLayout cardLayout = (CardLayout) contentPane.getLayout();

        featurePanel.addBackActionListener(e -> {
            cardLayout.show(contentPane, "Main");
            resizeAndPack();
        });
        featurePanelMap.put(name, featurePanel);
        contentPane.add(featurePanel, name);

        JButton button = new JButton(name);
        button.addActionListener(e -> {
            cardLayout.show(contentPane, name);
            resizeAndPack(name);
        });
        mainPanel.add(button, c);

        if (++c.gridx >= ROW_BUTTON_COUNT) {
            c.gridx %= ROW_BUTTON_COUNT;
            c.gridy++;
        }
    }

    //resize to feature panel
    private void resizeAndPack(String featurePanelName) {
        JPanel panel = featurePanelMap.get(featurePanelName);
        if (panel == null) panel = mainPanel;

        getContentPane().setPreferredSize(panel.getPreferredSize());
        pack();
    }

    //resize to main panel
    public void resizeAndPack() { resizeAndPack(null); }
}
