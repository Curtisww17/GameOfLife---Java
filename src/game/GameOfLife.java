package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameOfLife {

	public static void main(String[] args) {
		
		//Initializes Scanners and PrintWriters
		Scanner scan = new Scanner(System.in);
		Scanner scanner;
		PrintWriter pw;
		
		
		//Gets from the user what is necessary to run the code
		System.out.print("What is the name of the starting Board file?");
		File  fileIn = new File(scan.next());
		System.out.print("How many generations do you want to run?");
		while (!scan.hasNextInt()) {

			scan.next();
			System.out.println("Must be an integer.\nHow many generations do you want to run?");
		}
		int numGen = scan.nextInt();
		System.out.print("What is the name of the file in which you would like to output?");
		File fileOut = new File(scan.next());
		
		//Closes the scanner to prevent memory leak
		scan.close();
		
		
		//Tries to find the files that the user gave to open
		try {
		scanner = new Scanner(fileIn);
		pw = new PrintWriter(fileOut);
		} catch (FileNotFoundException fnfe) {
			System.err.print(fnfe.getMessage());
			return;
		}
		
		//Creates a string that contains all of the elements necessary to create a board
		String board = "";
		while (scanner.hasNextLine()) {
			board += scanner.nextLine() + "\n";
		}
		
		//Uses the string to create a board object
		Board newBoard = new Board(board);
		
		//Makes a BoardSequence object and runs it however many times the user wanted
		BoardSequence boardSequence = new BoardSequence(newBoard);
		boardSequence.runMoreSteps(numGen);
		
		//Prints out to the output file
		pw.print(boardSequence.toString());
		pw.flush();
		
		//Closes the PrintWriter and the Scanner
		pw.close();
		scanner.close();
	}

}
