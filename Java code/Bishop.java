import java.util.ArrayList;

public final class Bishop extends Piece {
	public static boolean meetCollision(
		int srcRank, int srcFile,
		int destRank, int destFile,
		long allPieces
		) {
		
		if(srcRank < destRank) {
			if(srcFile < destFile) {
				for(int i = 1; i < (destRank - srcRank); i++) {
					long rank = Bitboard.RANKS[srcRank + i];
					long file = Bitboard.FILES[srcFile + i];

					if(((rank & file) & allPieces) != 0) {
						return true;
					}
				}
			} else {
				System.out.println("cszul asta");

				for(int i = 1; i < (destRank - srcRank); i++) {
					long rank = Bitboard.RANKS[srcRank + i];
					long file = Bitboard.FILES[srcFile - i];

					if(((rank & file) & allPieces) != 0) {
						return true;
					}
				}
			}
		} else {
			if(srcFile < destFile) {
				for(int i = 1; i < (srcRank - destRank); i++) {
					long rank = Bitboard.RANKS[srcRank - i];
					long file = Bitboard.FILES[srcFile + i];

					if(((rank & file) & allPieces) != 0) {
						return true;
					}
				}
			} else {
				for(int i = 1; i < (srcRank - destRank); i++) {
					long rank = Bitboard.RANKS[srcRank - i];
					long file = Bitboard.FILES[srcFile - i];

					if(((rank & file) & allPieces) != 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isValidMove(long[] move, long allPieces) {
		long src = move[0], dest = move[1];

		int srcRank = Bitboard.getRank(src), srcFile = Bitboard.getFile(src);
		int destRank = Bitboard.getRank(dest), destFile = Bitboard.getFile(dest);

		if(Math.abs(srcRank - destRank) == Math.abs(srcFile - destFile)) {
			if(!meetCollision(srcRank, srcFile, destRank, destFile, allPieces)) {
				return true;
			}
		}

		return false;
	}

	public static ArrayList<long[]> generateMoves(long pieces) {
		ArrayList<long[]> moves = new ArrayList<long[]>();

		while(pieces != 0) {
			long src = 1;

			while((src & pieces) == 0) {
				src = (src << 1);
			}

			pieces = (pieces & (~src));

			int srcRank = Bitboard.getRank(src), srcFile = Bitboard.getFile(src);

			int rank, file;

			rank = srcRank - 1;
			file = srcFile - 1;

			while(rank >= 0 && file >= 0) {
				moves.add(new long[] {src, (Bitboard.RANKS[rank] & Bitboard.FILES[file])});
				rank--;
				file--;
			}

			rank = srcRank - 1;
			file = srcFile + 1;

			while(rank >= 0 && file < 8) {
				moves.add(new long[] {src, (Bitboard.RANKS[rank] & Bitboard.FILES[file])});
				rank--;
				file++;
			}

			rank = srcRank + 1;
			file = srcFile - 1;

			while(rank < 8 && file >= 0) {
				moves.add(new long[] {src, (Bitboard.RANKS[rank] & Bitboard.FILES[file])});
				rank++;
				file--;
			}

			rank = srcRank + 1;
			file = srcFile + 1;

			while(rank < 8 && file < 8) {
				moves.add(new long[] {src, (Bitboard.RANKS[rank] & Bitboard.FILES[file])});
				rank++;
				file++;
			}
		}

		return moves;
	}
}
