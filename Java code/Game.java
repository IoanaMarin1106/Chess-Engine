import java.util.*;

public class Game {
	private Bitboard board = new Bitboard();
	private int protocolVersion;

	private boolean isPlaying = false;
	private boolean isWhite = false;
	private boolean isWhiteTurn = false;

	private Scanner input = new Scanner(System.in);

	public Game() {}

	public void setupXboard() {
		String com = input.nextLine();

		if(com.equals("xboard")) {
			System.out.print("\n");
		}

		com = input.nextLine();
		protocolVersion = Integer.parseInt((com.split(" "))[1]);

		if(protocolVersion != 2) {
			System.exit(1);
		}

		System.out.print("feature done=0 myname=\"Cerebellum Game\" sigint=0 sigterm=0 ");
		System.out.print("setboard=1 ping=1 reuse=1 usermove=1 variants=\"normal\"");
		System.out.print("done=1\n");

		com = input.nextLine();
		String[] words = com.split(" ");

		while(words[0].equals("accepted") || words[0].equals("rejected")) {
			com = input.nextLine();
			words = com.split(" ");
		}
	}

	public void newCommand() {
		board.reset();
		isPlaying = true;
		isWhiteTurn = true;
		isWhite = false;
		//ceva cu clock
	}

	public void forceCommand() {
		isPlaying = false;
		//checks moves
	}

	public void goCommand() {
		isPlaying = true;
		isWhite = isWhiteTurn;
		//make a move
	}

	public void whiteCommand() {
		isWhiteTurn = true;
		isWhite = false;
	}

	public void blackCommand() {
		isWhiteTurn = false;
		isWhite = true;
	}

	public void quitCommand() {
		System.exit(0);
	}

	public void resignCommand() {
		
	}

	public void moveCommand(String[] words) {
	}

	public void executeCommands() {
		while(true) {
			String com = input.nextLine();
			String[] words = com.split(" ");

			if(words[0].equals("new")) {
				newCommand();
			} else if(words[0].equals("force")) {
				forceCommand();
			} else if(words[0].equals("go")) {
				goCommand();
				System.out.print("move a7a6\n");
			} else if(words[0].equals("white")) {
				whiteCommand();
			} else if(words[0].equals("black")) {
				blackCommand();
			} else if(words[0].equals("quit")) {
				quitCommand();
			} else if(words[0].equals("resign")) {
				resignCommand();
			} else if(words[0].equals("move")) {
				moveCommand(words);
				System.out.print("move a7a6\n");
			} else {
				System.out.print("move a7a6\n");
			}
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setupXboard();
		game.executeCommands();
	}
}