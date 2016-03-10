package sg.edu.nus.iss.se24_2ft.unit1.ca.util;

public class CSV {
	
	private static CSV csv;
	private CSVReader csvReader;
	private CSVWriter csvWriter;
	
	public static CSV getInstance()
	{
		if(null == csv)
		{
			csv = new CSV();
		}
		return csv;
	}
	
}
