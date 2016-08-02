package riemannSum;
import polyfun.Polynomial;

public class AddXSquaredTest {

	public static void main(String[] args) 
	{
		PolyPractice popeye = new PolyPractice(); 
		Polynomial poly = new Polynomial(new double[] {-6,-1}); 
		popeye.addxSquared(poly); 
	}
}