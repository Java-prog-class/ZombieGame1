package unit1;

import java.util.Scanner;

/*Ask the user for a 2 digit number
 *Print out the first digit, and the second digit
 *Josh Bakelaar, September 5,2018
*/

public class SplitDigit {


	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Type a 2 digit number");
		int number = sc.nextInt();
		
		int a = number/10;
		int b = number%10;
		
		System.out.println("int a= "+a +" int b= "+ b);
		
		
		
	}
}