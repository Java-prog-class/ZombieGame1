package unit2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;

public class TypeToFile {

	public static void main(String[] args) throws IOException {
		String filename = "Data.txt";
		PrintWriter pw = null;
		try {
			System.out.println("Enter the text you want to put into the file");
			BufferedReader brIn = new BufferedReader (new InputStreamReader (System.in));  //make Buffered Reader

			File f = new File ("Data.txt");
			pw = new PrintWriter( new BufferedWriter( new FileWriter (f)));

			while(true) {
				String text = brIn.readLine (); //Read the keyboard input using the readLine method
				if(text.equals("JOSH")) {
					break;
				}
				pw.println(text);			
			}

		} catch (FileNotFoundException e) {
			System.out.println(filename +" not found.");
			System.exit(0);
		}
		pw.close();
	}
}