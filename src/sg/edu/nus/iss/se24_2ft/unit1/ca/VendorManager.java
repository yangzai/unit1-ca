package sg.edu.nus.iss.se24_2ft.unit1.ca;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by yangzai on 9/3/16.
 */
public class VendorManager {
    private String directory;
    private Map<String, List<Vendor>> vendorMap;

    public VendorManager(String directory) throws IOException {
        this.directory = directory;
        vendorMap = new HashMap<>();

        initData();
    }

    private void initData() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(directory))) {
            stream
                    //get a list of filename in directory
                    .map(p -> p.getFileName().toString())
                    //filter vendor files
                    .filter(s -> s.matches("Vendors[A-Z][A-Z][A-Z].dat"))
                    //for each vendor file, read vendor list and insert to map
                    .forEach(s -> {
                        String categoryId = s.substring(7, 10);
                        CSVReader reader = null;
                        try {
                            reader = new CSVReader(
                                    Paths.get(directory, s).toString(), ',', Charset.forName("UTF-8")
                            );
                            List<Vendor> vendorList = new ArrayList<>();
                            while (reader.readRecord()) {
                                Object[] keepers = reader.getValues().toArray();

                                String name = keepers[0].toString();
                                String description = keepers[1].toString();
                                Vendor vendor = new Vendor(categoryId, name, description);

                                vendorList.add(vendor);
                            }
                            vendorMap.put(categoryId, vendorList);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        } finally {
                            if (reader != null) reader.close();
                        }
                    });
        } catch (IOException e) {
            throw e;
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }

    public List<Vendor> getVendorListByCategoryId(String categoryId) {
        return vendorMap.get(categoryId);
    }
}