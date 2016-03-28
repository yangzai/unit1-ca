package sg.edu.nus.iss.se24_2ft.unit1.ca.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member.Member;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Utils;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ProductManager {
    private final String[] COLUMN_NAMES = {"ID", "Name", "Description", "Quantity Avl.", "Price",
            "Bar Code", "Reorder Quantity", "Order Quantity"};
    private String filename;
    private CategoryManager categoryManager;
    private List<Product> productList;
    private Map<String, Product> productMap;
    private List<Product> understockProductList;
    private Map<String, Integer> maxSubIdMap;

    private AbstractTableModel tableModel;
    private AbstractTableModel understockTableModel;

    public ProductManager(String filename, CategoryManager categoryManager) throws IOException {
        tableModel = null;
        understockTableModel = null;

        this.filename = filename;
        this.categoryManager = categoryManager;

        productList = new ArrayList<>();
        productMap = new HashMap<>();
        understockProductList = new ArrayList<>();
        maxSubIdMap = new HashMap<>();

        initData();
    }

    public void initData() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.map(Utils::splitCsv).forEach(a -> {
                String id = a[0], name = a[1], description = a[2];
                double price = Utils.parseDoubleOrDefault(a[4], 0);
                int quantity = Utils.parseIntOrDefault(a[3], 0),
                        barCode = Utils.parseIntOrDefault(a[5], 0),
                        threshold = Utils.parseIntOrDefault(a[6], 0),
                        orderQuantity = Utils.parseIntOrDefault(a[7], 0);

                Product product = new Product(name, description, quantity,
                        price, barCode, threshold, orderQuantity);

                //TODO: try filter
                //if id already exist, skip
                if (productMap.containsKey(id)) return;

                String[] idArray = id.split("/");
                String categoryId = idArray[0];
                Category category = categoryManager.getCategory(categoryId);

                //if category does not exist, skip
                if (category == null) return;

                int current = Integer.parseInt(idArray[1]);
                int max = maxSubIdMap.getOrDefault(idArray[0], 0);
                if (max < current) maxSubIdMap.put(categoryId, current);

                addProduct(category, product, id);
            });
        }
    }

    public Product getProduct(String id) { return productMap.get(id); }

    public List<Product> getProductList() { return productList; }

    public void addProduct(Category category, Product product) {
    	if (category == null) {
			throw new IllegalArgumentException("Category is not valid");
		} else if (product == null){
			throw new IllegalArgumentException("Product is not valid");
		}

        String categoryId = category.getId();
        if (categoryId == null) {
			throw new IllegalArgumentException("Category ID is empty. Please input again");
		}

        int subId = maxSubIdMap.getOrDefault(categoryId, 0) + 1;
        maxSubIdMap.put(categoryId, subId);
        String id = categoryId + '/' + subId;

        addProduct(category, product, id);
    }

    private void addProduct(Category category, Product product, String id) {
        product.setId(id);
        product.setCategory(category);
        productMap.put(id, product);
        productList.add(product);

        int rowIndex = productList.size() - 1;

        if (tableModel != null)
            tableModel.fireTableRowsInserted(rowIndex, rowIndex);

        if (!product.isUnderstock()) return;

        understockProductList.add(product);   
        rowIndex = understockProductList.size() - 1;

        if (understockTableModel != null)
            understockTableModel.fireTableRowsInserted(rowIndex, rowIndex);
    }
    
    public boolean deductQuantity(String id, int quantity) {
        Product product = productMap.get(id);
        if (product == null) return false;
        else return deductQuantity(product, quantity);
    }
    
    public boolean deductQuantity(Product product, int quantity){
    	return product.deduct(quantity);
    }

    public void generatePurchaseOrder(List<Integer> understockIndexList) {
        //No change to table, short circuit
        if (understockIndexList == null || understockIndexList.size() == 0) return;

        //Assume immediate stock arrival
        understockIndexList.forEach(i -> understockProductList.get(i).restock());
        understockProductList = productList.stream()
                .filter(Product::isUnderstock)
                .collect(Collectors.toList());

        if (understockTableModel != null)
            understockTableModel.fireTableDataChanged();

        if (tableModel != null)
            tableModel.fireTableDataChanged();
    }

    public TableModel getTableModel() {
        if (tableModel != null) return tableModel;

        return tableModel = new AbstractTableModel() {
            @Override
            public String getColumnName(int column) { return COLUMN_NAMES[column]; }

            @Override
            public int getRowCount() { return productList.size(); }

            @Override
            public int getColumnCount() { return COLUMN_NAMES.length; }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Product product = productList.get(rowIndex);
                switch (columnIndex) {
                    case 0: return product.getId();
                    case 1: return product.getName();
                    case 2: return product.getDescription();
                    case 3: return product.getQuantity();
                    case 4: return product.getPrice();
                    case 5: return product.getBarCode();
                    case 6: return product.getThreshold();
                    case 7: return product.getOrderQuantity();
                    default: return null;
                }
            }
        };
    }

    public TableModel getUnderstockTableModel() {
        if (understockTableModel != null) return understockTableModel;

        return understockTableModel = new AbstractTableModel() {
            @Override
            public String getColumnName(int column) { return COLUMN_NAMES[column]; }

            @Override
            public int getRowCount() { return understockProductList.size(); }

            @Override
            public int getColumnCount() { return COLUMN_NAMES.length; }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Product product = understockProductList.get(rowIndex);
                switch (columnIndex) {
                    case 0: return product.getId();
                    case 1: return product.getName();
                    case 2: return product.getDescription();
                    case 3: return product.getQuantity();
                    case 4: return product.getPrice();
                    case 5: return product.getBarCode();
                    case 6: return product.getThreshold();
                    case 7: return product.getOrderQuantity();
                    default: return null;
                }
            }
        };
    }
}