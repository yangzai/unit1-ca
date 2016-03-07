/*********************************************
 * 
 * @author: Navy Gao 
 * @date:  2016.3.7
 * @Moudle: Store keeper class
 * 
 * Change history:
 * 
 * Copyright @ ISS, NUS.  All right reserved. 
 *********************************************/ 

package sg.edu.nus.iss.se24_2ft.unit1.ca;

public class StoreKeeper {
	
    private String name, password;
    
    public StoreKeeper(String name, String password) 
    {
    	this.name = name;
    	this.password = password;
    
    }
    
    /*Attribute accessor: Access the name of store keeper */
    public String getName()
    {
    	return name;
    }
    
    /*Attribute accessor: Access the password of store keeper */
    public String getPassword()
    {
    	return password;
    }
    
}