import java.util.ArrayList;

/**
 * This class stands for the Queen piece in the chess game.
 * It has two static methods: one that checks if a move
 * is valid, and one that generates all the moves possible.
 * 
 * @author Creierul si Cerebelii
 *
 */
public final class Queen extends Piece {
	/**
	 * Method which checks if a move is valid. This method
	 * returns true if the move is valid for the Queen piece, 
	 * for instance, it will return false if a player tries to 
	 * move the Queen as a Knight.
	 * @param move the move to be checked.
	 * @param allPieces the current state of the board.
	 * @return true for valid move, false otherwise.
	 */
	public static boolean isValidMove(long[] move, long allPieces) {
		return (Rook.isValidMove(move, allPieces) || Bishop.isValidMove(move, allPieces));
	}

	/**
	 * Methods which generates an ArrayList of possible moves
	 * for the Queen piece, by generating all the moves possible
	 * for Rook and Bishop. It only generates valid moves.
	 * @param piece the position of the Queen.
	 * @return an array with all moves possible for the Queen.
	 */
	public static ArrayList<long[]> generateMoves(long piece) {
		ArrayList<long[]> moves = Rook.generateMoves(piece);
		moves.addAll(Bishop.generateMoves(piece));

		return moves;
	}
}
