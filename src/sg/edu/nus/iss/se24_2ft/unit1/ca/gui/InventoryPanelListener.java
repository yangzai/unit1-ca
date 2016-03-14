package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import java.util.List;

/**
 * Created by yangzai on 14/3/16.
 */
public interface InventoryPanelListener {
    void purchaseOrderRequested(List<Integer> understockIndexList);
}