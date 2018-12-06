package unit2;

public class Except {

	public static void main(String[] args) {
		
		int ans;
		
		ans = divide (5,0);
		System.out.println(ans);
		

	}
	
	static int divide(int a,int b) {
		
		if(b == 0) {
			System.out.println("ERROR: Can Not Divide By Zero");
			return -1;
		}
		int x;
		try {
			x = a/b;
		} catch (ArithmeticException e) {
			x = 666;
		}
		return x;
	}

}
