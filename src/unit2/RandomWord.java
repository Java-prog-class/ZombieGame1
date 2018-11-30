package unit2;

public class RandomWord {

	public static void main(String[] args) {
		int r = (int) (Math.random()*26)+1;
		
		for(int i=0;i<=6; i++) {
			System.out.print((char) r);
		}

	}

}
