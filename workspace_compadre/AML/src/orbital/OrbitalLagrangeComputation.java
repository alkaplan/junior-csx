package orbital;

import java.awt.Color;

import useful.StepTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.BoundedShape;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.*;

public class OrbitalLagrangeComputation extends AbstractSimulation {

	DisplayFrame frame = new DisplayFrame("X", "Y", "Orbital");
	Planet p1;
	Planet p2;
	Trail t1;
	Trail t2;
	double x0;
	double y0;
	static double minX;
	static double maxX;
	static double minY;
	static double maxY;
	double time;
	int revolutions;
	Circle COM;
	Circle[] L;

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

		//double Axp1 = Fgx/m1;
		//double Ayp1 = Fgy/m1;

		double Axp2 = Fgx/m2;
		double Ayp2 = Fgy/m2;

		//double Vxp1 = p1.Vx;
		//double Vyp1 = p1.Vy;

		double Vxp2 = p2.Vx;
		double Vyp2 = p2.Vy;

		//Vxp1 += Axp1 * timestep;
		//Vyp1 += Ayp1 * timestep;
		Vxp2 += Axp2 * timestep;
		Vyp2 += Ayp2 * timestep;

		//p1.Vx = Vxp1;
		//p1.Vy = Vyp1;
		p2.Vx = Vxp2;
		p2.Vy = Vyp2;

		//p1.xCoord += p1.Vx * timestep;
		//p1.yCoord += p1.Vy * timestep;
		p2.xCoord += p2.Vx * timestep;
		p2.yCoord += p2.Vy * timestep;

		if(Math.round(p2.xCoord/1E5) > Math.round(maxX/1E5)) {
			maxX = p2.xCoord;
		}
		if(Math.round(p2.xCoord/1E5) < Math.round(minX/1E5)) {
			minX = p2.xCoord;
		}
		if(Math.round(p2.yCoord/1E5) > Math.round(maxY/1E5)) {
			maxY = p2.yCoord;
		}
		if(Math.round(p2.yCoord/1E5) < Math.round(minY/1E5)) {
			minY = p2.yCoord;
		}

		if(time > timestep *5 && Math.round(p2.xCoord/1E9) == Math.round(x0/1E9) 
				&& Math.round(p2.yCoord/1E10) == Math.round(y0/1E10)) {
			revolutions++;
		}

		double period = 2 * Math.PI * Math.sqrt((Math.pow(semiMajorAxis(),3))/((m1+m2)*G));

		double[][] points = new double[2][5];
		points = findLagrangePoints(m1,m2,G,period,p1,p2);

		for (int i = 0; i < L.length; i++) {
			L[i].setXY(points[0][i], points[1][i]);
		}

		double COMX = centerOfMass(p1,p2,m1,m2)[0];
		double COMY = centerOfMass(p1,p2,m1,m2)[1];
		COM.setXY(COMX, COMY);
		
		p1.setCoords();
		p2.setCoords();

		t1.addPoint(p1.xCoord, p1.yCoord);
		t2.addPoint(p2.xCoord, p2.yCoord);

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

		x0 = p2.xCoord;
		y0 = p2.yCoord;

		t1 = new Trail();
		t2 = new Trail();

		L = new Circle[5];

		for (int i = 0; i < L.length; i++) {
			L[i] = new Circle();
			frame.addDrawable(L[i]);
		}

		t1.color = Color.black;
		t2.color = Color.gray;

		p1.setCoords();
		p2.setCoords();
		
		COM = new Circle();
		COM.setX(oneDCOM(p1.xCoord,p2.xCoord,p1.mass,p2.mass));
		COM.setY(oneDCOM(p1.yCoord,p2.yCoord,p1.mass,p2.mass));
		COM.color = Color.cyan;
		frame.addDrawable(COM);


		frame.addDrawable(p1);
		frame.addDrawable(p2);

		frame.addDrawable(t1);
		frame.addDrawable(t2);

		
		frame.setPreferredMinMax(-5E11, 5E11, -5E11, 5E11);

