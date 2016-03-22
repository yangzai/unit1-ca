package sg.edu.nus.iss.se24_2ft.unit1.ca.category;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

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
        tableModel = null;

        this.filename = filename;
        categoryMap = new HashMap<>();
        categoryList = new ArrayList<>();

        initData();
    }

    private void initData() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.map(Utils::splitCsv).forEach(a -> {
                String id = a[0], name = a[1];
                Category category = new Category(id, name);
                category.setId();

                categoryMap.put(id, category);
                categoryList.add(category);
            });
        }
    }

    public Category getCategory(String id) {
        return categoryMap.get(id.toUpperCase());
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
        if (tableModel != null)
            tableModel.fireTableRowsInserted(insertedRowIndex, insertedRowIndex);
        return true;
        //TODO: persist immediately?
    }

    public TableModel getTableModel() {
        if (tableModel != null) return tableModel;

        return tableModel = new AbstractTableModel() {
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
    }
}
