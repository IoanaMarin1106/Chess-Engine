
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
		0x8080808080808080L, /* A */
		0x4040404040404040L, /* B */
		0x2020202020202020L, /* C */
		0x1010101010101010L, /* D */
		0x0808080808080808L, /* E */
		0x0404040404040404L, /* F */
		0x0202020202020202L, /* G */
		0x0101010101010101L, /* H */
	};

	private long[] whitePieces = new long[6];
	private long[] blackPieces = new long[6];

	public static int getRank(long pos) {
		for(int i = 0; i < RANKS.length; i++) {
			if((pos & RANKS[i]) != 0) {
				return i;
			}
		}

		return 0;
	}

	public static int getFile(long pos) {
		for(int i = 0; i < FILES.length; i++) {
			if((pos & FILES[i]) != 0) {
				return i;
			}
		}

		return 0;
	}

	public Bitboard() {
		reset();
	}

	public void reset() {
		whitePieces = WHITE_RESET;
		blackPieces = BLACK_RESET;
	}

	public long getColorPieces(long[] pieces) {
		long res = 0;

		for(long piece : pieces) {
			res |= piece;
		}

		return res;
	}

	public Piece.PieceType getPieceType(long pos, Piece.PieceColor color) {
		if(color == Piece.PieceColor.WHITE) {
			if((whitePieces[0] & pos) != 0)		return Piece.PieceType.KING;
			if((whitePieces[1] & pos) != 0)		return Piece.PieceType.QUEEN;
			if((whitePieces[2] & pos) != 0)		return Piece.PieceType.ROOK;
			if((whitePieces[3] & pos) != 0)		return Piece.PieceType.BISHOP;
			if((whitePieces[4] & pos) != 0)		return Piece.PieceType.KNIGHT;
			if((whitePieces[5] & pos) != 0)		return Piece.PieceType.PAWN;
		} else {
			if((blackPieces[0] & pos) != 0)		return Piece.PieceType.KING;
			if((blackPieces[1] & pos) != 0)		return Piece.PieceType.QUEEN;
			if((blackPieces[2] & pos) != 0)		return Piece.PieceType.ROOK;
			if((blackPieces[3] & pos) != 0)		return Piece.PieceType.BISHOP;
			if((blackPieces[4] & pos) != 0)		return Piece.PieceType.KNIGHT;
			if((blackPieces[5] & pos) != 0)		return Piece.PieceType.PAWN;
		}

		return null;
	}

	public boolean isValidMove(long[] move, Piece.PieceColor color) {
		if(move == null || move.length != 2 || move[0] == move[1]) {
			return false;
		}

		if(move[0] < 0 || move[0] >= (1L << 64L) 
				|| move[1] < 0 || move[1] >= (1L << 64L)) {
			return false;
		}

		long allBlackPieces = getColorPieces(blackPieces);
		long allWhitePieces = getColorPieces(whitePieces);

		if(color == Piece.PieceColor.WHITE) {
			if((move[0] & allWhitePieces) == 0 || (move[0] & allBlackPieces) != 0) {
				return false;
			}

			if((move[1] & allWhitePieces) != 0) {
				return false;
			}

			Piece.PieceType type = getPieceType(move[0], color);

			switch(type) {
				case KING:
					return King.isValidMove(move);
				case QUEEN:
					return Queen.isValidMove(move, (allWhitePieces | allBlackPieces));
				case ROOK:
					return Rook.isValidMove(move, (allWhitePieces | allBlackPieces));
				case BISHOP:
					return Bishop.isValidMove(move, (allWhitePieces | allBlackPieces));
				case KNIGHT:
					return Knight.isValidMove(move);
				case PAWN:
					return Pawn.isValidWhiteMove(move, allBlackPieces);
				default:
					//naspa coaie
			}
		} else {
			if((move[0] & allWhitePieces) != 0 || (move[0] & allBlackPieces) == 0) {
				return false;
			}

			if((move[1] & allBlackPieces) != 0) {
				return false;
			}

			Piece.PieceType type = getPieceType(move[0], color);

			switch(type) {
				case KING:
					return King.isValidMove(move);
				case QUEEN:
					return Queen.isValidMove(move, (allWhitePieces | allBlackPieces));
				case ROOK:
					return Rook.isValidMove(move, (allWhitePieces | allBlackPieces));
				case BISHOP:
					return Bishop.isValidMove(move, (allWhitePieces | allBlackPieces));
				case KNIGHT:
					return Knight.isValidMove(move);
				case PAWN:
					return Pawn.isValidBlackMove(move, allWhitePieces);
				default:
					//naspa coaie
			}
		}

		return false;
	}

	public void makeMove(long[] move, Piece.PieceColor color) {
		if(color == Piece.PieceColor.WHITE) {
			Piece.PieceType type = getPieceType(move[0], color);
			Move.unsetPosition(whitePieces, type.getIndex(), move[0]);
			Move.setPosition(whitePieces, type.getIndex(), blackPieces, move[1]);
		}
	}
}
