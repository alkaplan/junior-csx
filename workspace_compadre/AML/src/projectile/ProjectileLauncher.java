package projectile;

import java.awt.Color;
import java.util.Arrays;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.*;

/**
 * 
 * @author Alex Kaplan
 *
 */

public class ProjectileLauncher extends AbstractSimulation {
	
	/**
	 *  This program is going to be used for Simulations and Animation 
	 */
	
	Particle[] balls;
	DisplayFrame frame = new DisplayFrame("X", "Y","Display Frame Test");
	Obstacle wall;
	double minVel;
	
	static int step = 0;
	
	/**
	 * this is the doStep method which tells the animation what to do after each step. It basically
	 * uses physical equations (x=v0t + 1/2 at^2, v = v0 + at, Ar(air resistance) = -beta * v) to determine
	 * how fast the ball should be going at each step,
	 * then how far the ball should go at each step given the time interval from the control panel.
	 * it finally changes the x and y coordinates of the drawable object ball.
	 */
	protected void doStep() {
		step++;
		
		for (Particle ball : balls) {
			double Vy;
			double arx;
			double ary;
			if(ball.yPos <= 0 && step > 10) {
				ball.Vy = 0;
				ball.Vx = 0;
				continue;
			}
			else if(ball.xPos >= wall.distance&& ball.yPos <= wall.height - 0.05 && ball.xPos < wall.distance + wall.width) {
				ball.Vy = 0;
				ball.Vx = 0;
				continue;
			}
			else if(ball.xPos >= wall.distance&& ball.yPos >= wall.height) {
				if(ball.totalVel < minVel) {
					minVel = ball.totalVel;
					control.println("the minimum velocity is: " + minVel);
					control.println("the angle for that is: " + Math.toDegrees(ball.angle));
				}
				ball.color = Color.GREEN;
				ball.t.color = Color.CYAN;
			}
			
			//change the x velocity of the ball with air resistance
			arx = -1 * control.getDouble("beta") * ball.Vx;
			ball.setVx(ball.getVx() + (arx * control.getDouble("time")));
			
			//change the y velocity of the ball with air resistance
			Vy = ball.Vy;
			Vy = Vy + (control.getDouble("time")*control.getDouble("g"));
			ary = -1 * control.getDouble("beta") * Vy;
			Vy = Vy + (ary * control.getDouble("time"));
			ball.Vy = Vy;
			
			//change the position of the ball and the trail with velocities
			ball.setxPos(ball.getxPos() + (ball.getVx() * control.getDouble("time")));
			ball.setyPos(ball.getyPos() + (ball.getVy()*control.getDouble("time")));
			ball.setXY(ball.xPos, ball.yPos);
			ball.t.addPoint(ball.xPos, ball.yPos);
		}

	}
	
	/**
	 * initializes the program. it runs through several steps, all of which are documented. they include:
	 * setting values of steps to determine number of balls
	 * creating the array of balls
	 * initializing variables for the balls in the array
	 * create the balls with initialized variables
	 * draw the frame
	 */
	public void initialize() {
		
		//set values to determine how many balls there will be
		double angleStep = ((control.getDouble("max angle") - control.getDouble("min angle"))/control.getDouble("balls"));
		double angleStepRad = Math.toRadians(angleStep);
		int numAngles = (int)control.getDouble("balls");
		int numVelocities = (int)control.getDouble("balls");
		double velocityStep = (control.getDouble("max velocity") - control.getDouble("min velocity"))/control.getDouble("balls");
		
		//create the balls array with determined number of balls
		balls = new Particle[numVelocities * numAngles];
		Arrays.fill(balls, null);

		//start initializing variables used to set values for the balls
		int counter = 0;
		minVel = control.getDouble("max velocity");
		double angle = control.getDouble("min angle");
		double currentVelocity = control.getDouble("min velocity");
		
		//for loop to create each ball with a different angle and a different velocity
		//outer loop (the i=0, i++) is looping through velocities, inner loop is looping through angles
		for (int i = 0; i < numVelocities; i++) {
			angle = control.getDouble("min angle");
			double angleRad = Math.toRadians(angle);
			
			for (int j = 0; j < numAngles; j++) {
				double velocityX = currentVelocity * Math.cos(angleRad);
				double velocityY = currentVelocity * Math.sin(angleRad);
				balls[counter] = new Particle(velocityX, velocityY, currentVelocity, angleRad,0, 0);
				angleRad += angleStepRad;
				counter++;
			}
			
			currentVelocity += velocityStep;
		}
		
		//create the frame
		frame.setPreferredMinMaxX(-10, control.getDouble("wall distance") + 25);
		frame.setPreferredMinMaxY(-10, 100);
		frame.setVisible(true);
		
		//create the wall
		wall = new Obstacle(control.getDouble("wall distance"), control.getDouble("wall height")/2, control.getDouble("wall width"), control.getDouble("wall height"));
		wall.draw(frame);
		//add the balls to the frame
		for (int i = 0; i < balls.length; i++) {
			balls[i].setXY(0, 0);
			frame.addDrawable(balls[i]);
			frame.addDrawable(balls[i].t);
		}
		
	}
	
	/**
	 * resets all variables, adds control values
	 */
	public void reset() {
		frame.clearDrawables();
		control.setValue("wall distance", 96);
		control.setValue("wall width", 5);
		control.setValue("wall height", 11.3);
		control.setValue("beta", 0.01);
		control.setValue("balls", 50);
		control.setValue("min velocity", 10);
		control.setValue("max velocity", 100);
		control.setValue("min angle", 20);
		control.setValue("max angle", 70);
		control.setValue("g", -9.8);
		control.setValue("time", 0.1);
	}
	
	/**
	 * stops the program
	 */
	public void stop() {
		
	}
	
	/**
	 * this function is as of yet not used, but it will create another "optimal" ball given which ball was best
	 * in the simulation
	 * @param ball the ball with minimum velocity over the wall
	 * @return optimal the optimal ball
	 */
	public Particle optimalBallCreater(Particle ball) {
		double velocity = ball.totalVel;
		double angleRad = ball.angle;
		double velocityX = velocity * Math.sin(angleRad);
		double velocityY = velocity * Math.cos(angleRad);
		
		Particle optimal = new Particle(velocityX, velocityY, velocity, angleRad, 0, 0);
		
		return optimal;
	}
	
	/**
	 * create the app
	 * @param args
	 */
	public static void main(String[] args) {
		SimulationControl.createApp(new ProjectileLauncher());
	}

}
