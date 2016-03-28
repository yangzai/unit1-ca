package sg.edu.nus.iss.se24_2ft.unit1.ca;

/**
 * Created by yangzai on 27/2/16.
 */
public class Vendor {
    private String categoryId, name, description;

    public Vendor(String categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean equals(Object vendor) {
        if (vendor instanceof Vendor) {
            Vendor v = (Vendor) vendor;
            if (this.getCategoryId().equals(v.getCategoryId()) && this.getName().equals(v.getName()))
                if (this.getDescription() == null) {
                    if (v.getDescription() == null)
                        return true;
                    else
                        return false;
                } else if (v.getDescription() != null)
                    if (this.getDescription().equals(v.getDescription()))
                        return true;
        }
        return false;
    }
}