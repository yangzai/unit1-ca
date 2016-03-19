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

    public String getId() { return id; }
    public String getRequestedId() { return requestedId; }
    public String getName() { return name; }

    //protected setters
    protected void setId() { id = requestedId; }
}