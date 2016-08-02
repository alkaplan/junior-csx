import java.util.*;

public class MaximizingAngle {
	
	public static void main(String[] args) {
		
		//initialize variables, or "declare" them before setting their values
		
		double height;
		double v;
		double thetaMin;
		double thetaMax;
		double thetaStep;
		int numAngles;
		
		//create the "scanner" to ask the user for input
		Scanner scan = new Scanner(System.in);
		
		//ask the user for input in all of the independent variables, and angles to test
		System.out.println("Height?");
		height = scan.nextDouble();
		System.out.println("Initial velocity?");
		v = scan.nextDouble();
		System.out.println("Min angle?");
		thetaMin = scan.nextDouble();
		System.out.println("Max angle?");
		thetaMax = scan.nextDouble();
		System.out.println("Num angles?");
		numAngles = scan.nextInt();
		
		//find the "step", or interval, of angles to test (i.e. test every angle from 90 - 0, with a 0.1º step
		thetaStep = (thetaMax - thetaMin)/numAngles;
		
		//initialize the "arrays" of angles to test and resulting ranges to use later
		double[] angles = new double[numAngles];
		double[] ranges = new double[numAngles];
		
		//start the "current angle to test" at the minimum inputed angle
		double currentTheta = thetaMin;
		
		//create a loop to put values into each space in the array of angles, using the thetaStep variable
		for (int i = 0; i < angles.length; i++) {
			angles[i] = currentTheta;
			currentTheta += thetaStep;
		}
		
		//initialize the "maximum ranges" variables
		double maxRange = 0;
		double maxRangeAngle = 0;
		
		//this is the important loop: where it tests each angle with the same velocity and height, and records its range.
		//If the range for that angle is more than the current maximum range, replace the maximum range with the current range.
		for (int i = 0; i < ranges.length; i++) {
			ranges[i] = PhysicsDistanceSolver.distance(v, height, angles[i]); 
			//PhysicsDistanceSolver is another program that I created to find how far a projectile would go given its
			//initial velocity, height, and angle
			if(ranges[i] >= maxRange) {
				maxRange = ranges[i];
				maxRangeAngle = angles[i];
			}
		}
		
		
		//tell the user what the maximum ranges and angles are
		System.out.println("max range: " + maxRange);
		System.out.println("max angle: " + maxRangeAngle);
		
	}

}
