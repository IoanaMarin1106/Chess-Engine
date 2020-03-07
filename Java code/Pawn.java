public final class Pawn extends Piece {
	public static boolean isValidWhiteMove(long[] move, long blackPieces) {
		long src = move[0], dest = move[1];

		if((dest & blackPieces) != 0) {// attack
			if((src << 7L) == dest || (src << 9L) == dest) {
				return true;
			}
		} else {// avans
			if(dest == (src << 8L)) {
				return true;
			} else if(dest == (src << 16L) && (src >= (1L << 8L) && src < (1L << 16L))) {
				return true;
			}
		}

		return false;
	}

	public static boolean isValidBlackMove(long[] move, long whitePieces) {
		long src = move[0], dest = move[1];

		if((dest & whitePieces) != 0) {
			if((src >> 7L) == dest || (src >> 9L) == dest) {
				return true;
			}
		} else {
			if(dest == (src >> 8L)) {
				return true;
			} else if(dest == (src >> 16L) && (src >= (1L << 48L) && src < (1L << 56L))) {
				return true;
			}
		}

		return false;
	}
}
