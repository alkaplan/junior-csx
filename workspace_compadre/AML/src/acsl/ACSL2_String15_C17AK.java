package acsl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Random;

/**
 * 
 * @author Alex Kaplan
 * This is my ACSL program 2 in the 2015-2016 school year. 
 */

public class ACSL2_String15_C17AK {

	public static void main(String[] args) throws IOException {
		//declarations:
		String filein = "testdata";
		Scanner scan;
		FileOutputStream fop = null;

		try{
			scan = new Scanner(new BufferedReader(new FileReader(filein)));
			fop = new FileOutputStream(new File(filein+"-out"));
		} catch(FileNotFoundException e) {
			//switch to regular terminal input
			scan = new Scanner(System.in);
		}

		//main loop:
		while(scan.hasNext()) {
			String answer = solve(scan.nextLine());
			System.out.println(answer);
			if(fop!=null) fop.write((answer+"\n").getBytes());
		}

		fop.flush();
		fop.close();
		scan.close();
	}

	/**
	 * solve for a specific input
	 * @param s the input
	 * @return the output
	 */
	static String solve(String s) {

		String input[] = s.split(",[ ]*");

		//split the input up
		String a = input[0];
		String b = input[1];
		String c = input[2];

		//assign values from the input
		float numGiven = 0;
		int dec = Integer.parseInt(c);
		int length = Integer.parseInt(b);

		//get the prefix, and set the number to Stringify
		char prefix = a.charAt(0);
		if(prefix != '-' && prefix != '+') {
			prefix = 0;
			numGiven = Float.parseFloat(a);
		}

		//if there is a prefix then get the number from 1 over
		else {
			numGiven = Float.parseFloat(a.substring(1));
		}

		//use my handy dandy round function to get the output
		float output = round(numGiven, dec, prefix);

		String outputString = Float.toString(output);

		if(prefix != 0) {
			outputString = prefix + outputString;
		}
		
		if(outputString.length() == length) {
			return outputString;
		}
		else {
			outputString = addHashes(outputString, length, dec);
			return outputString;
		}
	}

	/**
	 * takes in a number, number of decimals, and the prefix and rounds it
	 * @param numGiven the number to round
	 * @param decimals how many decimals to round it
	 * @param pref the prefix
	 * @return finalNum the rounded number as a float
	 */
	public static float round(float numGiven, int decimals, char pref) {


		String dfString = "0.";
		for (int i = 0; i < decimals; i++) {
			dfString += "#";
		}


		DecimalFormat df = new DecimalFormat(dfString);
		df.setRoundingMode(RoundingMode.HALF_UP);
		if(pref == '-') {
			df.setRoundingMode(RoundingMode.HALF_DOWN);
		}

		double numDouble = numGiven;

		String roundedNumDouble = df.format(numDouble);

		float finalNum = Float.parseFloat(roundedNumDouble);

		return finalNum;
	}

	/**
	 * adds hashtags when number is not the right length, three cases:
	 * number is the right length (dont do anything)
	 * number is too long (trim
	 * number is too short (add hashes at the beginning until it is no longer too long)
	 * @param input
	 * @param length
	 * @return
	 */
	static String addHashes(String input, int length, int decimals) {

		if(input.length() == length) {
			return input;
		}

		else if(input.length() + 1 > length) {
			input = "";
			if(decimals > 0) {
				for (int i = 0; i < length - decimals - 1; i++) {
					input += "#";
				}
				input += ".";
				for (int i = 0; i < decimals; i++) {
					input += "#";
				}
			}
			else {
				for (int i = 0; i < length - 1; i++) {
					input += "#";
				}
				input += ".";
			}
		}

		else if(input.length() < length) {
			String extraHashes = "";
			for (int i = 0; i < (length - input.length()); i++) {
				extraHashes += "#";
			}
			input = extraHashes + input;
		}
		return input;
	}

}