package unit2;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadAWebpage {

	public static void main(String[] args) {
		
		//1. Do all of the file opening, etc. and set up the buffered reader
		BufferedReader brWeb = null;
		
		try {
			URL link = new URL("https://en.wikipedia.org/wiki/Canada"); 
			brWeb = new BufferedReader(new InputStreamReader(link.openStream()));
		}
		catch (MalformedURLException ex) {
			System.out.println(ex.toString());
			System.exit(0);
		}
		catch (IOException ex) {
			System.out.println(ex.toString());
			System.exit(0);
		}

		//2. Main loop for reading/writing
		while (true) {
			String s = "";
			try {
				s = brWeb.readLine();
			} catch (IOException ex) { 
				System.out.println(ex.toString());
				System.exit(0);
			}
			
			if (s == null) break;
			
			if (s.contains("Canada")) {
				System.out.println("'Canada' found");
			} else {
				System.out.println("'Canada' not found");
			}
		}
	}

}
