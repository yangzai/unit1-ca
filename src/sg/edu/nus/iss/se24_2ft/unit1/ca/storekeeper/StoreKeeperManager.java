/*********************************************
 * @author: Navy Gao 
 * @date:  2016.3.7
 * @Moudle: Store keeper manager class
 * 
 * Change history:
 * 
 * Copyright @ ISS, NUS.  All right reserved. 
 *********************************************/ 

package sg.edu.nus.iss.se24_2ft.unit1.ca.storekeeper;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class StoreKeeperManager {

    private String filename;
    private Map<String, StoreKeeper> storeKeeperMap;

    public StoreKeeperManager(String fileName) {
        this.filename = fileName;
        storeKeeperMap = new HashMap<>();

        load();
    }

    /**
     * Initialize the data from csv file for the store keepers
     *
     */
    private void load() {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.map(Util::splitCsv).forEach(a -> {
                String name = a[0], password = a[1];
                StoreKeeper storeKeeper = new StoreKeeper(name, password);
                storeKeeperMap.put(name, storeKeeper);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //returns true if login is successful
    public boolean login(String name, String password) {
        StoreKeeper storeKeeper = storeKeeperMap.get(name);

        return storeKeeper != null && storeKeeper.getPassword().equals(password);
    }
}
