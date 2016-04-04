package sg.edu.nus.iss.se24_2ft.unit1.ca.vendor;

import sg.edu.nus.iss.se24_2ft.unit1.ca.category.Category;
import sg.edu.nus.iss.se24_2ft.unit1.ca.category.CategoryManager;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yangzai on 9/3/16.
 */
public class VendorManager {
    private String directory;
    private CategoryManager categoryManager;
    private Map<String, List<Vendor>> vendorListMap; //cat id -> vendor list
    private Map<String, Vendor> vendorMap; //vendor name -> vendor

    public VendorManager(String directory, CategoryManager categoryManager) {
        this.directory = directory;
        this.categoryManager = categoryManager;
        vendorListMap = new HashMap<>();
        vendorMap = new HashMap<>();

        load();
    }

    private void load() {
        try (Stream<Path> pathStream = Files.list(Paths.get(directory))) {
            pathStream.filter(p -> p.getFileName() //filter vendor files
                    .toString()
                    .matches("Vendors[A-Z]{3}.dat")
            ).forEach(p -> { //for each vendor file, read vendor list and insert to map
                String categoryId = p.getFileName().toString().substring(7, 10);
                Category category = categoryManager.getCategory(categoryId);

                //if category does not exist, skip
                if (category == null) return;

                try (Stream<String> lineStream = Files.lines(p)) {
                    List<Vendor> vendorList = lineStream.map(Util::splitCsv)
                            .map(a -> {
                                String name = a[0];
                                Vendor vendor = vendorMap.get(name);
                                if (vendor == null) {
                                    vendor = new Vendor(name, a[1]);
                                    vendorMap.put(name, vendor);
                                }
                                //else return existing vendor
                                //assume name is unique and description is same
                                vendor.addCategory(category);
                                return vendor;
                            })
                            .collect(Collectors.toList());

                    vendorListMap.put(categoryId, vendorList);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (UncheckedIOException e) {
            e.getCause().printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Vendor> getVendorListByCategoryId(String categoryId) {
        return vendorListMap.get(categoryId);
    }
}