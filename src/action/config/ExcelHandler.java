package config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExcelHandler {

	
	/**
	 * change value of column in CSV with have a row
	 * 
	 * @param file
	 *            : url file CSV
	 * @param column
	 *            : form 0 to n
	 * @param data
	 *            : value will change at column
	 * @return
	 * @author Thuy.Duong..
	 */
	public void writeCellCSVData(String file, int row, int colum,String data) {
		FileInputStream fileCSV;
		String[] columCSV;
		File ftemp = new File(file);
		ftemp.getParent();
		String fileTempPath = ftemp.getParent() + ftemp.separator +"temp.txt";
		ftemp.exists();
		try {
			fileCSV = new FileInputStream(file);
			FileWriter filetemp = new FileWriter(fileTempPath);
			DataInputStream fileCSVin = new DataInputStream(fileCSV);
			BufferedReader fileCSVbr = new BufferedReader(
					new InputStreamReader(fileCSVin));

			String str;
			int countrow = 0;
			while ((str = fileCSVbr.readLine()) != null) {
				if (countrow == row) {
					columCSV = splitcsv(str);
					columCSV[colum] = data;
					filetemp.write(merge(columCSV));
					System.out.println(merge(columCSV));
				} else {
					filetemp.write(str);
				}
				filetemp.write("\n");
				countrow++;
			}

			filetemp.flush();
			filetemp.close();
			fileCSV.close();
			fileCSVin.close();
			fileCSVbr.close();

			// Copy file
			FileInputStream filetemp2 = new FileInputStream(fileTempPath);
			DataInputStream filetemp2In = new DataInputStream(filetemp2);
			BufferedReader filetemp2Br = new BufferedReader(
					new InputStreamReader(filetemp2In));
			FileWriter fw2 = new FileWriter(file);
			String str2;
			int i2 = 0;
			while ((str2 = filetemp2Br.readLine()) != null) {
				fw2.write(str2);
				
				if(i2==countrow-1){}
				else
					fw2.write("\n");
				i2++;
				
			}
			fw2.flush();
			fw2.close();
			File f = new File(fileTempPath);
			f.delete();
			filetemp2Br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	  * split string 
	  * @param value
	  * @return results : string
	  */
	public String[] splitcsv(String value) {
		String[] results = value.split(",");
		return results;
	}

	/**
	  * merge string 
	  * @param csv
	  * @return results : string
	  */
	public String merge(String[] csv) {
		String result = "";
		for (int i = 0; i < csv.length - 1; i++) {
			result = result + csv[i] + ",";
		}
		result = result + csv[csv.length - 1];
		return result;
	}
	
	 /**
	  * write data
	  * @param targetPath: e.g "\\\\192.168.191.114\\src\\demo.txt"
	  */
	 public void writeData(String targetPath) {
	     String text = "Hello world";
	         try {
	           File file = new File(targetPath);
	           BufferedWriter output = new BufferedWriter(new FileWriter(file));
	           output.write(text +"\n" + text);
	           output.close();
	         } catch ( IOException e ) {
	            e.printStackTrace();
	         }
	 }
	
}
