import java.util.*;

public class Bitboard {
	private static final long[] WHITE_RESET = {
		0x0000000000000010L, /* KING   */
		0x0000000000000008L, /* QUEEN  */
		0x0000000000000081L, /* ROOK   */
		0x0000000000000024L, /* BISHOP */
		0x0000000000000042L, /* KNIGHT */
		0x000000000000FF00L, /* PAWN   */
	};

	private static final long[] BLACK_RESET = {
		0x1000000000000000L, /* KING   */
		0x0800000000000000L, /* QUEEN  */
		0x8100000000000000L, /* ROOK   */
		0x2400000000000000L, /* BISHOP */
		0x4200000000000000L, /* KNIGHT */
		0x00FF000000000000L, /* PAWN   */
	};

	public static final long[] RANKS = {
		0x00000000000000FFL, /* 1 */
		0x000000000000FF00L, /* 2 */
		0x0000000000FF0000L, /* 3 */
		0x00000000FF000000L, /* 4 */
		0x000000FF00000000L, /* 5 */
		0x0000FF0000000000L, /* 6 */
		0x00FF000000000000L, /* 7 */
		0xFF00000000000000L, /* 8 */
	};

	public static final long[] FILES = {
		0x8080808080808080L, /* H */
		0x4040404040404040L, /* G */
		0x2020202020202020L, /* F */
		0x1010101010101010L, /* R */
		0x0808080808080808L, /* D */
		0x0404040404040404L, /* C */
		0x0202020202020202L, /* B */
		0x0101010101010101L, /* A */
	};
	
	private long[] whitePieces = new long[6];
	private long[] blackPieces = new long[6];

	/* FOR BITBOARD DEBUG -------------------*/

	public long[] getWhitePieces() {
		return this.whitePieces;
	}
	
	public long[] getBlackPieces() {
		return this.blackPieces;
	}
	
	 /*--------------------------------------*/

	 public Bitboard() {
		reset();
	}

	public void reset() {
		whitePieces = Arrays.copyOf(WHITE_RESET, WHITE_RESET.length);
		blackPieces = Arrays.copyOf(BLACK_RESET, BLACK_RESET.length);
	}

	public static int getRank(long position) {
		for(int i = 0; i < RANKS.length; i++) {
			if((position & RANKS[i]) != 0) {
				return i;
			}
		}

		return 0;
	}

	public static int getFile(long position) {
		for(int i = 0; i < FILES.length; i++) {
			if((position & FILES[i]) != 0) {
				return i;
			}
		}

		return 0;
	}

	public long getAllPieces(long[] pieces) {
		long allPieces = 0;

		for(long piece : pieces) {
			allPieces |= piece;
		}

		return allPieces;
	}

	public Piece.Type getPieceTypeAtPosition(long position, Piece.Color color) {
		long[] pieces = (color == Piece.Color.WHITE) ? whitePieces : blackPieces;

		for(int i = 0; i < pieces.length; i++) {
			if((pieces[i] & position) != 0) {
				return Piece.getType(i);
			}
		}

		return null;
	}

	public boolean isValidMove(long[] move, Piece.Color color) {
		if(move == null || move.length != 2 || move[0] == move[1]) {
			return false;
		}

		long color1Pieces, color2Pieces;
		color1Pieces = getAllPieces((color == Piece.Color.WHITE) ? whitePieces : blackPieces);
		color2Pieces = getAllPieces((color == Piece.Color.WHITE) ? blackPieces : whitePieces);

		if((move[0] & color1Pieces) == 0 || (move[0] & color2Pieces) != 0) {
			return false;
		}

		if((move[1] & color1Pieces) != 0) {
			return false;
		}

		Piece.Type type = getPieceTypeAtPosition(move[0], color);

		return Piece.isValidMove(type, color, move, color1Pieces, color2Pieces);
	}


	public void unsetPosition(long[] attacker, int index, long pos) {
		attacker[index] = attacker[index] & (~pos);
	}
	
	public void setPosition(long[] attacker, int index, long[] defender, long pos) {
		attacker[index] = attacker[index] | pos;

		for(int i = 0; i < defender.length; i++) {
			if((defender[i] & pos) != 0) {
				defender[i] = defender[i] & (~pos);
			}
		}
	}

	public void makeMove(long[] move, Piece.Color color) {
		long[] srcPieces = (color == Piece.Color.WHITE) ? whitePieces : blackPieces;
		long[] destPieces = (color == Piece.Color.WHITE) ? blackPieces : whitePieces;

		Piece.Type type = getPieceTypeAtPosition(move[0], color);
		System.out.println("in makeMove color = " + color + " type = " + type);
		unsetPosition(srcPieces, type.getIndex(), move[0]);
		setPosition(srcPieces, type.getIndex(), destPieces, move[1]);
	}

	public long[] generateMove(Piece.Color color) {
		long[] movingPieces = (color == Piece.Color.WHITE) ? whitePieces : blackPieces;

		ArrayList<long[]> moves;

		for(int i = 5; i >= 0; i--) { //start with pawn, knight...
			moves = Piece.generateMoves(Piece.getType(i), movingPieces[i]);

			for(long[] move : moves) {
				if(isValidMove(move, color)) {
					return move;
				}
			}
		}

		return null;
	}

	public boolean isCheck(Piece.Color defenderColor) {
		long[] defender = (defenderColor == Piece.Color.WHITE) ? whitePieces : blackPieces;
		long[] attacker = (defenderColor == Piece.Color.WHITE) ? blackPieces : whitePieces;
		Piece.Color attackerColor = (defenderColor == Piece.Color.WHITE) ? Piece.Color.BLACK :
																		Piece.Color.WHITE;

		ArrayList<long[]> moves;

		System.out.println("# ajunge in ischeck");
		System.out.println("defenderColor = " + defenderColor + " attackerColor = " + attackerColor);

		for(int i = 5; i >= 0; i--) {
			moves = Piece.generateMoves(Piece.getType(i), attacker[i]);

			for(long[] move : moves) {
				System.out.println("#");
				System.out.println("#blabla" + isValidMove(move, attackerColor));
				System.out.println("#" + (move[1] == defender[0]));
				if(isValidMove(move, attackerColor) && (move[1] == defender[0])) {
					System.out.println("# ajunge in isvalidmove");
					return true;
				}
			}
			System.out.println("#CE PLMedgbewhvfbhewgbfgewfgweyhgfew" + (6-i));
		}
		System.out.println("#nust daca aj aici");
		return false;
	}
}
