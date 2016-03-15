package sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSV;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * @author: Tran Ngoc Hieu
 */
public class MemberManager {
    private List<Member> memberList;
    private Map<String, Member> memberMap;
    private String filename;
    private CSV csvIO;
    private AbstractTableModel tableModel;

    public MemberManager(String filename) throws IOException {
        this.filename = filename;

        memberList = new ArrayList<>();
        memberMap = new HashMap<>();

        tableModel = new AbstractTableModel() {
            //TODO: move others here
            private final String[] COLUMN_NAMES = { "Name", "Member ID", "Loyalty Point" };

            @Override
            public String getColumnName(int column) {
                return COLUMN_NAMES[column];
            }

            @Override
            public int getRowCount() {
                return memberList.size();
            }

            @Override
            public int getColumnCount() {
                return COLUMN_NAMES.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Member member = memberList.get(rowIndex);
                switch (columnIndex) {
                    case 0: return member.getName();
                    case 1: return member.getId();
                    case 2: return member.getLoyaltyPoint();
                    default: return null;
                }
            }
        };

        initData();
    }

    private void initData() throws IOException {
        CSVReader reader = null;
        try {
            //TODO: refactor IO
            reader = new CSVReader(filename);
            csvIO = new CSV(filename);
            while (reader.readRecord()) {
                List<String> record = reader.getValues();

                String name = record.get(0);
                String id = record.get(1);
                int loyaltyPoint = -1;
                try {
                    // Convert the loyaltyPoint from String to int
                    loyaltyPoint = Integer.parseInt(record.get(2));
                } catch (NumberFormatException nfe) {
                    System.out.println("Member " + id + " NumberFormatException: " + nfe.getMessage());
                }
                Member member = new Member(id, name);
                member.setId();
                member.setLoyaltyPoint(loyaltyPoint);

                memberList.add(member);
                memberMap.put(id, member);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) reader.close();
        }

    }

    public boolean addMember(Member member) {
        String id = member.getRequestedId();
        if (memberMap.containsKey(id)) return false;

        member.setId();
        memberList.add(member);
        memberMap.put(id, member);

        int rowIndex = memberList.size() - 1;
        tableModel.fireTableRowsInserted(rowIndex, rowIndex);

        //TODO: refactor IO
        try {
            store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    //TODO: KIV
//    public Customer getCustomer(String id) {
//        if (id == null || id.equalsIgnoreCase("PUBLIC"))
//            return PublicCustomer.getInstance();
//
//        return getMember(id);
//    }

    public Member getMember(String memberID) {
        return memberMap.get(memberID);
    }

    //TODO: chk all other list methods, to return new list
    public List<Member> getMemberList() {
        return new ArrayList<>(memberList);
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void store() throws IOException {
        List<String> contents = memberList.stream()
                .sorted(Comparator.comparing(Member::getId))
                .map(Member::toString)
                .collect(Collectors.toList());

        csvIO.write(contents);
    }
}
