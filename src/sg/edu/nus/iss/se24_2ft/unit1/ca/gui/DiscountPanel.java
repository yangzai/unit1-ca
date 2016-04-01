package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

public class DiscountPanel extends FeaturePanel {
	private static final int VISIBLE_ROW = 5;
	private static final String MEMBER = "Member", ALL = "All";
	private static final String[] MA_ARRAY = { MEMBER, ALL };
	private JTable table;
	private JScrollPane scrollPane;
	private List<DiscountPanelListener> discountPanelListenerList;

	public DiscountPanel() {
		super(new BorderLayout());
		setPreferredSize(new Dimension(550, 400));

		table = new JTable();
		discountPanelListenerList = new ArrayList<>();
		add(detailPanel(), BorderLayout.CENTER);
		add(newDiscountPanel(), BorderLayout.SOUTH);
	}

	public void addDiscountPanelListener(DiscountPanelListener l) {
		discountPanelListenerList.add(l);
	}

	public void setTableModel(TableModel tableModel) {
		int dateColumnIndex = tableModel.getColumnCount() - 4;
		table.setModel(tableModel);
		Dimension d = table.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * VISIBLE_ROW + 1));
		// TODO: Consider replacing with localdate
		TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof Date)
					value = Util.formatDateOrDefault((Date) value, null);
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		table.getColumnModel().getColumn(dateColumnIndex).setCellRenderer(tableCellRenderer);
	}

	private JPanel detailPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// add Transaction JLabel
		c.gridx = c.gridy = 0;
		c.gridwidth = 2;
		panel.add(new JLabel("Discount"), c);

		// add scroll panel to grid
		c.weightx = c.weighty = 1;
		c.gridwidth = 1;
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		scrollPane = new JScrollPane(table);
		panel.add(scrollPane, c);

		// add Back btn
		// c.gridx++;
		// c.weightx = c.weighty = 0;
		// c.anchor = GridBagConstraints.NORTH;
		// c.fill = GridBagConstraints.HORIZONTAL;
		//
		// JButton backBtn = new JButton("Back");
		// backBtn.addActionListener(this::backActionPerformed);
		// panel.add(backBtn, c);

		return panel;
	}

	private JPanel newDiscountPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridwidth = 4;
		// add label
		c.weighty = 0;
		c.gridy = c.gridx = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel("Code"), c);

		// add textbox
		c.gridx++;
		c.gridwidth++;
		JTextField codeTextField = new JTextField();
		panel.add(codeTextField, c);

		// add label description
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		panel.add(new JLabel("Description"), c);

		// add Text description
		c.gridx++;
		c.gridwidth++;
		JTextField descriptionTextField = new JTextField();
		panel.add(descriptionTextField, c);

		// add label start date
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		panel.add(new JLabel("<html><div style='text-align:center;'>" + "Start Date<br>(YYYY-MM-DD)" + "</div></html>"),
				c);

		// add Text description
		c.gridx++;
		c.gridwidth++;
		JTextField dateTextField = new JTextField();
		panel.add(dateTextField, c);

		// add label period
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		panel.add(new JLabel("Period"), c);

		// add Text period
		c.gridx++;
		c.gridwidth++;
		JTextField periodTextField = new JTextField();
		panel.add(periodTextField, c);

		// add label percent
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		panel.add(new JLabel("Percent"), c);

		// add Text percent
		c.gridx++;
		c.gridwidth++;
		JTextField percentTextField = new JTextField();
		panel.add(percentTextField, c);

		// add label percent
		c.gridx--;
		c.gridy++;
		c.gridwidth--;
		panel.add(new JLabel("M/A"), c);

		// add Text percent
		c.gridx++;
		c.gridwidth++;
		JComboBox<String> MAComboBox = new JComboBox<>(MA_ARRAY);
		panel.add(MAComboBox, c);

		// spacer
		c.gridy++;
		c.gridwidth--;
		c.weightx = 1;
		panel.add(Box.createHorizontalGlue(), c);

		// add percent
		c.gridx = c.gridx + 2;
		c.gridy = 0;
		c.weightx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(e -> {
			boolean isMemberOnly = MAComboBox.getSelectedItem().toString().equals(MEMBER);

			String code = codeTextField.getText(), description = descriptionTextField.getText();
			Date start = Util.parseDateOrDefault(dateTextField.getText(), null);

			// date
			String periodStr = periodTextField.getText(); // ui
			if (periodTextField.getText().contains(".")) {
				periodStr = periodStr.substring(0, periodTextField.getText().indexOf("."));
				float periodF = Float.parseFloat(periodTextField.getText());
				float fracPart = periodF - (int) periodF;
				if (fracPart != 0) {
					JOptionPane.showMessageDialog(null, "The period input is not valid");
					return;
				}
			}
			int period = Util.parseIntOrDefault(periodStr, -1);

			double percent = Util.parseDoubleOrDefault(percentTextField.getText(), 0);

			Discount discount = new Discount(code, description, start, period, percent, isMemberOnly);

			discountPanelListenerList.forEach(l -> l.addDiscountRequested(discount));
		});
		panel.add(addBtn, c);

		// add Reset btn
		c.gridy++;
		c.weightx = 0;
		JButton resetBtn = new JButton("Reset");
		resetBtn.addActionListener(e -> {
			codeTextField.setText(null);
			descriptionTextField.setText(null);
			dateTextField.setText(null);
			periodTextField.setText(null);
			percentTextField.setText(null);
		});
		panel.add(resetBtn, c);
		return panel;
	}
}
