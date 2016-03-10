package sg.edu.nus.iss.se24_2ft.unit1.ca;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangzai on 28/2/16.
 */
public class CategoryManager {
    private String filename;
    private Map<String, Category> categoryMap;

    public CategoryManager(String filename) throws IOException {
        this.filename = filename;
        categoryMap = new HashMap<>();

        initData();
    }

    private void initData() throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader(filename);

            while(reader.readRecord()) {
                Object[] keepers = reader.getValues().toArray();

                String id = keepers[0].toString();
                String name = keepers[1].toString();
                Category category = new Category(id, name);

                categoryMap.put(id, category);
            }
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (reader != null) reader.close();
        }
    }

    public Category getCategory(String id) {
        return categoryMap.get(id);
    }

    public List<Category> getCategoryList() {
        return new ArrayList<>(categoryMap.values());
    }

    public boolean addCategory(Category category) {
        // Needs to check if already exist as per requirement
        //TODO: return false or throw error?

        //null implies success, not null implies collision
        return categoryMap.putIfAbsent(category.getId(), category) == null;
        //TODO: persist immediately?
    }
}
