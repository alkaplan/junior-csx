package projectile;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.DisplayFrame;

/**
 * 
 * @author Alex Kaplan
 *
 */

public class Obstacle {
	
	double distance;
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	double height;
	double width;
	double y;
	
	/**
	 * constructor for obstacle
	 * @param x distance from origin
	 * @param y height away from origin
	 * @param width the width of the wall. does this part really need javadoc? javadoc sucks. god damnit.
	 * @param height what the fuck do you think this parameter means!? it's the height for christ's sake!
	 */
	public Obstacle(double x, double y, double width, double height) {
		this.distance = x;
		this.height  = height;
		this.y = y;
		this.width = width;
		
	}
	
	/**
	 * draw the wall on the given frame
	 * @param frame the frame to draw the wall on
	 */
	void draw(DisplayFrame frame) {
		DrawableShape rectangle = DrawableShape.createRectangle(this.distance + (this.width/2), this.y, this.width, this.height);
		frame.addDrawable(rectangle);
		
	}

}
