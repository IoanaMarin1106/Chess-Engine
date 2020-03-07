public final class Move {

	private static int charToNumber(char c) {
		return c - 'a' + 1;
	}

	public static boolean isMove(String move) {
		/* Nu cred ca exista miscare cu lungime mai mica de 4.
		 * Eg: e4e5. Nu cred ca poti sa ai mai putin. */
		if (move == null || move.length() < 4) {
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

	public static long[] convertMove(String move) {
		long[] convertedMove = new long[2];
		
		int initialX = charToNumber(move.charAt(0));
		int initialY = Character.getNumericValue(move.charAt(1));
		int finalX   = charToNumber(move.charAt(2));
		int finalY   = Character.getNumericValue(move.charAt(3));
		
		long initialPosition = 1 << (8 * (initialY - 1));
		initialPosition = initialPosition << (initialX - 1);
		
		/* Delete comment for debugging.
		System.out.println("#DEBUG for convert move: -------------");
		System.out.println("#The move: " + move);
		System.out.println("#Initial X: " + initialX);
		System.out.println("#Initial Y: " + initialY);
		System.out.println("#Final X: " + finalX);
		System.out.println("#Final Y: " + finalY);
		System.out.print(("#Initial Position: "));
		System.out.println(8 * (initialY - 1) + (initialX - 1));
		System.out.print(("#Final Position: "));
		System.out.println(8 * (finalY - 1) + (finalX - 1));
		System.out.println("#-------------------------------------");
		*/
		
		long finalPosition = 1 << (8 * (finalY - 1));
		finalPosition = finalPosition << (finalX - 1);
		
		convertedMove[0] = initialPosition;
		convertedMove[1] = finalPosition;

		return convertedMove;
	}	
}

