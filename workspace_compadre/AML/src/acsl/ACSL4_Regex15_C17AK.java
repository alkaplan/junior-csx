package acsl;

import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * 
 * @author Alex Kaplan
 * 
 * Regex 15 for ACSL 4
 *
 */

public class ACSL4_Regex15_C17AK {
	public static void main(String[] args) {
		
		//take in input
		Scanner scan = new Scanner(System.in);
		String[] input = scan.nextLine().split(",[ ]*");
		
		for (int i = 0; i < 5; i++) {
						
			String singleInput = scan.nextLine();
			if(singleInput.equals("")) singleInput = "#";
			String output = "";
			
			
			for (int j = 0; j < input.length; j++) { 
				if(Pattern.matches(singleInput, input[j])) { 
					//Pattern.matches is regex matcher that takes in arguments regex, string and will return boolean
					output += input[j];
					output += ", ";
				}
			}
			
			if(output.length() > 2) {
				System.out.println(output.substring(0, output.length() - 2));
			}
			else {
				System.out.println("NONE");
			}
			
		}
	}
}