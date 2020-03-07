import java.util.ArrayList;

public final class Move {

	private static int charToNumber(char c) {
		return c - 'a' + 1;
	}

	public static ArrayList<Long> convertMove(String move) {
		ArrayList<Long> convertedMove = new ArrayList<Long>(2);
		
		int initialX = charToNumber(move.charAt(0));
		int initialY = Character.getNumericValue(move.charAt(1));
		int finalX   = charToNumber(move.charAt(2));
		int finalY   = Character.getNumericValue(move.charAt(3));
		
		long initialPosition = 1 << (8 * (initialY - 1));
		initialPosition = initialPosition << (initialX - 1);
		
		/*
		System.out.println("#DEBUG for convert move: -------------");
		System.out.println("The move: " + move);
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
		
		convertedMove.add( initialPosition);
		convertedMove.add(1, finalPosition);

		return convertedMove;
	}	
}
