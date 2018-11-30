package unit1;

import java.util.Arrays;

public class CharStuff {
	
	public static void main(String[] args) {
		
		String text = "Good Morning!";
		text = text.toUpperCase();
		char c = text.toUpperCase().charAt(0);
		
		int n = c;
		System.out.println(c + " " + n);
		System.out.println(c + n);
		
		System.out.println(c + "");
		System.out.println("" + c + '=');
		System.out.println(text);
		
		for(int i = 0; i <26; i++) {
			char cc = (char)('A' + i);
			System.out.println(cc);
		}
		
		System.out.println();
		for (int i = 32; i < 1000; i++) {
			System.out.println( (char) (i));
			if (i%20 == 0) System.out.println();
		}
		
		String text2 = "";
		for (int i=0;i<text.length(); i++) {
			char cc = text.charAt(i);
			if(cc>= 'A' && cc <= 'Z') text2 += cc;
			if(cc== ' ') text2+= cc;
			if(cc >= '0' && cc <= '9') text2 += cc;
		}
		System.out.println("\n");
		System.out.println(text);
		System.out.println(text2);
		
		/* ##### Really cool way of counting letters ######*/
		int totals[] = new int[26];
		for (int i=0; i<text.length(); i++) {
			char cc = text.charAt(i);
			if (cc < 'A'|| cc > 'Z') continue;
			totals[cc-65]++;
		}
		
		for(int i='A'; i< 'A'+26; i++) {
			System.out.print(" "+(char)(i) + ",");
		}
		System.out.println("\n"+Arrays.toString(totals));
		
		 	
	}
}


