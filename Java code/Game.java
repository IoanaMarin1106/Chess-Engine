import java.util.*;
import java.io.*;

public class Game {
	private Bitboard board = new Bitboard();
	private int protocolVersion;

	private boolean isPlaying = false;
	private Piece.PieceColor myColor = null;
	private Piece.PieceColor turnColor = null;

	private Scanner input = new Scanner(System.in);
	private BufferedOutputStream output = new BufferedOutputStream(System.out);

	/* FOR BITBOARD DEBUG -------------------*/
	 
	public Bitboard getBitboard() {
		return this.board;
	}

	
	 /*--------------------------------------*/
	
	public Game() {

	}

	public void setupFeatures() throws IOException {
		output.write("feature sigint=0 myname=\"Cerebellum\" done=1\n".getBytes());
		output.flush();
	}

	public void newCommand() {
		board.reset();
		isPlaying = true;
		turnColor = Piece.PieceColor.WHITE;
		myColor = Piece.PieceColor.BLACK;
		// ceva cu clock
	}

	public void forceCommand() {
		isPlaying = false;
		// checks moves
	}

	/*
		Dupa Machine plays white
		go -> turnColor = white, myColor = black
			-> flip
			-> turnColor = myColor = black

		Dupa Machine plays black
		go -> turnColor = black, myColor = white
			-> flip
			-> turnColor = myColor = black
	*/
	public void goCommand() throws IOException {
		isPlaying = true;

		if(myColor != turnColor) {
			board.flip();

			turnColor = Piece.PieceColor.BLACK;
		}

		myColor = turnColor;

		long[] myMove = board.generateMove(myColor);

		if(myMove == null) {
			output.write("resign\n".getBytes());
			output.flush();
		} else {
			board.makeMove(myMove, myColor);

			String convertedMove = "move " + Move.convertPositions(myMove) + "\n";
			output.write(convertedMove.getBytes());
			output.flush();

			Debug.displayBoard(this);
		}

		if(turnColor == Piece.PieceColor.WHITE) {
			turnColor = Piece.PieceColor.BLACK;
		} else {
			turnColor = Piece.PieceColor.WHITE;
		}
	}

	public void whiteCommand() throws IOException {
		turnColor = Piece.PieceColor.WHITE;
		myColor = Piece.PieceColor.BLACK;
		isPlaying = true;

		// long[] myMove = board.generateMove(myColor);

		// if(myMove == null) {
		// 	output.write("resign\n".getBytes());
		// 	output.flush();
		// } else {
		// 	board.makeMove(myMove, myColor);

		// 	String convertedMove = "move " + Move.convertPositions(myMove) + "\n";
		// 	output.write(convertedMove.getBytes());
		// 	output.flush();

		// 	Debug.displayBoard(this);
		// }
	}

	public void blackCommand() throws IOException {
		turnColor = Piece.PieceColor.BLACK;
		myColor = Piece.PieceColor.WHITE;
		isPlaying = true;

		// long[] myMove = board.generateMove(myColor);

		// if(myMove == null) {
		// 	output.write("resign\n".getBytes());
		// 	output.flush();
		// } else {
		// 	board.makeMove(myMove, myColor);

		// 	String convertedMove = "move " + Move.convertPositions(myMove) + "\n";
		// 	output.write(convertedMove.getBytes());
		// 	output.flush();

		// 	Debug.displayBoard(this);
		// }
	}

	public void quitCommand() {
		System.exit(0);
	}

	public void resignCommand() {

	}

	public void moveCommand(String word) throws IOException {
		long[] move = Move.convertMove(word);

		board.makeMove(move, turnColor);
		Debug.displayBoard(this);

		if(isPlaying) {
			long[] myMove = board.generateMove(myColor);

			if(myMove == null) {
				output.write("resign\n".getBytes());
				output.flush();
			} else {
				board.makeMove(myMove, myColor);
				String convertedMove = "move " + Move.convertPositions(myMove) + "\n";
				output.write(convertedMove.getBytes());
				output.flush();
				Debug.displayBoard(this);
			}
		} else {
			if(turnColor == Piece.PieceColor.WHITE) {
				turnColor = Piece.PieceColor.BLACK;
			} else {
				turnColor = Piece.PieceColor.WHITE;
			}
		}
	}

	public void moveCommand2222(String word) throws IOException {
		long[] move = Move.convertMove(word);

		if(board.isValidMove(move, turnColor)) {
			board.makeMove(move, turnColor);
			Debug.displayBoard(this);

			if(isPlaying) {
				long[] myMove = board.generateMove(myColor);

				if(myMove == null) {
					output.write("resign\n".getBytes());
					output.flush();
				} else {
					board.makeMove(myMove, myColor);

					String convertedMove = "move " + Move.convertPositions(myMove) + "\n";
					output.write(convertedMove.getBytes());
					output.flush();
					Debug.displayBoard(this);
				}
			}
		}
	}

	public void executeCommands () throws IOException {
		while (true) {
			String com = input.nextLine();

			String[] words = com.split(" ");

			if(words[0].equals("xboard")) {
				output.write("\n".getBytes());
				output.flush();
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

	public static void main(String[] args) throws IOException {
		Game game = new Game();
		game.executeCommands();
	}
}
