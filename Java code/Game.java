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

	public void goCommand() {
		isPlaying = true;
		myColor = turnColor;
		// make a move
	}

	public void whiteCommand() {
		turnColor = Piece.PieceColor.WHITE;
		myColor = Piece.PieceColor.BLACK;
	}

	public void blackCommand() {
		turnColor = Piece.PieceColor.BLACK;
		myColor = Piece.PieceColor.WHITE;
	}

	public void quitCommand() {
		System.exit(0);
	}

	public void resignCommand() {

	}

	public void moveCommand(String[] words) {

	}

	public void executeCommands () throws IOException {
		char c = 'a';

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
				output.write(("move " + c + "7" + c + "6\n").getBytes());
				output.flush();
				c++;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Game game = new Game();
		game.executeCommands();
	}
}
