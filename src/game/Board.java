package game;
import java.util.Scanner;

/**
 * 
 * @author Wesley Curtis
 * A snapshot of a Game of Life board at one point in time
 */
public class Board {
	// TODO: declare any additional member variables
	
	// The two-dimensional grid of board cells.
	// cells[r][c] is true if the cell at row r and column c is alive.
	private boolean[][] cells;
	

	
	/**
	 * Creates a Board with no live cells
	 * @param numberOfRows - number of rows in the board
	 * @param numberOfColumns - number of columns in the board
	 */
	public Board(int numberOfRows, int numberOfColumns){
		cells = new boolean[numberOfRows][numberOfColumns];
		
		//Goes through and sets each cell in cells to be false.
		for (int r = 0; r < numberOfRows; r++) {
			for (int c = 0; c <numberOfColumns; c++) {
				cells[r][c] = false;
			}
		}
	}
	
	
	/**
	 * Constructs a Board from the given String.
	 * @param boardInfo: Should be a String in the format specified in the instructions under "File Format"
	 *     The string should contain all of the lines, so it will have newlines in it.
	 * Example:
"Glider\n" +
"7\n" +
"8\n" +
"        \n" +
"  X     \n" +
"   X    \n" +
" XXX    \n" +
"        \n" +
"        \n" +
"        \n"
	 */
	public Board(String boardInfo){
		
		
		//Initializes the scanner of the given String.
		Scanner lineScanner = new Scanner(boardInfo);
		
		//Sets the name of the Board, this is not used later
		@SuppressWarnings("unused")
		String name = lineScanner.nextLine();
		
		//Gets the number of rows and columns
		int numRows = lineScanner.nextInt();
		lineScanner.nextLine();
		int numCols = lineScanner.nextInt();
		lineScanner.nextLine();
		
		
		//Initializes the cells array and the String for each line
		cells = new boolean[numRows][numCols];
		String line;
		
		
		//Goes through each row and column of the cells array
		for (int r = 0; r < numRows; r++) {
			
			//Sets line as the next line of the string and the makes a scanner to scan that line
			line = lineScanner.nextLine();
			Scanner charScan = new Scanner(line);
			
			
			for (int c = 0; c < numCols; c++) {
				
				//Goes through each character of the string and sets it to the String current
				charScan.useDelimiter("");
				String current = charScan.next();
				
				//Sets the cell to true if current is an "X" and false otherwise
				if (current.equals("X"))
					cells[r][c] = true;
				else cells[r][c] = false;
				
			}
			charScan.close();
		}
		lineScanner.close();
	}
	
	
	/**
	 * @return number of rows in the board
	 */
	public int getNumRows(){
		return cells.length;
	}
	
	/**
	 * @return number of columns in the board
	 */
	public int getNumCols(){
		return cells[1].length;
	}
	
