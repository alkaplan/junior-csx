package riemannSum;

import org.opensourcephysics.frames.*;

import java.util.Scanner;

import polyfun.Polynomial;

public class RSPlotTest {
	public static void main(String[] args)  {
		
		double leftEnd = -5;
		double rightEnd = 5;
		int subIntervals = 10;

		LeftHandRule LHR = new LeftHandRule();  //LeftHandRule extends Riemann implement 
		RightHandRule RHR = new RightHandRule();  //RightHandRule extends Riemann
		TrapezoidRule TPR = new TrapezoidRule(); 
		MidpointRule MDR = new MidpointRule();
		MinimumRule MIN = new MinimumRule();
		MaximumRule MAX = new MaximumRule();
		RandomRule RAN = new RandomRule();
		SimpsonsRule SIM = new SimpsonsRule();
		EllipseRule ELL = new EllipseRule();

		Polynomial p = new Polynomial(new double[] {3, -6, 3}); // p=3x^2-6x+3

		PlotFrame a = new PlotFrame("x","y","Left Hand Riemann Sum Graph");
		a.setPreferredMinMaxX(-10, 10);
		a.setDefaultCloseOperation(3);
		a.setVisible(true);
		PlotFrame b = new PlotFrame("x","y","Right Hand Riemann Sum Graph");
		b.setPreferredMinMaxX(-10, 10);
		b.setDefaultCloseOperation(3);
		b.setVisible(true);
		PlotFrame c = new PlotFrame("x","y","Trapezoid Riemann Sum Graph");
		c.setPreferredMinMaxX(-10, 10);
		c.setDefaultCloseOperation(3);
		c.setVisible(true);
		PlotFrame d = new PlotFrame("x","y","Midpoint Riemann Sum Graph");
		d.setPreferredMinMaxX(-10, 10);
		d.setDefaultCloseOperation(3);
		d.setVisible(true);
		PlotFrame e = new PlotFrame("x","y","Minimum Riemann Sum Graph");
		e.setPreferredMinMaxX(-10, 10);
		e.setDefaultCloseOperation(3);
		e.setVisible(true);
		PlotFrame f = new PlotFrame("x","y","Maximum Riemann Sum Graph");
		f.setPreferredMinMaxX(-10, 10);
		f.setDefaultCloseOperation(3);
		f.setVisible(true);
		PlotFrame g = new PlotFrame("x","y","Random Riemann Sum Graph");
		g.setPreferredMinMaxX(-10, 10);
		g.setDefaultCloseOperation(3);
		g.setVisible(true);
		PlotFrame h = new PlotFrame("x","y","Simpsons Riemann Sum Graph");
		h.setPreferredMinMaxX(-10, 10);
		h.setDefaultCloseOperation(3);
		h.setVisible(true);
		PlotFrame i = new PlotFrame("x","y","Alex's Ellipse Rule!");
		i.setPreferredMinMaxX(-10, 10);
		i.setDefaultCloseOperation(3);
		i.setVisible(true);

		LHR.rsPlot(a, p, 1, 0.01, leftEnd, rightEnd, subIntervals); // the left hand rule from x=0 to x=2, with 10 rectangles      

		RHR.rsPlot(b, p, 1, 0.01, leftEnd, rightEnd, subIntervals);

		TPR.rsPlot(c, p, 1, 0.01, leftEnd, rightEnd, subIntervals);

		MDR.rsPlot(d, p, 1, 0.01, leftEnd, rightEnd, subIntervals);

		MIN.rsPlot(e, p, 1, 0.01, leftEnd, rightEnd, subIntervals);

		MAX.rsPlot(f, p, 1, 0.01, leftEnd, rightEnd, subIntervals);

		RAN.rsPlot(g, p, 1, 0.01, leftEnd, rightEnd, subIntervals);
		
		SIM.rsPlot(h, p, 1, 0.01, leftEnd, rightEnd, 2);
		
		ELL.rsPlot(i, p, 1, 0.01, leftEnd, rightEnd, subIntervals);

	}
}       
