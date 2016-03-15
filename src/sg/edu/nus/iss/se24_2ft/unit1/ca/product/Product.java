package sg.edu.nus.iss.se24_2ft.unit1.ca.product;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by yangzai on 26/2/16.
 */
public class Product {
    private String id, name, description;
    private int quantity, barCode, threshold, orderQuantity;
    // BigDecimal price;
    private double price;

    public Product(String name, String description, int quantity,
                   double price, int barCode, int threshold, int orderQuantity) {
        id = null;

        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.barCode = barCode;
        this.threshold = threshold;
        this.orderQuantity = orderQuantity;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public int getQuantity() { return quantity; }

    public int getBarCode() { return barCode; }

    public int getThreshold() { return threshold; }

    public int getOrderQuantity() { return orderQuantity; }

    public double getPrice() { return price; }

    public boolean isUnderstock() { return quantity <= threshold; }

    @Override
    public String toString() {
        return Arrays.asList(id, name, description, quantity, price,
                barCode, threshold, orderQuantity).stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    //protected setters
    protected void setId(String id) { this.id = id; }

    protected boolean restock() {
        if (!isUnderstock()) return false;
        quantity += orderQuantity;
        return true;
    }
}