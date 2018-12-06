package unit2;

import java.io.*;

public class ReadFromJava {
	
	public static void main(String[] args) {
		String filename = "Data.txt";
		
		//Create a FileReader and then wrap it with BufferedReader.
		BufferedReader brFile = null;
		try {
			brFile = new BufferedReader(
						new FileReader(
							new File(filename)));
		} catch (FileNotFoundException e) {
			System.out.println(filename +" not found.");
			System.exit(0);
		}
		//read 1 line and print it
		while(true) {
			try {
				String text = brFile.readLine ();
				if (text == null) break;
				processLine(text);
				System.out.println(text);
			} catch (IOException e) {
				System.out.println("mysterious error");
			}
		}
		//now close the file
		try {
			brFile.close();
		} catch (IOException e) {}
	}

	private static void processLine(String text) {
		for(int i = 0; i <text.length(); i++) {
		//	System.out.println(text.charAt(i));
		}
	}
}
