package orbital;

import java.awt.Color;
import java.text.DecimalFormat;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.BoundedShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.*;

public class OrbitalLagrangeSimulation extends AbstractSimulation {

	static DisplayFrame frame = new DisplayFrame("X", "Y", "Orbital");
	Planet p1;
	Planet p2;
	Planet[] lagrange;
	static double time;
	static double period;
	static double G;
	static String output;
	static String firstLaw;
	static String secondLaw;
	static String thirdLaw;
	Trail t1;
	Trail t2;

	protected void doStep() {

		double m1 = control.getDouble("mass planet 1");
		double m2 = control.getDouble("mass planet 2");

		double timestep = control.getDouble("timestep");

		time += timestep;

		double G = control.getDouble("G");

		//find distance btw planets
		double r = distance(p1, p2);

		//find Fg on each planet (this is the same for both)
		double Fg = forcegravity(m1, m2, r, G);

		double theta = angle(p1, p2);

		double Fgx = Fg * Math.cos(theta);
		double Fgy = Fg * Math.sin(theta);

		double Axp1 = Fgx/m1;
		double Ayp1 = Fgy/m1;

		double Axp2 = Fgx/m2;
		double Ayp2 = Fgy/m2;

		double Vxp1 = p1.Vx;
		double Vyp1 = p1.Vy;


		double Vxp2 = p2.Vx;
		double Vyp2 = p2.Vy;

		Vxp1 += Axp1 * timestep;
		Vyp1 += Ayp1 * timestep;
		Vxp2 += Axp2 * timestep;
		Vyp2 += Ayp2 * timestep;


		p1.Vx = Vxp1;
		p1.Vy = Vyp1;
		p2.Vx = Vxp2;
		p2.Vy = Vyp2;

		p1.xCoord += p1.Vx * timestep;
		p1.yCoord += p1.Vy * timestep;
		p2.xCoord += p2.Vx * timestep;
		p2.yCoord += p2.Vy * timestep;


		for (int i = 0; i < lagrange.length; i++) {
			double fGL1Mag = forcegravity(m1, lagrange[i].mass, distance(p1, lagrange[i]),G);
			double fGL2Mag = forcegravity(m2, lagrange[i].mass, distance(p2, lagrange[i]),G);

			System.out.println(fGL1Mag + "\n" + fGL2Mag);

			double L1Theta = angle(p1, lagrange[i]);
			double L2Theta = angle(p2, lagrange[i]);

			double fGL1X = fGL1Mag * Math.cos(L1Theta);
			double fGL1Y = fGL1Mag * Math.sin(L1Theta);
			double fGL2X = fGL2Mag * Math.cos(L2Theta);
			double fGL2Y = fGL2Mag * Math.sin(L2Theta);

			double fGTotX = fGL1X + fGL2X;
			double fGTotY = fGL1Y + fGL2Y;

			double ax = fGTotX/lagrange[i].mass;
			double ay = fGTotY/lagrange[i].mass;

			lagrange[i].Vx += ax * timestep;
			lagrange[i].Vy += ay * timestep;

			lagrange[i].xCoord += lagrange[i].Vx * timestep;
			lagrange[i].yCoord += lagrange[i].Vy * timestep;

			//System.out.println(lagrange[i].xCoord + "\n" + lagrange[i].yCoord);

			lagrange[i].setCoords();
			
			if(i == 3) {
				t1.addPoint(lagrange[i].xCoord, lagrange[i].yCoord);
			}
			if(i == 4) {
				t2.addPoint(lagrange[i].xCoord, lagrange[i].yCoord);
			}
			
		}

		p1.setCoords();
		p2.setCoords();


	}

