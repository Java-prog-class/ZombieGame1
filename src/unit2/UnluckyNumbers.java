package unit2;

import java.util.Scanner;

public class UnluckyNumbers {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int goodLuck = 0;
		
		while(true) {
			int num = -1;
			System.out.println("Press Enter for new number");
			scanner.nextLine();
			try {
				num = rand(20);
				if (num == 7) goodLuck ++;
			}
			catch(BadLuckException e) {
				System.out.println(e.toString());
				e.printStackTrace();
				goodLuck--;
				if (goodLuck > 0) {
					System.out.println("phew. Your good luck overcomes badluck");
				} else {
					System.out.println("very unlucky. Die.");
					System.exit(0);
				}
			} finally {
				System.out.println("num="+num);
			}			
		}
	}	
	
	static int rand(int max) throws BadLuckException {
		int r = (int)(Math.random()*max) +1;
		if (r == 13) throw new BadLuckException("13 is bad luck");		
		return r;
	}
	
}

//Alternatively:

//try {
//	num = (int)(Math.random()*20) +1;
//	if (num == 7) goodLuck ++;
//	if (num == 13) throw new BadLuckException("13 is bad luck");		 //you can throw the exception inside a try-catch ! No need for a separate function.
//}
