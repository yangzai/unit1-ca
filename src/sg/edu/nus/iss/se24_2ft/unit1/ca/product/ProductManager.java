package sg.edu.nus.iss.se24_2ft.unit1.ca.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ProductManager {
    private static final String[] COLUMN_NAMES = {"ID","Name","Description","Quantity Avl.","Price",
            "Bar Code","Reorder Quantity","Order Quantity"};
    private String filename;
    private List<Product> productList;
    private Map<String, Product> productMap;
    private List<Product> understockProductList;
    private Map<String, Integer> maxSubIdMap;

    private AbstractTableModel understockTableModel;

    public ProductManager(String filename) throws IOException {
        this.filename = filename;
        productList = new ArrayList<>();
        productMap = new HashMap<>();
        understockProductList = new ArrayList<>();
        maxSubIdMap = new HashMap<>();
        understockTableModel = new AbstractTableModel() {
            @Override
            public String getColumnName(int column) {
                return COLUMN_NAMES[column];
            }

            @Override
            public int getRowCount() {
                return understockProductList.size();
            }

            @Override
            public int getColumnCount() {
                return COLUMN_NAMES.length;
            }

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

        initData();
    }

    public void initData() throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader(filename);

            while (reader.readRecord()) {
                ArrayList<String> record = reader.getValues();

                String id = record.get(0), name = record.get(1),
                        description = record.get(2);
                int quantity = Integer.parseInt(record.get(3)),
                        barCode = Integer.parseInt(record.get(5)),
                        threshold = Integer.parseInt(record.get(6)),
                        orderQuantity = Integer.parseInt(record.get(7));
                double price = Double.parseDouble(record.get(4));

                Product product = new Product(name, description,
                        quantity, price, barCode, threshold, orderQuantity);
                product.setId(id);

                //TODO: case where category doesn't exist
                String[] idArray = id.split("/");
                String categoryId = idArray[0];
                int current = Integer.parseInt(idArray[1]);
                int max = maxSubIdMap.getOrDefault(idArray[0], 0);
                if (max < current) maxSubIdMap.put(categoryId, current);

                productMap.put(id, product);
                productList.add(product);
                if (product.isUnderstock()) understockProductList.add(product);
            }
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (reader != null) reader.close();
        }
    }

    public Product getProduct(String id) { return productMap.get(id); }

    public List<Product> getProductList() { return productList; }

    public boolean addProduct(Category category, Product product) {
        if (category == null || product == null) return false;

        String categoryId = category.getId();
        if (categoryId == null) return false;

        int subId = maxSubIdMap.getOrDefault(categoryId, 0) + 1;
        maxSubIdMap.put(categoryId, subId);
        String id = categoryId + '/' + subId;
        product.setId(id);

        productMap.put(id, product);
        productList.add(product);
        if (product.isUnderstock()) {
            understockProductList.add(product);
            int rowIndex = understockProductList.size() - 1;
            understockTableModel.fireTableRowsInserted(rowIndex, rowIndex);
        }

        return true;
    }

    public void generatePurchaseOrder(List<Integer> understockIndexList) {
        //No change to table, short circuit
        if (understockIndexList == null || understockIndexList.size() == 0) return;

        //Assume immediate stock arrival
        understockIndexList.forEach(i -> understockProductList.get(i).restock());
        understockProductList = productList.stream()
                .filter(p -> p.isUnderstock())
                .collect(Collectors.toList());
        understockTableModel.fireTableDataChanged();
    }

    public TableModel getUnderstockTableModel() { return understockTableModel; }

}