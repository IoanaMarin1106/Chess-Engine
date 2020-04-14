import java.util.*;

/**
 * This class stands for the Pawn piece in the chess game.
 * 
 * It has three static methods: one that checks if a move is valid for white
 * color(this means that it can only move to the top of board), one that checks
 * if a move is valid for black color(this means that it cand only move to the
 * bottom of the board) and one that generates all the moves possible.
 *
 * @author Creierul si Cerebelii
 *
 */
public final class Pawn extends Piece {
	
	public static ArrayList<long[]> generateMoves(
		Type type, Color color, long pawns, long attackerPieces, long defenderPieces
		) {

		ArrayList<long[]> moves = new ArrayList<long[]>();

		long blockers = (attackerPieces | defenderPieces);

		if(color == Color.WHITE) {
			while(pawns != 0) {
				long src = (1L << Long.numberOfTrailingZeros(pawns));
				pawns &= (~src);

				long singlePush = ((src << 8) & (~blockers));

				if(singlePush != 0) {
					moves.add(new long[] {src, singlePush});

					long doublePush = ((singlePush << 8) & (~blockers) & Bitboard.RANKS[3]);

					if(doublePush != 0) {
						moves.add(new long[] {src, doublePush});
					}
				}

				long attackEast = (((src & (~Bitboard.FILES[0])) << 9) & defenderPieces & (~attackerPieces));

				if(attackEast != 0) {
					moves.add(new long[] {src, attackEast});
				}

				long attackWest = (((src & (~Bitboard.FILES[7]))<< 7) & defenderPieces & (~attackerPieces));

				if(attackWest != 0) {
					moves.add(new long[] {src, attackWest});
				}
			}
		} else {
			while(pawns != 0) {
				long src = (1L << Long.numberOfTrailingZeros(pawns));
				pawns &= (~src);

				long singlePush = ((src >>> 8) & (~blockers));

				if(singlePush != 0) {
					moves.add(new long[] {src, singlePush});

					long doublePush = ((singlePush >>> 8) & (~blockers) & Bitboard.RANKS[4]);

					if(doublePush != 0) {
						moves.add(new long[] {src, doublePush});
					}
				}

				long attackEast = (((src & (~Bitboard.FILES[0])) >>> 7) & defenderPieces & (~attackerPieces));

				if(attackEast != 0) {
					moves.add(new long[] {src, attackEast});
				}

				long attackWest = (((src & (~Bitboard.FILES[7])) >>> 9) & defenderPieces & (~attackerPieces));

				if(attackWest != 0) {
					moves.add(new long[] {src, attackWest});
				}
			}
		}

		return moves;
	}

	public static long generateMovesMap(Color color, long pawns, long attackerPieces, long defenderPieces) {
		long attacksMap = 0L;

		if(color == Color.WHITE) {
			long blockers = defenderPieces | attackerPieces;

			// single push
			attacksMap |= ((pawns << 8) & (~blockers));

			// double push
			long doublePush = (attacksMap << 8) & (~blockers) & Bitboard.RANKS[3];
			attacksMap |= doublePush;

			// attacks
			attacksMap |= (((pawns & (~Bitboard.FILES[0])) << 9) & defenderPieces);
			attacksMap |= (((pawns & (~Bitboard.FILES[7])) << 7) & defenderPieces);

			attacksMap &= (~attackerPieces);
		} else {
			long blockers = defenderPieces | attackerPieces;

			// single push
			attacksMap |= ((pawns >>> 8) & (~blockers));

			// double push
			long doublePush = (attacksMap >>> 8) & (~blockers) & Bitboard.RANKS[4];
			attacksMap |= doublePush;

			// attacks
			attacksMap |= (((pawns & (~Bitboard.FILES[7])) >>> 9) & defenderPieces);
			attacksMap |= (((pawns & (~Bitboard.FILES[0])) >>> 7) & defenderPieces);

			attacksMap &= (~attackerPieces);
		}

		return attacksMap;
	}
}
