package sg.edu.nus.iss.se24_2ft.unit1.ca.category;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by yangzai on 27/2/16.
 */
public class Category {
    private String id, requestedId, name;

    public Category(String requestedId, String name) {
        id = null;
        this.requestedId = requestedId.trim();
        this.name = name.trim();
    }

    public String getId() { return id; }
    public String getRequestedId() { return requestedId; }
    public String getName() { return name; }

    //setters
    /*package*/ void setId() { id = requestedId; }

    @Override
    public String toString() {
        return Arrays.asList(id, name).stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}