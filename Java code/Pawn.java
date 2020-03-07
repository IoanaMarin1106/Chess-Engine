public final class Pawn extends Piece {
	public static boolean isValidWhiteMove(long[] move, long blackPieces) {
		long src = move[0], dest = move[1];

		if((dest & blackPieces) != 0) {// attack
			if((src << 7) == dest || (src << 9) == dest) {
				return true;
			}
		} else {// avans
			if(dest == (src << 8)) {
				return true;
			} else if(dest == (src << 16) && (src >= (1 << 8) && src < (1 << 16))) {
				return true;
			}
		}

		return false;
	}

	public static boolean isValidBlackMove(long[] move, long whitePieces) {
		long src = move[0], dest = move[1];

		if((dest & blackPieces) != 0) {
			if((src >> 7) == dest || (src >> 9) == dest) {
				return true;
			}
		} else {
			if(dest == (src >> 8)) {
				return true;
			} else if(dest == (src >> 16) && (src >= (1 << 48) && src < (1 << 56))) {
				return true;
			}
		}

		return false;
	}
}
