
package orbital;

public class LagrangeTester {
	public static void main(String[] args) {
		
		final double G = 6.67408E-11;
		
		Planet earth = new Planet(5.972E24, 1.5E11, 0, 0, 0);
		Planet sun   = new Planet(1.989E30, 0, 0, 0, 0);
		
		double R = OrbitalLagrangeComputation.distance(sun, earth);
		double m1 = sun.mass;
		double m2 = earth.mass;
		double semiMajorAxis = 1.496E8;
		double period = 2 * Math.PI * Math.sqrt((Math.pow(semiMajorAxis,3))/((m1+m2)*G));

		Point L1 = new Point(0,0,0);
		Point L2 = new Point(0,0,0);
		Point L3 = new Point(0,0,0);
		Point L4 = new Point(0,0,0);
		Point L5 = new Point(0,0,0);
		
		double l1r = earth.xCoord - (R * Math.pow((m2/(3 * m1)),(1/3)));
		L1.xCoord = l1r;
		
		double l2r = earth.xCoord + (R * Math.pow((m2/(3 * m1)),(1/3)));
		L2.xCoord = l2r;
		
		double l3r = R * (7 * m2)/(12 * m1);
		L3.xCoord = -1*(earth.xCoord - l3r);
		
		double l4x = findL4(sun, earth)[0];
		double l4y = findL4(sun, earth)[1];
		L4.xCoord = l4x;
		L4.yCoord = l4y;
		
		double l5x = findL5(sun, earth)[0];
		double l5y = findL5(sun, earth)[1];
		L5.xCoord = l5x;
		L5.yCoord = l5y;
		
		System.out.println("L1: " + OrbitalLagrangeComputation.lagranginees(G, m1, m2, L1.xCoord, 0, sun, earth, period));
		System.out.println("L2: " + OrbitalLagrangeComputation.lagranginees(G, m1, m2, L2.xCoord, 0, sun, earth, period));
		System.out.println("L3: " + OrbitalLagrangeComputation.lagranginees(G, m1, m2, L3.xCoord, 0, sun, earth, period));
		System.out.println("L4: " + OrbitalLagrangeComputation.lagranginees(G, m1, m2, L4.xCoord, L4.yCoord, sun, earth, period));
		System.out.println("L5: " + OrbitalLagrangeComputation.lagranginees(G, m1, m2, L5.xCoord, L5.yCoord, sun, earth, period));

		
	}
	
	static double[] findL4(Planet p1, Planet p2) {
		double sideLength = OrbitalLagrangeComputation.distance(p1, p2);
		double xCoord = p1.xCoord + sideLength/2;
		double yCoord = sideLength * Math.sqrt(3)/2;
		double[] coords = {xCoord, yCoord};	
		return coords;
	}
	
	static double[] findL5(Planet p1, Planet p2) {
		double sideLength = OrbitalLagrangeComputation.distance(p1, p2);
		double xCoord = p1.xCoord + sideLength/2;
		double yCoord = p1.yCoord - (sideLength * Math.sqrt(3)/2);
		double[] coords = {xCoord, yCoord};	
		return coords;
	}

}
