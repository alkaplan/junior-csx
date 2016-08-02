package riemannSum;

import java.util.Random;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;
/**
 * This rule calculates the Riemann sum using a random value of the polynomial over the slice's width to
 * calculate the height.
 * @author Alex Kaplan
 *
 */
public class RandomRule extends Riemann{
	
	Random ran = new Random();
	PolyPractice popeye = new PolyPractice();

	public double slice(Polynomial p, double sleft, double sright) {		
		double length = Math.abs(sright - sleft);
		double height = popeye.eval(p,(sleft + ran.nextDouble()*length));
		return (height * length);
	}

	public void slicePlot(PlotFrame frame, Polynomial p, double sleft, double sright) {
		double width = Math.abs(sright-sleft);
		double height = popeye.eval(p,(sleft + ran.nextDouble()*width));
		double xCoord = sleft + width/2;
		double yCoord = height/2;
		
		DrawableShape rect = DrawableShape.createRectangle(xCoord, yCoord, width, height);
		frame.addDrawable(rect);
	}
}
