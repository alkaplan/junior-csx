package matrixmath;


import org.opensourcephysics.frames.PlotFrame;
import matrixmath.*;
import polyfun.*;
import riemannSum.*;

/**
 * yo yo yo.  Welcome to the bonus class.  You've made it past the boring stuff, now here's where
 * shit gets cool.  
 * 
 * This class finds the derivative of a function.  You may think that the VDM class does this, but in reality
 * it is only able to find the slope at a point given the x-coordinate of the point and the function as
 * parameters.  This class, however, is able to find the actual *EQUATION* of the derivative.
 * 
 * Here's how it works: not that complicated, actually.
 * 
 * The derivative of any function p(x) is of degree one less than that of p(x).  So we can write the
 * generic derivative of p(x):
 * 
 * 	 			(a_n * x^n) + (a_n-1 * x^n-1) + ... + (a_2 * x^2) + (a_1 * x) + (a_0)
 * 
 * We notice that here we have n+1 numbers of coefficients (including a_0), and what we really want to do
 * is just find these coefficients.  If we can find the different values of a_i for all i, then
 * we have our derivative function!
 * 
 * And it turns out we actually CAN find the values of all of the coefficients by creating an equation
 * of matrices (realistically, a system of equations, but hey, this is the matrix assignment).  
 * 
 * ********important part begins********* (top part isn't super necessary to read)
 * 
 * The way we find the values of coefficients of the derivative is by plugging in known values of (x,y) for
 * n times, where n is the number of coefficients.  We can do this because with the VDM class we know
 * what the derivative equals at certain x values, so if we plug in those x values to our generic derivative
 * system of equations and our y values as a result column matrix, then we have a solveable (and large!)
 * system of equations to find each coefficient.  
 * 
 * Here's a simple example: say we have p(x) = ax^3 + bx^2 + cx + d
 * 
 * let us define dx as : Ax^2 + Bx + C (NOTE: A != a, B != b, C != c.) We can then create a system of matrices
 * like so:
 * 
 * ( 1^2 1^1 1^0 ) (A) = (vdm.slopeAtPoint(1)) | x = 1, y = slope of p(x) at 1
 * ( 2^2 2^1 2^0 ) (B) = (vdm.slopeAtPoint(2)) | x = 2, y = "              " 2
 * ( 3^2 3^1 3^0 ) (C) = (vdm.slopeAtPoint(3)) | x = 3, y = "              " 3
 * 
 * where each subsequent row is just a 1 higher value of x, e.g. x increases by one for each row, so that
 * we have independant equations to solve in a system. This esentially makes "x" the coefficient and solves
 * for the remaining variables.
 * 
 * we can now use our matrix operations to solve for A, B, and C by inverting the left-hand matrix
 * and multiplying it by the final matrix (that on the right).  
 * 
 * ------------------------------------------------------------------------------
 * 
 * So that's the first part of the this class and the important one, for which it is named.  The other
 * important function is proving the fundamental theorem of calculus, (or really checking my work, because
 * we know that the F.T.C. is correct) that the integral (accumulation function) of the derivative is equal
 * to the original function.  
 * 
 * @author Alex Kaplan
 *
 */

public class Derivative {

	/**
	 * make class an object so you can use it
	 */
	public Derivative() {

	}

