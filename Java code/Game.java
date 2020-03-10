import java.util.*;
import java.io.*;

public class Game {
	private Bitboard board = new Bitboard();
	private int protocolVersion;

	private boolean isPlaying = false;
	private Piece.Color myColor = null;
	private Piece.Color turnColor = null;

	private Scanner input = new Scanner(System.in);
	private BufferedOutputStream output = new BufferedOutputStream(System.out);

	/* FOR BITBOARD DEBUG -------------------*/
	 
	public Bitboard getBitboard() {
		return this.board;
	}

	
	 /*--------------------------------------*/
	
	public Game() {

	}

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

	public void setupFeatures() {
		sendToXboard("feature sigint=0 myname=\"Cerebellum\" done=1\n");
	}

	public void makeMove() {
		long[] myMove = board.generateMove(myColor);

		if(myMove == null) {
			sendToXboard("resign\n");
		} else {
			board.makeMove(myMove, myColor);
			String convertedMove = "move " + Move.convertPositions(myMove) + "\n";
			sendToXboard(convertedMove);
			Debug.displayBoard(this);
		}
	}

	public void switchTurnColor() {
		if(turnColor == Piece.Color.WHITE) {
			turnColor = Piece.Color.BLACK;
		} else {
			turnColor = Piece.Color.WHITE;
		}
	}

	public void newCommand() {
		board.reset();
		isPlaying = true;
		turnColor = Piece.Color.WHITE;
		myColor = Piece.Color.BLACK;
	}

	public void forceCommand() {
		isPlaying = false;
	}

	public void goCommand() {
		isPlaying = true;
		myColor = turnColor;

		makeMove();
		switchTurnColor();
	}

	public void whiteCommand() {
		turnColor = Piece.Color.WHITE;
		myColor = Piece.Color.BLACK;
		isPlaying = true;
	}

	public void blackCommand() {
		turnColor = Piece.Color.BLACK;
		myColor = Piece.Color.WHITE;
		isPlaying = true;
	}

	public void quitCommand() {
		System.exit(0);
	}

	public void resignCommand() {
		quitCommand(); // se termina jocul
	}

	public void moveCommand(String word) {
		long[] move = Move.convertMove(word);

		board.makeMove(move, turnColor);
		Debug.displayBoard(this);

		if(isPlaying) {
			makeMove();
		} else {
			switchTurnColor();
		}
	}

	public void executeCommands () {
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

	public static void main(String[] args) throws IOException {
		Game game = new Game();
		game.executeCommands();
	}
}
