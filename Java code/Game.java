import java.util.*;

public class Game {
	private Bitboard board = new Bitboard();
	private int protocolVersion;

	@SuppressWarnings("unused")
	private boolean isPlaying = false;
	@SuppressWarnings("unused")
	private boolean isWhite = false;
	private boolean isWhiteTurn = false;

	private Scanner input = new Scanner(System.in);

	public Game() {
	}

	public void setupXboard() {
		String com = input.nextLine();

		if (com.equals("xboard")) {
			System.out.print("\n");
		}

		com = input.nextLine();
		protocolVersion = Integer.parseInt((com.split(" "))[1]);

		if (protocolVersion != 2) {
			System.exit(1);
		}

		System.out.print("feature done=0 myname=\"Cerebellum Game\" sigint=0 sigterm=0\n");
		System.out.print("feature debug=1 setboard=1 ping=1 reuse=1 usermove=1 variants=\"normal\"\n");
		System.out.print("feature done=1\n");

		com = input.nextLine();
		String[] words = com.split(" ");

		while (words[0].equals("accepted") || words[0].equals("rejected")) {
			com = input.nextLine();
			words = com.split(" ");
		}
	}

	public void newCommand() {
		board.reset();
		isPlaying = true;
		isWhiteTurn = true;
		isWhite = false;
		// ceva cu clock
	}

	public void forceCommand() {
		isPlaying = false;
		// checks moves
	}

	public void goCommand() {
		isPlaying = true;
		isWhite = isWhiteTurn;
		// make a move
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

	private boolean isMove(String move) {
		/* Nu cred ca exista miscare cu lungime mai mica de 4.
		 * Eg: e4e5. Nu cred ca poti sa ai mai putin. */
		if (move.length() < 4) {
			return false;
		}
		/* Aici e posibil sa modificam, ca s-ar putea sa fie miscari
		 * care sa nu aiba numericele asa. */
		if (Character.isDigit(move.charAt(1)) == false ||
			Character.isDigit(move.charAt(3)) == false) {
			return false;
		}	
		/* La fel si aici, dar momentan e ok. */
		if (Character.isLetter(move.charAt(0)) == false ||
			Character.isLetter(move.charAt(2)) == false) {
			return false;
		}
		/* Si sa verificam si ca literele alea sunt intre a si h,
		 * ca altfel clar nu sunt miscari. */
		if (move.charAt(0) < 'a' || move.charAt(0) > 'h' ||
			move.charAt(2) < 'a' || move.charAt(2) > 'h') {
			return false;
		}

		return true;
	}

	public void executeCommands() {
		while (true) {
			String com = input.nextLine();
			String[] words = com.split(" ");

			if (words[0].equals("new")) {
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
			} else if (isMove(words[0]) == true) {
				System.out.println("Valid move: " + words[0]);
			}
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		// game.setupXboard();
		game.executeCommands();
	}
}
