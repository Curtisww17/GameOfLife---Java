package game;

import java.util.ArrayList;

public class BoardSequence {

	//Initializes class variables
	Board startingBoard;
	Board currentBoard;
	ArrayList<Board> boards = new ArrayList<Board>();
	String sequence ="";
	int numRunSoFar = 0;
	
	/**
	 * The constructor of the BoardSequence class. Sets class variables.
	 * @param startingBoard this is the initial Board on which this class builds upon.
	 */
	public BoardSequence(Board startingBoard) {
		this.startingBoard = startingBoard;
		boards.add(startingBoard);
		currentBoard = startingBoard;
	}
	
	
	/**
	 * This method creates new generations based on the generation previous.
	 * @param numGen The number of new generations to be run.
	 */
	public void runMoreSteps(int numGen) {
		
		//goes through this once for each generation
		for (int i = 0; i < numGen + 1; i++) {
			
			//adds these generations to the sequence String
			sequence += "Generation " + i + "\n";
			sequence += currentBoard.toString();
			
			//makes the next Board and adds it to the ArrayList boards
			currentBoard = currentBoard.nextBoard();
			boards.add(currentBoard);
			
		}
	}
	
	
	/**
	 * This method is get method that gets the Board at a specific generation.
	 * @param index This is the generation number of the Board that is wanted, starting at 0.
	 * @return This returns the Board at the indicated generation.
	 * @throws IllegalArgumentException This is thrown if the index is less than zero or if it is 
	 * calling a generation that has not been created yet.
	 */
	public Board boardAt(int index) throws IllegalArgumentException {
		
		//Throws the exception if the index is less than 0 or greater 
		//than the number of generations run so far
		if (index < 0 || index > numRunSoFar)
			throw new IllegalArgumentException("Cannot call a board that does not exist!");
		
		return boards.get(index);
	}
	
	
	/**
	 * This method compiles the final String of the entire 
	 * sequence of boards with the found cycle.
	 * @return This returns the entire sequence of board generations as a String.
	 */
	public String toString() {
		int cycle = findCycle();
		if (cycle == -1)
			sequence+="No cycle found";
		else sequence+="Cycle found in generation " + cycle;
		
		return sequence;
	}
	
	/**
	 * This method determines if there is a cycle in the sequence of boards.
	 * @return This returns the integer of the generation in which the first repeat is found.
	 */
	public int findCycle() {
		for (int i = 0; i < boards.size(); i++) {
			for (int j = i + 1; j < boards.size(); j++) {
				if (boards.get(i).isSame(boards.get(j)))
					return j;
			}
		}
		return -1;
	}
	
}
