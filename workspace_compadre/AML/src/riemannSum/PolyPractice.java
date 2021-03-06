package riemannSum;

import polyfun.*;

import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.*;


public class PolyPractice {
	public static void main(String[] args) {
		Polynomial p = new Polynomial(new double[] {-6,-1});
		
		addxSquared(p);
		
	}
	public static double eval(Polynomial p, double x) {
		double val = p.evaluate(x).getTerms()[0].getTermDouble();
		return val;
	}
	public static void addxSquared(Polynomial p) {
		Polynomial xSquared = new Polynomial('x', 2);
		p.plus(xSquared);
		DisplayFrame frame = new DisplayFrame("X","Y","Poly + X^2");
		
		Trail t = new Trail();
		for (int i = -15; i < 15; i++) {
			t.addPoint(i, eval(p, i));		
		}
		frame.addDrawable(t);
		frame.setVisible(true);
		
	}

}
