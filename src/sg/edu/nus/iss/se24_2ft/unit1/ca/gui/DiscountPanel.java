package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

<<<<<<< HEAD
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
=======
import java.awt.*;
>>>>>>> dev
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;

<<<<<<< HEAD
import sg.edu.nus.iss.se24_2ft.unit1.ca.CustomerDiscount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.DiscountManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.MemberDiscount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;

public class DiscountPanel extends FeaturePanel {
	private static final int VISIBLE_ROW = 5;
	private JTable table;
	private JScrollPane scrollPane;
	private List<Discount> discountList = null;
	private List<DiscountPanelListener> discountPanelListenerList;

	public DiscountPanel() {
		super(new GridBagLayout());
		discountPanelListenerList = new ArrayList<>();
		discountList = DiscountManager.getInstance().getDiscountList();
		table = new JTable(getDiscountTableModel());

		scrollPane = new JScrollPane(table);
		Dimension d = table.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * VISIBLE_ROW + 1));

		GridBagConstraints c = new GridBagConstraints();

		// add scroll panel to grid
		c.weightx = c.weighty = 1;
		c.gridx = c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		add(scrollPane, c);

		// add label

		c.gridy++;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		JLabel label = new JLabel("Code");
		add(label, c);

		// add textbox
		c.gridx++;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField codeTextField = new JTextField();
		add(codeTextField, c);

		// add label description
		c.gridx--;
		c.gridy++;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("Description"), c);

		// add Text description
		JTextField descTextArea = new JTextField();
		c.gridx++;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		add(descTextArea, c);

		// add label start date
		c.gridx--;
		c.gridy += 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("Start Date"), c);

		// add Text description
		JTextField dateTextField = new JTextField();
		c.gridx++;
		c.fill = GridBagConstraints.BOTH;
		add(dateTextField, c);

		// add label period
		c.gridx--;
		c.gridy++;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("Period"), c);

		// add Text period
		JTextField periodTextFiled = new JTextField();
		c.gridx++;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(periodTextFiled, c);

		// add label percent
		c.gridx--;
		c.gridy++;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("Percent"), c);

		// add Text percent
		JTextField percentTextFiled = new JTextField();
		c.gridx++;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(percentTextFiled, c);

		// add label percent
		c.gridx--;
		c.gridy++;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		add(new JLabel("M/A"), c);

		// add Text percent
		JComboBox<String> appliedComboBox = new JComboBox<String>();
		appliedComboBox.addItem("Member");
		appliedComboBox.addItem("All");
		c.gridx++;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(appliedComboBox, c);
		// add percent
		JButton addBtn = new JButton("Add Discount");
		addBtn.addActionListener(e -> {
			System.out.println("Add button clicked");
			boolean memberDiscount = appliedComboBox.getSelectedItem().toString().equals("Member") ? true : false;
			Discount discount;
			if (memberDiscount) {
				discount = new MemberDiscount();
			} else {
				discount = new CustomerDiscount();
			}
			try {
				discount.setCode(codeTextField.getText());
				discount.setDescription(descTextArea.getText());

				if (!dateTextField.getText().toUpperCase().equals("ALWAYS")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try {
						discount.setStartDate(sdf.parse(dateTextField.getText()));
					} catch (ParseException ex) {
						// TODO Auto-generated catch block
						System.out.println(ex.toString());
						JOptionPane.showMessageDialog(null, "Date format incorrect, dd/MM/yyyy or ALWAYS");
					}
				}
				if (discount.getStartDate() != null) {
					discount.setPercent(Double.parseDouble(percentTextFiled.getText()));
					discount.setPeriod(periodTextFiled.getText().equals("ALWAYS") ? -1
							: Integer.parseInt(periodTextFiled.getText()));
				}
			} catch (Exception ex) {
				System.out.println(ex.toString());
				JOptionPane.showMessageDialog(null, "data input incorrect");

			}
			// TODO: validate category
			if (dateTextField.getText().toUpperCase().equals("ALWAYS") || discount.getStartDate() != null) {
				discountPanelListenerList.forEach(l -> l.addDiscountRequested(discount));
				int firstRow = table.getRowHeight() - 1;
				System.out.println(firstRow);
				((AbstractTableModel) table.getModel()).fireTableRowsInserted(firstRow, firstRow);
				codeTextField.setText("");
				descTextArea.setText("");
				percentTextFiled.setText("");
				periodTextFiled.setText("");
				dateTextField.setText("");

			}
		});
		c.gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(addBtn, c);
		// add Back btn
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(e -> backActionPerformed(e));
		c.gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(backBtn, c);

	}

	private void initData() {

	}

	public void addDiscountPanelListener(DiscountPanelListener l) {
		discountPanelListenerList.add(l);
	}

	public TableModel getDiscountTableModel() {
		return new AbstractTableModel() {
			private final String[] COLUMN_NAMES = { "Code", "Description", "Start Date", "Period", "Percent", "M/A" };

			@Override
			public String getColumnName(int column) {
				return COLUMN_NAMES[column];
			}

			@Override
			public int getRowCount() {
				return discountList.size();
			}

			@Override
			public int getColumnCount() {
				return COLUMN_NAMES.length;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Discount discount = discountList.get(rowIndex);
				switch (columnIndex) {
				case 0:
					return discount.getCode();
				case 1:
					return discount.getDescription();
				case 2:
					return discount.getStartDate() != null ? discount.getStartDate().toString() : "ALWAYS";
				case 3:
					return discount.getPeriod() != -1 ? discount.getPeriod() : "ALWAYS";
				case 4:
					return discount.getPercent();
				case 5:
					return discount instanceof MemberDiscount ? "M" : "A";
				default:
					return null;
				}
			}

		};
	};

=======
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

public class DiscountPanel extends FeaturePanel {
    private static final int VISIBLE_ROW = 5;
    private static final String MEMBER = "Member", ALL = "All";
    private static final String[] MA_ARRAY = {MEMBER, ALL};
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
        add(new JLabel("<html><div style='text-align:center;'>" +
                "Start Date<br>(YYYY-MM-DD)" +
                "</div></html>"), c);

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
            Date start = Utils.parseDateOrDefault(dateTextField.getText(), null); //TODO: date ui
            int period = Utils.parseIntOrDefault(periodTextField.getText(), -1);
            double percent = Utils.parseDoubleOrDefault(percentTextField.getText(), 0);

            Discount discount = new Discount(code, description, start, period, percent, isMemberOnly);

            discountPanelListenerList.forEach(l -> l.addDiscountRequested(discount));
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
        scrollPane.setPreferredSize(new Dimension(d.width,table.getRowHeight()*VISIBLE_ROW+1));
    }
>>>>>>> dev
}
