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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

public class StoreKeeperManager {
	
	private ArrayList<StoreKeeper> storeKeeperList;
	private static StoreKeeperManager storeKeeperManager = new StoreKeeperManager();
	
	/**
	 * Define singleton pattern to unify the store keeper manager instance
	 *
	 */
	public static StoreKeeperManager getInstance()
	{
		return storeKeeperManager;
	}
	
	private StoreKeeperManager()
	{
		storeKeeperList = new ArrayList<>();
	}
	
	/**
	 * Initialize the data from csv file for the store keepers
	 * 
	 * @param fileName
	 *            The path to the file for the data loading.
	 */
	public void initData(String fileName) throws IOException
	{
		if (null == fileName)
			return;
		
		CSVReader reader = null;
		//ArrayList<ArrayList<String>> csvList;
		
		try
		{
			//csvList = new ArrayList<ArrayList<String>>();
			
            reader = new CSVReader(fileName,',',Charset.forName("SJIS")); 
            
            while(reader.readRecord()){
                //csvList.add(reader.getValues());  
            	Object[] keepers = reader.getValues().toArray();

            	StoreKeeper storeKeeper = new StoreKeeper(keepers[0].toString(), keepers[1].toString());

            	storeKeeperList.add(storeKeeper);
            }
		}
		catch (FileNotFoundException fnfe) {
			throw fnfe;
		}
		catch (IOException ioe) {
			throw ioe;
		}
		finally {
			if(null != reader)
			{
				reader.close();
			}
		}
		
		
	}


	public ArrayList<StoreKeeper> getStoreKeeperList()
	{
		return storeKeeperList;
	}

}
