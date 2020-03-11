import java.util.*;

public final class Rook extends Piece {
	// public static boolean meetCollisionForColumns2(long src, long dest, long allPieces) {
	// 	/* Se duce de sus in jos  */
	// 	if (src > dest) {
	// 		System.out.println("# src = " + src + " dest = " + dest);
	// 		src = src >> 8;

	// 		while (src != dest) {
	// 			//System.out.println("# bucla infinita 1");
	// 			if ((src & allPieces) != 0) {
	// 				return true;
	// 			}
	// 			src = src >> 8L;
	// 		}

	// 	} else if (src < dest) {
	// 		/* Altfel se duce de jos in sus scz pt commurile prost puse */
	// 		src = src << 8;

	// 		while (src != dest) {
	// 			System.out.println("# bucla infinita 2");

	// 			if ((src & allPieces) != 0) {
	// 				return true;
	// 			}
	// 			src = src << 8L;
	// 		}
	// 	}
	// 	return false;
	// }

	// public static boolean meetCollisionForLines2(long src, long dest, long allPieces) {
	// 	/* Ar trebui sa se duca in dreapta  */
	// 	if(src > dest) {
	// 		src = src >> 1;

	// 		while (src != dest) {
	// 			System.out.println("# bucla infinita 3");

	// 			if ((src & allPieces) != 0) {
	// 				return true;
	// 			}
	// 			src = src >> 1L;
	// 		}

	// 	} else if (src < dest) {
	// 		/* Ar trebui sa se duca in stanga */
	// 		src = src << 1;

	// 		while (src != dest) {
	// 			System.out.println("# bucla infinita 4");

	// 			if ((src & allPieces) != 0) {
	// 				return true;
	// 			}
	// 			src = src << 1L;
	// 		}
	// 	}
	// 	return false;
	// }

	public static boolean meetCollisionForColumns(
		long file,
		int srcRank, int destRank,
		long allPieces
		) {

		if(srcRank < destRank) {
			for(int i = srcRank + 1; i < destRank; i++) {
				if(((Bitboard.RANKS[i] & file) & allPieces) != 0) {
					return true;
				}
			}
		} else {
			for(int i = destRank + 1; i < srcRank; i++) {
				if(((Bitboard.RANKS[i] & file) & allPieces) != 0) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean meetCollisionForLines(
		long rank,
		int srcFile, int destFile,
		long allPieces
		) {

		if(srcFile < destFile) {
			for(int i = srcFile + 1; i < destFile; i++) {
				if(((Bitboard.FILES[i] & rank) & allPieces) != 0) {
					return true;
				}
			}
		} else {
			for(int i = destFile + 1; i < srcFile; i++) {
				if(((Bitboard.FILES[i] & rank) & allPieces) != 0) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isValidMove(long[] move, long allPieces) {

		long src = move[0], dest = move[1];

		// long srcRank = Bitboard.RANKS[Bitboard.getRank(src)], 
		// 	srcFile = Bitboard.RANKS[Bitboard.getFile(src)];
		// long destRank = Bitboard.RANKS[Bitboard.getRank(dest)], 
		// 	destFile = Bitboard.RANKS[Bitboard.getFile(dest)];

		int srcRank = Bitboard.getRank(src), srcFile = Bitboard.getFile(src);
		int destRank = Bitboard.getRank(dest), destFile = Bitboard.getFile(dest);

		/* Sursa si destinatia se afla pe aceeasi coloana */
		if (srcFile == destFile) {
			long file = Bitboard.FILES[srcFile];

			if (meetCollisionForColumns(file, srcRank, destRank, allPieces) == false) {
				return true;
			}
		}

		/* Sursa si destinatia sunt pe aceeasi linie */
		if(srcRank == destRank) {
			long rank = Bitboard.RANKS[srcRank];

			if (meetCollisionForLines(rank, srcFile, destFile, allPieces) == false) {
				return true;
			}
		}

		return false;
	}

	/* 1L poate daca nu merge roxi cica jura ca merge bln */
	/* pieces = masca turelor */
	/* La rookRank si rookFile poate fi greseala cu int ala*/
	public static ArrayList<long[]> generateMoves(long pieces) {
		ArrayList<long[]> moves = new ArrayList<long[]>();

		while(pieces != 0) {
			long src = 1;

			while((src & pieces) == 0) {
				src = (src << 1);
			}

			pieces = (pieces & (~src)); //sterg bitul asta (aka tura asta)

			int rookRank = Bitboard.getRank(src),
				rookFile = Bitboard.getFile(src);

			for(int i = 1; i <= rookRank; i++) {
				moves.add(new long[] {src, (src >> (8 * i))});
			}

			for (int i = 1; i <= (7 - rookRank); i++) {
				moves.add(new long[] {src, (src << (8 * i))});
			}

			for (int i = 1; i <= rookFile; i++) {
				moves.add(new long[] {src, (src >> i)});
			}

			for (int i = 1; i <= (7 - rookFile); i++) {
				moves.add(new long[] {src, (src << i)});
			}
		}

		return moves;
	}
}
