package sg.edu.nus.iss.se24_2ft.unit1.ca;

import sg.edu.nus.iss.se24_2ft.unit1.ca.gui.MainFrame;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVWriter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.io.UncheckedIOException;


/**
 * Created by yangzai on 26/2/16.
 */
public class StoreApplication {
    public static void main (String args[]) throws UncheckedIOException {
        MainFrame mainFrame = new MainFrame();
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


    }
}