		frame.setVisible(true);


	}

	public void reset() {
		control.setValue("mass planet 1", 1.989E30);
		control.setValue("mass planet 2", 1E30);
		control.setValue("x coord planet 1", 0);
		control.setValue("y coord planet 1", 0);
		control.setValue("x coord planet 2", 1.5E11);
		control.setValue("y coord planet 2", 0);
		control.setValue("Vx planet 1", 0);
		control.setValue("Vy planet 1", 0);
		control.setValue("Vx planet 2", 0);
		control.setValue("Vy planet 2", 30000);
		control.setValue("timestep", 100000.0);
		control.setValue("G", 6.67E-11);

	}

	public void stop() {

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

	static double angle(Planet planet1, Planet planet2) {

		double xDist = planet1.xCoord - planet2.xCoord;
		double yDist = planet1.yCoord - planet2.yCoord;

		double theta = Math.atan2(yDist, xDist); //in radians

		return theta;
	}

	static double anglePoints(double x1, double y1, double x2, double y2) {

		double xDist = Math.abs(x1 - x2);
		double yDist = Math.abs(y1 - y2);

		double theta = Math.atan2(yDist, xDist);

		if(y2 > y1) {
			theta = ((Math.PI)/2 - theta);
		}

		return theta;
	}

	static double semiMajorAxis() {
		double h = ((maxX + minX)/2);
		double a = (maxX - h);
		return a;
	}

	static double distancePlanetPoint(Planet p, double x, double y) {
		double xDist = Math.abs(p.xCoord - x);
		double yDist = Math.abs(p.yCoord - y);

		double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

		return dist;
	}

	public double[][] findLagrangePoints(double m1, double m2, double G, 
			double period, Planet planet1, Planet planet2) {

		double xMax = 2 * maxX;
		double xMin = 2 * minX;
		double yMax = 2 * maxY;
		double yMin = 2 * minY;

		int numToTest = 50;

		double[] xValsToTest = StepTest.arithArr(xMax, xMin, numToTest);
		double[] yValsToTest = StepTest.arithArr(yMax, yMin, numToTest);

		/*
		Circle[] showTestPoints = new Circle[numToTest*numToTest];
		for (int i = 0; i < numToTest; i++) {
			for (int j = 0; j < numToTest; j++) {
				showTestPoints[i] = new Circle();
				showTestPoints[i].setXY(xValsToTest[i], yValsToTest[j]);
				frame.addDrawable(showTestPoints[i]);
			}
		}
		 */

		ArrayList<Double> xVals = new ArrayList<Double>();
		ArrayList<Double> yVals = new ArrayList<Double>();

		Point[][] points = new Point[numToTest][numToTest];
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[0].length; j++) {
				points[i][j] = new Point(xValsToTest[i],yValsToTest[j],
						lagranginees(G, m1, m2, xValsToTest[i],yValsToTest[j],planet1,planet2,period));
			}
		}
		
		Point[] oneDPoints = new Point[numToTest*numToTest];

		int x = 0;
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[0].length; j++) {
				oneDPoints[x] = new Point(points[i][j].xCoord, points[i][j].yCoord,points[i][j].lVal);
				x++;
			}
		}

		Point[] sortedPoints = insertionSortPoints(oneDPoints);

		/*
		System.out.println("--------");
		for (int i = 0; i < sortedPoints.length; i++) {
			System.out.println(sortedPoints[i].lVal);
		}
		System.out.println("--------");
		 */

		for (int i = 0; i < 5; i++) {
			xVals.add(sortedPoints[i].xCoord);
			yVals.add(sortedPoints[i].yCoord);
		}

		double[][] answers = new double[2][5];

		for (int i = 0; i < 5; i++) {
			answers[0][i] = xVals.get(i);
			answers[1][i] = yVals.get(i);
			//System.out.println("x" + i + ": " + xVals.get(i));
			//System.out.println("y" + i + ": " + yVals.get(i));
		}
		return answers;
	}

	public static double lagranginees(double G, double m1, double m2, double x, double y, Planet planet1, 
			Planet planet2, double period) {

		double rSL = distancePlanetPoint(planet1, x, y);
		double rEL = distancePlanetPoint(planet2, x, y);
		

		double FgSL = (G * m1)/(Math.pow(rSL, 2));
		double FgEL = (G * m2)/(Math.pow(rEL, 2));
		
		//Vector FgS = new Vector(FgSL, thetaSL);
		//Vector FgE = new Vector(FgEL, thetaEL);


		double FgTOT;

		double FgSX = xComponent(x, y, planet1.xCoord, planet1.yCoord, FgSL);
		double FgSY = yComponent(x, y, planet1.xCoord, planet1.yCoord, FgSL);
		double FgEX = xComponent(x, y, planet2.xCoord, planet2.yCoord, FgEL);
		double FgEY = yComponent(x, y, planet2.xCoord, planet2.yCoord, FgEL);

		double FgTOTX = FgSX + FgEX;
		double FgTOTY = FgSY + FgEY;

		FgTOT = Math.sqrt(Math.pow(FgTOTX, 2) + Math.pow(FgTOTY, 2));

		double COMX = centerOfMass(planet1, planet2, m1, m2)[0];
		double COMY = centerOfMass(planet1, planet2, m1, m2)[1];

		//find Fr.  MAGNITUDE DOES NOT MATTER, ONLY DIRECTION. You will see why shortly.

		double FrX = COMX - x;
		double FrY = COMY - y;

		double FrTOT = Math.sqrt(Math.pow(COMX - x, 2) + Math.pow(COMY - y, 2));

		//tricky vector dot product part: we are finding Va • Vb = |Va||Vb|cos(theta), then dividing both sides
		//by |Va||Vb| and checking if cos theta ~ 1.

		double leftSide; //dot product of vector A (FGTOT) and vector B (Fr)
		double rightSide; //Scalar product of magnitude of FGTOT * magnitude of Fr (no costheta in this)
		leftSide = (FgTOTX * FrX) + (FgTOTY * FrY);
		rightSide = FgTOT * FrTOT;

		double direction = leftSide/rightSide; //leftside/rightside = costheta, if theta = 0, cos = 1
				
		double distanceToCOM = Math.sqrt(Math.pow(x - COMX,2) + Math.pow(y - COMY,2));
		
		double FrFinal = 1 * Math.pow(((2 * Math.PI)/period),2) * distanceToCOM;
		
		double finalLVal = 10000;
		
		System.out.println(leftSide);
		System.out.println(rightSide);
		
		//if it's in the right direction, check the magnitude
		if(direction > 0.98 && direction < 1.02) {
			finalLVal = FgTOT - FrFinal;
			System.out.println("--");
			System.out.println(finalLVal);
			System.out.println("--");
		}
		
		return finalLVal;

	}

	public static double xComponent(double x1, double y1, double x2, double y2, double magnitude) {

		double theta = anglePoints(x1, y1, x2, y2);

		double comp = magnitude * Math.cos(theta);

		return comp;
	}

	public static double yComponent(double x1, double y1, double x2, double y2, double magnitude) {

		double theta = anglePoints(x1, y1, x2, y2);

		double comp = magnitude * Math.sin(theta);

		return comp;
	}

	public static double[] centerOfMass(Planet planet1, Planet planet2, double m1, double m2) {
		double xCoord = oneDCOM(planet1.xCoord, planet2.xCoord, m1, m2);
		double yCoord = oneDCOM(planet1.yCoord, planet2.yCoord, m1, m2);

		double[] coords = {xCoord, yCoord};

		return coords;
	}

	public static double oneDCOM(double x1, double x2, double m1, double m2) {

		double Xcm = ((m1 * x1) + (m2 * x2))/(m1 + m2);

		return Xcm;
	}

	public static Point[] insertionSortPoints(Point[] p) {
		Point temp;
		for (int i = 0; i < p.length; i++) {
			for (int j = i; j > 0; j--) {
				if(p[j].lVal < p[j-1].lVal) {
					temp = p[j];
					p[j] = p[j-1];
					p[j-1] = temp;
				}
			}
		}
		return p;
	}

	public static void main(String[] args) {		
		SimulationControl.createApp(new OrbitalLagrangeComputation());
	}

}
