package riemannSum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.*;

import polyfun.Polynomial;

/**
 * This rule calculates the Riemann sum using the left hand endpoint of each rectangle as the height.
 * @author Alex Kaplan
 *
 */
public class LeftHandRule extends Riemann {
	
	public double slice(Polynomial p, double sleft, double sright) {
		double area;
		double height;
		double length;
		PolyPractice popeye = new PolyPractice();
		
		height = popeye.eval(p, sleft);
		length = sright - sleft;
		area = height*length;
		
		return area;
	}
	
	public void slicePlot(PlotFrame frame, Polynomial p, double sleft, double sright) {
		
		double height;
		PolyPractice popeye = new PolyPractice();
		height = popeye.eval(p, sleft);
		double width = Math.abs(sright - sleft);
		double xCoord = sleft + width/2;
		double yCoord = height/2;

		DrawableShape rect = DrawableShape.createRectangle(xCoord, yCoord, width, height);
		frame.addDrawable(rect);
		
	}
}
