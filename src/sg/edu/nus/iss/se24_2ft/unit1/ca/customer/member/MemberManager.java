package sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    public MemberManager(String filename) throws IOException {
        tableModel = null;
        this.filename = filename;

        memberList = new ArrayList<>();
        memberMap = new HashMap<>();

        initData();
    }

    private void initData() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.map(Utils::splitCsv).forEach(a -> {
                String name = a[0], id = a[1];

                int loyaltyPoint = Utils.parseIntOrDefault(a[2], -1);

                Member member = new Member(id, name);
                member.setId();
                member.setLoyaltyPoint(loyaltyPoint);

                memberList.add(member);
                memberMap.put(id, member);
            });
        }
    }

    public boolean addMember(Member member) {
        String id = member.getRequestedId();
        if (memberMap.containsKey(id)) return false;

        member.setId();
        memberList.add(member);
        memberMap.put(id, member);

        int rowIndex = memberList.size() - 1;
        if (tableModel != null)
            tableModel.fireTableRowsInserted(rowIndex, rowIndex);

        //TODO: KIV try/catch for IO
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

    private void store() throws IOException {
        Stream<String> stream = memberList.stream()
                .sorted(Comparator.comparing(Member::getId))
                .map(Member::toString);

        Files.write(Paths.get(filename), (Iterable<String>) stream::iterator,
                StandardOpenOption.CREATE);
    }
}
