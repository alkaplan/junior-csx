package springs;

import org.opensourcephysics.display.Circle;
import useful.Vector;

public class SpringParticle extends Circle {
	
	double mass;
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	double k;
	double x;
	double y;
	double vy;
	double vx;
	Vector netForce;
	
	public SpringParticle(double m, double k, double x, double y, double vx, double vy) {
		this.mass = m;
		this.k = k;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}
	
	public void setCoords() {
		this.setXY(this.x, this.y);
	}
}