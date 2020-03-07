public final class Queen extends Piece {
	public static boolean isValidMove(long[] move, long allPieces) {
		return (Rook.isValidMove(move, allPieces) || Bishop.isValidMove(move, allPieces));
	}
}
