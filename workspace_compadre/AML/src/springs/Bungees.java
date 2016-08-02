package springs;

import java.awt.Color;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.DisplayFrame;

public class Bungees extends AbstractSimulation {

	SpringParticle[][] bungees;
	double restDistance;
	static double time = 0.001;
	static double timeElapsed = 0;
	static double height;
	static double g = 9.81;
	static double minHeight;
	DisplayFrame frame = new DisplayFrame("X", "Y", "String");

	protected void doStep() {

		for(int i = 0; i < bungees.length; i++) {
			for (int j = 0; j < bungees[0].length; j++) {

				double Fx1 = 0;
				double Fx2 = 0;
				double Fy1 = 0;
				double Fy2 = 0;

				if(i != (bungees[0].length-1)) {
					Fx1 = xForce(bungees[j][i], bungees[j][i+1]);
					Fy1 = yForce(bungees[j][i], bungees[j][i+1]);
				}
				else {
					Fx1 = 0;
					Fy1 = -1 * bungees[j][bungees[0].length-1].mass * g;
				}

				if(i != 0) { 
					Fx2 = xForce(bungees[j][i], bungees[j][i-1]);
					Fy2 = yForce(bungees[j][i], bungees[j][i-1]);
				}

				double Fxtot = Fx1 + Fx2;
				double Fytot = Fy1 + Fy2;

				double ax = Fxtot/(bungees[j][i].mass);
				double ay = Fytot/(bungees[j][i].mass);

				bungees[j][i].vx += ax * time;
				bungees[j][i].vy += ay * time;

				/*
			System.out.println("---");
			System.out.println(i);
			System.out.println(Fx1);
			System.out.println(Fx2);
			System.out.println(Fy1);
			System.out.println(Fy2);
			System.out.println(bungee[i].vx);
			System.out.println(bungee[i].vy);
				 */

			}
		}

		for (int j = 0; j < bungees.length; j++) {
			for (int i = 1; i < bungees[0].length; i++) {
				bungees[j][i].x += bungees[j][i].vx * time;
				bungees[j][i].y += bungees[j][i].vy * time;
				bungees[j][i].setXY(bungees[j][i].x, bungees[j][i].y);
			}		
		}

		for (int j = 0; j < bungees.length; j++) {
			bungees[j][0].x = 0;
			bungees[j][0].y = height;
		}

		timeElapsed += time;

		double totalK = (bungees[0][0].k)/(bungees[0].length);

		/*
		if(minHeight > bungees[bungees.length-1].y) {
			minHeight = bungees[bungees.length-1].y;
		}
		else {
			if(bungees[bungees.length-1].vy < 0.5 && bungees[bungees.length-1].vy > -0.5) {
				System.out.println("minimum height reached: " + minHeight + " k for that height: " + totalK);
			}
		}
		 */

	}

	public void initialize() {

		time = control.getDouble("time step");
		height = control.getDouble("height");

		int numParticles = (int)control.getDouble("number of particles");
		int numBungees = (int)control.getDouble("number of bungees");
		double mass = control.getDouble("total mass")/numParticles;
		double yStep = (control.getDouble("length"))/numParticles;
		double kEach = control.getDouble("k");
		double spacing = control.getDouble("bungee spacing");

		bungees = new SpringParticle[numBungees][numParticles];

		this.delayTime = 10;

		for (int j = 0; j < bungees.length; j++) {
			for (int i = 0; i < bungees[0].length; i++) {
				bungees[j][i] = new SpringParticle(mass, kEach, j * spacing, height-(i*yStep), 0, 0);
				bungees[j][i].setXY(bungees[j][i].x, bungees[j][i].y);
				bungees[j][i].color = Color.RED;
				frame.addDrawable(bungees[j][i]);
			}
		}

		for (int j = 0; j < bungees.length; j++) {
			bungees[j][bungees.length-1].mass = 60.25;
		}

		restDistance = distance(bungees[0][1], bungees[0][2]);

		minHeight = height - control.getDouble("length");

		frame.setVisible(true);
		frame.setPreferredMinMax(-3, 3 + (numBungees * spacing), 0, 120);

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
		control.setValue("time step", 0.004);
		control.setValue("number of particles", 200);
		control.setValue("number of bungees", 10);
		control.setValue("bungee spacing", 5);
		control.setValue("length", 20);
		control.setValue("height", 100);
		control.setValue("total mass", 50);
		control.setValue("object mass", 60);
		control.setValue("k", 4000);
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

		double dist = distance(part1, part2) - restDistance;

		double yDist = part2.y - part1.y;
		double xDist = part2.x - part1.x;

		double angle = Math.atan2(yDist, xDist);

		double totalForce = part1.k * dist;

		double xForce = totalForce * Math.cos(angle);

		return xForce;
	}

	public double yForce(SpringParticle part1, SpringParticle part2) {

		double dist = distance(part1, part2) - restDistance;

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
		SimulationControl.createApp(new Bungees());
	}

}
