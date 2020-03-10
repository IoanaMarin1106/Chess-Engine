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

	public long getColorPieces(long[] pieces) {
		long res = 0;

		for(long piece : pieces) {
			res |= piece;
		}

		return res;
	}

	public long flipPiece222(long piece) {

		for(int i = 0; i < 4; i++) {
			long upRank = (RANKS[8-i-1] & piece);
			upRank = upRank >> 8;
			upRank = (upRank & (~RANKS[7]));
			upRank = upRank >> (8 * (8-2*i-2));

			long downRank = (RANKS[i] & piece);
			downRank = downRank << (8 * (8-2*i-1));

			piece = ((piece & (~RANKS[i])) & (~RANKS[8-i-1]));
			piece = ((piece | upRank) | downRank);
		}

		return piece;
	}

	public long flipPiece(long piece) {
		for(int i = 0; i < 32; i++) {
			long upBit = (piece & (1L << (64 - i - 1)));
			long downBit = (piece & (1L << i));

			upBit = upBit >> (64 - 2*i - 1);
			downBit = downBit << (64 - 2*i - 1);

			piece = ((piece & (~(1L << (64 - i)))) & (~(1L << i)));
			piece = ((piece | upBit) | downBit);
		}

		return piece;
	}

	public void flip() {

		for(int i = 0; i < 6; i++) {
			long aux = flipPiece(blackPieces[i]);
			blackPieces[i] = flipPiece(whitePieces[i]);
			whitePieces[i] = aux;
		}
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

		long color1Pieces, color2Pieces;

		if(color == Piece.PieceColor.WHITE) {
			color1Pieces = getColorPieces(whitePieces);
			color2Pieces = getColorPieces(blackPieces);
		} else {
			color1Pieces = getColorPieces(blackPieces);
			color2Pieces = getColorPieces(whitePieces);
		}

		if((move[0] & color1Pieces) == 0 || (move[0] & color2Pieces) != 0) {
			return false;
		}

		if((move[1] & color1Pieces) != 0) {
			return false;
		}

		Piece.PieceType type = getPieceType(move[0], color);

		switch(type) {
			case KING:
				return King.isValidMove(move);
			case QUEEN:
				return Queen.isValidMove(move, (color1Pieces | color2Pieces));
			case ROOK:
				return Rook.isValidMove(move, (color1Pieces | color2Pieces));
			case BISHOP:
				return Bishop.isValidMove(move, (color1Pieces | color2Pieces));
			case KNIGHT:
				return Knight.isValidMove(move);
			case PAWN:
				if(color == Piece.PieceColor.WHITE) {
					return Pawn.isValidWhiteMove(move, color2Pieces,
											(color1Pieces | color2Pieces));
				} else {
					return Pawn.isValidBlackMove(move, color2Pieces,
											(color1Pieces | color2Pieces));
				}
			default:
				//naspa
		}

		return false;
	}

	public void makeMove(long[] move, Piece.PieceColor color) {
		if(color == Piece.PieceColor.WHITE) {
			Piece.PieceType type = getPieceType(move[0], color);
			Move.unsetPosition(whitePieces, type.getIndex(), move[0]);
			Move.setPosition(whitePieces, type.getIndex(), blackPieces, move[1]);
		} else {
			Piece.PieceType type = getPieceType(move[0], color);
			Move.unsetPosition(blackPieces, type.getIndex(), move[0]);
			Move.setPosition(blackPieces, type.getIndex(), whitePieces, move[1]);
		}
	}

	public long[] generateMove(Piece.PieceColor color) {
		if(color == Piece.PieceColor.WHITE) {
			
		} else {
			ArrayList<long[]> moves;
			moves = Pawn.generateMoves(blackPieces[5]);

			for(long[] move : moves) {
				if(isValidMove(move, color)) {
					return move;
				}
			}

			moves = Queen.generateMoves(blackPieces[1]);

			for(long[] move : moves) {
				if(isValidMove(move, color)) {
					return move;
				}
			}

			moves = Bishop.generateMoves(blackPieces[3]);

			for(long[] move : moves) {
				if(isValidMove(move, color)) {
					return move;
				}
			}


			moves = Knight.generateMoves(blackPieces[4]);
			for(long[] move : moves) {
				if(isValidMove(move, color)) {
					return move;
				}
			}

			moves = Rook.generateMoves(blackPieces[2]);
			for(long[] move : moves) {
				if(isValidMove(move, color)) {
					return move;
				}
			}

			moves = King.generateMoves(blackPieces[0]);

			for(long[] move : moves) {
				if(isValidMove(move, color)) {
					return move;
				}
			}
		}

		return null;
	}
}
