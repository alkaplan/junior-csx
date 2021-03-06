package springs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.DisplayFrame;

public class FundamentalFrequency extends AbstractSimulation {

	SpringParticle[][] strings;
	double restDistance;
	static double time;
	static double timeElapsed = 0;
	static double amplitude;
	static double period;
	static double startingY;
	static double startingX;
	static double waveTime;
	static double maxX;
	JButton button;
	JFrame jFrame;
	static boolean buttonPressed;
	static double waveTop;
	static double[] frequency;
	static double[] highestPoints;
	static double[] topOfWave;
	static int[] numOscillations;
	Set<Double> freqs = new HashSet<>();
	DisplayFrame frame = new DisplayFrame("X", "Y", "String Wave");

	protected void doStep() {

		for(int j = 0; j < strings.length; j++) {
			for (int i = 0; i < strings[0].length; i++) {

				double Fx1 = 0;
				double Fx2 = 0;
				double Fy1 = 0;
				double Fy2 = 0;

				if(i != (strings[0].length-1)) {
					Fx1 = xForce(strings[j][i], strings[j][i+1]);
					Fy1 = yForce(strings[j][i], strings[j][i+1]);
				}

				if(i != 0) { 
					Fx2 = xForce(strings[j][i], strings[j][i-1]);
					Fy2 = yForce(strings[j][i], strings[j][i-1]);
				}

				double Fxtot = Fx1 + Fx2;
				double Fytot = Fy1 + Fy2;

				double ax = Fxtot/(strings[j][i].mass);
				double ay = Fytot/(strings[j][i].mass);

				strings[j][i].vx += ax * time;
				strings[j][i].vy += ay * time;			

			}

			for (int i = 1; i < strings[0].length; i++) {
				strings[j][i].x += strings[j][i].vx * time;
				strings[j][i].y += strings[j][i].vy * time;
				strings[j][i].setCoords();
			}

			strings[j][0].x = 0;
			strings[j][0].y = startingY + amplitude * Math.sin(timeElapsed * 2 * Math.PI * frequency[j]);
			strings[j][0].setCoords();
			strings[j][strings[0].length-1].x = maxX;
			strings[j][strings[0].length-1].y = startingY;
			strings[j][strings[0].length-1].setXY(strings[j][strings.length-1].x, strings[j][strings.length-1].y);				

		}

		
		
		/*
		 * BEGIN DERIVATION PART
		 */
		double[] lastStepPoints = highestPoints;

		ArrayList<Double> stableFreqs = new ArrayList<Double>();

		for (int j = 0; j < highestPoints.length; j++) {

			//find the top of the wave
			for (int i = 0; i < strings[0].length; i++) {
				if(strings[j][i].y > highestPoints[j]) {
					highestPoints[j] = strings[j][i].y;
				}
			}

			//see if the wave is moving a lot
			for (int i = 0; i < lastStepPoints.length; i++) {
				if(isCloseTo(highestPoints[i], lastStepPoints[i], .000001)) {
					numOscillations[i]++;
					if(numOscillations[i] > 10) {
						if(topOfWave[i] > startingY + (amplitude * 4)) {
							stableFreqs.add(frequency[i]);
						}
					}
					topOfWave[i] = highestPoints[i];
				}
			}
		}

		if(stableFreqs.size() > 0)	{

			ArrayList<Double> finalFreqs = new ArrayList<Double>();
			
			Set<Double> hs = new HashSet<>();
			
			hs.addAll(stableFreqs);
			
			finalFreqs.addAll(hs);

			double fundamental = finalFreqs.get(0);
			for (int i = 0; i < finalFreqs.size(); i++) {
				if(finalFreqs.get(i) < fundamental) {
					fundamental = finalFreqs.get(i);
				}
			}
			System.out.println();
			System.out.println("Fundamental freq: " + fundamental);
			for (int i = 0; i < finalFreqs.size(); i++) {
				System.out.println("Stable frequency: " + finalFreqs.get(i));
			}
			System.out.println();
		}
		/*
		 * END DERIVATION PART
		 */

		timeElapsed += time;

	}

