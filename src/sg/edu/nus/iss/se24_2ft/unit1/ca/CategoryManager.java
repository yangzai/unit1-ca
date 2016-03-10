package sg.edu.nus.iss.se24_2ft.unit1.ca;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.*;

/**
 * Created by yangzai on 28/2/16.
 */
public class CategoryManager {
    private static final String[] COLUMN_NAMES = {"ID", "Name"};
    private String filename;
    private Map<String, Category> categoryMap;
    private List<Category> categoryList;

    private TableModel tableModel;

    public CategoryManager(String filename) throws IOException {
        this.filename = filename;
        categoryMap = new HashMap<>();
        categoryList = new ArrayList<>();
        tableModel = new AbstractTableModel() {
            @Override
            public String getColumnName(int column) {
                return COLUMN_NAMES[column];
            }

            @Override
            public int getRowCount() {
                return categoryList.size();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Category category = categoryList.get(rowIndex);
                switch (columnIndex) {
                    case 0: return category.getId();
                    case 1: return category.getName();
                    default: return null;
                }
            }
        };

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
                categoryList.add(category);
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

//        //null implies success, not null implies collision
//        return categoryMap.putIfAbsent(category.getId(), category) == null;

        String categoryId = category.getId();
        if (categoryMap.get(categoryId) != null) return false;

        categoryMap.put(categoryId, category);
        categoryList.add(category);

        return true;
        //TODO: persist immediately?
    }

    public TableModel getTableModel() { return tableModel; }
}
