/*********************************************
 * @author: Navy Gao 
 * @date:  2016.3.7
 * @Moudle: Store keeper manager class
 * 
 * Change history:
 * 
 * Copyright @ ISS, NUS.  All right reserved. 
 *********************************************/ 

package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

public class StoreKeeperManager {

    private String filename;
    private Map<String, StoreKeeper> storeKeeperMap;

    public StoreKeeperManager(String fileName) throws IOException {
        this.filename = fileName;
        storeKeeperMap = new HashMap<>();

        initData();
    }

    /**
     * Initialize the data from csv file for the store keepers
     *
     */
    private void initData() throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader(filename);
            
            while(reader.readRecord()) {
                Object[] keepers = reader.getValues().toArray();

                String name = keepers[0].toString();
                String password = keepers[1].toString();
                StoreKeeper storeKeeper = new StoreKeeper(name, password);

                storeKeeperMap.put(name, storeKeeper);
            }
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (reader != null) reader.close();
        }
    }

    //returns true if login is successful 
    public boolean login(String name, String password) {
        StoreKeeper storeKeeper = storeKeeperMap.get(name);

        return storeKeeper != null && storeKeeper.getPassword().equals(password);
    }
}
