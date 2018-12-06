package unit1;

import java.util.Scanner;

/*Program to get a sentence into a substring
 *Print out the letter immediately before the substring
 *and immediately after.
 *public class FindLetters {
*
*Josh Bakelaar, September 4,2018
*/

public class FindLetters {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// syso CTRL-SPACE then ENTER
		System.out.println("Type in a sentence");
		String s = sc.nextLine();
		
		System.out.println("Type in a 'substring': ");
		String s2 = sc.nextLine();
		
		System.out.println(">>> " + s);
		
		//TODO: write the program
		int last = s.indexOf(s2) + s2.length();
		int first = s.indexOf(s2) - 1;
		
		if (s.contains(s2)) {
			if (s.indexOf(s2) == 0) {
				System.out.print("*");
			}else {
				System.out.print(s.charAt(first));
			}
			if (last >= s.length()) {
			System.out.println("*");
		}else {
				System.out.print(" "+ s.charAt(last));
		}
		}else {
			System.out.println("ERROR");
		}
		
		//if the substring is at beginning or end
		
		
		}

}
