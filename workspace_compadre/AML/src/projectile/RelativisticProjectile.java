package projectile;

import java.awt.Color;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.*;

import useful.StepTest;

/**
 * 
 * @author Alex Kaplan
 * This class uses Einstein's theory of special relativity to show the difference between Newtonian mechanics and 
 * Classical mechanics. It makes this discrepancy obvious by changing the speed of light to be much slower, and thus 
 * emphasizing relativistic effects.
 * 
 * I use the minimum angle and velocity as computed by my ProjectileLauncher class, using the ball that just barely passes
 * over the wall. The relativistic discrepancy sometimes makes the relativistic ball a home run while the classical ball
 * is not, depending on the given speed of light. 
 * 
 * The Question:
 * How does the speed of light and relativistic effects resulting from it?
 * 
 * WHAT I LEARNED:
 * Relativistic effects are basically negligeable (10^-13 m) for everyday speeds and differences. If you use a different speed of
 * light, though, the effects will become more and more noticeable.  However, the effects are still very difficult to physically
 * measure until the speed of light is especially small. The diameter of an atom is 10^-10 m, so most of the speeds of light return
 * a distance that is practically immesaureable, because Lorentz's gamma factor, or the relativistic effects, is proportional to
 * v^2/c^2, so as c increases, c^2 really increases. 
 */

public class RelativisticProjectile extends AbstractSimulation{

	Particle[] relBalls;
	Particle[] classicBalls;
	double[] cVals;
	Obstacle wall;
	DisplayFrame[] cFrames;
	double g;
	static int step;

	/**
	 * ah, the all important doStep()! This is where the magic happens. here's a step-by-step breakdown of this function.
	 * One important thing is that the only real difference between the calculations of the relativistic ball and the classical ball
	 * is that the relativistic ball uses a slightly longer timestep, "t". 
	 * 1. add one to the static counter int "step"
	 * 2. start the for loop using the number of frames
	 * 3. initialize several variables that will be used to better keep track of calculations
	 * 4. calculate "improper time"
	 * 5. enter if statement to check if the ball hit the wall (using the checkBallHit() method)
	 * 6. calculate position
	 * 7. draw positions
	 * 8. check distance between two balls and print to the frame
	 */
	protected void doStep() {
		step++;

		for (int i = 0; i < cFrames.length; i++) {

			double Vy;
			double arx;
			double ary;
			double t0 = control.getDouble("timeStep");
			//calculate improper time
			double t = timeStep(t0, relBalls[i].totalVel, cVals[i]);

			//classic ball loop to calculate and move the ball with classical physics
			if(checkBallHit(classicBalls[i]) == false) {

				//change the x velocity of the classical ball with air resistance
				arx = -1 * control.getDouble("beta") * classicBalls[i].Vx;
				classicBalls[i].setVx(classicBalls[i].getVx() + (arx * t0));

				//change the y velocity of the classical with air resistance
				Vy = classicBalls[i].Vy;
				Vy = Vy + (t0*control.getDouble("g"));
				ary = -1 * control.getDouble("beta") * Vy;
				Vy = Vy + (ary * t0);
				classicBalls[i].Vy = Vy;

				//set total velocity
				classicBalls[i].setTotalVel(Math.sqrt(Math.pow(classicBalls[i].Vx, 2) + Math.pow(classicBalls[i].Vy, 2)));

				//change the position of the classical ball and the trail with velocities
				classicBalls[i].setxPos(classicBalls[i].getxPos() + (classicBalls[i].getVx()*t0));
				classicBalls[i].setyPos(classicBalls[i].getyPos() + (classicBalls[i].getVy()*t0));
				classicBalls[i].setXY(classicBalls[i].xPos, classicBalls[i].yPos);
				classicBalls[i].t.addPoint(classicBalls[i].xPos, classicBalls[i].yPos);

			}

			//relativistic ball loop to calculate and move the ball with reletavistic physics
			if(checkBallHit(relBalls[i]) == false) {
				//change the x velocity of the relativistic ball with air resistance
				arx = -1 * control.getDouble("beta") * relBalls[i].Vx;
				relBalls[i].setVx(relBalls[i].getVx() + (arx * t));

				//change the y velocity of the relativistic ball with air resistance
				Vy = relBalls[i].Vy;
				Vy = Vy + (t*control.getDouble("g"));
				ary = -1 * control.getDouble("beta") * Vy;
				Vy = Vy + (ary * t);
				relBalls[i].Vy = Vy;	

				//set total velocity for ball after each step
				relBalls[i].setTotalVel(Math.sqrt(Math.pow(relBalls[i].Vx, 2) + Math.pow(relBalls[i].Vy, 2)));

				//change the position of the relativistic ball and the trail with velocities
				relBalls[i].setxPos(relBalls[i].getxPos() + (relBalls[i].getVx()*t));
				relBalls[i].setyPos(relBalls[i].getyPos() + (relBalls[i].getVy()*t));
				relBalls[i].setXY(relBalls[i].xPos, relBalls[i].yPos);
				relBalls[i].t.addPoint(relBalls[i].xPos, relBalls[i].yPos);
			}


			//print discrepancy 
			double xDiscrep = relBalls[i].getxPos() - classicBalls[i].getxPos();
			double yDiscrep = relBalls[i].getyPos() - classicBalls[i].getyPos();
			double totalDiscrep = Math.sqrt(Math.pow(xDiscrep,2) + Math.pow(yDiscrep,2)); //distance formula
			cFrames[i].setMessage("" + totalDiscrep); /* THIS IS HOW YOU MAKE THE LITTLE YELLOW BOX */
		}

	}

