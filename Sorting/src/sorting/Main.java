package sorting;

public class Main {

	static JArray arr;
	static JArray arr2;

	public static void main(String[] args) {
		arr = new JArray(4);
		arr2 = new JArray(4);
		System.out.println(arr.toString());
		mergeSort(arr.arr);
		System.out.println(arr.toString());
		
		
		System.out.println(arr2.toString());
		quickSort(arr2.arr, 0, arr2.arr.length - 1);
		System.out.println(arr2.toString());
	}
	
	private static void mergeSort(int[] arr) {
		int[] helper = new int[arr.length];
		mergeSort(arr, helper, 0, arr.length - 1);
	}
	
	private static void mergeSort(int[] array, int[] helper, int low, int high) {
		if (low < high) {
			int middle = (low + high) / 2;
			mergeSort(array, helper, low, middle); // Sort left half
			mergeSort(array, helper, middle+1, high); // Sort right half
			merge(array, helper, low, middle, high); // Merge them
		}
	}
	
	private static void merge(int[] array, int[] helper, int low, int middle, int high) {
		// Copy both halves into the helper array.
		for (int i = low; i <= high; i++) {
			helper[i] = array[i];
		}
		
		int helperLeft = low;
		int helperRight = middle + 1;
		int current = low;
		
		/*
		 * Iterate through the helper array.  Compare the left and right half, copying
		 * back the smaller element from the two halves into the original array.
		 */
		while (helperLeft <= middle && helperRight <= high) {
			if (helper[helperLeft] <= helper[helperRight]) {
				array[current] = helper[helperLeft];
				helperLeft++;
			} else { // If right element is smaller than left element
				array[current] = helper[helperRight];
				helperRight++;
			}
			current++;
		}
		
		// Copy the rest of the left side of the array into the target array.
		int remaining = middle - helperLeft;
		for (int i = 0; i <= remaining; i++) {
			array[current + i] = helper[helperLeft + i];
		}
	}
	
	private static void quickSort(int[] arr, int left, int right) {
		int index = partition(arr, left, right);
		if (left < index - 1) { // Sort left half
			quickSort(arr, left, index - 1);
		}
		if (index < right) { // Sort right half
			quickSort(arr, index, right);
		}
	}
	
	private static int partition(int[] arr, int left, int right) {
		int pivot = arr[(left + right) / 2]; // Pick pivot point
		while (left <= right) {
			// Find element on left that should be on right
			while (arr[left] < pivot) left++;
			
			// Find element on right that should be on left
			while (arr[right] > pivot) right--;
			
			// Swap elements, and move left and right indices
			if (left <= right) {
				swap(arr, left, right); // Swap elements
				left++;
				right--;
			}
		}
		return left;
	}
	
	private static void swap(int[] arr, int left, int right) {
		int val = arr[right];
		arr[right] = arr[left];
		arr[left] = val;
	}

}

class JArray {
	
	public int[] arr;
	
	public JArray(int val) {
		arr = new int[val];
		for (int i = 0; i < val; i++) {
			arr[i] = (int) (Math.random() * 100);
		}
	}
	
	private String getString(int n) {
		return "" + arr[n];
	}
	
	public String toString() {
		String str = "[ " + getString(0);
		for (int a = 1; a < arr.length; a++) {
			str += ", " + getString(a);
		}
		return str + " ]";
	}
}