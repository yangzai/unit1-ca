package sg.edu.nus.iss.se24_2ft.unit1.ca.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

public class ProductManager {
    private String filename;
    private List<Product> productList;
    private Map<String, Product> productMap;
    private List<Product> understockProductList;
    private Map<String, Integer> maxSubIdMap;

    public ProductManager(String filename) throws IOException {
        this.filename = filename;
        productList = new ArrayList<>();
        productMap = new HashMap<>();
        understockProductList = new ArrayList<>();

        maxSubIdMap = new HashMap<>();
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
                if (quantity <= threshold) understockProductList.add(product);
            }
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (reader != null) reader.close();
        }
    }

    public Product getProduct(String id) { return productMap.get(id); }

    public List<Product> getProductList() { return productList; }

    public void addProduct(String categoryId, Product product) {
        //TODO: verify category exist
        int subId = maxSubIdMap.getOrDefault(categoryId, 0) + 1;
        maxSubIdMap.put(categoryId, subId);
        String id = categoryId + '/' + subId;
        product.setId(id);

        productMap.put(id, product);
        productList.add(product);
        if (product.getQuantity() <= product.getThreshold())
            understockProductList.add(product);
    }
}