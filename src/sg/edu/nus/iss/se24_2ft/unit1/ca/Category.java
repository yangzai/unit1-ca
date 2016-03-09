package sg.edu.nus.iss.se24_2ft.unit1.ca;

/**
 * Created by yangzai on 27/2/16.
 */
public class Category {
    private String id, name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
}