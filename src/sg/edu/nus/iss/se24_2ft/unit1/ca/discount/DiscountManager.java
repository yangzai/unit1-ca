package sg.edu.nus.iss.se24_2ft.unit1.ca.discount;

//@author: Nguyen Trung
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class DiscountManager {
    private String filename;
    private List<Discount> discountList;
    private Map<String, Discount> discountMap;
    private AbstractTableModel tableModel;

    public DiscountManager(String filename) {
        tableModel = null;
        this.filename = filename;
        discountList = new ArrayList<>();
        discountMap = new HashMap<>();

        load();
    }

    private void load() {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.map(Util::splitCsv).forEach(a -> {
                String code = a[0], description = a[1];
                Date start = Util.parseDateOrDefault(a[2], null);
                int period = Util.parseIntOrDefault(a[3], -1);
                double percent = Util.parseDoubleOrDefault(a[4], 0);
                boolean memberOnly = a[5].toUpperCase().equals("M");

                Discount discount = new Discount(code, description, start, period, percent, memberOnly);

                String requestedCode = discount.getRequestedCode();
                //if id already exist, skip
                if (discountMap.containsKey(requestedCode)) return;

                discount.setCode();

                discountList.add(discount);
                discountMap.put(requestedCode, discount);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Discount getMaxDiscount(Customer customer) {
        Optional<Discount> discountOptional = discountList.stream()
                .filter(d -> {
                    if (!d.isDiscountAvailable()) return false;
                    if (!d.isMemberOnly()) return true;
                    if (customer instanceof Member) {
                        int loyaltyPoint = ((Member) customer).getLoyaltyPoint();
                        String code = d.getCode().toUpperCase();
                        if (code.equals("MEMBER_FIRST") && loyaltyPoint == -1)
                            return true;
                        if (code.equals("MEMBER_SUBSEQ") && loyaltyPoint != -1)
                            return true;
                    }
                    return false;
                }).max(Comparator.comparing(Discount::getPercent));

        return discountOptional.isPresent() ? discountOptional.get() : null;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public boolean addDiscount(Discount discount) {
        String code = discount.getRequestedCode();
        //TODO: check constraint for all add
        if (discountMap.containsKey(code)) return false;

        discount.setCode();
        discountList.add(discount);
        discountMap.put(code, discount);

        //TODO: move rowIndex for all add
        int rowIndex = discountList.size() - 1;
        if (tableModel != null)
            tableModel.fireTableRowsInserted(rowIndex, rowIndex);

        store();

        return true;
    }

    public TableModel getTableModel() {
        if (tableModel != null) return tableModel;

        return tableModel = new AbstractTableModel() {
            private final String[] COLUMN_NAMES = {"Code", "Description", "Start Date", "Period", "Percent", "M/A"};

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
                Discount d = discountList.get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return d.getCode();
                    case 1:
                        return d.getDescription();
                    case 2:
                        return d.getStart() != null ? d.getStart() : "ALWAYS";
                    case 3:
                        return d.getPeriod() > -1 ? d.getPeriod() : "ALWAYS";
                    case 4:
                        return d.getPercent();
                    case 5:
                        return d.isMemberOnly() ? "M" : "A";
                    default:
                        return null;
                }
            }
        };
    }

    private void store() {
        Stream<String> stream = discountList.stream()
                .sorted(Comparator.comparing(Discount::getCode))
                .map(Discount::toString);

        try {
            Files.write(Paths.get(filename), (Iterable<String>) stream::iterator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}