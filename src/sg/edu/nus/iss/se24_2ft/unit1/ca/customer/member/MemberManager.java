package sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * @author: Tran Ngoc Hieu
 */
public class MemberManager {
    private List<Member> memberList;
    private Map<String, Member> memberMap;
    private String filename;
    private AbstractTableModel tableModel;

    public MemberManager(String filename) {
        tableModel = null;
        this.filename = filename;

        memberList = new ArrayList<>();
        memberMap = new HashMap<>();

        load();
    }

    private void load() {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.map(Util::splitCsv).forEach(a -> {
                String name = a[0], id = a[1];

                int loyaltyPoint = Util.parseIntOrDefault(a[2], -1);

                Member member = new Member(id, name);

                String requestedId = member.getRequestedId();
                if (memberMap.containsKey(requestedId)) return;

                member.setId();
                member.setLoyaltyPoint(loyaltyPoint);

                memberList.add(member);
                memberMap.put(requestedId, member);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMember(Member member) {
        String id = member.getRequestedId();
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("Member ID field is blank. Please input again");
        if (memberMap.containsKey(id))
            throw new IllegalArgumentException("Member ID " + id + " already existed. Please input again");

        member.setId();
        memberList.add(member);
        memberMap.put(id, member);

        int rowIndex = memberList.size() - 1;
        if (tableModel != null)
            tableModel.fireTableRowsInserted(rowIndex, rowIndex);

        store();
    }

    public void debitLoyaltyPoint(String id, int loyaltyPoint) {
        Member member = memberMap.get(id);

        if (member == null)
            throw new IllegalArgumentException("Member is not valid");
        if (loyaltyPoint == 0) return;
        if (member.getLoyaltyPoint() < loyaltyPoint)
            throw new IllegalArgumentException("Loyalty Point is not enough");

        member.removeLoyaltyPoint(loyaltyPoint);

        int index = memberList.indexOf(member);

        if (index > -1 && tableModel != null)
            tableModel.fireTableCellUpdated(index, 2);

        store();
    }

    public void creditLoyaltyPoint(String id, int loyaltyPoint) {
        Member member = memberMap.get(id);
        if (member == null)
            throw new IllegalArgumentException("Member is not valid");

        member.addLoyaltyPoint(loyaltyPoint);

        int index = memberList.indexOf(member);

        if (index > -1 && tableModel != null)
            tableModel.fireTableCellUpdated(index, 2);

        store();
    }

    public Member getMember(String memberID) {
        return memberMap.get(memberID);
    }

    //TODO: chk all other list methods, to return new list
    public List<Member> getMemberList() {
        return new ArrayList<>(memberList);
    }

    public TableModel getTableModel() {
        if (tableModel != null) return tableModel;

        return tableModel = new AbstractTableModel() {
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
    }

    private void store() {
        Stream<String> stream = memberList.stream()
                .sorted(Comparator.comparing(Member::getId))
                .map(Member::toString);

        try {
            Files.write(Paths.get(filename), (Iterable<String>) stream::iterator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
