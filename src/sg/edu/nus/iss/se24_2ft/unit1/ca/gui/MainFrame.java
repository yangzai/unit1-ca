package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import javax.swing.*;

import sg.edu.nus.iss.se24_2ft.unit1.ca.StoreApplication;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzai on 29/2/16.
 */
public class MainFrame extends JFrame {

	private static final int ROW_BUTTON_COUNT = 2;
	private JPanel mainPanel;
	private JPanel currentPanel;
	private JPanel contentPanel;
	private JTabbedPane mainPane = new JTabbedPane(JTabbedPane.LEFT);
	GridBagConstraints c;

	public MainFrame() {
		super("University Souvenir Store");

		// CardLayout cardLayout = new CardLayout();
		//// JPanel contentPane = new JPanel(cardLayout);
		// JPanel contentPane = new JPanel(new BorderLayout());
		// setContentPane(contentPane);
		//
		// currentPanel = mainPanel = new JPanel(new GridBagLayout());
		//// contentPane.add(mainPanel, "Main");
		// contentPane.add(mainPanel, BorderLayout.WEST);
		//
		// contentPanel = new JPanel(cardLayout);
		// contentPane.add(contentPanel, BorderLayout.CENTER);
		//
		// c = new GridBagConstraints();
		// c.gridx = c.gridy = 0;
		// c.fill = GridBagConstraints.HORIZONTAL;
		
		setContentPane(mainPane);
	}

	public void addFeaturePanel(String name, FeaturePanel featurePanel) {
		// Container contentPane = getContentPane();
		//// CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		// CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
		//
		// featurePanel.addBackActionListener(e -> {
		// cardLayout.show(contentPane, "Main");
		// currentPanel = mainPanel;
		// resizeAndPack();
		// });
		//// contentPane.add(featurePanel, name);
		// contentPanel.add(featurePanel,name);
		//
		// JButton button = new JButton(name);
		// button.addActionListener(e -> {
		//// cardLayout.show(contentPane, name);
		// cardLayout.show(contentPanel, name);
		// currentPanel = featurePanel;
		// resizeAndPack();
		// });
		// mainPanel.add(button, c);
		// c.gridy++;
		//
		//// if (++c.gridx >= ROW_BUTTON_COUNT) {
		//// c.gridx %= ROW_BUTTON_COUNT;
		//// c.gridy++;
		//// }
		
		mainPane.add(featurePanel, name);
	}

	// resize to currentPanel
	public void resizeAndPack() {
		// getContentPane().setPreferredSize(currentPanel.getPreferredSize());
		pack();
	}
}
