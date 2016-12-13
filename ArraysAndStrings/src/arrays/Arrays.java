package arrays;

public class Arrays {

	public static void main(String[] args) {
		int[][] matrix = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
		int[][] zeroMatrix = new int[][]{{1,2,0},{4,0,5},{7,8,9}};
		// CCI - 1.7
		System.out.println("Cracking Code Interview: 1.7");
		System.out.println(rotateMatrix(matrix));
		printMatrix(matrix);
		
		// CCI - 1.8
		System.out.println("Cracking Code Interview: 1.8");
		zeroMatrix(zeroMatrix);
		printMatrix(zeroMatrix);
		
		
	}
	
	private static boolean rotateMatrix(int[][] matrix) {
		if (matrix.length == 0 || matrix.length != matrix[0].length) return false;
		
		int n = matrix.length;
		// Get the layer of the matrix.  Only need to do half, as we're doing the
		// other half at the same time.
		for (int layer = 0; layer < n / 2; layer++) {
			// Get the layer we're working on.
			int first = layer;
			// Get the other side of the layer we're working on.
			int last = n - 1 - layer;
			// From the start of the layer to the end of the layer.
			for (int i = first; i < last; i++) {
				int offset = i - first;
				
				int top = matrix[first][i]; // save top
				
				// left -> top
				matrix[first][i] = matrix[last-offset][first];
				
				// bottom -> left
				matrix[last-offset][first] = matrix[last][last-offset];
				
				// right -> bottom
				matrix[last][last-offset] = matrix[i][last];
				
				// top -> right
				matrix[i][last] = top; // right <- saved top
			}
		}
		
		return true;
	}
	
	private static void zeroMatrix(int[][] matrix) {
		boolean rowHasZero = false;
		boolean colHasZero = false;
		
		// Check if first row has zero
		for (int j = 0; j < matrix.length; j++) {
			if (matrix[0][j] == 0) {
				rowHasZero = true;
				break;
			}
		}
		
		// Check if first col has zero
		for (int i = 0; i < matrix[0].length; i++) {
			if (matrix[i][0] == 0) {
				colHasZero = true;
				break;
			}
		}
		
		// Check for zeros in the rest of the array
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}
		
		// Nullify rows based on values in the first column
		for (int i = 1; i < matrix[0].length; i++) {
			if (matrix[i][0] == 0) {
				nullifyRow(matrix, i);
			}
		}
		
		// Nullify rows based on values in first row
		for (int i = 1; i < matrix.length; i++) {
			if (matrix[0][i] == 0) {
				nullifyColumn(matrix, i);
			}
		}
		
		// Nullify first row
		if (rowHasZero) {
			nullifyRow(matrix, 0);
		}
		
		// Nullify first column
		if (colHasZero) {
			nullifyColumn(matrix, 0);
		}
	}
	
	private static void nullifyRow(int[][] matrix, int row) {
		for (int i = 0; i < matrix[0].length; i++) {
			matrix[row][i] = 0;
		}
	}
	
	private static void nullifyColumn(int[][] matrix, int col) {
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][col] = 0;
		}
	}
	
	private static void printMatrix(int[][] matrix) {
		if (matrix.length == 0) return;
		int m = matrix.length;
		int n = matrix[0].length;
		for (int i = 0; i < m; i++) {
			System.out.print("[");
			for (int j = 0; j < n; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.print("]\n");
		}
	}
	
}
