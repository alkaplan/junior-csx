import java.util.Scanner;


public class TestClass {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("input a");
		double a = scan.nextDouble();
		System.out.println("input b");
		double b = scan.nextDouble();
		System.out.println("input c");
		double c = scan.nextDouble();
		System.out.println("input x");
		double x = scan.nextDouble();
		Quadratic p = new Quadratic(a,b,c);
		
		System.out.println("axis: " + p.axisOfSymmetry() + "\n extremeValue: " + p.extremeValue() + 
				"\n f of 10: " + p.fof(x) + "\n max or min: " + p.maxOrMin() + "\n num roots: " + 
				p.numberOfRoots() + "\n real roots? : " + p.realRoots() + "\nzeroes: " +
				p.zeroes()[0] +", " + p.zeroes()[1]);
		
	}
}
