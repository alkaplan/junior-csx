package projectile;

import org.opensourcephysics.display.*;

/**
 * 
 * @author Alex Kaplan
 *
 */

public class Particle extends Circle{
	double Vx;
	public double getVx() {
		return Vx;
	}
	public void setVx(double vx) {
		Vx = vx;
	}
	double Vy;
	public double getVy() {
		return Vy;
	}
	public void setVy(double vy) {
		Vy = vy;
	}
	double xPos;
	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	double yPos;
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	
	Trail t;
	public Trail getT() {
		return t;
	}
	public void setT(Trail t) {
		this.t = t;
	}
	public double getTotalVel() {
		return totalVel;
	}
	public void setTotalVel(double totalVel) {
		this.totalVel = totalVel;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	double totalVel;
	double angle;
	
	/**
	 * constructs the ball
	 * @param VelX velocity in the x direciton
	 * @param VelY velocity in the y direction
	 * @param totalVel	total velocity (used for calculating gamma, time interval, saving the optimal velocity, etc.)
	 * @param angle initial angle it was launched at
	 * @param xPosition x position of the ball
	 * @param yPosition y position of the ball
	 */
	public Particle(double VelX, double VelY, double totalVel, double angle, double xPosition, double yPosition) {
		this.Vx = VelX;
		this.Vy = VelY;
		this.totalVel = totalVel;
		this.angle = angle;
		this.xPos = xPosition;
		this.yPos = yPosition;
		this.t = new Trail();
		
	}
} 
