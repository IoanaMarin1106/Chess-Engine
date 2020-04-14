import java.util.ArrayList;

/**
 * This class stands for the King piece in the chess game.
 *
 * It has two static methods: one that checks if a move
 * is valid and one that generates all the moves possible.
 * 
 * @author Creierul si Cerebelii
 *
 */
public final class King extends Piece {

	public static ArrayList<long[]> generateMoves(
		Piece.Type type, Piece.Color color, long king, long attackerPieces, long defenderPieces
		) {

		ArrayList<long[]> moves = new ArrayList<long[]>();

		long attackWest = ((king & (~Bitboard.FILES[7])) >>> 1);

		if(attackWest != 0) {
			if((attackWest & (~attackerPieces)) != 0) {
				moves.add(new long[] {king, attackWest});
			}

			if((((attackWest & (~Bitboard.RANKS[0])) >>> 8) & (~attackerPieces)) != 0) {
				moves.add(new long[] {king, ((attackWest & (~Bitboard.RANKS[0])) >>> 8)});
			}

			if((((attackWest & (~Bitboard.RANKS[7])) << 8) & (~attackerPieces)) != 0) {
				moves.add(new long[] {king, ((attackWest & (~Bitboard.RANKS[7])) << 8)});
			}

		}

		long attackEast = ((king & (~Bitboard.FILES[0])) << 1);

		if(attackEast != 0) {
			if((attackEast & (~attackerPieces)) != 0) {
				moves.add(new long[] {king, attackEast});
			}

			if((((attackEast & (~Bitboard.RANKS[0])) >>> 8) & (~attackerPieces)) != 0) {
				moves.add(new long[] {king, ((attackEast & (~Bitboard.RANKS[0])) >>> 8)});
			}

			if((((attackEast & (~Bitboard.RANKS[7])) << 8) & (~attackerPieces)) != 0) {
				moves.add(new long[] {king, ((attackEast & (~Bitboard.RANKS[7])) << 8)});
			}
		}

		long attackUp = ((king & (~Bitboard.RANKS[7])) << 8);

		if((attackUp & (~attackerPieces)) != 0) {
			moves.add(new long[] {king, attackUp});
		}

		long attackDown = ((king & Bitboard.RANKS[0]) >>> 8);

		if((attackDown & (~attackerPieces)) != 0) {
			moves.add(new long[] {king, attackDown});
		}

		return moves;
	}

	public static long generateMovesMap(Color color, long king, long attackerPieces, long defenderPieces) {
		long attacks = 0L;

		attacks |= ((king & (~Bitboard.FILES[7])) >>> 1);
		attacks |= ((king & (~Bitboard.FILES[0])) << 1);

		king |= attacks;

		attacks |= ((king & (~Bitboard.RANKS[0])) >>> 8);
		attacks |= ((king & (~Bitboard.RANKS[7])) << 8);

		attacks &= ~attackerPieces;

		return attacks;
	}
}
