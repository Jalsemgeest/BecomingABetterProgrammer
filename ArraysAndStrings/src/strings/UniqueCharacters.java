package strings;

import java.lang.reflect.Array;

public class UniqueCharacters {

	public static void main(String[] args) {
		// CCI - 1.1
		System.out.println("Cracking Code Interview: 1.1");
		System.out.println(isUnique("abc"));
		System.out.println(isUnique("Jake"));
		System.out.println(isUnique("abbc"));
		System.out.println(isUnique("testing"));
		
		// CCI 1.2
		System.out.println("\nCracking Code Interview: 1.2");
		System.out.println(isPermutation("test", "testing"));
		System.out.println(isPermutation("test", "test"));
		System.out.println(isPermutation("test", "apple"));
		
		// CCI 1.3
		System.out.println("\nCracking Code Interview: 1.3");
		char[] testing = new char[19];
		String s = "testing is cool";
		for (int i = 0; i < s.length(); i++) {
			testing[i] = s.charAt(i);
		}
		replaceSpaces(testing, 15);
		System.out.println(testing);
		
		// CCI 1.4
		System.out.println("\nCracking Code Interview: 1.4");
		System.out.println(palindromePermutation("Tact Coa"));
		
		// CCI 1.5
		System.out.println("\nCracking Code Interview: 1.5");
		System.out.println(closeEdits("pale", "ple"));
		System.out.println(closeEdits("pales", "pale"));
		System.out.println(closeEdits("pale", "bale"));
		System.out.println(closeEdits("pale", "bae"));
		
		// CCI 1.6
		System.out.println("\nCracking Code Interview: 1.6");
		System.out.println(compress("aabcccccaaa"));
		System.out.println(compress("aabcc"));
		System.out.println(compress("aabc"));

		// CCI 1.9
		System.out.println("\nCracking Code Interview: 1.9");
		System.out.println(isRotation("waterbottle", "erbottlewat"));
	}
	
	// O(n) - assuming basic alphabet and all single case.
	private static boolean isUnique(String str) {
		if (str.length() > 26) return false;
		for (int i = 0; i < str.length(); i++) {
			if (str.indexOf(str.charAt(i)) != i) {
				return false;
			}
		}
		return true;
	}
	
	// O(n)
	private static boolean isPermutation(String x, String y) {
		if (x.length() != y.length()) return false;
		
		int[] letters = new int[128]; // Assuming it won't be longer
		char[] x_array = x.toCharArray();
		for (char c : x_array) {
			letters[c]++;
		}
		
		for (int i = 0; i < y.length(); i++) {
			int c = (int) y.charAt(i);
			letters[c]--;
			if (letters[c] < 0) {
				return false;
			}
		}
		
		return true;
	}
	
	private static void replaceSpaces(char[] str, int trueLength) {
		int spacesCount = 0, index, i = 0;
		for (i = 0; i < trueLength; i++) {
			if (str[i] == ' ') {
				spacesCount++;
			}
		}
		index = trueLength + spacesCount * 2;
		if (trueLength < str.length) str[trueLength] = '\0'; // End of array
		for (i = trueLength - 1; i >= 0; i--) {
			if (str[i] == ' ') {
				str[index-1] = '0';
				str[index-2] = '2';
				str[index-3] = '%';
				index = index - 3;
			} else {
				str[index - 1] = str[i];
				index--;
			}
		}
	}
	
	// O(n)
	private static boolean palindromePermutation(String s) {
		int bitVector = createBitVector(s);
		return bitVector == 0 || checkForExactlyOneBitSet(bitVector);
	}
	
	private static int createBitVector(String s) {
		int bitVector = 0;
		for (char c : s.toCharArray()) {
			int x = getCharNumber(c);
			bitVector = toggle(bitVector, x);
		}
		return bitVector;
	}
	
	private static int toggle(int bitVector, int index) {
		if (index < 0) return bitVector;
		
		int mask = 1 << index;
		if ((bitVector & mask) == 0) {
			bitVector |= mask;
		} else {
			bitVector &= ~mask;
		}
		
		return bitVector;
	}
	
	private static int getCharNumber(char c) {
		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		int val = Character.getNumericValue(c);
		if (a <= val && val <= z) {
			return val - a;
		}
		return -1;
	}
	
	private static boolean checkForExactlyOneBitSet(int bitVector) {
		return (bitVector & (bitVector - 1)) == 0;
	}
	
	// O(n)
	private static boolean closeEdits(String before, String after) {
		if (before.length() == after.length()) {
			return oneEditAway(before, after);
		} else if (before.length() + 1 == after.length()) {
			return oneEditAway(before, after);
		} else if (before.length() == after.length() + 1) {
			return oneEditAway(after, before);
		}
		return false;
	}
	
	private static boolean oneEditAway(String first, String second) {
		// If they are different by more than 1 length, they must be more than one edit away.
		if (Math.abs(first.length() - second.length()) > 1) return false;
		
		// Define the first string and second string.
		String s1 = first.length() < second.length() ? first : second;
		String s2 = first.length() < second.length() ? second : first;
		
		int index1 = 0;
		int index2 = 0;
		boolean foundDifference = false;
		
		// When there are still characters to compare, continue.
		// Otherwise, it must be true.
		while (index2 < s2.length() && index1 < s1.length()) {
			// If the characters at respective indexes differ
			if (s1.charAt(index1) != s2.charAt(index2)) {
				
				// If we found a difference before, then it's more than one. Return false.
				if (foundDifference) return false;
				
				// Regardless, we found a difference.
				foundDifference = true;
				
				// If they are the same length, increase index1 to check for replace.
				if (s1.length() == s2.length()) {
					index1++; // On replace, move shorter pointer
				}
			} else {
				index1++; // If matching, move shorter pointer
			}
			index2++;
		}
		return true;
	}

	
	private static String compress(String s) {
		if (s.length() <= 2) return s;
		StringBuilder compressed = new StringBuilder();
		char previous = s.charAt(0);
		int count = 1;
		for (char a : s.toCharArray()) {
			if (previous != a) {
				compressed.append(previous);
				compressed.append(count);
				previous = a;
				count = 1;
			} else {
				count++;
			}
		}
		
		compressed.append(previous);
		compressed.append(count);
		
		return compressed.length() < s.length() ? compressed.toString() : s;
	}
	
	private static boolean isRotation(String s1, String s2) {
		int length = s1.length();
		if (length == s2.length() && length > 0) {
			String s1s1 = s1 + s1;
			return s1s1.contains(s2);
		}
		return false;
	}
}
