
import org.opensourcephysics.display.Circle;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.*;

import java.awt.Color;

public class GraphicsTest {
	public static void main(String[] args) {
		
				
		double a = 1;
		double b = 0;
		double c = 0;
		
		Quadratic p = new Quadratic(a,b,c);
		
		PlotFrame myFrame = new PlotFrame("time (s)","distance (m)", "My Next Graph");
		myFrame.setPreferredMinMax(-15, 15, -10, 100);
		myFrame.setConnected(1, true);
		myFrame.setMarkerSize(1, 0);
		myFrame.setConnected(2, true);
		myFrame.setMarkerSize(2, 0);
				
		double x1 = -10;
		
		//TRAIL
		Trail myTrail = new Trail();
		/*
		while(x1<15) {
			myTrail.addPoint(x1,p.fof(x1));
			x1 += .1;
		}
		double x2 = -10;
		while(x2<10) {
			myTrail.addPoint(x2,line(x2));
			x2 += .1;
		}
		**/
		
		//FOR DRAWABLES
		DisplayFrame frame = new DisplayFrame("X", "Y", "Drawing Shapes");

		
		//CIRCLE
		Circle myCircle = new Circle(2, 1, 5);
		myCircle.color = Color.blue;

		
		//RECTANGLE
		DrawableShape myRec = DrawableShape.createRectangle(5, 4, 2, 4);
		myRec.edgeColor = Color.green;
		myRec.color = Color.gray;
		
		frame.addDrawable(myCircle);
		//frame.addDrawable(myTrail);
		frame.addDrawable(myRec);
		
		//myFrame.setVisible(true);
		frame.setVisible(true);
	}
	static double line(double x) {
		double y = 2*x;
		
		return y;
	}

}
