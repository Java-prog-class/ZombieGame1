package objects;

public class DogStuff {

	public static void main (String[] args) {

		Dog dog1 = new Dog("Rover", 2);

		System.out.println(dog1.name +", "+ dog1.age);

		dog1.age = 5;
		dog1.name = "Argon";
		System.out.println(dog1.name +", "+ dog1.age);

		Dog dog2 = new Dog ("Fluffy", 4);
		System.out.println(dog2.name +", "+ dog2.age);

		if (dog1 == dog2) {
			System.out.println("== same");
		} else {
			System.out.println("!=");
		}

		if (dog1.equals(dog2)) {
			System.out.println(".equal");
		} else {
			System.out.println("NOT .equal");
		}


		//cloning dog 1
		Dog dog3 = dog1;
		dog3.age = 1;

		System.out.println(dog1.name +", "+ dog1.age);
		System.out.println(dog3.name +", "+ dog3.age);

		dog1.bark();
		dog2.bark();
		dog3.bark();

	}

}
