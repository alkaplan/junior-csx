import java.util.Scanner;

public class PhysicsDistanceSolver {
	
	static final double g = -9.806;

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);


		while(true) {

			System.out.println("Initial Velocity");
			double V0 = scan.nextDouble();
			System.out.println("Height");
			double height = scan.nextDouble();
			System.out.println("Angle");
			double angle = scan.nextDouble();

			System.out.println("x distance: " + distance(V0, height, angle));

			System.out.println();
			System.out.println("-------------------");
			System.out.println();
		}

	}

	public static double distance(double V, double height, double angle) {
		double angleRad = Math.toRadians(angle);

		double Vx = V * Math.cos(angleRad);
		double Vy = V * Math.sin(angleRad);

		double t = ((-1*Vy) - Math.sqrt(Math.pow(Vy, 2) - (2*g*height)))/g;
		double xDistance = Vx * t;

		return xDistance;
	}

}
