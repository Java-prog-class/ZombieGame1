package unit1;

import java.util.Scanner;

/*Program to get a sentence into a substring
 *Print out the letter immediately before the substring
 *and immediately after.
 *public class FindLetters {
*
*Josh Bakelaar, September 4,2018
*/

public class ReplaceLetters{
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// syso CTRL-SPACE then ENTER
		System.out.println("Type in a sentence");
		String s = sc.nextLine();
		
		System.out.println("Type in a 'substring': ");
		String s2 = sc.nextLine();
		
		System.out.println(">>> " + s);
		
		//TODO: write the program
		
		if (s.contains(s2)) {
			int first = s.indexOf(s2);
			for (int i = first; i <= s2.length(); i++) {
				char[] schars = s.toCharArray();
				schars[i] = 'x';	
				if(s2.length()==2) {
					i++;
					schars[i] = 'x';
				}
				s = String.valueOf(schars);
				
			}
			System.out.println(s);
			
		}else {
			System.out.println("ERROR");
		}
		
		//if the substring is at beginning or end
		
		
		}

}
