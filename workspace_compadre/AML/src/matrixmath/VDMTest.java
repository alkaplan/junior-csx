package matrixmath;

import polyfun.Polynomial;

/**
 * this class tests the VDM class:  it asks VDM to print out a couple of tangent lines to functions as well
 * as asking it to graph the derivative by finding the slope of the TL at each point and plugging them in.
 * That's just how the graphing of derivative in VDM works.
 * @author student
 *
 */

public class VDMTest
{

        public static void main(String[] args) 
        {
                
                VerticalDifferenceMethod vdm = new VerticalDifferenceMethod();
                
                //gomp's constructors for polynomials
                Polynomial poly1 = new Polynomial (new double[] {1, 5, 3, -2}); // -2x^3 + 3x^2 + 5x + 1
                
                Polynomial poly2 = new Polynomial (new double[] {5, -3, 7, 4, 0, 1}); // x^5 + 4x^3 + 7x^2 - 3x + 5
                
                Polynomial poly3 = new Polynomial (new double[] {0, 0, 1}); // x^2
                
                //print out the slopes too yo
                System.out.println(vdm.slopeAtPoint(poly1, 2.0));
                
                vdm.tangentLine(poly1, 2.0);
                
                vdm.slopeFunction(poly1);
                
                System.out.println("\n");
                
                System.out.println(vdm.slopeAtPoint(poly2, -5.0));
                
                vdm.tangentLine(poly2, -5.0);
                
                vdm.slopeFunction(poly2);
                
                System.out.println("\n");
                
                System.out.println(vdm.slopeAtPoint(poly3, 0.0));
                
                vdm.tangentLine(poly3, 0.0);
                
                vdm.slopeFunction(poly3);
        }
}
