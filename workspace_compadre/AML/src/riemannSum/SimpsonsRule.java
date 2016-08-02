package riemannSum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;

/**
 * This rule calculates the Riemann sum using the area under the parabolas' curve drawn at each slice, approximated
 * using the midpoint rule and trapezoid rule.
 * @author Alex Kaplan
 *
 */
public class SimpsonsRule extends Riemann {

	PolyPractice popeye = new PolyPractice();
	MidpointRule MDP = new MidpointRule();
	TrapezoidRule TRP = new TrapezoidRule();

	public double slice(Polynomial p, double sleft, double sright) 
	{
		double area;

		double MidArea = MDP.slice(p, sleft, sright);
		double TrapArea = TRP.slice(p, sleft, sright);
		//FORMULA: A ~= (2M + T)/3; t = trapezoid area, m = midpoint area
		area = ((2*MidArea) + TrapArea)/3;

		return area;
	}

	public void slicePlot(PlotFrame frame, Polynomial p, double sleft, double sright) 
	{
		double midpoint = (sleft - sright)/2;
		PolyPractice popeye = new PolyPractice();
		
		double x1 = sleft;
		double x2 = sright;
		double x3 = midpoint;
		
		double y1 = popeye.eval(p, x1);
		double y2 = popeye.eval(p, x2);
		double y3 = popeye.eval(p, x3);
		
		double aNum = (y1*x2 - y1*x3 + y2*x3 - y2*x2 - x1*x2 + x1*y3 + x2*y2 - x2*y3);
		double aDenom = (Math.pow(x1, 2)*x2 - Math.pow(x1, 2)*x3 + Math.pow(x2, 2)*x3 - x1*Math.pow(x2, 2) + 
				x1*Math.pow(x3, 2) + x2*x3);
		
		double a = aNum/aDenom;
		double b = (y1 - y2 - a*(Math.pow(x1, 2) - Math.pow(x2, 2)))/(x1 - x2);
		double c = y1 - a*(Math.pow(x1, 2)) - b*x1;
		
		Polynomial simpson = new Polynomial(new double[] {c, b, a});

		
		Trail t = new Trail();
		
		t.addPoint(sleft, 0);
		
		for (double i = -10; i <= 10; i+=.1) {
			t.addPoint(i, popeye.eval(simpson,i));
		}
		
		t.addPoint(sright, 0);
		
		t.closeTrail();
		
		frame.addDrawable(t);
		
	}

}

