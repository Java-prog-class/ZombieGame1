package objects;

public class AnotherObjectProgram {
	
	int n =6;
	
	public static void main(String[]args) {
		new AnotherObjectProgram();
	}	
	//Constructor
	AnotherObjectProgram(){
		method2();
	}
	
	static void method1() {
		System.out.println("1");
	}
	
	void method2() {
		System.out.println("2" + n);
	}
}
