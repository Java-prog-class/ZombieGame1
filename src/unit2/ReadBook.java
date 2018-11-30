package unit2;

import java.io.*;
import java.math.*;
import java.util.Arrays;

public class ReadBook {

	static int letters[] = new int [26];
	static int letters2[] = new int [26];

	public static void main(String[] args) {
		String filename = "Book.txt";

		//Create a FileReader and then wrap it with BufferedReader.
		BufferedReader brFile = null;
		try {
			brFile = new BufferedReader(new FileReader(	new File(filename)));
		} catch (FileNotFoundException e) {
			System.out.println(filename +" not found.");
			System.exit(0);
		}
		//read each line
		while(true) {
			try {
				String text = brFile.readLine ();
				if(text == null) break;
				findLetter(text);

			} catch (IOException e) {
				System.out.println("mysterious error");
			}

		}
		//print out letters and how many times they appear
		for(int i= 0; i< 26; i++) {
			System.out.println((char)(i + 65) + "\t" + letters[i]);
		}


		Arrays.sort(letters2);
		System.out.println("");
		//Print Smallest
		for(int n=0; n<5; n++) {
			for(int i =0; i<letters.length; i++) {
				if(letters2[n]==letters[i]) {
					System.out.println("Smallest "+ (char)(n+49) +": " + (char)(i + 65) + " = " + letters[i]);
				}
			}
		}
		System.out.println("");
		//Print Largest
		for(int n=25; n>=21 ; n--) {
			for(int i =0; i<letters.length; i++) {
				if(letters2[n]==letters[i]) {
					System.out.println("Largest "+(char)(74-n)+": " + (char)(i + 65) + " = " + letters[i]);
				}
			}
		}
	}
	//assign a letter to the array
	static void findLetter(String text) {

		for(int i = 0; i<text.length(); i++) {
			text = text.toUpperCase();
			char c = text.charAt(i);

			if (c >= 'A'&& c<= 'Z') {
				int n = c-65;
				letters[n]++;
				letters2[n]++;
			}
		}
	}
}

