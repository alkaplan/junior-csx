package riemannSum;

import org.opensourcephysics.display.InteractiveShape;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;
/**
 * This rule calculates the Riemann sum and draws it using ellipses! I made it up. 
 * @author Alex Kaplan
 *
 */

public class EllipseRule extends Riemann {

	PolyPractice popeye = new PolyPractice();
	
	public double slice(Polynomial p, double sleft, double sright) {
		
		double width = Math.abs(sleft - sright);
		double height = popeye.eval(p, (width/2));
		double area = Math.PI * width * height; //area of an ellipse : pi * a * b (like pi*r^2 but not)
		return area;
	}

	public void slicePlot(PlotFrame frame, Polynomial p, double sleft, double sright) {
		double width = Math.abs(sright - sleft);
		double height = popeye.eval(p, (sleft + (width/2)));
		double xCoord = sleft + width/2;
		double yCoord = height/2;
		
		InteractiveShape ellipse = InteractiveShape.createEllipse(xCoord, yCoord, width, height);

		frame.addDrawable(ellipse);
		
	}
}
