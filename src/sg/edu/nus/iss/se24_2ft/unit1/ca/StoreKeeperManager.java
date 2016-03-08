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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

public class StoreKeeperManager {

    private String filename;
    private List<StoreKeeper> storeKeeperList;

    public StoreKeeperManager(String fileName) throws IOException {
        this.filename = fileName;
        storeKeeperList = new ArrayList<>();

        initData();
    }

    /**
     * Initialize the data from csv file for the store keepers
     *
     */
    private void initData() throws IOException {
        if (filename == null) return;

        CSVReader reader = null;
        try
        {
            reader = new CSVReader(filename);
            
            while(reader.readRecord()){
                Object[] keepers = reader.getValues().toArray();

                StoreKeeper storeKeeper = new StoreKeeper(keepers[0].toString(), keepers[1].toString());

                storeKeeperList.add(storeKeeper);
            }
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (reader != null) reader.close();
        }
    }

    public List<StoreKeeper> getStoreKeeperList() {
        return storeKeeperList;
    }
}
