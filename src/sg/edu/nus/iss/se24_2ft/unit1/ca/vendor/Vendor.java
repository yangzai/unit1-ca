package sg.edu.nus.iss.se24_2ft.unit1.ca.vendor;

/**
 * Created by yangzai on 27/2/16.
 */
public class Vendor {
    private String categoryId, name, description;

    public Vendor(String categoryId, String name, String description) {
        this.categoryId = (categoryId == null ? null : categoryId.trim());
        this.name = (name == null ? null : name.trim());
        this.description = (description == null ? null : description.trim());
    }

    public String getCategoryId() { return categoryId; }

    public String getName() { return name; }

    public String getDescription() { return description; }
}