	public void initialize() {
		p1 = new Planet(
				control.getDouble("mass planet 1"), 
				control.getDouble("x coord planet 1"),
				control.getDouble("y coord planet 1"), 
				control.getDouble("Vx planet 1"), 
				control.getDouble("Vy planet 1"));

		p2 = new Planet(
				control.getDouble("mass planet 2"),
				control.getDouble("x coord planet 2"),
				control.getDouble("y coord planet 2"),
				control.getDouble("Vx planet 2"),
				control.getDouble("Vy planet 2"));

		p1.color = Color.YELLOW;
		p2.color = Color.blue;
		
		t1 = new Trail();
		t1.color = Color.black;
		
		t2 = new Trail();
		t2.color = Color.black;

		G = control.getDouble("G");

		p1.setCoords();
		p2.setCoords();
		
		double period = (2 * Math.PI)/Math.sqrt(Math.pow(p2.Vx, 2) + Math.pow(p2.Vy,2));

		double semiMajorAxis = 1.496E8;

		lagrange = new Planet[5];

		double m1 = control.getDouble("mass planet 1"); 
		double m2 = control.getDouble("mass planet 2");

		double lagrangeMass = control.getDouble("lagrange mass");
		double R = distance(p1, p2);

		/* mass, xCoord, yCoord, Vx, Vy*/

		lagrange[0]  = new Planet(
				lagrangeMass,
				l1V(p1.mass, p2.mass, p1.xCoord, p2.xCoord),
				0,
				0,
				0 //29451.86339315331
				);

		lagrange[1] = new Planet(
				lagrangeMass,
				l2V(p1.mass, p2.mass, p1.xCoord, p2.xCoord),
				0,
				0,
				0 //30040.89127706062
				);

		lagrange[2] = new Planet(
				lagrangeMass,
				l3V(p1.mass, p2.mass, p1.xCoord, p2.xCoord),
				0,
				0,
				0 //-29745.354791694088
				);


		lagrange[3] = new Planet(
				lagrangeMass,
				1.5E11 * Math.cos(Math.toRadians(120)),
				1.5E11 * Math.sin(Math.toRadians(120)),
				-29745.354791694088 * Math.cos(Math.toRadians(-30)),
				29745.354791694088 * Math.sin(Math.toRadians(-30))
				);

		lagrange[4] = new Planet(
				lagrangeMass,
				1.5E11 * Math.cos(Math.toRadians(60)),
				-1.5E11 * Math.sin(Math.toRadians(60)),
				29745.354791694088 * Math.cos(Math.toRadians(30)),
				29745.354791694088 * Math.sin(Math.toRadians(30))
				);
		
		double com = oneDCOM(p1.xCoord, p2.xCoord, p1.mass, p2.mass);
		
//		for (int i = 0; i < lagrange.length; i++) {
//			double vl = velocity(lagrange[i].xCoord, lagrange[i].yCoord, com, period);
//			double vlx = Math.cos(angle(p1,lagrange[i])) * vl;
//			double vly = Math.sin(angle(p1,lagrange[i])) * vl;
//			lagrange[i].Vx = vlx;
//			lagrange[i].Vy = vly;
//		}

		for (int i = 0; i < lagrange.length; i++) {
			lagrange[i].trail = new Trail();
			lagrange[i].setCoords();
			frame.addDrawable(lagrange[i]);
			frame.addDrawable(lagrange[i].t);
		}
		
		frame.addDrawable(t1);
		frame.addDrawable(t2);



		time = 0;

		frame.addDrawable(p1);
		frame.addDrawable(p2);


		frame.setPreferredMinMax(-5E11, 5E11, -5E11, 5E11);

		frame.setVisible(true);		

	}

	public void reset() {
		control.setValue("mass planet 1", 1.989E30);
		control.setValue("mass planet 2", 5.792E24);
		control.setValue("x coord planet 1", 0);
		control.setValue("y coord planet 1", 0);
		control.setValue("x coord planet 2", 1.5E11);
		control.setValue("y coord planet 2", 0);
		control.setValue("Vx planet 1", 0);
		control.setValue("Vy planet 1", 0);
		control.setValue("Vx planet 2", 0);
		control.setValue("Vy planet 2", 29745.354791694088);
		control.setValue("lagrange mass", 1000);
		control.setValue("timestep", 100000.0);
		control.setValue("G", 6.67E-11);

	}

