package orbital;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoDayArraySortTest {
	
	public static void main(String[] args) {
		
		double[][] testArr = new double[4][4];
		
		testArr[0][0] = 0;
		testArr[0][1] = 4;
		testArr[0][2] = 7;
		testArr[0][3] = 1;
		testArr[1][0] = -3;
		testArr[1][1] = 9;
		testArr[1][2] = -1;
		testArr[1][3] = 2;
		testArr[2][0] = -5;
		testArr[2][1] = 5;
		testArr[2][2] = 3;
		testArr[2][3] = 10;
		testArr[3][0] = -2;
		testArr[3][1] = 6;
		testArr[3][2] = -6;
		testArr[3][3] = 40;

			//{{0, 4, 7, 1},{-3, 9, -1, 2},{-5, 5, 3, 10},{-2, 5.4, -6, 0.5}};
		
		ArrayList<Double> minVals = new ArrayList<Double>();
		
		
		double minVal = testArr[0][0];
		for (int i = 0; i < testArr.length; i++) {
			for (int j = 0; j < testArr.length; j++) {
				if(testArr[i][j] < minVal) {
					minVals.add(testArr[i][j]);
					//test
				}
			}
		}

		
		for (int i = 0; i < testArr.length; i++) {
			for (int j = 0; j < testArr[0].length; j++) {
				System.out.print(testArr[i][j] + " ");
			}
			System.out.println();
		}
		
	}

}
