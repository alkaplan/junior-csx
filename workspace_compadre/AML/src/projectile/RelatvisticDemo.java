package projectile;

import org.opensourcephysics.controls.AbstractSimulation;

import java.awt.Color;
import java.util.Arrays;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

public class RelatvisticDemo extends AbstractSimulation{
	
	DisplayFrame f1  = new DisplayFrame("X","Y","Animation 1");
	DisplayFrame f2  = new DisplayFrame("X","Y","Animation 2");
	DisplayFrame f3  = new DisplayFrame("X","Y","Animation 3");

	protected void doStep() {
		
	}
	
	public void reset() {
		control.setValue("f1", 1);
		control.setValue("f2", 0);
		control.setValue("f3", 0);
		f1.setVisible(false);
		f2.setVisible(false);
		f3.setVisible(false);
	}
	
	public void initialize() {
		if(control.getDouble("f1") == 1) {
			f1.setVisible(true);
		}
		else if(control.getDouble("f2") == 1) {
			f2.setVisible(true);
		}
		else if(control.getDouble("f3") == 1) {
			f3.setVisible(true);
		}
	}
	
	public void stop() {
		
	}

}