	/**
	 * This is the class that creates the derivative as described above.  Look for in-line 
	 * documentation to follow each step.
	 * @param p the polynomial to find the derivative of
	 * @return derivative the derivative of polynomial p
	 */
	public Polynomial dx(Polynomial p) {

		//make object to use
		VerticalDifferenceMethod vdm = new VerticalDifferenceMethod();

		//create the system of eqeuations matrix; that in the upper-left corner of the example in top block
		Matrix sysEqns = new Matrix((p.getDegree()), (p.getDegree()));

		//fill the system of equations matrix
		int x = 1;
		//start with x=1 as we did in example, as x=0 would just return 0+0+0+0+0+a_0 = slope at 0
		for (int i = 0; i < sysEqns.matrix.length; i++) {
			for (int j = 0; j < sysEqns.matrix[0].length; j++) {
				//this line took quite a bit of debugging to figure out:
				//it sets each value of the matrix, which should follow the following rules:
					//degree to raise x to is equal to the length of matrix[0] - the column you're on
					//each lower row should have a 1 further x-value
				sysEqns.setEntry(i, j, Math.pow(x, (sysEqns.matrix[0].length-(j+1))));
			}
			x++;
		}

		//this is the right-hand matrix in the example above. I never created the (A B C) matrix because
		//it isn't actually necessary
		Matrix finalCoefVals = new Matrix(p.getDegree(), 1);

		//fill the right-matrix with the slope for each x-value using the VDM class.
		x = 1;
		for (int i = 0; i < finalCoefVals.matrix.length; i++) {
			finalCoefVals.matrix[i][0] = vdm.slopeAtPoint(p, x);
			x++;
		}

		//matrix math: to move the left hand matrix to the right side of the equation, we multiply
		//both sides by the inverse of the matrix, but we don't really care about the left side, just what
		//ends up on the right, so we multiply the right-side matrix by inverse of sysEqns
		finalCoefVals = sysEqns.invert().times(finalCoefVals);

		//here is our double array of coefficients, the thing that we'll actually use to make our derivative
		//polynomial
		double[] coefsArr = new double[p.getDegree()];

		//fill the coefArr double array with proper values (NOTE: go in reverse order b/c that's how
		//gomp wrote polynomial constructor)
		for (int i = 0; i < finalCoefVals.matrix.length; i++) {
			coefsArr[i] = finalCoefVals.matrix[(finalCoefVals.matrix.length - (i+1))][0];
			coefsArr[i] = Math.round(coefsArr[i]*10000)/10000;  
			//clever little trick to chop off annoying 10^-14 errors
			
		}

		Polynomial derivative = new Polynomial(coefsArr);

		return derivative;
	}

	/**
	 * this is avoid class that prints the derivative and the original polynomial on the same
	 * graph, just to make things easier for test classes.
	 * @param p the polynomial to find the derivative of and graph with the derivative
	 */
	public void printDx(Polynomial p) {

		//same shenanigans as last 
		double left = -10;
		double right = 10;
		double precision = .01;
		Polynomial derivative = dx(p);
		PlotFrame frame = new PlotFrame ("Blue = Function, Green = Derivative", "", "Derivative");

		//all the same stuff. does this even need commenting? it's late, and this is due soon.
		//this just adds the stuff to the graph
		for (double i = left; i <= right; i+=precision) {
			frame.append(1, i, PolyPractice.eval(derivative, i)); 
			frame.append(2, i, PolyPractice.eval(p, i)); 
		}
		
		frame.setVisible(true);
		frame.setPreferredMinMax(left, right, -10, 10);
	}
	
	/**
	 * This is the class that checks that the integral (accumulation function using riemann sum) of the
	 * derivative is equal to the original function, by graphing the original function vs. the derivative
	 * as well as the accumulation function of the derative vs. the derivative.
	 * @param p the polynomial to test it on
	 */
	public void derivativeCheckPrint(Polynomial p) {
		
		//lets be real everything in here is super self-explanatory. also like come on this is due so soon.
		
		LeftHandRule lhr = new LeftHandRule();
		double left = -10;
		double right = 10;
		double precision = .01;
		PlotFrame dxAcc = new PlotFrame("Blue = Acc of dx, Green = dx","","Acc of dx and dx");
		Polynomial derivative = dx(p);
			
		for (double i = left; i <= right; i+=precision) {
			dxAcc.append(1, i, PolyPractice.eval(derivative, i));
		}
		
		//this plots the accumulation function of the derivative
		lhr.rsAcc(dxAcc, derivative, 2, precision, 0);
		
		PlotFrame dxPoly = new PlotFrame("Blue = Function, Green = dx","","Original f and dx");
		
		for(double i = left; i<= right; i+=precision){
			dxPoly.append(1, i, PolyPractice.eval(derivative,i));
			dxPoly.append(2, i, PolyPractice.eval(p,i));
		}
		
		dxAcc.setVisible(true);
		dxAcc.setPreferredMinMax(left, right, -10, 10);
		dxPoly.setVisible(true);
		dxPoly.setPreferredMinMax(left, right, -10, 10);
		
	}

}