	public void initialize() {

		time = control.getDouble("time step");
		amplitude = control.getDouble("wave amplitude");
		int numParticles = (int)control.getDouble("number of particles");
		startingX = control.getDouble("minimum x");
		maxX = control.getDouble("maximum x");
		startingY = control.getDouble("starting y");
		int numStrings = (int)(control.getDouble("strings to test"));

		double mass = control.getDouble("particle mass");

		double minPeriod = control.getDouble("min period");
		double maxPeriod = control.getDouble("max period");

		double step = (maxX - startingX)/(numParticles - 1);

		restDistance = step;

		button = new JButton("Print out resonant frequencies");
		
		buttonPressed = false;
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed = true;
			}
		});
		
		jFrame = new JFrame("Button");
		
		jFrame.add(button);
		jFrame.setSize(50, 50);
		jFrame.setVisible(true);


		double kEach = 4000;

		//initialize 2d stuff
		numStrings = (int)control.getDouble("strings to test");

		frequency = new double[numStrings];
		highestPoints = new double[numStrings];
		topOfWave = new double[numStrings];
		numOscillations = new int[numStrings];


		double frequencyStep = (maxPeriod - minPeriod)/numStrings;
		double currentFrequency = minPeriod;

		for (int i = 0; i < frequency.length; i++) {
			frequency[i] = currentFrequency;
			currentFrequency += frequencyStep;
		}

		this.delayTime = 1;

		int stringsToTest = (int)control.getDouble("strings to test");

		strings = new SpringParticle[stringsToTest][numParticles];

		int colorStep = (int)255/strings.length;

		for (int j = 0; j < strings.length; j++) {
			for (int i = 0; i < strings[0].length; i++) {
				strings[j][i] = new SpringParticle(mass, kEach, i * step, startingY, 0, 0);
				strings[j][i].setCoords();
				strings[j][i].color = new Color(0, 255 - j * colorStep, j * colorStep);
				strings[j][0].color = Color.red;
				frame.addDrawable(strings[j][i]);
			}
		}

		frame.setVisible(true);
		frame.setLocation(310, 0);
		frame.setSize(1000, 400);
		frame.setPreferredMinMax(startingX - ((maxX-startingX)/2), maxX + ((maxX-startingX)/2), startingY - startingY/2, startingY + startingY/2);

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
		control.setValue("time step", 0.005);
		control.setValue("number of particles", 100);
		control.setValue("minimum x", 0);
		control.setValue("maximum x", 100);
		control.setValue("starting y", 10);
		control.setValue("wave amplitude", 2);
		control.setValue("particle mass", 1);
		control.setValue("total k", 24);
		control.setValue("strings to test", 10);
		control.setValue("min period", 0.1);
		control.setValue("max period", 1);
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

	private double findV(SpringParticle[] arr, double totalTime) {

		double velocity = arr[arr.length-1].x/totalTime;

		return velocity;
	}

	private double findLambda(SpringParticle[] arr) {

		double tallest = arr[arr.length-1].y;

		double lambda = 0;

		boolean pastFirstWave = false;

		for (int i = arr.length-1; i > 0; i--) {

			if(arr[i].y < startingY) {
				pastFirstWave = true;
			}

			if(pastFirstWave == true) {
				if(arr[i].y/tallest > .975 && arr[i].y/tallest < 1.025) {
					lambda = arr[arr.length-1].x - arr[i].x;
					break;
				}
			}
		}

		return lambda;
	}

	//	private double[] nodes(SpringParticle[] arr) {
	//		
	//		double[] nodes;
	//		
	//		
	//		return ;
	//	}
	public boolean isCloseTo(double num1, double num2, double precision) {

		if(num1/num2 > 1 - precision && num1/num2 < 1 + precision) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void main(String[] args) {
		SimulationControl.createApp(new FundamentalFrequency());
	}

}
