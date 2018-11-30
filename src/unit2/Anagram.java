package unit2;

//Josh Bakelaar October 2nd
// Get a 4 letter word and print out every possible anagram(it will be 24 different combinations)
//

import java.io.IOException;
import java.util.Scanner;

public class Anagram {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		// syso CTRL-SPACE then ENTER
		System.out.println("Type in a 4 letter word");
		String s = sc.nextLine();
		String letters[] = s.split("");
		String print[] = new String[4];
		//If the user does not enter a 4 letter word
		if(letters.length != 4) {
			System.out.println("Needs to be a 4 letter word");
			return;
		}
		//Printing the anagram
		
		for(int i = 0; i<4; i++) {
			//0 is going to = whatever i is
			print[0]=(letters[i]);
			for(int n = 0; n<4; n++) {
				//if the number that n is doesn't = i then the 2nd number stored in print will be whatever n is
				if(n!=i) {
					print[1]=(letters[n]);
					for(int j= 0; j<4; j++) {
						//if the number that j is doesn't = i or n then the 3rd number stored in print will be whatever j is
						if(j!= i && j!= n) {
							print[2]=(letters[j]);
							for(int k = 0; k<4; k++) {
								//if the number that k is doesn't = i, n, or j then the 4th number stored in print will be whatever k is
								if(k!= i && k!= j && k!= n) {
									print[3]=(letters[k]);
									for(int o=0; o<4; o++) {
										//print out each character stored in 0,1,2 and 3
										System.out.print(print[o]);
									}
									//print out the space
									System.out.println("");
									//go back and do it again
									//once it reaches no more possible combinations it stops
								}
							}
						}
					}
				}
			}
		}
	}
}

/* Marking:  18/20
Well done!

comments are pretty good. Though this one is wrong: //print out the space
41-44 (for loop) could be explained better. It tooke me a long time to figure out what's going on.

A comment like this "0 is going to = whatever i is" doesn't really tell us much.

You don't need () in line 32, 36, etc.
*/

