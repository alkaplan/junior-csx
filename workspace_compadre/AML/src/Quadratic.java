
public class Quadratic{
	double a;
	double b;
	double c;
	
	public Quadratic(double a, double b, double c){
		this.a=a;
		this.b=b;
		this.c=c;
	}
	public boolean realRoots(){
		double root1 = (-b + Math.sqrt(Math.pow(b,2) - 4*a*c))/(2*a);
		double root2 = (-b - Math.sqrt(Math.pow(b,2) - 4*a*c))/(2*a);
		if(Double.isNaN(root1) || Double.isNaN(root2)) {
			return false;
		}
		else{
			return true;
		}
	}
	public int numberOfRoots() {
		if(realRoots() == false) {
			return 0;
		}
		else {
			if((Math.pow(b,2) - 4*a*c) == 0) {
				return 1;
			}
			else {
				return 2;
			}
		}	
	}
	public double[] zeroes() {
		double[] zeroes = new double[2];
		if(numberOfRoots() == 2) {
			zeroes[0] = (-b + Math.sqrt(Math.pow(b,2) - 4*a*c))/(2*a);
			zeroes[1] = (-b - Math.sqrt(Math.pow(b,2) - 4*a*c))/(2*a);
		}
		else if(numberOfRoots() == 1) {
			zeroes[0] = (-b + Math.sqrt(Math.pow(b,2) - 4*a*c))/(2*a);

		}
		return zeroes;
	}
	public double axisOfSymmetry() {
		double axis;
		axis = (zeroes()[0] + zeroes()[1])/2;
		return axis;
	}
	public double extremeValue() {
		double axis = axisOfSymmetry();
		double value = (a*Math.pow(axis,2) + b*axis + c);
		return value;
		
	}
	public String maxOrMin() {
		if(a>0) {
			return "Min";
		}
		else{
			return "Max";
		}
		
	}
	public double fof(double x) {
		
		return (a*Math.pow(x,2) + b*x + c);
		
	}
	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}
	
}