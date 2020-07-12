package sudoku;

import java.io.*;

public class Sudoku {
	private int[][] board;

	public Sudoku() {
		board = new int[9][9];
	}

	public Sudoku(int[][] board) {
		this.board = board;
	}

	public Sudoku(String filename) throws IOException {
		board = new int[9][9];

		BufferedReader fin = new BufferedReader(new FileReader(filename));

		String line = fin.readLine();

		try {
			for (int i = 0; i < 9; i++) {
				String[] numbers = line.split(" ");

				for (int j = 0; j < 9; j++) {
					board[i][j] = Integer.parseInt(numbers[j]);
				}

				line = fin.readLine();
			}

			fin.close();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Invalid input file format: has to be 9x9 separated by spaces");
		}
	}

	public void setSquare(int i, int j, int value) {
		if (value <= 9 && value >= 0) {
			try {
				board[i][j] = value;
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new ArrayIndexOutOfBoundsException("The board is 9x9");
			}
		} else {
			throw new IllegalArgumentException("Value has to be between 0 and 9");
		}
	}

	public int getSquare(int i, int j) {
		try {
			return board[i][j];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("The board is 9x9");
		}
	}

	public boolean[] getPossibleValues(int i, int j) {
		boolean[] possibleValues = new boolean[9];

		for (int k = 0; k < 9; k++) {
			possibleValues[k] = true;
		}

		for (int k = 0; k < 9; k++) {
			if (k != j && board[i][k] != 0) {
				possibleValues[board[i][k] - 1] = false;
			}
		}

		for (int k = 0; k < 9; k++) {
			if (k != i && board[k][j] != 0) {
				possibleValues[board[k][j] - 1] = false;
			}
		}

		for (int k = (i / 3) * 3; k < ((i / 3) + 1) * 3; k++) {
			for (int l = (j / 3) * 3; l < ((j / 3) + 1) * 3; l++) {
				if ((k != i && l != j) && board[k][l] != 0) {
					possibleValues[board[k][l] - 1] = false;
				}
			}
		}

		return possibleValues;
	}

	public boolean isSolved() {
		for (int i = 0; i < 9; i++) {
			boolean[] numInRow = new boolean[9];

			for (int j = 0; j < 9; j++) {
				if (board[i][j] != 0) {
					numInRow[board[i][j] - 1] = true;
				} else {
					return false;
				}
			}

			for (boolean present : numInRow) {
				if (!present) {
					return false;
				}
			}
		}

		for (int j = 0; j < 9; j++) {
			boolean[] numInColumn = new boolean[9];

			for (int i = 0; i < 9; i++) {
				if (board[j][i] != 0) {
					numInColumn[board[j][i] - 1] = true;
				} else {
					return false;
				}
			}

			for (boolean present : numInColumn) {
				if (!present) {
					return false;
				}
			}
		}

		return true;
	}

	public Sudoku copySudoku() {
		int[][] copyBoard = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				copyBoard[i][j] = board[i][j];
			}
		}

		return new Sudoku(copyBoard);
	}

	public void printBoard() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j] + " ");
				if (j % 3 == 2 && j != 8) {
					System.out.print("| ");
				}
			}

			System.out.println();
			if (i % 3 == 2 && i != 8) {
				System.out.println("------+-------+------");
			}
		}
	}
}
