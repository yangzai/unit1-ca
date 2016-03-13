package sg.edu.nus.iss.se24_2ft.unit1.ca.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

public class ProductManager {
    private String filename;
    private List<Product> productList = null;
    private HashMap<String, Integer> threshold = new HashMap<String, Integer>();

    public List<Product> getProductList() {
        return productList;
    }

    public HashMap<String, Integer> getThreshold() {
        return threshold;
    }

    public void setThreshold(HashMap<String, Integer> threshold) {
        this.threshold = threshold;
    }

    public ProductManager(String filename) throws Exception {
        this.filename = filename;
        productList = new ArrayList<Product>();

        initData();
    }

    public void initData() {
        List<List<String>> _list = new ArrayList();
        CSVReader reader = null;

        try {
            reader = new CSVReader(filename);

            while (reader.readRecord()) {
                ArrayList<String> productStrList = reader.getValues();
                _list.add(productStrList);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }

            for (List<String> params : _list) {
                Product product = new Product(params.get(1), params.get(2), Integer.parseInt(params.get(3)),
                        Double.parseDouble(params.get(4)), Integer.parseInt(params.get(5)),
                        Integer.parseInt(params.get(6)), Integer.parseInt(params.get(7)));
                product.setId(params.get(0));

                productList.add(product);
            }
        }
    }

    @SuppressWarnings("null")
    public List<Product> getListProductThreshold() {
        List<Product> listProductThreshold = new ArrayList<>();
        for (Product pro : productList) {
            if (pro.getQuantity() < pro.getThreshold()) {
                listProductThreshold.add(pro);
            }
        }
        return listProductThreshold;
    }

    public void getMaxProductID() {
        for (Product pro : productList) {
            String record = pro.toString();
            String category = record.substring(0, record.indexOf("/"));
            int number = Integer.parseInt(record.substring(record.indexOf("/") + 1, record.indexOf(",")));
            if (threshold.get(category) != null) {
                if (threshold.get(category) < number) {
                    threshold.put(category, number);
                }
            } else {
                threshold.put(category, number);
            }
        }
    }

    public double getPrice(String id) {
        return getProductByID(id).getPrice();
    }

    public Product getProductByID(String id) {
        for (Product pro : productList)
            if (pro.getId().equals(id)) return pro;
        return null;
    }

    public boolean isBelowThreshold(String id) {
        Product product = getProductByID(id);
        return product.getQuantity() < product.getThreshold();
    }
}