	public void stop() {

	}

	public static void main(String[] args) {
		SimulationControl.createApp(new OrbitalLagrangeSimulation());
	}

	static double distance(Planet planet1, Planet planet2) {
		double dist;

		double xDist = Math.abs(planet1.xCoord - planet2.xCoord);

		double yDist = Math.abs(planet1.yCoord - planet2.yCoord);

		dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

		return dist;
	}

	static double forcegravity(double m1, double m2, double r, double g) {
		double force;

		force = (g*(m1*m2))/(Math.pow(r, 2));

		return force;
	}

	public static double oneDCOM(double x1, double x2, double m1, double m2) {

		double Xcm = ((m1 * x1) + (m2 * x2))/(m1 + m2);

		return Xcm;
	}
	
	static double velocity(double xcoord, double ycoord, double COM, double period) {
		
		double r = distancePoints(xcoord, ycoord, COM, 0);
		
		double velocity = (Math.PI * 2 * r)/period;
		
		return velocity;
	}
	
	static double distancePoints(double x1, double y1, double x2, double y2) {
		
		double dist = Math.sqrt(Math.pow((x2 - x1),2) + Math.pow((y2 - y1),2));
		
		return dist;
		
	}

	static double angle(Planet planet1, Planet planet2) {

		double xDist = planet1.xCoord - planet2.xCoord;
		double yDist = planet1.yCoord - planet2.yCoord;

		double theta = Math.atan2(yDist, xDist); //in radians

		return theta;
	}

	static double l1V(double m1, double m2, double coord1, double coord2) {

		double COM = oneDCOM(coord1, coord2, m1, m2);

		double r1 =	Math.abs(COM - coord1);
		double r2 = Math.abs(COM - coord2);

		double b = m2/(m1 + m2);

		double xcoord = (Math.abs(coord2 - coord1) * (1 - Math.pow((b/3),(1/3))));

		return xcoord;
	}

	static double l2V(double m1, double m2, double coord1, double coord2) {

		double COM = oneDCOM(coord1, coord2, m1, m2);

		double r1 =	Math.abs(COM - coord1);
		double r2 = Math.abs(COM - coord2);

		double b = m2/(m1 + m2);

		double xcoord = (Math.abs(coord2 - coord1) * (1 + Math.pow((b/3),(1/3))));

		return xcoord;
	}

	static double l3V(double m1, double m2, double coord1, double coord2) {

		double COM = oneDCOM(coord1, coord2, m1, m2);

		double r1 =	Math.abs(COM - coord1);
		double r2 = Math.abs(COM - coord2);

		double b = m2/(m1 + m2);

		double xcoord = (-1 * Math.abs(-r1 + r2)) * (1+ b * (5/12));

		return xcoord;
	}

	static double[] l4V(double m1, double m2, double coord1, double coord2) {

		double COM = oneDCOM(coord1, coord2, m1, m2);

		double r1 =	Math.abs(COM - coord1);
		double r2 = Math.abs(COM - coord2);

		double b = m2/(m1 + m2);
		double c = m1/(m1 + m2);

		double xcoord = ((Math.abs(-r1 + r2))/2) * (c-b);
		double ycoord = Math.abs(-r1 + r2) * ((Math.sqrt(3))/2);

		double[] coords = {xcoord, ycoord};
		
		return coords;
	}

	static double[] l5V(double m1, double m2, double coord1, double coord2) {

		double COM = oneDCOM(coord1, coord2, m1, m2);

		double r1 =	Math.abs(COM - coord1);
		double r2 = Math.abs(COM - coord2);

		double b = m2/(m1 + m2);
		double c = m1/(m1 + m2);

		double xcoord = ((Math.abs(-r1 + r2))/2) * (c-b);
		double ycoord = -1 * Math.abs(-r1 + r2) * ((Math.sqrt(3))/2);

		double[] coords = {xcoord, ycoord};
		
		return coords;
	}

}
