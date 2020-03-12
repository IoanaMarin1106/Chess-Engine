import java.util.*;
import java.io.*;

/**
 * This is the class representing the chess game entity.
 * Within it, there is the main method of the program and other
 * input and output processing methods, including methods
 * for all possible commands received from Xboard.
 *
 * Just like a game of chess should, a Game object has a board
 * with all the pieces of both black and white color and keeps
 * track of whose turn it is.
 *
 * @author Creierul si Cerebelii
 */
public class Game {
	/**
	 * The internal board of the engine, where it keeps track of
	 * all the pieces's positions through the game.
	 */
	private Bitboard board = new Bitboard();

	/**
	 * Field which is true if the engine is playing, false if it's
	 * in Analyze mode.
	 */
	private boolean isPlaying = false;

	/**
	 * The color that the engine is currently playing with.
	 */
	private Piece.Color myColor = null;

	/**
	 * The color that is next on move.
	 */
	private Piece.Color turnColor = null;

	/**
	 * The scanner for the input data received from Xboard.
	 */
	private Scanner input = new Scanner(System.in);

	/**
	 * The output stream for sending the engine's commands to Xboard.
	 */
	private BufferedOutputStream output = new BufferedOutputStream(System.out);

	/**
	 * Default constructor.
	 */
	public Game() {}

	/**
	 * Method that sends the engine's command for the Xboard.
	 */
	public void sendToXboard(String command) {
		try {
			output.write(command.getBytes());
			output.flush();
		}
		catch(Exception e) {
			System.out.println("# Write exception");
			System.exit(1);
		}
	}

	/**
	 * Method to setup the initial features of the game, after
	 * receiving the "protover 2" prompt.
	 * The features include setting off sigint.
	 */
	public void setupFeatures() {
		sendToXboard("feature sigint=0 myname=\"Cerebellum\" done=1\n");
	}

	/**
	 * Method which generates a move that the engine may play.
	 * First it checks if there are any valid moves left,
	 * resigns if not. If there are, then it updates the engine's
	 * internal board, but checks if that move endangers its King.
	 * Resigns if so, else it sends the xboard the converted move.
	 */
	public void makeMove() {
		long[] myMove = board.generateMove(myColor);

		if(myMove == null ) {
			sendToXboard("resign\n");
		} else {
			board.makeMove(myMove, myColor);

			if(board.isCheck(myColor)) {
				sendToXboard("resign\n");
				return;
			}

			String convertedMove = "move " + Move.convertPositions(myMove) + "\n";
			sendToXboard(convertedMove);
			Debug.displayBoard(this);
		}
	}

	/**
	 * Method to switch the color that is next on move from
	 * white to black and vice-versa.
	 */
	public void switchTurnColor() {
		if(turnColor == Piece.Color.WHITE) {
			turnColor = Piece.Color.BLACK;
		} else {
			turnColor = Piece.Color.WHITE;
		}
	}

	/**
	 * Method corresponding to the "new" command from Xboard.
	 * It resets the initial board and colors and sets the engine
	 * on playing with black.
	 */
	public void newCommand() {
		board.reset();
		isPlaying = true;
		turnColor = Piece.Color.WHITE;
		myColor = Piece.Color.BLACK;
	}

	/**
	 * Method corresponding to the "force" command from Xboard
	 * It sets the engine to Analyze mode where it just receives moves
	 * from each color and updates its internal board.
	 */
	public void forceCommand() {
		isPlaying = false;
	}

	/**
	 * Method corresponding to the "go" command from Xboard.
	 * It sets the engine on move with whatever color's turn it is.
	 */
	public void goCommand() {
		isPlaying = true;
		myColor = turnColor;

		makeMove();
		switchTurnColor();
	}

	/**
	 * Method corresponding to the "white" command from Xboard.
	 * It is white's turn to move. The engine is playing with black.
	 */
	public void whiteCommand() {
		turnColor = Piece.Color.WHITE;
		myColor = Piece.Color.BLACK;
		isPlaying = true;
	}

	/**
	 * Method corresponding to the "black" command from Xboard.
	 * It is black's turn to move. The engine is playing with white.
	 */
	public void blackCommand() {
		turnColor = Piece.Color.BLACK;
		myColor = Piece.Color.WHITE;
		isPlaying = true;
	}

	/**
	 * Method corresponding to the "quit" command from Xboard.
	 * It terminates the program normally.
	 */
	public void quitCommand() {
		System.exit(0);
	}

	/**
	 * Method corresponding to the "resign" command from Xboard.
	 */
	public void resignCommand() {

	}

	/**
	 * Method corresponding to the "move" command from Xboard.
	 * It converts the coordinates received from Xboard. Then it
	 * updates the bitboard of that move, checks if the engine's King
	 * is in check and resigns if so. If not, it either sends a move
	 * of its own or just switches the color that moves next if it's in
	 * force mode.
	 * @param word the coordinates for the move
	 */
	public void moveCommand(String word) {
		long[] move = Move.convertMove(word);

		board.makeMove(move, turnColor);
		Debug.displayBoard(this);

		if(board.isCheck(myColor)) {
			sendToXboard("resign\n");
			return;
		}
		
		if(isPlaying) {
			makeMove();
		} else {
			switchTurnColor();
		}
	}

	/**
	 * Method for processing input data. It reads line
	 * by line and, depending on the first word on the line,
	 * calls the respective function for that command.
	 */
	public void executeCommands() {
		/**
		 * Uses a continuos loop. The program stops when it
		 * reads "quit".
		 */
		while (true) {
			String com = input.nextLine();

			String[] words = com.split(" ");

			if(words[0].equals("xboard")) {
				sendToXboard("\n");
			} else if(words[0].equals("protover")) {
				setupFeatures();
			} else if (words[0].equals("new")) {
				newCommand();
			} else if (words[0].equals("force")) {
				forceCommand();
			} else if (words[0].equals("go")) {
				goCommand();
			} else if (words[0].equals("white")) {
				whiteCommand();
			} else if (words[0].equals("black")) {
				blackCommand();
			} else if (words[0].equals("quit")) {
				quitCommand();
			} else if (words[0].equals("resign")) {
				resignCommand();
			} else if (Move.isMove(words[0]) == true) {
				moveCommand(words[0]);
			}
		}
	}

	/**
	 * Main method of the program, which creates a new game object
	 * to execute the commands received from Xboard.
	 * @param args unsed.
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.executeCommands();
	}

	/**	------------ FOR CORRECT DEBUGGING ---------------
	 * Get method for a game's bitboard. It is used only for debugging
	 * purposes, in order to display the bitboard in a readable format.
	 * @return a reference to the game's bitboard.
	 */
	 
	public Bitboard getBitboard() {
		return this.board;
	}
}
