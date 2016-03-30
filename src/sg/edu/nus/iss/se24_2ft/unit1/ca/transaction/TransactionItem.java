package sg.edu.nus.iss.se24_2ft.unit1.ca.transaction;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.PublicCustomer;
import sg.edu.nus.iss.se24_2ft.unit1.ca.product.Product;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TransactionItem {
    //TODO: this might be a package/inner class
    private Product product;
    private int quantity;
    private Transaction parent;
    /**
     * created by Srishti
     */

    public TransactionItem(Product product, int quantity) {
        parent = null;

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }

    public int getQuantity() { return quantity; }

    public Transaction getParent() { return parent; }

    //setter
    /*package*/ void setParent (Transaction parent) { this.parent = parent; }

    /*package*/ void addQuantity(int quantity) { this.quantity += quantity; }

    @Override
    public String toString() {
        Customer customer = parent.getCustomer();
        if (customer == null) customer = PublicCustomer.getInstance();

        Integer parentId = parent != null ? parent.getId() : null;
        String productId = product != null ? product.getId() : null;

        return Arrays.asList(parentId, productId, customer.getId(),
                quantity, Utils.formatDateOrDefault(parent.getDate(), null)).stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}