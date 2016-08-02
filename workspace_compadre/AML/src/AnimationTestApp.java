import org.opensourcephysics.controls.*;

public class AnimationTestApp extends AbstractSimulation{

	int x;
	
	protected void doStep() {
		
		control.println("X = " + x);
		x++;
		
	}
	public void reset() {
		control.setValue("x", 50);
	}
	
	public void initialize() {
		int z = control.getInt("x");
	}
	
	public static void main(String[] args) {
		
		SimulationControl.createApp(new AnimationTestApp());
	}

}
