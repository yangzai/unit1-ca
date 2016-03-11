package sg.edu.nus.iss.se24_2ft.unit1.ca;

/**
 * Created by yangzai on 26/2/16.
 */
public class Product {
    private String id, name, description;
    private int quantity, barCode, treshold, orderQuantity;
    // BigDecimal price;
    private double price;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public int getTreshold() {
        return treshold;
    }

    public void setTreshold(int treshold) {
        this.treshold = treshold;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return id + "," + name + "," + description + "," + quantity + "," + price + "," + barCode + "," + treshold + ","
                + orderQuantity;
    }
}
