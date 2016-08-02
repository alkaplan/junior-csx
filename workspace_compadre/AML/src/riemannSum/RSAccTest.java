package riemannSum;

import org.opensourcephysics.frames.PlotFrame;
import polyfun.Polynomial;

public class RSAccTest 
{
        public static void main(String[] args) 
        {
                LeftHandRule LHR = new LeftHandRule();  //LeftHandRule extends Riemann
                RightHandRule RHR = new RightHandRule();  //RightHandRule extends Riemann
                TrapezoidRule TR= new TrapezoidRule(); //TrapezoidRule extends Riemann
                        
                Polynomial p = new Polynomial(new double[] {0,1,1}); // p=x^2+x
        
                PlotFrame f = new PlotFrame("x","y","Left Hand Rule Accumulation Function Graph");
                f.setPreferredMinMaxX(-3, 3);
                f.setPreferredMinMaxY(-10, 10);
                f.setDefaultCloseOperation(3);
                f.setVisible(true);
                        
                PlotFrame g = new PlotFrame("x","y","Right Hand Rule Accumulation Function Graph");
                g.setPreferredMinMaxX(-3, 3);
                g.setPreferredMinMaxY(-10, 10);
                g.setDefaultCloseOperation(3);
                g.setVisible(true);
                        
                PlotFrame i = new PlotFrame("x","y","Trapezoid Rule Accumulation Function Graph");
                i.setPreferredMinMaxX(-3, 3);
                i.setPreferredMinMaxY(-10, 10);
                i.setDefaultCloseOperation(3);                  
                i.setVisible(true);
                        
                LHR.rsAcc(f, p, 2, .01, -1.0); //plots the left hand rule acccumulation function of x^2+x, with base x=-1;
                //LHR.rsPlot(f, p, 1, .01, -11, 11, 2000);        
                
                RHR.rsAcc(g, p, 2, .01, -1.0); //plots the right hand rule acccumulation function of x^2+x, with base x=-1;
                //RHR.rsPlot(g, p, 1, .01, -11, 11, 2000);    
                
                TR.rsAcc(i, p, 2, .01, -1.0); //plots the trapezoid rule acccumulation function of x^2+x, with base x=-1;  
                //TR.rsPlot(i, p, 1, .01, -11, 11, 2000);
        }
}       