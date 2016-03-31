package sg.edu.nus.iss.se24_2ft.unit1.ca;

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
    private Map<String, List<Vendor>> vendorMap;

    public VendorManager(String directory) {
        this.directory = directory;
        vendorMap = new HashMap<>();

        initData();
    }

    private void initData() {
        try (Stream<Path> pathStream = Files.list(Paths.get(directory))) {
            pathStream.filter(p -> p.getFileName() //filter vendor files
                    .toString()
                    .matches("Vendors[A-Z]{3}.dat")
            ).forEach(p -> { //for each vendor file, read vendor list and insert to map
                String categoryId = p.getFileName().toString().substring(7, 10);

                try (Stream<String> lineStream = Files.lines(p)) {

                    List<Vendor> vendorList = lineStream.map(Util::splitCsv)
                            .map(a -> new Vendor(categoryId, a[0], a[1]))
                            .collect(Collectors.toList());

                    vendorMap.put(categoryId, vendorList);
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
        return vendorMap.get(categoryId);
    }
}