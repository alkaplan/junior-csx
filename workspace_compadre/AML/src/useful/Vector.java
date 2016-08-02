package useful;

/**
 * the inspiration for this class came from davis, but 100% of the code was written by me.
 * @author student
 *
 */

public class Vector {
	
	/**
	 * The magnitude of the vector
	 */
	public double magnitude;
	
	/**
	 * The angle of the vector, in radians, from [0,2pi)
	 */
	public double angle;
	
	public Vector(double magnitude, double angle) {
		this.magnitude = magnitude;
		this.angle = angle;
	}
	
	public double xComp(Vector v) {
		
		double component = v.magnitude * Math.cos(v.angle);
		
		return component;
	}
	
	public double yComp(Vector v) {
		
		double component = v.magnitude * Math.sin(angle);
		
		return component;
	}
	
	public Vector componentsToVector(double xComp, double yComp) {
		
		double angle = getAnglePoints(0,0,xComp,yComp);
		double magnitude = Math.sqrt(Math.pow(xComp, 2) + Math.pow(yComp,2));
		
		Vector v = new Vector(magnitude, angle);
		return v;
	}
	
	public Vector add(Vector v1, Vector v2) {
		
		double xComp = xComp(v1) + xComp(v2);
		double yComp = yComp(v1) + yComp(v2);
		
		Vector sum = componentsToVector(xComp, yComp);
		
		return sum;
	}
	
	/**
	 * subtracts Vector v2 from Vector v1
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return the difference between the vectors
	 */
	public Vector subtract(Vector v1, Vector v2) {
		
		double xComp = xComp(v1) - xComp(v2);
		double yComp = yComp(v1) - yComp(v2);
		
		Vector difference = componentsToVector(xComp, yComp);
		
		return difference;
		
	}
	
	public double dotProduct(Vector v1, Vector v2) {
		
		double dot = 0;
		
		dot = xComp(v1)*xComp(v2) + yComp(v1)*yComp(v2);
		
		return dot;
	}
	
	public double getAnglePoints(double x1, double y1, double x2, double y2) {
		
		double angle = 0;
		double temp = 0;
		
		if(x1 == x2 || y1 == y2) {
			if(x1 == x2) {
				if(y1 > y2) {
					angle = (3/2) * Math.PI;
				}
				else if(y2 > y1) {
					angle = Math.PI/2;
				}
				else if(y2 == y1) {
					angle = 0;
				}
			}
			else if(y1 == y2) {
				if(x1 > x2) {
					angle = Math.PI;
				}
				else if(x2 > x1) {
					angle = 0;
				}
				else {
					angle = 0;
				}
			}
		}
		else if(x1 > x2) {	
			if(y1 > y2) {
				//theta in quadrant 3
				temp = Math.atan((y1 - y2)/(x1 - x2));
				angle = Math.PI + temp;
			}
			else {
				//theta in quadrant 2
				temp = Math.atan((x1 - x2)/(y2 - y1));
				angle = (Math.PI)/2 + temp;
			}
		}
		else {
			if(y1 > y2) {
				//theta in quadrant 4
				temp = Math.atan((x2 - x1)/(y1 - y2));
				angle = ((3/2) * Math.PI) + temp;
			}
			else {
				//theta in quadrant 1
				temp = Math.atan((x2 - x1)/(y2 - y1));
				angle = temp;
			}
		}
		
		return angle;
	}

}