	/**
	 * initializes the animation by creating the different frames and setting values for the balls, wall, etc.
	 * This method has several important parts. it runs as so:
	 * 1. set component parts of ball (total velocity, angle of incline, x velocity, y velocity)
	 * 2. create an array of "speeds of light" (cVals) to test. This was the most difficult part of this whole method, and I 
	 * created a helper class to do this. Read more in the comment at line 121
	 * 3. initialize arrays of balls and frames using number of c values given
	 * 4. create the wall
	 * 5. iterate through each element of each array, including the balls and the frames
	 * 6. draw everything
	 */
	public void initialize() {

		//set ball variables
		double Vtot = control.getDouble("v");
		double angleRad = Math.toRadians(control.getDouble("angle"));
		double Vx = Vtot * Math.cos(angleRad);
		double Vy = Vtot * Math.sin(angleRad);

		/* create the array of c values LOGARITHMICALLY. this was very difficult. (i used a separate class to do it.)
		 * basically it makes the array even steps between each number, so for example, having 4 numbers,
		 * a min of 3 and max of 3000 would return an array of {3, 30, 300, 3000}. It was hard to do!
		 * I basically found x for 10^x for the min and max, then divided x by length of the array and made 10^ that the "step"
		 * Ask me more for more information, the helper class is also documented to help you through
		 */
		int numC = (int)(control.getDouble("num test"));
		cVals = new double[numC];
		cVals = StepTest.arr(control.getDouble("max c"), control.getDouble("min c"), numC);
		cVals[0] = control.getDouble("min c");
		
		//create everything based on how many c values there are (num frames, num balls particles
		cFrames = new DisplayFrame[numC];
		relBalls = new Particle[numC];
		classicBalls = new Particle[numC];

		//make wall
		wall = new Obstacle(control.getDouble("wall distance"), control.getDouble("wall height")/2, 
				control.getDouble("wall width"), control.getDouble("wall height"));

		//initialize everything
		for (int i = 0; i < cVals.length; i++) {
			cFrames[i] = new DisplayFrame("X","Y","Speed of Light: " + cVals[i]);
			relBalls[i] = new Particle(Vx, Vy, Vtot, control.getDouble("angle"), 0, 0);
			classicBalls[i] = new Particle(Vx, Vy, Vtot, control.getDouble("angle"), 0, 0);
			classicBalls[i].color = Color.blue;
			cFrames[i].setPreferredMinMax(-10, wall.distance + (wall.distance)/10, -10, 100);
			cFrames[i].addDrawable(relBalls[i]);
			cFrames[i].addDrawable(classicBalls[i]);
			cFrames[i].setVisible(true);
			wall.draw(cFrames[i]);
		}
	}

	/**
	 * sets values of the control panel
	 */
	public void reset() {
		control.setValue("wall distance", 96);
		control.setValue("wall width", 5);
		control.setValue("wall height", 11.3);
		control.setValue("beta", 0.01);
		control.setValue("v", 33.6);
		control.setValue("angle", 41.33333334);
		control.setValue("c", 3E8);
		control.setValue("min c", 3E4);
		control.setValue("max c", 3E8);
		control.setValue("num test", 4);
		control.setValue("g", -9.8);
		control.setValue("timeStep", 0.01);
		control.setValue("thickness of paint", .005);
	}

	/**
	 * stops the animation
	 */
	public void stop() {

	}

	/**
	 * This calculates the "timestep" for the relativistic ball. It uses einstein's equation (delta t = gamma * delta t0)
	 * also known as the "improper time" (the ball's clock) is equal to the "proper time" (the ground's clock) times a factor gamma, 
	 * which is dependant on speed. Gamma is specifically 1/sqrt(1-(v^2/c^2))
	 * @param t the proper time timestep
	 * @param v the velocity of the ball
	 * @param c the speed of light, the indepedant variable
	 * @return timestep or "improper time"
	 */
	public double timeStep(double t, double v, double c) {
		double gamma;

		gamma = 1/(Math.sqrt(1 - ((Math.pow(v, 2))/(Math.pow(c, 2)))));

		return t*gamma;
	}

	/**
	 * This checks whether or not the ball hit the wall. the wall is a static variable so you don't
	 * need it as a parameter
	 * @param ball the ball you are checking whether or not hit wall
	 * @return boolean whether or not it has hit the wall
	 */
	public boolean checkBallHit(Particle ball) {
		if(ball.yPos <= 0 && step>5) {
			return true;
		}
		else if(ball.xPos >= wall.distance && ball.yPos <= wall.height - 0.05 && ball.xPos < wall.distance + wall.width) {
			return true;
		}
		else{
			return false;
		}
	}

	public static void main(String[] args) {
		SimulationControl.createApp(new RelativisticProjectile());
	}

}
