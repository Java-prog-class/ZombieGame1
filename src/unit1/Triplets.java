package unit1;

import java.util.Scanner;

/*Read in a sentence
 * print out only words that have the same letter repeated 3 or more times in a row (e.g. happpy)
 *
 *Josh Bakelaar, September 24,2018
 */

public class Triplets {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// syso CTRL-SPACE then ENTER
		System.out.println("Type in a sentence");
		String s = sc.nextLine();
		String words[] = s.split(" ");

		for (int i = 0; i < words.length; i++) {
			String oneword = words[i];
			for(int n = 0; n<=oneword.length()-2;n++) {
				if(oneword.charAt(n) == oneword.charAt(n+1) && oneword.charAt(n) == oneword.charAt(n+2)) {
					System.out.println(oneword);
				}
			}
		}
	}
	
}


