//Josh Bakelaar 2018-11-19
//ArraySorting
//Get an array of 25 random numbers(0-1000)
//Sort the array from least to greatest

package unit2;

public class ArraySorting{
	public static void main(String[] args) {
		//Place holder if the first is bigger then the second
		int tempnum;
	
		int numbers[] = new int[25];
		
		for(int i = 0; i<numbers.length; i++) {
			numbers[i] = (int)(Math.random()*1000);
		}
		

		//sorting the array
		for (int i = 0; i < 25; i++){
			for (int j = i + 1; j < 25; j++){
				//if numbers 0 is greater then numbers 1
				if (numbers[i] > numbers[j]){
					//Put the greater in a place holder just for now
					tempnum = numbers[i];
					//0 is going to = the smaller number
					numbers[i] = numbers[j];
					//1 is going to be what we put in the place holder
					numbers[j] = tempnum;
				}
				//repeat with all the other places in the array
			}
		}

		//print sorted array 		
		System.out.println("Sorted:");
		for (int i = 0; i < 25; i++){
			System.out.println(numbers[i]);
		}
	}   
}