package matrixmath;

import polyfun.*;
import org.opensourcephysics.frames.PlotFrame;
import riemannSum.PolyPractice;

/**
 * 
 * @author Alex Kaplan
 * This is the VDM class. It takes in a function and determines the slope at a given x coordinate
 * using the vertical difference method and matrixmath.  The most difficult part of this code is how the
 * matrix that I named k is filled.  There is simple a pattern to filling out the coefficients of a polynomial
 * as systems of equations, and it is interesting as a 2-dimesional array.  The way that this VDM class works
 * (and how the matrix is filled follows) is as so:
 * 
 * We take our function p(x) and subtract (mx+b) so that it becomes p(x)-mx-b.  We can set this function
 * to a generic polynomial (x-a)^(p.degree-1) with a bunch of generic coefficients, say q_0, q_1, q_2, etc.
 * 
 * Here is what a system of equations that our eventual VDM class will take in and solve might look like:
 * 
 * ( 1  0  -2  .5  0  1) (q_5) = (3)
 * ( 0  1  3.7  2 -9  0) (q_4) = (2)
 * ( 0  0   1 -1.4 3  2) (q_3) = (6)
 * ( 0  0   0   1  2  0) (q_2) = (1)
 * ( 0  0   0   0  1  3) (q_1) = (7)
 * ( 0  0   0   0  0  1) (q_0) = (3)
 * 
 * 
 * Then our VDM can invert the first matrix and multiply the second matrix by it to find the value of each
 * coefficient. The only thing we actually care about, though, is the value of q_1, as it is the coefficient
 * of the x term, or the slope of the tangent line!
 * 
 * Sorry my documentation for this one isn't as fun as the one for the bonus class. I was much more excited
 * during the bonus class documentation.
 */


public class VerticalDifferenceMethod {

	/**
	 * empty constructor for VDM object so you can do things with it
	 */
	public void VDM() {
		
	}
	
	/**
	 * Finds the slope of a given polynomial at given value
	 * @param p the polynomial
	 * @param xCoord the x value to find the slope at
	 * @return the slope at the given point (which is the coefficient of the linear term, or m)
	 */
	public double slopeAtPoint (Polynomial p, double xCoord) {
		
		//create the matrix with all of the values in it: in VDM algebra solving, this is 
		//the one that is eventually in upper triangular form and you can then reduce, but instead
		//here we just invert it and multiply it by vFinal to get the coefficients
		Matrix k = new Matrix(p.getDegree()+1, p.getDegree()+1); 
		
		//column matrix of end values (the right side of the equation in VDM math)
		//k times qCoef is vFinal, so invert k then multiply by V to get values of qCoef terms
		Matrix vFinal = new Matrix(p.getDegree()+1, 1);

		// for loop that fills the matrix k with proper values
		for (int i=0; i<p.getDegree()+1; i++) { 
			for (int j=0; j<p.getDegree()+1; j++) { 
				
				//i figured this part out by doing a couple of VDM problems:
				//if the row and column are the same, it will be 1 because it is in upper triangular form
				//but for the other values, it depends on the xCoord and degree of the function, which
				//is actually kind of cool! my favorite observation is that if the column you're in is
				//2 less than the row you're in, the value of the coefficient will just be x^2.
				
				if (j==i) {
					k.matrix[i][j] = 1;
				}
				else if (i!=p.getDegree() && j==i-1) { 
					k.matrix[i][j] = -2*xCoord;
				}
				else if (j==i-2) { 
					k.matrix[i][j] = xCoord*xCoord;
				}
				else {
					k.matrix[i][j] = 0;
				}
			}
		}
		
		//fill vFinal matrix with proper values:
		//use the .getCoef (and other stuff) found in polynomial javadoc to fill array
		//this was kind of annoying but not that complicated, just see polyfun javadoc
		for (int i = p.getDegree(); i>=0; i--) {
			vFinal.matrix[p.getDegree()-i][0] = p.getCoefficient(i).getTerms()[0].getTermDouble(); 
		}

		//create the matrix of coefficients (the left matrix in VDM math)
		Matrix qCoef = new Matrix(p.getDegree()+1, 1);

		//the jist of VDM matrix math and the super important step:
		//multiply the coef array by the inverted k array (of values)
		//IMPORTANT: remember that multiplication does not commute in matrix algebra, so it's
		//k.invert.times(vFinal) instead of vFinal.times(k.invert)
		for (int i = 0; i < p.getDegree()+1; i++) {
			qCoef.matrix[i][0] = k.invert().times(vFinal).matrix[i][0];
		}
		
		//returns the linear coefficient of x, or the slope of the tangent line! woohoo!
		return qCoef.matrix[p.getDegree()-1][0];
	}
	
	/**
	 * Graphs the tangent line to given polynomial p at given x coordinate xCoord
	 * @param p	the polynomial to find the tangent line to
	 * @param xCoord the place at which to find the tangent line
	 */
	public void tangentLine (Polynomial p, double xCoord) {
		
		//set the bounds of the graph: -10 to 10, have a point every 0.1 units
		double left = -10; 
		double right = 10; 
		double precision = .01; 
		
		PlotFrame frame = new PlotFrame ("Blue = TL, Green = Function", "", "Tangent line function"); // plots
		
		//for loop to append points onto the graph
		for (double i = left; i <= right; i+=precision) { 
			//given polynomial (in color 1)
			frame.append(1, i, PolyPractice.eval(p, i)); // original polynomial graph
			//tangent line to p at xCoord 
			frame.append(2, i, slopeAtPoint(p, xCoord)*(i-xCoord) + PolyPractice.eval(p, xCoord)); 
		}
		
		frame.setVisible(true);
		frame.setPreferredMinMax(left, right, -10, 10);
	}
	
	/**
	 * Graphs the slope function (derivative) of given polynomial as well as original poly
	 * @param p the polynomial to differentiate
	 */
	public void slopeFunction (Polynomial p) { // completes above process for all points and graphs
		
		//same shenanigans as last 
		double left = -10;
		double right = 10;
		double precision = .01;
		PlotFrame frame = new PlotFrame ("Blue = Function, Green = Slope Function", "", "Slope Function");
		
		//all the same stuff. does this even need commenting? it's late, and this is due soon.
		//this just adds the stuff to the graph
		for (double i = left; i <= right; i+=precision) {
			frame.append(1, i, slopeAtPoint(p, i)); 
			frame.append(2, i, PolyPractice.eval(p, i)); 
		}
		
		frame.setVisible(true);
		frame.setPreferredMinMax(left, right, -10, 10);
	}

}
