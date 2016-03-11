package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import sg.edu.nus.iss.se24_2ft.unit1.ca.gui.MainFrame;

/**
 * Created by yangzai on 26/2/16.
 */
public class StoreApplication {
    public static void main(String args[]) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                // mainFrame.dispose();
                System.exit(0);
            }
        });

        Store store = new Store(mainFrame);

        mainFrame.setVisible(true);

        // StoreKeeperManager storeKeeperManager = null;
        // try {
        // storeKeeperManager = new StoreKeeperManager("data/Storekeepers.dat");
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        //
        // System.out.println(storeKeeperManager.login("xxx", "fail"));
        // System.out.println(storeKeeperManager.login("Stacy", "Dean56s"));
        // System.out.println(storeKeeperManager.login("Johny", "A12ysd45"));
        // System.out.println(storeKeeperManager.login("Johny", "fail"));

        // try {
        // //TODO: Test resource path
        // PrintWriter writer = new PrintWriter("level1/test.txt");
        // writer.println("test");
        // writer.close();
        //
        // BufferedReader reader = new BufferedReader(new
        // FileReader("test.txt"));
        //
        // System.out.println(reader.readLine());
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // ProductManager productManager = null;
        // try {
        // productManager = new ProductManager("data/Products.dat");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        //
        // for (Product pro : productManager.getProductList()) {
        // String string = pro.toString();
        // System.out.println(string);
        // }
        //
        // productManager.getMaxProductID();
        //
        // String str = productManager.getThreshold().toString();
        // System.out.println(str);
        // System.out.println(productManager.getPrice("CLO/1"));
        //
        // productManager.getListProductThreshold();
        // for (Product pro : productManager.getListProductThreshold()) {
        // String string = pro.toString();
        // System.out.println(string);
        // }

    }
}
