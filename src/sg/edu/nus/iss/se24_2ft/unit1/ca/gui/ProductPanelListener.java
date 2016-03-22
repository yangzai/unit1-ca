package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;

import java.util.List;

/**
 * Created by yangzai on 21/3/16.
 */
public interface ProductPanelListener {
    void addProductRequested(String categoryId, Product product);
}
