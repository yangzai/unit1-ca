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
import sg.edu.nus.iss.se24_2ft.unit1.ca.storekeeper.StoreKeeperManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.transaction.TransactionManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.vendor.VendorManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by yangzai on 26/2/16.
 */
public class StoreApplication {
    private static final String
            CHECK_OUT = "Checkout", DISCOUNT = "Discount",
            INVENTORY = "Inventory", NEW_MEMBER = "New Member",
            NEW_PRODUCT = "New Product", NEW_CATEGORY = "New Category",
            REPORTS = "Reports";

    public static void main (String args[]) {
        StoreKeeperManager storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");
        LoginPanel loginPanel = new LoginPanel() {
            @Override
            protected boolean login(String username, String password) {
                return storeKeeperManager.login(username, password);
            }
        };
        loginPanel.setVisible(true);

        if (!loginPanel.isSuccess()) System.exit(0);

        CategoryManager categoryManager = new CategoryManager("data/Category.dat");
        VendorManager vendorManager = new VendorManager("data");
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
        memberPanel.addMemberPaneListener(memberManager::addMember);

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

        mainFrame.addFeaturePanel(CHECK_OUT, checkoutPanel);
        mainFrame.addFeaturePanel(NEW_CATEGORY, categoryPanel);
        mainFrame.addFeaturePanel(NEW_MEMBER, memberPanel);
        mainFrame.addFeaturePanel(NEW_PRODUCT, productPanel);
        mainFrame.addFeaturePanel(INVENTORY, inventoryPanel);
        mainFrame.addFeaturePanel(DISCOUNT, discountPanel);
        mainFrame.addFeaturePanel(REPORTS, reportPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}