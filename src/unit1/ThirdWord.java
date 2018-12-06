package unit1;

import java.util.Scanner;

public class ThirdWord {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Type in a sentence");
		String s = sc.nextLine();
		String[]words = s.split(" ");
		
		for(int i = 2; i< words.length; i+=3) {
			System.out.println(words[i]);
		}
	}

}
