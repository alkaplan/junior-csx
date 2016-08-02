package riemannSum;

import java.util.Scanner;

import polyfun.Polynomial;
import riemannSum.*;

public class RSTest 
{
	public static void main(String[] args) 
	{
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Which method would you like to implement? Pick a number:");
		System.out.println("1: LeftHand Rule");
		System.out.println("2: RightHand Rule");
		System.out.println("3: Trapezoid Rule");
		System.out.println("4: Midpoint Rule");
		System.out.println("5: Minimum");
		System.out.println("6: Maximum");
		System.out.println("7: Random");
		System.out.println("8: Simpsons");
		System.out.println("9: Ellipse Rule (by Alex)");
		
		int input = scan.nextInt();
		
		LeftHandRule LHR = new LeftHandRule();  //LeftHandRule extends Riemann
		RightHandRule RHR = new RightHandRule();  //RightHandRule extends Riemann
		TrapezoidRule TR= new TrapezoidRule(); //TrapezoidRule extends Riemann
		MidpointRule MDP = new MidpointRule(); 
		MinimumRule MIN = new MinimumRule();
		MaximumRule MAX = new MaximumRule();
		RandomRule RAN = new RandomRule();
		SimpsonsRule SIM = new SimpsonsRule();
		EllipseRule ELL = new EllipseRule();

		Polynomial p = new Polynomial(new double[] {0,0,3}); //p=3x^2

		if(input == 1) {
			System.out.println(LHR.rs(p, 0, 1, 2000)); // the left hand rule from x=0 to x=2, with 10 rectangles      
		}
		else if(input == 2) {
			System.out.println(RHR.rs(p, 0, 1, 2000));        		
		}
		else if(input == 3) {
			System.out.println(TR.rs(p, 0, 1, 2000));        		
		}
		else if(input == 4) {
			System.out.println(MDP.rs(p, 0, 1, 2000));		
		}
		else if(input == 5) {
			System.out.println(MIN.rs(p, 0, 1, 2000));
		}
		else if(input == 6) {
			System.out.println(MAX.rs(p, 0, 2, 10));
		}
		else if(input == 7) {
			System.out.println(RAN.rs(p, 0, 2, 10));
		}
		else if(input == 8) {
			System.out.println(SIM.rs(p, 0, 2, 10));
		}
		else if(input == 9) {
			System.out.println(ELL.rs(p, 0, 2, 10));
		}
		
		else {
			System.out.println("Not yet implemented!");
		}
	}
}