/*
 * Created on Oct 9, 2005 at 4:04:21 PM.
 */

/**
 * @author jonl
 * Example illustrating passing a reference value to fill up an array.
 */
public class ArrayExample {

	public void printArray(int[] array, String identifier) {
		for (int i = 0; i < array.length; i++)
			System.out.println(identifier + "[" + i + "] = " + array[i]);
	}

	public void fillIntArray(int[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = i;
	}

	public void run() {
		int[] my_ints = new int[10];
		fillIntArray(my_ints);
		printArray(my_ints, "my_ints");
	}
}
