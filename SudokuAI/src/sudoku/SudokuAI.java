package sudoku;

import java.io.IOException;

public class SudokuAI {
	public static void main(String[] args) throws IOException {
		Sudoku puzzle = new Sudoku("sudoku.txt");
		SudokuAI solver = new SudokuAI(puzzle);
		solver.solve();
		solver.getPuzzle().printBoard();
	}
	
	private Sudoku puzzle;
	
	//creates ai that solves puzzle
	public SudokuAI(Sudoku puzzle) {
		this.puzzle = puzzle;
	}
	
	/**
	 * solves the puzzle using backtracking
	 */
	public void solve() {
		if(!puzzle.isSolved()) {
			puzzle = solve(puzzle.copySudoku(), 0, 0);
		}
	}
	
	/**
	 * helper method for solve that uses recursion
	 * @param puzzle - puzzle to solve
	 * @param i - current row
	 * @param j - current column
	 * @return the solved puzzle
	 */
	private Sudoku solve(Sudoku puzzle, int i, int j) {
		if(puzzle.isSolved()) {
			return puzzle;
		} else if (puzzle.getSquare(i, j) != 0) {
			int newJ = (j + 1) % 9;
			int newI = newJ == 0 ? i+1 : i;
			
			return solve(puzzle, newI, newJ);
		} else {
			boolean[] possibleValues = puzzle.getPossibleValues(i, j);
			for(int k = 0; k < 9; k++) {
				if(possibleValues[k]) {
					Sudoku nextPuzzle = puzzle.copySudoku();
					nextPuzzle.setSquare(i, j, k + 1);
					
					int newJ = (j + 1) % 9;
					int newI = newJ == 0 ? i+1 : i;
					
					Sudoku possibleSolution = solve(nextPuzzle, newI, newJ);
					if(possibleSolution != null) {
						return possibleSolution;
					}
				}
			}
		}
		
		return null;
	}
	
	//returns the puzzle
	public Sudoku getPuzzle() {
		return puzzle;
	}
}
