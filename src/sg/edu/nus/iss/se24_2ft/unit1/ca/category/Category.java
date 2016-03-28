package sg.edu.nus.iss.se24_2ft.unit1.ca.category;

/**
 * Created by yangzai on 27/2/16.
 */
public class Category {
    private String id, requestedId, name;

    public Category(String requestedId, String name) {
        id = null;
        this.requestedId = requestedId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getRequestedId() {
        return requestedId;
    }

    public String getName() {
        return name;
    }

    // setters
    /* package */ void setId() {
        id = requestedId;
    }

    // override the equals method for testing
    public boolean equals(Object category) {
        if (category instanceof Category) {
            Category c = (Category) category;
            if (this.getRequestedId().equals(c.getRequestedId())) {
                if (this.getName() == null)
                    if (c.getName() == null)
                        return true;
                    else
                        return false;
                else if (c.getName() != null)
                    if (this.getName().equals(c.getName()))
                        return true;
            }
        }
        return false;
    }
}