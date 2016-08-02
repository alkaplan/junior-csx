package matrixmath;

import matrixmath.Matrix;

public class TimesTest {

        public static void main(String[] args) {
                
                Matrix trixie  = new Matrix(3,4); //The matrix on the left
                
                Matrix alice = new Matrix(4, 5); //The matrix on the right
                
                for(int i=0; i<3; i++) //Filling the left matrix
                {
                        for(int j=0; j<4; j++)
                        {
                                trixie.setEntry(i,j, i+j);
                        }
                }
                
                for(int i=0; i<4; i++) //Filling the right matrix
                {
                        for(int j=0; j<5; j++)
                        {
                                alice.setEntry(i,j, i-j);
                        }
                }
                
                trixie.print();
                System.out.println();
                System.out.println();
                System.out.println();
                
                alice.print();
                System.out.println();
                System.out.println();
                System.out.println();
                
                trixie.times(alice).print(); //The product of the two matrices
                
                System.out.println("\n \n \n \n");
                
                Matrix timestester1 = new Matrix(3,3);
                Matrix timestester2 = new Matrix(3,3);
                for (int i = 0; i < 3; i++) {
                	for (int j = 0; j < 3; j++) {
                		timestester1.setEntry(i, j, i + j);
                		timestester2.setEntry(i, j, i - j);
                	}
				}
                timestester1.print();
                
                System.out.println("\n \n \n \n");

                timestester2.print();
                
                System.out.println("\n \n \n \n");

                timestester1.times(timestester2).print();
                
                //trying by hand the matrix and invert matrix to see if it makes the identity
                
                Matrix invertTest1 = new Matrix(3,3);
                
                invertTest1.setEntry(0, 0, 1);
                invertTest1.setEntry(0, 1, 2);
                invertTest1.setEntry(0, 2, 3);
                invertTest1.setEntry(1, 0, 4);
                invertTest1.setEntry(1, 1, 5);
                invertTest1.setEntry(1, 2, 6);
                invertTest1.setEntry(2, 0, 7);
                invertTest1.setEntry(2, 1, 8);
                invertTest1.setEntry(2, 2, 10);
                
                System.out.println("\n \n \n \n");

                invertTest1.print();
                
                System.out.println("\n \n \n \n");

                
                Matrix invertTest2 = new Matrix(3,3);
                invertTest2.setEntry(0, 0, -0.67);
                invertTest2.setEntry(0, 1, -1.33);
                invertTest2.setEntry(0, 2, 1);
                invertTest2.setEntry(1, 0, -0.67);
                invertTest2.setEntry(1, 1, 3.67);
                invertTest2.setEntry(1, 2, -2);
                invertTest2.setEntry(2, 0, 1);
                invertTest2.setEntry(2, 1, -2);
                invertTest2.setEntry(2, 2, 1);
                
                invertTest2.print();
                
                System.out.println("\n \n \n \n");

                invertTest1.times(invertTest2).print();
                
        }

}