package riemannSum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;

/**
 * This rule calculates the Riemann sum using the maximum value of the polynomial over the slice's width to
 * calculate the height.
 * @author Alex Kaplan
 *
 */

public class MaximumRule extends Riemann {
	
	PolyPractice popeye = new PolyPractice();
	
	public double slice(Polynomial p, double sleft, double sright) 
	{
		double area;
		double max;

		double end1 = popeye.eval(p, sleft);
		double end2 = popeye.eval(p, sright);
		
		if(end1>end2) max = end1;
		else max = end2;
		
		area = max * (sright - sleft);

		return area;
	}

	public void slicePlot(PlotFrame frame, Polynomial p, double sleft, double sright) 
	{
		
		double max;

		double end1 = popeye.eval(p, sleft);
		double end2 = popeye.eval(p, sright);
		
		if(end1>end2) max = end1;
		else max = end2;
		double width = sright - sleft;
		double xCoord = ((sright+sleft)/2);
		double yCoord = max/2;
		
		DrawableShape rect = DrawableShape.createRectangle(xCoord, yCoord, width, max);
		
		frame.addDrawable(rect);

	}

}