	/**
	 * @return value of the cell at the given @param row and @param col
	 * @throws IllegalArgumentException if row or col is out of bounds.
	 */
	public boolean getCell(int row, int col) throws IllegalArgumentException {
		if (row < 0 || row > cells.length || col < 0 || col > cells[0].length)
			throw new IllegalArgumentException("Row and Column must be within bounds.");
		return cells[row][col];
	}
	
	
	/**
	 * @return A new Board for the next generation (i.e., after this).
	 *    The cell values for the next generation
	 *    are determined by the rules of Game of Life.
	 */
	public Board nextBoard() {
		
		//Creates an arbitrary name and adds it to the String
		String string ="NAME\n";
		
		//Adds the number of rows and columns as according to the way 
		//that the Board constructor can read
		string+= this.getNumRows() + "\n" + this.getNumCols() + "\n";
		
		//Creates a new 2D array of cells that is the same as the existing cells array
		boolean[][] newCells = new boolean[getNumRows()][getNumCols()];
		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				newCells[r][c] = cells[r][c];
			}
		}
		
		
		//Goes through each cell in the cells array
		for (int r = 0; r < newCells.length; r++) {
			for (int c = 0; c < newCells[0].length; c++) {
				
				//If the current cell is false and there are 3 live cells around it, it becomes alive
				if (getNumLiveCellsAround(r, c) == 3 && newCells[r][c] == false)
					newCells[r][c] = true;
				
				//Only goes through this if the current cell is alive
				else if (newCells[r][c] == true) {
					
					// the cell dies if there are less than 2 alive around it
					//1 is subtracted because getNumLiveCellsAround includes the current cell
					if (getNumLiveCellsAround(r,c)-1 < 2) {
						newCells[r][c] = false;
						
					}
					
					// the cell stays alive if there are 2 or 3 live cells around it
					//1 is subtracted because getNumLiveCellsAround includes the current cell
					else if (getNumLiveCellsAround(r,c)-1 <= 3) {
						
					}
					
					// the cell dies if there are more than 3 alive around it
					//1 is subtracted because getNumLiveCellsAround includes the current cell
					else if (getNumLiveCellsAround(r,c)-1 > 3) {
						newCells[r][c] = false;
					}
				}
			}
		}
		
		//Goes through the newCells array and turns it into a string
		for (int r = 0; r < newCells.length; r++) {
			for (int c = 0; c < newCells[0].length; c++) {
				if (newCells[r][c])
					string+="X";
				else string+=" ";
			}
			string+="\n";
		}
		
		
		//Makes a new Board based on the string just made.
		Board newBoard = new Board(string);
		return newBoard;
		
	}
	

	/**This method determines if two boards are equivalent or not
	 * @return true if other is the same size as this
	 *   and all of the cells of other have the same
	 *   values as this
	 */
	public boolean isSame(final Board other){
		
		//Returns false if the number of rows or columns is not the same
		if (other.getNumCols() != this.getNumCols() || other.getNumRows() != this.getNumRows()) 
			return false;
		
		//Goes through each cell and returns false if any cell is not the same as in the other Board
		for (int r = 0; r < other.cells.length; r++) {
			for (int c = 0; c < other.cells[r].length; c++) {
				if (other.cells[r][c] != this.cells[r][c])
					return false;
			}
		}
		
		//If the method made it this far, the two Boards are the same, so it returns true
		return true;
		
	}
	
	/**
	 * @return a String representation of the current board state
	 * Example:
	 *    ......\n
	 *    ......\n
	 *    .X....\n
	 *    ..X...\n
	 *    .XX...\n
	 *    ......\n
	 *    
	 *    Each row of the board should be printed on its own line.
	 *    The \n above represents a newline.
	 *    . characters are for dead cells.
	 *    X characters are for live cells.
	 *    The String should end with a newline.
	 */
	@Override
	public String toString(){
		
		//Initializes an empty string
		String string = "";
		
		//Goes through each cell and compiles a string
		for (int r = 0; r <this.cells.length; r++) {
			for (int c = 0; c < this.cells[r].length; c++) {
				if (this.getCell(r, c))
					string += "X";
				else string += ".";
			}
			string += "\n";
		}
		return string;
	}
	
	
	
	
	private int getNumLiveCellsAround(int row, int col) {
		//Initializes the number of cells that are alive at 0, 
		//this will be added to and returned
		int numAlive = 0;
		
		//Sets upper and lower bounds in order to check the 8 cells around the current cell
		int rowLowerBound = row-1;
		int rowUpperBound = row+1;
		int colLowerBound = col-1;
		int colUpperBound = col+1;
		
		//Makes sure that the checker does not check cells that are not within the bounds
		if (rowLowerBound == -1)
			rowLowerBound++;
		if (rowUpperBound == cells.length)
			rowUpperBound--;
		if (colLowerBound == -1)
			colLowerBound++;
		if (colUpperBound == cells[0].length)
			colUpperBound--;
		
		//Goes through the cells and adds to numAlive when it finds a live cell, 
		//including the cell that we are checking around
		for (int r = rowLowerBound; r <= rowUpperBound; r++) {
			for (int c = colLowerBound; c <= colUpperBound; c++) {
				if (cells[r][c])
					numAlive++;
			}
		}
		
		//Returns the number of live cells around a particular cell, including that cell
		return numAlive;
	}
	

}
