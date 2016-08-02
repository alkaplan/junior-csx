package springs;

import org.opensourcephysics.display.Circle;

public class Girl extends Circle {

	double mass;
	double x;
	double y;
	double vx;
	double vy;
	
	public Girl(double mass, double x, double y, double vx, double vy) {
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}
}
