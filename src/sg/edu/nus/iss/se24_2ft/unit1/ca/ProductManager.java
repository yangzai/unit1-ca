package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

public class ProductManager {
    private String filename;
    private List<Product> productList = null;
    private Product productByID = new Product();
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
                Product product = new Product();
                product.setId(params.get(0));
                product.setName(params.get(1));
                product.setDescription(params.get(2));
                product.setQuantity(Integer.parseInt(params.get(3)));
                product.setPrice(Double.parseDouble(params.get(4)));
                product.setBarCode(Integer.parseInt(params.get(5)));
                product.setTreshold(Integer.parseInt(params.get(6)));
                product.setOrderQuantity(Integer.parseInt(params.get(7)));
                productList.add(product);
            }
        }
    }

    @SuppressWarnings("null")
    public List<Product> getListProductThreshold() {
        List<Product> listProductThreshold = null;
        for (Product pro : productList) {
            if (pro.getQuantity() < pro.getTreshold()) {
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
        productByID = this.getProductByID(id);
        return productByID.getPrice();
    }

    public Product getProductByID(String id) {
        for (Product pro : productList) {
            if (pro.getId().equals(id)) {
                productByID = pro;
            }
        }
        return productByID;
    }

    public boolean isBelowThreshold(String id) {
        productByID = this.getProductByID(id);
        if (productByID.getQuantity() < productByID.getTreshold()) {
            return true;
        } else {
            return false;
        }
    }

}
