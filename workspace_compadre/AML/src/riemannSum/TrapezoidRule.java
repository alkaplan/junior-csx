package riemannSum;

import org.opensourcephysics.display.DrawableShape;

import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;
import org.opensourcephysics.frames.PlotFrame;
import polyfun.Polynomial;
/**
 * This rule calculates the Riemann sum using the left hand endpoint and the right hand endpoint of the base of 
 * each rectangle to create a trapezoid that approximates the area.
 * @author Alex Kaplan
 *
 */
public class TrapezoidRule extends Riemann {
	public double slice(Polynomial p, double sleft, double sright) {
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

	
	public void slicePlot(PlotFrame frame, Polynomial p, double sleft, double sright) {
		PolyPractice popeye = new PolyPractice();
		double leftHeight = popeye.eval(p, sleft);
		double rightHeight = popeye.eval(p, sright);
		double width = Math.abs(sright - sleft);

		frame.append(2, sleft, 0);
		frame.append(2, sleft, leftHeight);
		frame.append(2, sright, rightHeight);
		frame.append(2, sright, 0);


		frame.setConnected(true);
		
	}
	
}
