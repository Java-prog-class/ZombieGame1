package objects;

public class Dog {
	String name = "-none-";
	int age;
	Dog(String name, int age) {
		this.name = name;
		if(age < 0) age = 0;
		this.age = age;
	}
	
	boolean equals(Dog d1) {
//		if(this.name.equals(d1.name)) {
//			if (this.age == d1.age) {
//				return true;
//			}
//		}
//		return false;
		
		if(!this.name.equals(d1.name)) return false;
		if(this.age != d1.age) return false;
		return true;
	}
	
	void bark() {
		if (age < 2)
			System.out.println("yip");
		else
			System.out.println("bork");
		
	}

}
