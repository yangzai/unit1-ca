package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;

import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

public class DiscountPanel extends FeaturePanel {
	private static final int VISIBLE_ROW = 5;
	private static final String MEMBER = "Member", ALL = "All";
	private static final String[] MA_ARRAY = { MEMBER, ALL };
	private JTable table;
	private JScrollPane scrollPane;
	private List<DiscountPanelListener> discountPanelListenerList;

	public DiscountPanel() {
		super(new GridBagLayout());

		table = new JTable();
		scrollPane = new JScrollPane(table);
		discountPanelListenerList = new ArrayList<>();
		GridBagConstraints c = new GridBagConstraints();

		// add scroll panel to grid
		c.weightx = c.weighty = 1;
		c.gridx = c.gridy = 0;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		add(scrollPane, c);

		// add label
		c.weightx = c.weighty = 0;
		c.gridy++;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("Code"), c);

		// add textbox
		c.gridx++;
		c.gridwidth++;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField codeTextField = new JTextField();
		add(codeTextField, c);

		// add label description
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("Description"), c);

		// add Text description
		c.gridx++;
		c.gridwidth++;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField descriptionTextField = new JTextField();
		add(descriptionTextField, c);

		// add label start date
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("<html><div style='text-align:center;'>" + "Start Date<br>(YYYY-MM-DD)" + "</div></html>"), c);

		// add Text description
		c.gridx++;
		c.gridwidth++;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField dateTextField = new JTextField();
		add(dateTextField, c);

		// add label period
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("Period"), c);

		// add Text period
		c.gridx++;
		c.gridwidth++;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField periodTextField = new JTextField();
		add(periodTextField, c);

		// add label percent
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("Percent"), c);

		// add Text percent
		c.gridx++;
		c.gridwidth++;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField percentTextField = new JTextField();
		add(percentTextField, c);

		// add label percent
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("M/A"), c);

		// add Text percent
		c.gridx++;
		c.gridwidth++;
		c.fill = GridBagConstraints.HORIZONTAL;
		JComboBox<String> MAComboBox = new JComboBox<>(MA_ARRAY);
		add(MAComboBox, c);

		// spacer
		c.gridy++;
		c.gridwidth--;
		c.weightx = 1;
		c.fill = GridBagConstraints.NONE;
		add(Box.createHorizontalGlue(), c);

		// add percent
		c.gridx++;
		c.weightx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton addBtn = new JButton("Add Discount");
		addBtn.addActionListener(e -> {
			boolean isMemberOnly = MAComboBox.getSelectedItem().toString().equals(MEMBER);

			String code = codeTextField.getText(), description = descriptionTextField.getText();
			Date start = Utils.parseDateOrDefault(dateTextField.getText(), null); // TODO:
																					// date
																					// ui
			if (periodTextField.getText().contains(".")) {
				JOptionPane.showMessageDialog(null, "The period input is not valid");
			}
			int period = Utils.parseIntOrDefault(periodTextField.getText(), -1);
			double percent = Utils.parseDoubleOrDefault(percentTextField.getText(), 0);

			Discount discount = new Discount(code, description, start, period, percent, isMemberOnly);

			discountPanelListenerList.forEach(l -> l.addDiscountRequested(discount));
			dateTextField.setText("");
			descriptionTextField.setText("");
			periodTextField.setText("");
			codeTextField.setText("");
			percentTextField.setText("");			
		});
		add(addBtn, c);

		// add Back btn
		c.gridy++;
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(e -> backActionPerformed(e));
		add(backBtn, c);
	}

	public void addDiscountPanelListener(DiscountPanelListener l) {
		discountPanelListenerList.add(l);
	}

	public void setTableModel(TableModel tableModel) {
		table.setModel(tableModel);
		Dimension d = table.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * VISIBLE_ROW + 1));
	}
}
