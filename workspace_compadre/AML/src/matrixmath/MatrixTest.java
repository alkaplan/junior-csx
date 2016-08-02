package matrixmath;

import matrixmath.Matrix;

public class MatrixTest {
	
	public static void main(String[] args) {
		Matrix Test = new Matrix(5, 5);
		
		for (int i = 0; i < Test.matrix.length; i++) {
			for (int j = 0; j < Test.matrix[0].length; j++) {
				Test.setEntry(i, j, .1+5*i+j);
			}
		}
		
		Test.print();

	}
}
