package main;

public class Main {
	
	public static void main(String[] args) {
		int[] arrA = {1,2,3};
		int[] arrB = {1,2,3};
		// When to add runtimes, vs multiply them
		
		// Add them together
		for (int a : arrA) {
			print(a);
		}
		
		for (int b: arrB) {
			print(b);
		}
		
		// Multiply them together
		for (int a: arrA) {
			for (int b: arrB) {
				print(a + ", " + b);
			}
		}
		
		// O (log n) - Binary search
		print(binarySearch(5, new int[]{1,2,3,4,5,6,7,8,9,10}));
		
		// O (2^N+1 - 1)
		print(recursiveFunction(4));
		
		// O(n^2)
		allPairs(new int[]{1,2,3,4,5});
		
		// Is it O(n)?
		// O(N + P) where P < N/2 (yes)
		// O(2N) (yes)
		// O(N + log N) (yes)
		// O(N + M) (no - don't know what M is)
		
		// Summing a Binary Tree's values: O(N) where N is number of nodes.
		
		// Prime Checker - O(sqrt(N))
		print(isPrime(33));
		
		// Computing factorial: O(N)
		
		// Permutations of String: O(N^2 * N!)
		permutation("abc", "");
		
		// Fibonacci: O(2^N) - generally multiple recursive calls means exponential runtime.
		print(fibonacci(10));
		
		// Fibonacci with Memoization: O(N)
		int[] fibArr = new int[11];
		for (int i = 0; i < 10; i++) {
			print(i + " : " + fib(i, fibArr));
		}
	}
	
	private static int fib(int n, int[] arr) {
		if (n <= 0) return 0;
		else if (n == 1) return 1;
		else if (arr[n] > 0) return arr[n];
		
		arr[n] = fib(n - 1, arr) + fib(n - 2, arr);
		
		return arr[n];
	}
	
	private static int fibonacci(int n) {
		if (n <= 0) return 0;
		else if (n == 1) return 1;
		return fibonacci(n - 1) + fibonacci(n - 2);
	}
	
	private static void permutation(String str, String prefix) {
		if (str.length() == 0){
			print(prefix);
		} else {
			// Removing a string value every time.
			for (int i = 0; i < str.length(); i++) {
				String rem = str.substring(0, i) + str.substring(i+1);
				permutation(rem, prefix + str.charAt(i));
			}
		}
	}
	
	private static boolean isPrime(int n) {
		for (int x = 2; x * x <= n; x++) {
			if (n % x == 0) {
				return false;
			}
		}
		return true;
	}
	
	private static void allPairs(int[] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				print(i + ", " + j);
			}
		}
	}
	
	private static int recursiveFunction(int n) {
		if (n <= 1) {
			return 1;
		}
		return recursiveFunction(n - 1) + recursiveFunction(n - 1);
	}
	
	private static int binarySearch(int target, int[] arr) {
		int low = 0;
		int high = arr.length - 1;
		
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (target < arr[mid]) high = mid - 1;
			else if (target > arr[mid]) low = mid + 1;
			else return mid;
		}
		
		return -1;
	}
	
	private static void print(int val) {
		System.out.println(val);
	}
	
	private static void print(String val) {
		System.out.println(val);
	}
	
	private static void print(boolean val) {
		System.out.println(val);
	}
	
	
}

