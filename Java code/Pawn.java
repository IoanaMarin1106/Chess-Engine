import java.util.*;

public final class Pawn extends Piece {

	public static boolean isValidWhiteMove(long[] move, long blackPieces, long allPieces) {
		long src = move[0], dest = move[1];

		if((dest & blackPieces) != 0) {// attack
			if((src << 7L) == dest && (src & Bitboard.FILES[7]) == 0) {
				return true;
			}

			if((src << 9L) == dest && (src & Bitboard.FILES[0]) == 0) {
				return true;
			}
		} else {// avans
			if(dest == (src << 8L) && (src & Bitboard.RANKS[7]) == 0) {
				return true;
			} else if(dest == (src << 16L) && (src >= (1L << 8L) && src < (1L << 16L))) {
				if(((src << 8L) & allPieces) == 0) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isValidBlackMove(long[] move, long whitePieces, long allPieces) {
		long src = move[0], dest = move[1];

		if((dest & whitePieces) != 0) {
			if((src >> 7L) == dest && (src & Bitboard.FILES[0]) == 0) {
				return true;
			}

			if((src >> 9L) == dest && (src & Bitboard.FILES[7]) == 0) {
				return true;
			}
		} else {
			if(dest == (src >> 8L) && (src & Bitboard.RANKS[0]) == 0) {
				return true;
			} else if(dest == (src >> 16L) && (src >= (1L << 48L) && src < (1L << 56L))) {
				if(((src >> 8L) & allPieces) == 0) {
					return true;
				}
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

			pieces = (pieces & (~src)); // sterg bitul asta (aka pionul asta)

			moves.add(new long[] {src, (src >> 8)});	// single push
			moves.add(new long[] {src, (src >> 16)});	// double push
			moves.add(new long[] {src, (src >> 7)});	// attack
			moves.add(new long[] {src, (src >> 9)});	// attack
			moves.add(new long[] {src, (src << 8)});
			moves.add(new long[] {src, (src << 16)});
			moves.add(new long[] {src, (src << 7)});
			moves.add(new long[] {src, (src << 9)});
		}

		return moves;
	}
}
