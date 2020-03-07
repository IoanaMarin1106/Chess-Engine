public final class Rook extends Piece {
	public static boolean meetCollisionForColumns(long src, long dest, long allPieces) {
		/* Se duce de sus in jos  */
		if (src > dest) {
			while (src != dest) {
				if ((src >> 8) & allPieces != 0) {
					return true;
				}
				src = src >> 8;
			}

		} else if (src < dest) {
			/* Altfel se duce in jos scz pt commurile prost puse */
			while (src != dest) {
				if ((src << 8) & allPieces != 0) {
					return true;
				}
				src = src << 8;
			}
		}
		return false;
	}

	public static boolean meetCollisionForLines(long src, long dest, long allPieces) {
		/* Ar trebui sa se duca in dreapta  */
		if(src > dest) {
			while (src != dest) {
				if ((src << 1) & allPieces != 0) {
					return true;
				}
				src = src << 1;
			}

		} else if (src < dest) {
			/* Ar trebui sa se duca in stanga */
			while (src != dest) {
				if ((src >> 1) & allPieces != 0) {
					return true;
				}
				src = src >> 1;
			}
		}
		return false;
	}

	public static boolean isValidMove(long[] move, long allPieces) {

		long src = move[0], dest = move[1];

		long srcRank = Bitboard.RANKS[Bitboard.getRank(src)], 
			srcFile = Bitboard.RANKS[Bitboard.getFile(src)];
		long destRank = Bitboard.RANKS[Bitboard.getRank(dest)], 
			destFile = Bitboard.RANKS[Bitboard.getRank(dest)];

		/* Sursa si destinatia se afla pe aceeasi coloana */
		if (srcFile == destFile) { 
			if (meetCollisionForColumns(srcFile, destFile, allPieces) == false) {
				return true;
			}
		}

		/* Sursa si destinatia sunt pe aceeasi linie */
		if(srcRank == destRank) {
			if (meetCollisionForLines(srcRank, destRank, allPieces) == false) {
				return true;
			}
		}

		return false;
	}
}
