/**
 * This class is designed for the Move Command received
 * from the xboard/winboard. It basically has methods to check
 * if a string is either a move or something else,
 * to convert the string to a move and vice-versa. 
 * 
 * @author Creieru' si Cerebelii
 *
 */
public final class Move {

	/**
	 * Private method to convert the letter for
	 * the column into a digit. For instance:
	 * a -> 1, b -> 2, etc.
	 */
	private static int charToNumber(char c) {
		return c - 'a' + 1;
	}

	/**
	 * Method which checks if a string can represent a possible
	 * move or not.
	 * @param move the string which represents the possible move.
	 * @return true if the string is a move, false otherwise.
	 */
	public static boolean isMove(String move) {
		/* The string for a move cannot be shorter
		 * than 4 characters. For instance: 
		 * a2a4 -> valid, a2h -> invalid.
		 */
		if (move == null || move.length() < 4) {
			return false;
		}
		/* A valid string for a move has digits as
		 * the second and fourth characters.
		 */
		if (Character.isDigit(move.charAt(1)) == false || Character.isDigit(move.charAt(3)) == false) {
			return false;
		}
		/* A valid string for the move has characters
		 * as the first and third characters. 
		 */
		if (Character.isLetter(move.charAt(0)) == false || Character.isLetter(move.charAt(2)) == false) {
			return false;
		}
		/* A valid string for the move only has characters
		 * in a-h range, lowercase. 
		 */
		if (move.charAt(0) < 'a' || move.charAt(0) > 'h' || move.charAt(2) < 'a' || move.charAt(2) > 'h') {
			return false;
		}

		return true;
	}

	/**
	 * Method which converts a string to a move. The string
	 * is converted into a array of two long elements, first one
	 * representing the source square (a long number of 64 bits)
	 * and the second one representing the destination square 
	 * (a long number of 64 bits).
	 * @param move the string to be converted.
	 * @return a long with two elements, the source and the destination squares.
	 */
	public static long[] convertMove(String move) {
		long[] convertedMove = new long[2];

		int initialX = charToNumber(move.charAt(0));
		int initialY = Character.getNumericValue(move.charAt(1));
		int finalX = charToNumber(move.charAt(2));
		int finalY = Character.getNumericValue(move.charAt(3));

		long initialPosition = 1L << (8L * (initialY - 1));
		initialPosition = initialPosition << (initialX - 1);

		long finalPosition = 1L << (8L * (finalY - 1));
		finalPosition = finalPosition << (finalX - 1);

		convertedMove[0] = initialPosition;
		convertedMove[1] = finalPosition;
		
		/*
		Delete comment for correct debugging. 		
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

		return convertedMove;
	}

	/**
	 * Method which converts a move to a string. The array of two longs,
	 * first representing the source and second representing the destinations,
	 * is converted into the string representing the actual move, for instance:
	 * a2a3, g7g6, etc.
	 * @param positions the array of two longs to be converted into string.
	 * @return the string representing the move.
	 */
	public static String convertPositions(long[] positions) {
		return convertPosition(positions[0]) + convertPosition(positions[1]);
	}

	/**
	 * Method which converts a long number representing the 
	 * position/square into the string representing the coordinates.
	 * @param position the position/square
	 * @return the string with the coordinates
	 */
	public static String convertPosition(long position) {
		String convertedPosition = "";
		
		for(int i = 0; i < Bitboard.FILES.length; i++) {
			if((position & Bitboard.FILES[i]) != 0) {
				if(i == 7) convertedPosition += 'a';
				if(i == 6) convertedPosition += 'b';
				if(i == 5) convertedPosition += 'c';
				if(i == 4) convertedPosition += 'd';
				if(i == 3) convertedPosition += 'e';
				if(i == 2) convertedPosition += 'f';
				if(i == 1) convertedPosition += 'g';
				if(i == 0) convertedPosition += 'h';
			}
		}
		
		for(int i = 0; i < Bitboard.RANKS.length; i++) {
			if((position & Bitboard.RANKS[i]) != 0) {
				convertedPosition += Integer.toString(i + 1);	
				break;
			}
		}
	
		return convertedPosition;
	}
}
