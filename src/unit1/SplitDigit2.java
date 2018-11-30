package unit1;

import java.util.Scanner;

/*Ask the user for a 2 digit number
 *Print out the first digit, and the second digit
 *Josh Bakelaar, September 5,2018
*/

public class SplitDigit2 {


	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Type a 2 digit number");
		int number = sc.nextInt();
		
		String numberString = Integer.toString(number);
		char first = numberString.charAt(0);
		char second = numberString.charAt(1);
		
		System.out.println("int a= " + first +" int b= "+ second);
		
	}
}