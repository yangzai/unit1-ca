package sg.edu.nus.iss.se24_2ft.unit1.ca.gui.checkout;

import sg.edu.nus.iss.se24_2ft.unit1.ca.Transaction;

/**
 * Created by yangzai on 27/3/16.
 */
public interface CheckoutPanelListener {
    void addTransactionRequested(Transaction transaction);
}
