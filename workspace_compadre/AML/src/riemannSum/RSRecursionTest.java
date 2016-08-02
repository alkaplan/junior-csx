package riemannSum;

import polyfun.Polynomial;

public class RSRecursionTest {
	
	public double rs(Polynomial p, double left, double right, double subintervals) {
		double delta = (right - left)/subintervals;
		double area = 0;

		double x = right;
		if(x == left + delta) {
			area = slice(p, x-delta, x);
		}
		else {
			area = slice(p, x-delta, x) + rs(p, left, x-delta, subintervals-1);
		}

		return area;
	}
	// area = slice(here) + rs(everywhere else)
	double slice(Polynomial p, double one, double two) {
		return 0;
	}

}
