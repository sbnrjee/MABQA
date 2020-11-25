package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertiesWriter {

	public static void main(String[] args) {
		Properties prop=new Properties();
		FileWriter fw;
		
		try {
			fw = new FileWriter(new File("./reports/ReportUrl.properties"),true);
		
		BufferedWriter bw=new BufferedWriter(fw);
				
		 bw.write("hello");
		  bw.write("\n");
		  bw.write("helliii");
		  bw.write("\n");
	       
			 bw.close();
			 fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
