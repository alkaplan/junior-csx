package matrixmath;

/**
 * Matrix Class! WOOOOOO! We hype here it is
 * 
 * This class is a matrix object which has some properties as well as, more importantly, methods that it can
 * do.  The only important properties are its width and length (i.e. #rows, #columns) and the value of numbers
 * at each of those places.  
 * @author student
 *
 */
public class Matrix {

	double[][] matrix;

	/**
	 * constructor for matrix
	 * @param r num rows
	 * @param c num columns
	 */
	public Matrix(int r, int c) {

		matrix = new double[r][c];

	}

	/**
	 * sets an entry in the matrix. this is how you fill them
	 * @param row which row to put the entry in
	 * @param column which column to put the entry in
	 * @param entry what the entry you want to put in is
	 */
	public void setEntry(int row, int column, double entry) {
		this.matrix[row][column] = entry;
	}

	/**
	 * prints the matrix. this is like CS2 shit.
	 */
	public void print() {
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[0].length; j++) {
				System.out.print(String.format("%.2f", this.matrix[i][j]) + " ");
			}
			System.out.println();
		}
	}

	/**
	 * adds two matrices together by pushing one off of a table onto another! Just kidding, but kinda.
	 * @param otherMatrix matrix you wish to add to this
	 * @return sum the final matrix which is the sum of the given one and (this) one
	 */
	public Matrix plus(Matrix otherMatrix) {

		Matrix sum = new Matrix(this.matrix.length, this.matrix[0].length);

		//surround with try catch in case of out of bounds
		try{
			//make sure sizes are compatible
			if(otherMatrix.matrix.length == this.matrix.length && 
					otherMatrix.matrix[0].length == this.matrix[0].length) {
				//simple double for loop to hit each value
				for (int i = 0; i < this.matrix.length; i++) {
					for (int j = 0; j < this.matrix[0].length; j++) {
						sum.setEntry(i, j, this.matrix[i][j] + otherMatrix.matrix[i][j]);
					}
				}
			}
			else {
				throw new Exception();
			}
		}

		catch(Exception E){
			System.out.println("Error: matrices not the same size");
		}

		return sum;
	}

	/**
	 * this one was a little tricky at first.  This takes this.matrix and multiplies another matrix by it.
	 * Note that it returns a matrix so you can't do it to an object like array.sort or something.
	 * @param otherMatrix the matrix to multiply this one by	
	 * @return product the product of the two matrices
	 */
	public Matrix times(Matrix otherMatrix) {

		Matrix product = new Matrix(this.matrix.length, otherMatrix.matrix[0].length);

		//check compatibility
		if(this.matrix[0].length == otherMatrix.matrix.length) {

			double sum = 0;

			//part 1: for loop to set entries in the product array

			//pick which row of first matrix
			for (int i = 0; i < matrix.length; i++) {

				//pick which column of other matrix
				for (int j = 0; j < otherMatrix.matrix[0].length; j++) {
					sum = 0;

					//loop through each entry in the row a
					for (int k = 0; k < this.matrix[0].length; k++) {

						sum += (this.matrix[i][k] * otherMatrix.matrix[k][j]);

					}
					//add after done with each row
					product.setEntry(i, j, sum);
				}
			}
		}
		else {
			System.out.println("Error: Matrices not compatible sizes");

		}

		return product;

	}

	/**
	 * This multiplies a row of a matrix by a scalar, Aka a number. Useful for invert/rowreduce
	 * @param scalar num to multiply row by
	 * @param row which row to scalar mult
	 * @return result the rsulting matrix after scalar multiplication
	 */
	public Matrix scalarTimesRow(double scalar, int row) {


		Matrix result = new Matrix(this.matrix.length, this.matrix[0].length);

		//create the result matrix
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[0].length; j++) {
				result.matrix[i][j] = this.matrix[i][j];
			}
		}

		//multiply the scalar by the row
		for (int i = 0; i < this.matrix[row].length; i++) {
			result.setEntry(row, i, (scalar*result.matrix[row][i]));
		}

		return result;
	}

	/**
	 * switch two rows of a matrix. I created temporary double[]s to hold the rows of the matrix
	 * while i switched them.	 Note: order doesn't matter bc you're switching them	
	 * @param firstrow the first row to swtich
	 * @param secondrow the second row to switch
	 * @return result the switchRows'ed result
	 */
	public Matrix switchRows(int firstrow, int secondrow) {

		Matrix result = new Matrix(this.matrix.length, this.matrix[0].length);

		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[0].length; j++) {
				result.matrix[i][j] = this.matrix[i][j];
			}
		}

		//create temp double arrays
		double[] row1 = new double[this.matrix[firstrow].length];
		double[] row2 = new double[this.matrix[secondrow].length];

		for (int i = 0; i < row1.length; i++) {
			row1[i] = this.matrix[firstrow][i];
			row2[i] = this.matrix[secondrow][i];
		}

		//switch rows using double arrays
		for (int i = 0; i < matrix[0].length; i++) {
			result.matrix[firstrow][i] = row2[i];
			result.matrix[secondrow][i] = row1[i];
		}

		return result;

	}

	/**
	 * this combines two rows by adding a scalar multiple of one row to another. 
	 * @param scalar scalar to multiply by		
	 * @param firstrow the row to add to the second row
	 * @param secondrow the row to be added to
	 * @return result the resulting matrix
	 */
	public Matrix linearCombRows(double scalar, int firstrow, int secondrow) {

		Matrix result = new Matrix(this.matrix.length, this.matrix[0].length);

		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[0].length; j++) {
				result.matrix[i][j] = this.matrix[i][j];
			}
		}

		double[] row1 = new double[this.matrix[firstrow].length];
		double[] row2 = new double[this.matrix[secondrow].length];

		for (int i = 0; i < row1.length; i++) {
			row1[i] = this.matrix[firstrow][i];
			row2[i] = this.matrix[secondrow][i];
		}

		//multiply row 1 by the scalar
		for (int i = 0; i < row1.length; i++) {
			row1[i] = row1[i] * scalar;
		}

		//add row 1 to row 2;
		for (int i = 0; i < row2.length; i++) {
			row2[i] = row2[i] + row1[i];
		}

		//change the result matrix with the new row 2

		for (int i = 0; i < result.matrix[secondrow].length; i++) {
			result.matrix[secondrow][i] = row2[i];
		}

		return result;
	}

	/**
	 * rowreduce sucks. This took me a while.  It reduces any matrix (not just square ones) to reduced
	 * row echelon form, in which there is never a non-0 directly under a 1 which is situated on 
	 * the cross (upper left to bottom right) diagonal of the matrix.
	 * @return
	 */
	public Matrix rowreduce() {

		//creating the matrix
		Matrix result = new Matrix(this.matrix.length, this.matrix[0].length);

		//filling the matrix
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[0].length; j++) {
				result.matrix[i][j] = this.matrix[i][j];
			}
		}

		//for loop to row reduce: step 1: start at the first column
		for (int i = 0; i < result.matrix[0].length; i++) {
			//only go on if the column can be rowreduced (if the column # is less then the num rows)
			if(i < matrix.length) {

				//start at the column that you're on for the row number (why you need previous if statement)
				//then as long as where you are right now is not 0, invert it (multiply by reciprocal)
				//and then switchrows 
				for (int j = i; j < matrix.length; j++) {
					if(result.matrix[j][i] != 0) {
						result = result.scalarTimesRow((1/result.matrix[j][i]), j);
						result = result.switchRows(i, j);
						break;
					}
				}
				//make all the other rows zero
				for (int j = 0; j < matrix.length; j++) {
					if(j != i) {
						result = result.linearCombRows(-1*(result.matrix[j][i]), i, j);
					}
				}
			}
		}

		return result;

	}

	/**
	 * inverts matrices yo
	 * so this is kinda cool because it uses all the other functions of the matrix class. Notice that it
	 * does not take any parameters, it just changes the existing object so it is done on an object.  
	 * @return result the resulting matrix after being inverted
	 */
	public Matrix invert() {

		//create a augmented matrix same way we invert matrices in class
		Matrix augment = new Matrix(this.matrix.length, this.matrix[0].length * 2);

		//fill the first half of the augmented matrix (the one that is what this matrix looks like)
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[0].length; j++) {
				augment.matrix[i][j] = this.matrix[i][j];
			}
		}

		//fill the second half of the augmented matrix (just identity matrix)
		for (int i = augment.matrix[0].length/2; i < augment.matrix[0].length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if(i - (augment.matrix[0].length/2) == j) {
					augment.matrix[j][i] = 1;
				}
				else{
					augment.matrix[j][i] = 0;
				}
			}
		}

		//row reduce augmented matrix to get left side all nice and reduced. What's cool about row reduce
		//is it will actually fully reduce all reduceable matrices, so as long as your matrix can be inverted
		//it will be by this class.
		augment = augment.rowreduce();

		//creating the resulting matrix
		Matrix result = new Matrix(this.matrix.length, this.matrix[0].length);
		
		//fill result with the right half of the augmented matrix
		int x = 0;
		for (int i = augment.matrix[0].length/2; i < augment.matrix[0].length; i++) {
			for (int j = 0; j < augment.matrix.length; j++) {
				result.matrix[j][x] = augment.matrix[j][i];
			}
			x++;
		}




		return result;
	}

}
