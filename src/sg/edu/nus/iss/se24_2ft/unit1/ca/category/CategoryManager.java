package sg.edu.nus.iss.se24_2ft.unit1.ca.category;

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

    private AbstractTableModel tableModel;

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
                return COLUMN_NAMES.length;
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
                List<String> record = reader.getValues();

                String id = record.get(0);
                String name = record.get(1);
                Category category = new Category(id, name);
                category.setId();

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
        return categoryList;
    }

    public boolean addCategory(Category category) {
        // Needs to check if already exist as per requirement
        //TODO: return false or throw error?

//        //null implies success, not null implies collision
//        return categoryMap.putIfAbsent(category.getId(), category) == null;

        String categoryId = category.getRequestedId();
        if (categoryMap.get(categoryId) != null) return false;

        category.setId();
        categoryMap.put(categoryId, category);
        categoryList.add(category);

        int insertedRowIndex = categoryList.size() - 1;
        tableModel.fireTableRowsInserted(insertedRowIndex, insertedRowIndex);
        return true;
        //TODO: persist immediately?
    }

    public TableModel getTableModel() { return tableModel; }
}
