import java.util.ArrayList;

public final class Queen extends Piece {
	public static boolean isValidMove(long[] move, long allPieces) {
		return (Rook.isValidMove(move, allPieces) || Bishop.isValidMove(move, allPieces));
	}

	public static ArrayList<long[]> generateMoves(long pieces) {
		ArrayList<long[]> moves = Rook.generateMoves(pieces);
		moves.addAll(Bishop.generateMoves(pieces));

		return moves;
	}
}
