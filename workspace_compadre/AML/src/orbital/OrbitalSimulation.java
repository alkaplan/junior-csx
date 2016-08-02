package orbital;

import java.awt.Color;
import java.text.DecimalFormat;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.BoundedShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.*;

public class OrbitalSimulation extends AbstractSimulation {
	
	static DisplayFrame frame = new DisplayFrame("X", "Y", "Orbital");
	Planet p1;
	Planet p2;
	Trail t1;
	Trail t2;
	static double minX;
	static double maxX;
	static double minY;
	static double maxY;
	double x0;
	double y0;
	static int revolutions = 0;
	static double time;
	static double period;
	static String output;
	static String firstLaw;
	static String secondLaw;
	static String thirdLaw;

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
		
		//calculate areal velocity:
		arealVelocity(p1, p2, timestep);
		
		p1.xCoord += p1.Vx * timestep;
		p1.yCoord += p1.Vy * timestep;
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
		
		//System.out.println("("  + p2.xCoord + ", " + p2.yCoord + ")");
		//System.out.println(Math.round(p2.xCoord/1E9) + " X " + Math.round(x0/1E9));
		//System.out.println(Math.round(p2.yCoord/1E9) + " Y " + Math.round(y0/1E9));
		//System.out.println(maxX + "\n" + minX + "\n" + maxY + "\n" + minY);
		
		
		if(time > timestep *5 && Math.round(p2.xCoord/1E9) == Math.round(x0/1E9) && Math.round(p2.yCoord/1E10) == Math.round(y0/1E10)) {
			double periodtest = 2 * Math.PI * Math.sqrt((Math.pow(semiMajorAxis(),3))/(m1*G));
			if(revolutions == 0) {
				period = time;
				//System.out.println(time);
				//System.out.println(periodtest);
				//System.out.println();
				//System.out.println(timestep);
			}
			revolutions++;
			createEllipse(p1, p2);
			periodVMajor(period, m1, r, G);
			System.out.println("First Law: " + firstLaw);
			System.out.println("Areal velocity: " + secondLaw);
			System.out.println("Proportion of period to axis vs GM/4pi^2: " + thirdLaw);
		}
		
		p1.setCoords();
		p2.setCoords();
		
		t1.addPoint(p1.xCoord, p1.yCoord);
		t2.addPoint(p2.xCoord, p2.yCoord);
			
		if(revolutions > 1) {
			//System.out.println(output);
		}
		
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
		t2 = new Trail();
		
		t1.color = Color.black;
		t2.color = Color.gray;
		
		p1.setCoords();
		p2.setCoords();
		
		x0 = p2.xCoord;
		y0 = p2.yCoord;
		
		time = 0;
		
		maxX = 0;
		maxY = 0;
		minX = 0;
		minY = 0;
		
		frame.addDrawable(p1);
		frame.addDrawable(p2);
		
		frame.addDrawable(t1);
		frame.addDrawable(t2);
		
		frame.setPreferredMinMax(-5E11, 5E11, -5E11, 5E11);
		
		frame.setVisible(true);
		
		System.out.println("Welcome to orbital.  This program will prove three laws. They are:");
		System.out.println("1. Planets orbit in ellipses. This will be proved by solving the ellipse equation for the current"
				+ "x- and y-coordinates of the planet and derived center and axes. If the resulting number is equal to 1, Kepler's"
				+ "first law is correct.");
		System.out.println();
		System.out.println("2. Areal velocity is constant. This will be shown by calculating areal velocity after each timestep.");
		System.out.println();
		System.out.println("3. The period squared of an orbit is proportional to the semi-major axis cubed. This program will print out"
				+ "their proportion, and then the proportion of (GM_1)/(4pi^2), which Kepler's 3rd law says should equal a^3/t^2.");
		
		
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
		control.setValue("Vy planet 2", 30000);
		control.setValue("timestep", 100000.0);
		control.setValue("G", 6.67E-11);
		
	}
	
	public void stop() {
		
	}
	
	public static void main(String[] args) {
		SimulationControl.createApp(new OrbitalSimulation());
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
	
	static void createEllipse(Planet p1, Planet p2) {
		if(revolutions > 0) {
			
			double h = ((maxX + minX)/2);
			double k = ((maxY + minY)/2);
			double a = (maxX - h);
			double b = (maxY - k);
			
			BoundedShape ellipse = BoundedShape.createBoundedEllipse(Math.abs(h)
					,Math.abs(k),(2*a),(2*b));
			
			//System.out.println("h: " + h + "\n k: " + k + "\n a: " + a + "\n b: " + b);
			
			frame.addDrawable(ellipse);
			
			firstLaw = 
			"" + ((Math.pow((p2.xCoord - h), 2))/(Math.pow(a, 2)) + (Math.pow((p2.yCoord - k), 2))/(Math.pow(b, 2)));
			
			
			//System.out.println(Math.round((Math.pow((p2.xCoord - h), 2))/(Math.pow(a, 2)) + (Math.pow((p2.yCoord - k), 2))/(Math.pow(b, 2))));
		}
	}
	
	static double semiMajorAxis() {
		double h = ((maxX + minX)/2);
		double a = (maxX - h);
		return a;
	}
	
	static void arealVelocity(Planet p1, Planet p2, double t) {
		double Ax = p2.xCoord;
		double Ay = p2.yCoord;
		double Bx = p2.xCoord + (p2.Vx * t);
		double By = p2.yCoord + (p2.Vy * t);
		double Ox = p1.xCoord;
		double Oy = p1.yCoord;
		
		double area = (Ax * (By-Oy) + Bx*(Oy - Ay) + Ox*(Ay- By))/2; 
		
		secondLaw = "" + area;
		
	}
	
	static void periodVMajor(double period, double m1, double r, double G) {
		double a = semiMajorAxis();
		double t = period;
		//System.out.println(a + "     "  + t);
		double proportion = (Math.pow(a, 3))/(Math.pow(t, 2));
		double otherProportion = (G*m1)/(4 * Math.pow(Math.PI,2));
//		System.out.println("-------\n" + G + "\n" + m1 + "\n" + r + "\n" + period + "\n --------");
//		System.out.println(proportion);
//		System.out.println(otherProportion);
		String equal = "!";
		if(Math.round(proportion/1E5) == Math.round(otherProportion/1E5)) {
			equal = "=";
		}
		else if(Math.round(proportion) == Math.round(otherProportion)) {
			equal = "~";
		}
		thirdLaw = "" + proportion + equal + otherProportion;
	}

}
