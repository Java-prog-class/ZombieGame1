//Josh Bakelaar October 3rd, 2018
//Given a list of 6 letter words {http://listofrandomwords.com/}, and one other word
//find the largest number of letters that this word has in common with any word in the list.
//print out the number of characters in common from the word which has the most in common

package unit2;

import java.util.Arrays;

public class LettersInCommon {

	public static void main(String[] args) {
		//Get the 6 words off of http://listofrandomwords.com
		String randomWords = "hejira jehria monnet caving zagreb hapten merida hjjjja";
			
		String wordList[] = randomWords.split(" ");
		//get another random word
		String word = "hjzzzz";
		int[] counted = new int[wordList.length];
		
		
		for(int n=0; n<wordList.length; n++) {
			//resets count back to 0 every time
			int count = 0;
			for(int i=0; i<word.length(); i++) {
				for(int j=0; j<wordList[n].length(); j++) {
					//if the character at whatever i is matches any character from the word(which ever n is) it will count
					if (word.charAt(i)== wordList[n].charAt(j)) {
						count++;
						// put that count into another array, this array stores the counts from each random word
						counted[n]= count;
					}
				}
			}
		}
		
		//clone counted
		int counted2[] = counted.clone();
		//sort the array from least to greatest
		Arrays.sort(counted);
		int most = 0;
		for(int i=0; i<=counted.length;i++) {
			int m = counted[counted.length-1];
			if (m== counted2[i]) {
				most=i;
				break;
			}
		}
		//print out the word with the most common
		System.out.println(wordList[most]);
		//print whatever is stored in the last(how many times the letter appears)
		System.out.print(counted[counted.length-1]);
	}
}

/* Marking:  16/20

I'm getting this printed out now: hapten, hapten, hapten, 5

I tried adding another word, 
I tried adding the same letter multiple times
I tried adding exactly the same word as the search word

It didn't work.  It's probably the part that finds the biggest number and prints it out.
The logic for the main part looks good.

*/
