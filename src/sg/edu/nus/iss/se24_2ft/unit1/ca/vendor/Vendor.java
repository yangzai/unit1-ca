package sg.edu.nus.iss.se24_2ft.unit1.ca.vendor;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzai on 27/2/16.
 */
public class Vendor {
    private String name, description;
    private List<Category> categoryList;

    public Vendor(String name, String description) {
        this.name = (name == null ? null : name.trim());
        this.description = (description == null ? null : description.trim());
        categoryList = new ArrayList<>();
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public List<Category> getCategoryList() { return new ArrayList<>(categoryList); }

    //setter
    public void addCategory(Category category) { categoryList.add(category); }
}