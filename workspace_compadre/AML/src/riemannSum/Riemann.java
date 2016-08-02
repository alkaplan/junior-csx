package riemannSum;

import polyfun.Polynomial;
import org.opensourcephysics.frames.*;

/**
 * This is the abstract class Riemann. This class calculates the area under
 * a curve using Riemann Sums, and it is implimented by different rules which extend Riemann,
 * such as Right Hand Rule, Left Hand Rule, Midpoint Rule, etc. 
 * 
 * 
 * @author Alex Kaplan
 *
 */

public abstract class Riemann {

	double delta;
	double left;
	double right;
	double subintervals;

	/**
	 * This is the getter for dx, the width of each subinterval
	 * @param delta
	 * @return the width of each subinterval
	 */
	public double getDelta() {
		return delta;
	}
	public void setDelta(double delta) {
		this.delta = delta;
	}
	/**
	 * This is the getter for the left hand endpoint
	 * @param left
	 * @return the left hand endpoint
	 */
	public double getLeft() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	/**
	 * This is the getter for the right hand endpoint
	 * @param right
	 * @return the right hand endpoint
	 */
	public double getRight() {
		return right;
	}
	public void setRight(double right) {
		this.right = right;
	}
	/**
	 * This is the getter for the number of subintervals
	 * @param subintervals
	 * @return the number of subintervals
	 */
	public double getSubintervals() {
		return subintervals;
	}
	public void setSubintervals(double subintervals) {
		this.subintervals = subintervals;
	}

	/**
	 * This method calculates a Riemann sum. If RiemannSumRule extends Riemann and 
	 * RSR is an object of type RiemannSumRule, then RSR.rs should calculate a 
	 * Riemann sum using the particular Riemann sum rule implemented in RiemannSumRule.
	 * @param p is the polynomial whose Riemann sum is to be calculated
	 * @param left is the left hand endpoint of the Riemann sum
	 * @param right is the right hand endpoint of the Riemann sum
	 * @param subintervals is the number of subintervals in the Riemann sum
	 * @return Returns the value of the Riemann sum, claculated 
	 * according to a particular rule which is determined by the child class that extends this method
	 */
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

	/**
	 * This method graphs the input polynomial on the input PlotFrame. 
	 * It also draws the regions used to calculate a particular Riemann sum. 
	 * If RiemannSumRule extends Riemann and RSR is an object of type RiemannSumRule, 
	 * then RSR.rsPlot should graph the input polynomial and draw the regions used to calculate the 
	 * Riemann sum using the rule implemented in RiemannSumRule.
	 * @param frame is the PlotFrame on which the polynomial and the Riemann sum are drawn
	 * @param p is the polynomial whose Riemann sum is to be drawn
	 * @param index is the number associated to the collection of (x,y) coordinates that make up the dataset which, 
	 * when plotted, is the graph of the polynomial
	 * @param precision is the difference between the x-coordinates of two adjacent 
	 * points on the graph of the polynomial
	 * @param left is the left hand endpoint of the Riemann sum
	 * @param right is the right hand endpoint of the Riemann sum
	 * @param subintervals is the number of subintervals in the Riemann sum
	 */
	public void rsPlot(PlotFrame frame, Polynomial p, int index, double precision, double left, double right,
			int subintervals) {

		//draw the parabola
		double delta = (Math.abs(right-left))/subintervals;
		PolyPractice popeye = new PolyPractice();
		for (double i = left; i <= right; i+=precision) {
			frame.append(1, i, popeye.eval(p,i));
		}

		//draw the rectangles
		for (int i = 1; i <= subintervals; i++) {
			slicePlot(frame, p, left+(i-1)*delta, left+i*delta);
		}

	}

	/**
	 * This method graphs the accumulation function corresponding to the input polynomial and the 
	 * input left hand endpoint. If RiemannSumRule extends Riemann and RSR is an object of type RiemannSumRule, 
	 * then RSR.rsAcc should graph the accumulation function corresponding to the input polynomial and the input 
	 * left hand endpoint using the particular Riemann sum rule implemented in RiemannSumRule.
	 * @param frame is the PlotFrame on which the polynomial and the Riemann sum are drawn
	 * @param p is the polynomial whose accumulation function is to be calculated
	 * @param index is the number associated to the collection of (x,y) coordinates that make up the dataset which, 
	 * when plotted, is the graph of the accumulation function
	 * @param precision  is the difference between the x-coordinates of two adjacent points on the graph 
	 * of the accumulation function
	 * @param base is the left hand endpoint of the accumulation function
	 */
	public void rsAcc(PlotFrame frame, Polynomial p, int index, double precision, double base) {

		double yCoord;
		
		//draw the accumulation function from 10 to the left of the base, in the positive direction
		for (double xCoord = base - 10; xCoord<base+10; xCoord+=precision) {
			yCoord = rs(p, base, xCoord, 200);
			frame.append(index, xCoord, yCoord);
		}

	}

	/**
	 * This method calculates the (signed) area between the graph of a polynomial and the x-axis, 
	 * over a given interval on the x-axis. If RiemannSumRule extends Riemann and RSR is an object of type 
	 * RiemannSumRule, then RSR.slice should calculate this area using the particular Riemann sum rule 
	 * implemented in RiemannSumRule.
	 * @param p  is the polynomial whose area (over or under the x-axis), over the interval from sleft to sright, 
	 * is to be calculated
	 * @param sleft is the left hand endpoint of the interval
	 * @param sright is the right hand endpoint of the interval
	 */
	public abstract double slice(polyfun.Polynomial p, double sleft, double sright);

	/**
	 * This method draws the region whose (signed) area is calculated by slice. If RiemannSumRule extends 
	 * Riemann and RSR is an object of type RiemannSumRule, then RSR.slicePlot should draw this region 
	 * using the particular Riemann sum rule implemented in RiemannSumRule.
	 * @param frame is the PlotFrame on which the slicePlot is to be drawn
	 * @param p is the polynomial whose area (over or under the x-axis), over the interval from sleft to sright, 
	 * is to be drawn
	 * @param sleft is the left hand endpoint of the interval
	 * @param sright is the right hand endpoint of the interval
	 */
	public abstract void slicePlot(PlotFrame frame, Polynomial p, double sleft, double sright);

}
