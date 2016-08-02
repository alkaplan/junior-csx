package orbital;

import org.opensourcephysics.display.*;

/**
 * 
 * @author student
 *
 */

public class Planet extends Circle {
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
	double xCoord;
	public double getxCoord() {
		return xCoord;
	}
	public void setxCoord(double xPos) {
		this.xCoord = xPos;
	}
	double yCoord;
	public double getyCoord() {
		return yCoord;
	}
	public void setyPos(double yCoord) {
		this.yCoord = yCoord;
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
	double mass;
	public double getMass() {
		return this.mass;
	}
	public void setMass (double mass) {
		this.mass = mass;
	}
	double totalVel;
	double angle;
	Trail trail;
	
	/**
	 * constructs the ball
	 * @param VelX velocity in the x direciton
	 * @param VelY velocity in the y direction
	 * @param totalVel	total velocity (used for calculating gamma, time interval, saving the optimal velocity, etc.)
	 * @param angle initial angle it was launched at
	 * @param xPosition x position of the ball
	 * @param yPosition y position of the ball
	 */
	public Planet(double mass, double xCoord, double yCoord, double VelX, double VelY)
	{
		this.mass = mass;
		this.Vx = VelX;
		this.Vy = VelY;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.t = new Trail();
	}
	public void setCoords() {
		this.setXY(this.xCoord, this.yCoord);
	}
}
