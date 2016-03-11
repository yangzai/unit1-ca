package sg.edu.nus.iss.se24_2ft.unit1.ca.util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;

public class CSV {

	private CSVReader csvReader;
	private CSVWriter csvWriter;
	private String fileName;

	public CSV(String fileName) throws IOException {
		this.fileName = fileName;
	}

	public void write(ArrayList<String> contents) throws IOException {
		FileWriter writer = new FileWriter(fileName);
		for (String record : contents) {
			writer.write(record);
			if (record != contents.get(contents.size() - 1)) {
				writer.write(System.lineSeparator());
			}
		}
		writer.flush();
		writer.close();
	}

}
