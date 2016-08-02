package springs;

import java.awt.Color;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.DisplayFrame;

public class StringLongitudeWave extends AbstractSimulation {

	SpringParticle[] string;
	double restDistance;
	static double time = 0.001;
	static double timeElapsed = 0;
	static double amplitude = 2;
	static double period = 40;
	static double startingY;
	static double startingX;
	static double maxX;
	DisplayFrame frame = new DisplayFrame("X", "Y", "String Longitude Wave");

	protected void doStep() {

		for (int i = 0; i < string.length; i++) {

			double Fx1 = 0;
			double Fx2 = 0;
			double Fy1 = 0;
			double Fy2 = 0;

			if(i != (string.length-1)) {
				Fx1 = xForce(string[i], string[i+1]);
				Fy1 = yForce(string[i], string[i+1]);
			}

			if(i != 0) { 
				Fx2 = xForce(string[i], string[i-1]);
				Fy2 = yForce(string[i], string[i-1]);
			}

			double Fxtot = Fx1 + Fx2;
			double Fytot = Fy1 + Fy2;

			double ax = Fxtot/(string[i].mass);
			double ay = Fytot/(string[i].mass);

			string[i].vx += ax * time;
			string[i].vy += ay * time;
			
			System.out.println("---");
			System.out.println(i);
			System.out.println(Fx1);
			System.out.println(Fx2);
			System.out.println(Fy1);
			System.out.println(Fy2);
			System.out.println(ax);
			System.out.println(ay);

		}

		for (int i = 1; i < string.length-1; i++) {
			string[i].x += string[i].vx * time;
			string[i].y += string[i].vy * time;
			string[i].setXY(string[i].x, string[i].y);
		}
		
		string[0].y = startingY;
		string[0].x = amplitude * Math.sin(timeElapsed * period);
		string[0].setXY(string[0].x, string[0].y);
		
		string[string.length-1].x = maxX;
		string[string.length-1].y = startingY;
		string[string.length-1].setXY(string[string.length-1].x, string[string.length-1].y);
		
		
		
		timeElapsed += time;

	}

	public void initialize() {
		
		frame.clearDrawables();

		time = control.getDouble("time step");
		amplitude = control.getDouble("wave amplitude");
		period = control.getDouble("period");
		int numParticles = (int)control.getDouble("number of particles");
		startingX = control.getDouble("minimum x");
		maxX = control.getDouble("maximum x");
		startingY = control.getDouble("starting y");
		double mass = control.getDouble("particle mass");
		double kTot = control.getDouble("total k");
		
		
		double step = (maxX - startingX)/numParticles;
		
		int colorStep = (int)255/numParticles;
				
		restDistance = step;
		
		double kEach = 4000;
		
		this.delayTime = 10;
		
		string = new SpringParticle[numParticles];
		
		for (int i = 0; i < string.length; i++) {
			string[i] = new SpringParticle(mass, kEach, i * step, startingY, 0, 0);
			string[i].setXY(string[i].x, string[i].y);
			string[i].color = new Color(255 - colorStep * i, colorStep * i, 0);
			frame.addDrawable(string[i]);
		}
		
		maxX = string[string.length-1].x;

		frame.setVisible(true);
		frame.setPreferredMinMax(-5, 115, 5, 15);

	}

	/**
	 * finds individual k given total k
	 * @param kTot
	 * @param numParticles
	 * @return
	 */
	private double findK(double kTot, int numParticles) {
		return kTot * numParticles;
	}

	public void reset() {
		control.setValue("time step", 0.001);
		control.setValue("number of particles", 100);
		control.setValue("minimum x", 0);
		control.setValue("maximum x", 100);
		control.setValue("starting y", 10);
		control.setValue("wave amplitude", 2);
		control.setValue("period", 40);
		control.setValue("particle mass", 1);
		control.setValue("total k", 24);
	}

	public void stop() {

	}

	/**
	 * calculates the spring force on particle 1 due to particle 2
	 * @param part1
	 * @param part2
	 * @return
	 */
	public double xForce(SpringParticle part1, SpringParticle part2) {

		double dist = distance(part1, part2);

		double yDist = part2.y - part1.y;
		double xDist = part2.x - part1.x;

		double angle = Math.atan2(yDist, xDist);

		double totalForce = part1.k * dist;

		double xForce = totalForce * Math.cos(angle);

		return xForce;
	}

	public double yForce(SpringParticle part1, SpringParticle part2) {

		double dist = distance(part1, part2);

		double yDist = part2.y - part1.y;
		double xDist = part2.x - part1.x;

		double angle = Math.atan2(yDist, xDist);

		double totalForce = part1.k * dist;

		double yForce = totalForce * Math.sin(angle);

		return yForce;

	}

	public double distance(SpringParticle a, SpringParticle b) {

		double dist = Math.sqrt(Math.pow((a.x - b.x),2) + Math.pow((a.y - b.y),2));

		return dist;
	}

	public static void main(String[] args) {
		SimulationControl.createApp(new StringLongitudeWave());
	}

}
