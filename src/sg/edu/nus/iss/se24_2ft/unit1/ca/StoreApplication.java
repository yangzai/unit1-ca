package sg.edu.nus.iss.se24_2ft.unit1.ca;

import sg.edu.nus.iss.se24_2ft.unit1.ca.gui.MainFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by yangzai on 26/2/16.
 */
public class StoreApplication {
    public static void main (String args[]) {
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

    }
}
