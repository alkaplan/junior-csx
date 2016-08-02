package riemannSum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;
/**
 * This rule calculates the Riemann sum using the midpoint of each rectangle's base to calculate the height.
 * @author Alex Kaplan
 *
 */
public class MidpointRule extends Riemann {
	public double slice(Polynomial p, double sleft, double sright) 
	{
		double area;
		double base1;
		double base2;
		double height;
		PolyPractice popeye = new PolyPractice();
		
		height = sright-sleft;
		base1 = popeye.eval(p, sleft);
		base2 = popeye.eval(p, sright);
		
		area = ((base1+base2)/2)*height;
		
		return area;
	}
	public void slicePlot(PlotFrame frame, Polynomial p, double sleft, double sright) 
	{
		
		PolyPractice popeye = new PolyPractice();
		double leftHeight = popeye.eval(p, sleft);
		double rightHeight = popeye.eval(p, sright);
		double height = Math.abs((leftHeight + rightHeight)/2);
		double width = Math.abs(sright-sleft);
		double xCoord = sleft + width/2;
		double yCoord = height/2;
		
		DrawableShape rect = DrawableShape.createRectangle(xCoord, yCoord, width, height);
		
		frame.addDrawable(rect);
	}

}
