package matrixmath;

import polyfun.Polynomial;
import riemannSum.*;

/**
 * tests the derivative, yo.  
 * @author alex kaplan
 *
 */

public class DerivativeTest	
{

        public static void main(String[] args) 
        {
                
                VerticalDifferenceMethod vdm = new VerticalDifferenceMethod();
                Derivative d = new Derivative();
                
                Polynomial poly1 = new Polynomial (new double[] {1, 5, 3, -2}); // -2x^3 + 3x^2 + 5x + 1
                
                Polynomial poly2 = new Polynomial (new double[] {5, -3, 7, 4, 0, 1}); // x^5 + 4x^3 + 7x^2 - 3x + 5
                
                Polynomial poly3 = new Polynomial (new double[] {0, 0, 1}); // x^2
                
                
                //just print the vdm graphs for good measure and to compare
                vdm.tangentLine(poly1, 2.0);
                
                vdm.slopeFunction(poly1);
                
                vdm.slopeFunction(poly2);
                
                vdm.slopeFunction(poly3);
                
                //d is the object, prindtDx is the function that draws the derivative of poly1 on a graph 
                //along with poly1
                d.printDx(poly1);
                d.printDx(poly2);
                d.printDx(poly3);
                
                poly1.print();
                d.dx(poly1).print();
                
                System.out.println(" \n");
                System.out.println(" \n");

                poly2.print();
                d.dx(poly2).print();
                
                System.out.println(" \n");
                System.out.println(" \n");
                
                poly3.print();
                d.dx(poly3).print();
                
                //it works!!!!!!!!!!!!
                
                //now for the cool part: print the accumulation function (the integral) of the derivative
                //which should just print the regular function:
                
                //this is the cool one btw
                d.derivativeCheckPrint(poly1);
                
        }
}
