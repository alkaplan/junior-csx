package useful;

/**
 * 
 * @author Alex Kaplan
 * This class computes a logarithmically divided array based on a minimum value, a maximum value, and a number of entries
 * 
 */

public class StepTest {
	//for testing purposes, i've included a main
	public static void main(String[] args) {
		double minNum = -1.554098231033938E11;
		double maxNum = 1.50006409064213E11;
		int numNums = 20;
		
		//logarithmic
		double[] nums = arr(maxNum, minNum, numNums);
		System.out.println("logarithmically calculated steps:");
		for (int i = 0; i < nums.length; i++) {
			System.out.println(nums[i]);
		}
		
		//arithmatic
		System.out.println();
		System.out.println("arithmatically calculated steps:");
		double[] arithNums = arithArr(maxNum, minNum, numNums);
		for (int i = 0; i < arithNums.length; i++) {
			System.out.println(arithNums[i]);
		}
	}
	
	/**
	 * returns an array of numbers evenly (and by that I mean logarithmically) spaced between the min and max
	 * @param max the upper end of the array
	 * @param min the lower end of the array
	 * @param length how many numbers there are in the array
	 * @return arr the array with the evenly spaced numbers
	 */
	public static double[] arr(double max, double min, int length) {
		
		boolean negative = false;
		double tempMin = 0;
		
		if(Integer.signum((int)(max)) != Integer.signum((int)(min))) {
			//System.out.println("HELLO");
			negative = true;
			//System.out.println(max);
			max = max - min;
			//System.out.println(max);
			//System.out.println(min);
			tempMin = min;
			min = 0;
			length = length/2;
		}
		
		//find the power of ten of each number
		int tensMax = numTens(max);
		int tensMin = numTens(min);
		
		double coef = max/(Math.pow(10, tensMax));
		
		double[] nums = new double[length];

		double tensDiff = tensMax - tensMin;
		int arrLength = nums.length;
		double tensStep = tensDiff/(arrLength - 1);
		
		double exponent = tensMin;
		
		for (int i = 0; i < nums.length; i++) {
			nums[i] = coef * Math.pow(10, exponent);
			exponent += tensStep;
			
		}
		
		double[] negativeNums = new double[length];
		double[] totalNegativeNums = new double[length*2];
		if(negative) {
			for (int i = 0; i < nums.length; i++) {
				negativeNums[i] = nums[(nums.length-1) - i];
			}
			for (int i = 0; i < totalNegativeNums.length; i++) {
				if(i<length) {
					totalNegativeNums[i] = -1 * negativeNums[i];
				}
				else {
					totalNegativeNums[i] = nums[i - length];
				}
			}
			return totalNegativeNums;
		}
		
		return nums;
		
	}
	
	/**
	 * finds the exponent of the "10" term in scientific notation representation of number. E.g.:
	 * 41323 would return 4 because there are 4 digits, or 10^4 * 4.1323
	 * This uses recursion! It checks how many tens there are in num/10 until it gets to 0, each time adding one.
	 * @param num is the num to check how many tens there are
	 * @return the exponent of tens factors in the numbers
	 */
	public static int numTens(double num) {
		if(num < 10) {
			return 0;
		}
		else {
			return 1 + numTens(num/10);
		}
	}
	
	public static double[] arithArr(double max, double min, int length) {
		double[] arithNums = new double[length];
		double numStep = (max - min)/length;
		double currentNum = min;
		for (int i = 0; i < arithNums.length; i++) {
			arithNums[i] = currentNum;
			currentNum += numStep;
		}
		return arithNums;
	}
}
