package sg.edu.nus.iss.se24_2ft.unit1.ca;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.MemberManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.Discount;
import sg.edu.nus.iss.se24_2ft.unit1.ca.discount.DiscountManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.gui.*;
import sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout.CheckoutPanel;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.ProductManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Function;

/**
 * Created by yangzai on 26/2/16.
 */
public class StoreApplication {
    private static final String
            CHECK_OUT = "Checkout", DISCOUNT = "Discount",
            INVENTORY = "Inventory", NEW_MEMBER = "New Member",
            NEW_PRODUCT = "New Product", NEW_CATEGORY = "New Category",
            REPORTS = "Reports", NULL = "NULL";

    public static void main (String args[]) {
        //TODO: handle IOException within managers' constructors
        CategoryManager categoryManager = new CategoryManager("data/Category.dat");
        ProductManager productManager = new ProductManager("data/Products.dat", categoryManager);
        MemberManager memberManager = new MemberManager("data/Members.dat");
        DiscountManager discountManager = new DiscountManager("data/Discounts.dat");
        TransactionManager transactionManager =
                new TransactionManager("data/Transactions.dat", productManager, memberManager);

        MainFrame mainFrame = new MainFrame();
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                // mainFrame.dispose();
                System.exit(0);
            }
        });

        DiscountPanel discountPanel = new DiscountPanel();
        discountPanel.setTableModel(discountManager.getTableModel());
        discountPanel.addDiscountPanelListener(discountManager::addDiscount);
        
        CategoryPanel categoryPanel = new CategoryPanel();
        categoryPanel.setTableModel(categoryManager.getTableModel());
        categoryPanel.addCategoryPanelListener(categoryManager::addCategory);

        MemberPanel memberPanel = new MemberPanel();
        memberPanel.setTableModel(memberManager.getTableModel());
        memberPanel.addMemberPanelistener(memberManager::addMember);

        ProductPanel productPanel = new ProductPanel();
        productPanel.setTableModel(productManager.getTableModel());
        productPanel.addProductPanelListener(
                (cid, p) -> productManager.addProduct(categoryManager.getCategory(cid), p)
        );

        InventoryPanel inventoryPanel = new InventoryPanel();
        inventoryPanel.setTableModel(productManager.getUnderstockTableModel());
        inventoryPanel.addInventoryPanelListener(productManager::generatePurchaseOrder);

        CheckoutPanel checkoutPanel = new CheckoutPanel() {
            @Override
            protected Product getProduct(String id) {
                return productManager.getProduct(id);
            }

            @Override
            protected Member getMember(String id) {
                return memberManager.getMember(id);
            }

            @Override
            protected Discount getDiscount(Customer customer) {
                return discountManager.getMaxDiscount(customer);
            }
        };
        checkoutPanel.addCheckoutPanelListener(transactionManager::addTransaction);

        ReportPanel reportPanel = new ReportPanel();
        reportPanel.setCategoryTableModel(categoryManager.getTableModel());
        reportPanel.setProductTableModel(productManager.getTableModel());
        reportPanel.setTransactionTableModel(transactionManager.getTableModel());
        reportPanel.setMemberTableModel(memberManager.getTableModel());

        mainFrame.addFeaturePanel(NEW_CATEGORY, categoryPanel);
        mainFrame.addFeaturePanel(NEW_MEMBER, memberPanel);
        mainFrame.addFeaturePanel(NEW_PRODUCT, productPanel);
        mainFrame.addFeaturePanel(INVENTORY, inventoryPanel);
        mainFrame.addFeaturePanel(DISCOUNT, discountPanel);
        mainFrame.addFeaturePanel(CHECK_OUT, checkoutPanel);
        mainFrame.addFeaturePanel(REPORTS, reportPanel);
        //TODO: Temp panel
        Function<String, FeaturePanel> getTempPanel = s -> new FeaturePanel() {
            {
                add(new JLabel(s));
                add(new JButton("Back") {
                    {
                        addActionListener(e -> backActionPerformed(e));
                    }
                });
            }
        };
        mainFrame.addFeaturePanel(NULL, getTempPanel.apply(NULL));

        mainFrame.resizeAndPack();

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
//			customerManager = new CustomerManager("data/Members.dat");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//        customerManager.addMember("TestID", "TestName");
//        customerManager.addMember("TestID2", "TestName2");
//        customerManager.removeMember("X437F356");
//        ArrayList<Member> members = customerManager.getMembersAsList();
//        for (Member member : members) {
//			System.out.println(member.toString());
//		}
//        System.out.println("New Test");
//        Customer member = customerManager.getCustomer("F42563743156");
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

//        ****************** srishti-test ****************************************
//        TransactionManager transactionManager = null;
//        try {
//            transactionManager = new TransactionManager("data/Transactions.dat");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        ****************** cy-test ****************************************
//         for (Product pro : productManager.getProductList()) {
//             String string = pro.toString();
//             System.out.println(string);
//         }

//        ****************** understock test ****************************************
//        productManager.addProduct(categoryManager.getCategory("CLO"), new Product("t", "t", 1, 10.5, 101, 5, 5));
//        productManager.addProduct(categoryManager.getCategory("CLO"), new Product("z", "z", 1, 10.5, 102, 10, 5));
        
////      ****************** Tran Test on Report Panel ********************************
//        ReportPanel reportPanel = new ReportPanel();
//        reportPanel.setCategoryTableModel(categoryManager.getTableModel());
//        reportPanel.setMemberTableModel(memberManager.getTableModel());
//        mainFrame.addFeaturePanel(REPORTS, reportPanel);
    }
}