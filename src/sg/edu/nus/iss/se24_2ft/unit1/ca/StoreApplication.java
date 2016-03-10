package sg.edu.nus.iss.se24_2ft.unit1.ca;

import sg.edu.nus.iss.se24_2ft.unit1.ca.gui.MainFrame;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by yangzai on 26/2/16.
 */
public class StoreApplication {
    public static void main (String args[]) throws IOException {
        //TODO: handle IOException within managers' constructors
        CategoryManager categoryManager = new CategoryManager("data/Category.dat");

        MainFrame mainFrame = new MainFrame() {
            @Override
            public TableModel getCategoryTableModel() {
                return categoryManager.getTableModel();
            }
        };
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
//                mainFrame.dispose();
                System.exit(0);
            }
        });

        Store store = new Store(mainFrame);

        mainFrame.setVisible(true);

//        ****************** yangzai-test ****************************************
//        StoreKeeperManager storeKeeperManager = null;
//        try {
//            storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(storeKeeperManager.login("xxx", "fail"));
//        System.out.println(storeKeeperManager.login("Stacy", "Dean56s"));
//        System.out.println(storeKeeperManager.login("Johny", "A12ysd45"));
//        System.out.println(storeKeeperManager.login("Johny", "fail"));

//        try {
//            //TODO: Test resource path
//            PrintWriter writer = new PrintWriter("level1/test.txt");
//            writer.println("test");
//            writer.close();
//
//            BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
//
//            System.out.println(reader.readLine());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        VendorManager vm = null;
//        try {
//            vm = new VendorManager("data");
//            vm.getVendorListByCategoryId("MUG")
//                    .stream()
//                    .map(v -> v.getCategoryId()+','+v.getName()+','+v.getDescription())
//                    .forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        ****************** Tran-test ****************************************
//        CustomerManager customerManager = null;
//        try {
//			customerManager = new CustomerManager("data-sample/Members.dat");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//        customerManager.addMember("TestID", "TestName");
//        ArrayList<Member> members = customerManager.getMembersAsList();
//        for (Member member : members) {
//			System.out.println(member.toString());
//		}
//        System.out.println("New Test");
//        Customer member = customerManager.getCustomer("X437F356");
//        System.out.println("MemberID " + member.getMemberIDasString());
//        member.setAddPoint(53.6f);
//        member.setRedeemPoint(261);
//        System.out.println("Redeem Point: " + member.getRedeemPoint());
//        System.out.println("Redeem Price: " + member.getRedeemPrice());
//        System.out.println("Current Point: " + member.getLoyaltyPoint());
//        System.out.println("Add-on Point: " + member.getAddPoint());
//        member.updateLoyaltyPoint();
//        System.out.println("Confirm Payment: " + member.getLoyaltyPoint());
//        
//        Customer pubCustomer = customerManager.getCustomer(null);
//        System.out.println("MemberID " + pubCustomer.getMemberIDasString());
//        pubCustomer.setAddPoint(53.6f);
//        pubCustomer.setRedeemPoint(261);
//        System.out.println("Redeem Point: " + pubCustomer.getRedeemPoint());
//        System.out.println("Redeem Price: " + pubCustomer.getRedeemPrice());
//        System.out.println("Current Point: " + pubCustomer.getLoyaltyPoint());
//        System.out.println("Add-on Point: " + pubCustomer.getAddPoint());
//        member.updateLoyaltyPoint();
//        System.out.println("Confirm Payment: " + pubCustomer.getLoyaltyPoint());
    }
}
