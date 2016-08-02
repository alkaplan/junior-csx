package acsl;

/**
 * @author Alex Kaplan
 * ACSL 3 15-16 Year: ABC
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ACSL3_ABC15_C17AK {
	
	public static void main(String[] args) throws IOException {
		//declarations:
		String filein = "testdata";
		Scanner scan;
		FileOutputStream fop = null;

		try{
			scan = new Scanner(new BufferedReader(new FileReader(filein)));
			fop = new FileOutputStream(new File(filein+"-out"));
		} catch(FileNotFoundException e) {
			//switch to regular terminal input
			scan = new Scanner(System.in);
		}

		//main loop:
		while(scan.hasNext()) {
			String answer = solve(scan.nextLine());
			System.out.println(answer);
			if(fop!=null) fop.write((answer+"\n").getBytes());
		}

		fop.flush();
		fop.close();
		scan.close();
	}

	/**
	 * solve for a specific input
	 * @param s the input
	 * @return the output
	 */
	static String solve(String s) {
		String[] input = s.split(",[ ]*");

		String[][] boardSix = new String[6][6];

		for (int i = 0; i < boardSix.length; i++) {
			for (int j = 0; j < boardSix.length; j++) {
				boardSix[i][j] = "0";
			}

		}

		//fill the board with the xors
		for (int i = 0; i < 4; i++) {
			boardSix[numToCoords(input[i])[0]][numToCoords(input[i])[1]] = "X";
		}

		//fill the board with the As, Bs, Cs
		for (int i = 0; i < Integer.parseInt(input[4]); i++) {
			int row = numToCoords(input[6 + i*2])[0];
			int column = numToCoords(input[6 + (i*2)])[1];
			boardSix[row][column] = input[(i*2) + 5];
		}


		String[][] fourArray = sixToFour(boardSix);

		fourArray[0][0] = fourArray[0][0];

		String[][] solvedBoard = solveBoard(fourArray);

		String finalString = "";

		for (int i = 0; i < solvedBoard.length; i++) {
			for (int j = 0; j < solvedBoard.length; j++) {
				if(solvedBoard[i][j].charAt(0) == 'X') {
					continue;
				}
				else {
					finalString += solvedBoard[i][j];
				}
			}
		}

		finalString.replaceAll("X", "");

		return finalString;
	}

	static int[] numToCoords(String num) {

		int numint = Integer.parseInt(num);

		int row = (int)((numint - 1)/6);
		int column = numint%6 - 1;
		if(column == -1) {
			column = 5;
		}

		int[] coords = {row, column};


		return coords;
	}

	static String[][] sixToFour (String[][] sixArr) {

		String[][] fourArr = new String[4][4];
		for (int i = 0; i < fourArr.length; i++) {
			for (int j = 0; j < fourArr.length; j++) {
				if(sixArr[i + 1][j + 1] == "X") {
					fourArr[i][j] = "X";
				}
				else {
					fourArr[i][j] = "0";
				}
			}
		}

		//first check the top row
		for (int i = 1; i < 5; i++) {
			if(sixArr[0][i] != "0") {
				String letter = sixArr[0][i];
				if(sixArr[1][i] == "0") {
					fourArr[0][i - 1] = letter;
				}
				else if(sixArr[1][i] == "X") {
					if(sixArr[2][i] == "0") {
						fourArr[1][i-1]  = letter;
					}
					else if(sixArr[2][i] == "X") {
						if(sixArr[3][i] == "0") {
							fourArr[2][i-1]  = letter;
						}
						else if(sixArr[3][i] == "X") {
							if(sixArr[4][i] == "0") {
								fourArr[3][i-1]  = letter;
							}
						}
					}
				}
			}
		}

		//now check the left column
		for(int i = 1; i<5; i++){
			String letter = sixArr[i][0];
			if(sixArr[i][0] != "0") {
				if(sixArr[i][1] == "0") {
					fourArr[i-1][0] = letter;
				}
				else if(sixArr[i][1] == "X") {
					if(sixArr[i][2] == "0") {
						fourArr[i-1][1] = letter;
					}
					else if(sixArr[i][2] == "X") {
						if(sixArr[i][3] == "0") {
							fourArr[i-1][2] = letter;
						}
						else if(sixArr[i][3] == "X") {
							if(sixArr[i][4] == "0") {
								fourArr[i-1][3] = letter;
							}
						}
					}
				}
			}
		}

		//now check the bottom

		for (int i = 1; i < 5; i++) {
			if(sixArr[5][i] != "0") {
				String letter = sixArr[5][i];
				if(sixArr[4][i] == "0") {
					fourArr[3][i-1] = letter;
				}
				else if(sixArr[4][i] == "X") {
					if(sixArr[3][i] == "0") {
						fourArr[2][i-1] = letter;
					}
					else if(sixArr[3][i] == "X") {
						if(sixArr[2][i] == "0") {
							fourArr[1][i-1] = letter;
						}
						else if(sixArr[2][i] == "X") {
							if(sixArr[1][i] == "0") {
								fourArr[0][i-1] = letter;
							}
						}
					}
				}
			}
		}

		//now check the right column

		for (int i = 1; i < 5; i++) {
			if(sixArr[i][5] != "0") {
				String letter = sixArr[i][5];
				if(sixArr[i][4] == "0") {
					fourArr[i-1][3] = letter;
				}
				else if(sixArr[i][4] == "X") {
					if(sixArr[i][3] == "0") {
						fourArr[i-1][2] = letter;
					}
					else if(sixArr[i][3] == "X") {
						if(sixArr[i][2] == "0") {
							fourArr[i-1][1] = letter;
						}
						else if(sixArr[i][2] == "X") {
							if(sixArr[i][1] == "0") {
								fourArr[i-1][0] = letter;
							}
						}
					}
				}
			}
		}


		


		return fourArr;
	}

	static String[][] solveBoard(String[][]board) {

		boolean[] columnHasA = new boolean[4];
		boolean[] rowHasA = new boolean[4];
		boolean[] columnHasB = new boolean[4];
		boolean[] rowHasB = new boolean[4];
		boolean[] columnHasC = new boolean[4];
		boolean[] rowHasC = new boolean[4];


		boolean doneSearching = false;

		while(doneSearching == false) {

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if(board[i][j].charAt(0) == 'A'){
						columnHasA[j] = true;
						rowHasA[i] = true;
					}
					else if(board[i][j].charAt(0) == 'B') {
						rowHasB[i] = true;
						columnHasB[j] = true;
					}
					else if(board[i][j].charAt(0) == 'C'){
						rowHasC[i] = true;
						columnHasC[j] = true;
					}
				}
			}

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if(board[i][j].charAt(0) == '0') {
						if(columnHasA[j] && columnHasB[j]) {
							board[i][j] = "C";
						}
						else if(columnHasB[j] && columnHasC[j]) {
							board[i][j] = "A";
						}
						else if(columnHasA[j] && columnHasC[j]) {
							board[i][j] = "B";
						}
						if(rowHasA[i] && rowHasB[i]) {
							board[i][j] = "C";
						}
						else if(rowHasB[i] && rowHasC[i]) {
							board[i][j] = "A";
						}
						else if(rowHasA[i] && rowHasC[i]) {
							board[i][j] = "B";
						}
					}
				}
			}

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if(board[i][j].charAt(0) == 'A'){
						columnHasA[j] = true;
						rowHasA[i] = true;
					}
					else if(board[i][j].charAt(0) == 'B') {
						rowHasB[i] = true;
						columnHasB[j] = true;
					}
					else if(board[i][j].charAt(0) == 'C'){
						rowHasC[i] = true;
						columnHasC[j] = true;
					}
				}
			}

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if(board[i][j].charAt(0) == '0') {
						if(rowHasA[i] && columnHasC[j]) {
							board[i][j] = "B";
						}
						else if(rowHasA[i] && columnHasB[j]) {
							board[i][j] = "C";
						}
						else if(rowHasB[i] && columnHasA[j]) {
							board[i][j] = "C";
						}
						else if(rowHasB[i] && columnHasC[j]) {
							board[i][j] = "A";
						}
						else if(rowHasC[i] && columnHasA[j]) {
							board[i][j] = "B";
						}
						else if(rowHasC[i] && columnHasB[j]) {
							board[i][j] = "A";
						}
					}
				}
			}

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if(board[i][j].charAt(0) == '0') {
						doneSearching = false;
						break;
					}
					else {
						doneSearching = true;
					}
				}
				if(doneSearching == false) {
					break;
				}
			}
		}



		return board;
	}
}
