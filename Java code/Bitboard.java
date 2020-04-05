import java.util.*;

/**
 * This class serves as the chessboard, for which we used
 * a bitboard representation. Bitboards are finite sets
 * of 64 bits, each bit for one square of the chessboard.
 *
 * Using operations on those sets, the Bitboard class
 * has methods for deciding whether a move is valid and
 * for generating a valid move. 
 *
 * @author Creierul si Cerebelii
 */
public class Bitboard {

	/**
	 * The initial positions of the white pieces on the board.
	 */
	private static final long[] WHITE_RESET = {
		0x0000000000000010L, /* KING   */
		0x0000000000000008L, /* QUEEN  */
		0x0000000000000081L, /* ROOK   */
		0x0000000000000024L, /* BISHOP */
		0x0000000000000042L, /* KNIGHT */
		0x000000000000FF00L, /* PAWN   */
	};

	/**
	 * The initial positions of the black pieces on the board.
	 */
	private static final long[] BLACK_RESET = {
		0x1000000000000000L, /* KING   */
		0x0800000000000000L, /* QUEEN  */
		0x8100000000000000L, /* ROOK   */
		0x2400000000000000L, /* BISHOP */
		0x4200000000000000L, /* KNIGHT */
		0x00FF000000000000L, /* PAWN   */
	};

	/**
	 * Bit masks for each rank of the board (all bits on that
	 * rank are set to 1).
	 */
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

	/**
	 * Bit masks for each file of the board (all bits on that file
	 * are set to 1).
	 */
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
	
	/**
	 * Array for the current positions of the white pieces on the board,
	 * where the order of the pieces is the same as for WHITE_RESET.
	 */
	protected long[] whitePieces = new long[6];

	/**
	 * Array for the current positions of the black pieces on the board,
	 * where the order of the pieces is the same as for BLACK_RESET.
	 */
	protected long[] blackPieces = new long[6];

	protected int[] remainingWhitePieces = {1, 1, 2, 2, 2, 8};
	protected int[] remainingBlackPieces = {1, 1, 2, 2, 2, 8};

	private long[] lastWhiteMove = null;
	private long[] lastBlackMove = null;

	/**
	 * Default constructor for a Bitboard, which resets the board's
	 * initial positions.
	 */
	public Bitboard() {
		reset();
	}

	/**
	 *
	 */
	@Override
	public Object clone() {
		Bitboard ret = new Bitboard();

		for(int i = 0; i < whitePieces.length; i++) {
			ret.whitePieces[i] = this.whitePieces[i];
			ret.blackPieces[i] = this.blackPieces[i];
			ret.remainingWhitePieces[i] = this.remainingWhitePieces[i];
			ret.remainingBlackPieces[i] = this.remainingBlackPieces[i];
		}

		return ret;
	}

	/**
	 * Method to reset the positions of all pieces on board.
	 */
	public void reset() {
		whitePieces = Arrays.copyOf(WHITE_RESET, WHITE_RESET.length);
		blackPieces = Arrays.copyOf(BLACK_RESET, BLACK_RESET.length);
	}

	/**
	 * Method which finds on which rank it's a position and returns it.
	 * For doing so, it goes through each rank and checks whether its
	 * mask and the given position have any overlapping bits.
	 * @param position the given position of a piece.
	 */
	public static int getRank(long position) {
		for(int i = 0; i < RANKS.length; i++) {
			if((position & RANKS[i]) != 0) {
				return i;
			}
		}

		return 0;
	}

	/**
	 * Method which finds on which file it's a position and returns it.
	 * For doing so, it goes through each file and checks whether its
	 * mask and the given position have any overlapping bits.
	 * @param position the given position of a piece.
	 */
	public static int getFile(long position) {
		for(int i = 0; i < FILES.length; i++) {
			if((position & FILES[i]) != 0) {
				return i;
			}
		}

		return 0;
	}

	/**
	 * Method which adds all the bits of an array to create a bitmap
	 * for all the pieces in that array.
	 * @param pieces the array of the positions of each piece.
	 * @return the positions of all pieces in one long variable.
	 */
	public long getAllPieces(long[] pieces) {
		long allPieces = 0;

		for(long piece : pieces) {
			allPieces |= piece;
		}

		return allPieces;
	}

	/**
	 * Method which finds what type of piece holds a certain position on board.
	 * @param position the position where the piece is at.
	 * @param color the color of the piece.
	 * @return the type of the piece at that position and of that color.
	 */
	public Piece.Type getPieceTypeAtPosition(long position, Piece.Color color) {
		long[] pieces = (color == Piece.Color.WHITE) ? whitePieces : blackPieces;

		for(int i = 0; i < pieces.length; i++) {
			if((pieces[i] & position) != 0) {
				return Piece.getType(i);
			}
		}

		return null;
	}

	/**
	 * Method that unsets the bit at a position for a piece, meaning that
	 * a piece of that type has left its position for another or was captured.
	 * @param pieces the array of pieces of the color that has to be unset.
	 * @param index the index in that array for the specific piece that has
	 * to be unset.
	 */
	public void unsetPosition(long[] pieces, int index, long pos) {
		pieces[index] = pieces[index] & (~pos);
	}
	
	/**
	 * Method which sets the bit at a position for a piece, meaning it moved.
	 * Also, the method checks whether the attacker captured a defender's piece
	 * and, if so, unsets that piece from the defender's array.
	 * @param attacker the array of the pieces of the color that is on move.
	 * @param index the index in that array for the specific piece that has to
	 * be set.
	 * @param defender the array of the pieces of the color that is on defence.
	 * @param pos the position that has to be set.
	 */
	public void setPosition(Piece.Color color, long[] attacker, int index, long[] defender, long pos) {
		attacker[index] = attacker[index] | pos;

		for(int i = 0; i < defender.length; i++) {
			if((defender[i] & pos) != 0) {
				if(color == Piece.Color.WHITE) {
					remainingBlackPieces[i]--;
				} else {
					remainingWhitePieces[i]--;
				}
				
				unsetPosition(defender, i, pos);
			}
		}
	}

	/**
	 * Method which updates the chessboard with a new move.
	 * @param move array of 2 elements for the initial and destination positions
	 * of the move.
	 * @param color the color that is on move.
	 */
	public void makeMove(long[] move, Piece.Color color) {
		long[] srcPieces = (color == Piece.Color.WHITE) ? whitePieces : blackPieces;
		long[] destPieces = (color == Piece.Color.WHITE) ? blackPieces : whitePieces;

		Piece.Type type = getPieceTypeAtPosition(move[0], color);
		unsetPosition(srcPieces, type.getIndex(), move[0]);
		setPosition(color, srcPieces, type.getIndex(), destPieces, move[1]);

		if(type == Piece.Type.PAWN) {
			enPassant(move, color, type);
		}

		if(color == Piece.Color.WHITE) {
			lastWhiteMove = move;
		} else {
			lastBlackMove = move;
		}
	}

	/**
	 *
	 */
	public void enPassant(long[] move, Piece.Color color, Piece.Type type) {
		if(color == Piece.Color.WHITE && lastBlackMove != null && (move[0] & RANKS[4]) != 0) {
			if(move[1] == (move[0] << 9)) {
				if((blackPieces[5] & (move[0] << 1)) != 0 && (move[0] << 1) == lastBlackMove[1]) {
					blackPieces[5] = blackPieces[5] & (~(move[0] << 1));
					remainingBlackPieces[5]--;
				}
			} else if(move[1] == (move[0] << 7)) {
				if((blackPieces[5] & (move[0] >>> 1)) != 0 && (move[0] >>> 1) == lastBlackMove[1]) {
					blackPieces[5] = blackPieces[5] & (~(move[0] >>> 1));
					remainingBlackPieces[5]--;
				}
			}
		} else if(lastWhiteMove != null && (move[0] & RANKS[3]) != 0) {
			if(move[1] == (move[0] >>> 9)) {
				if((whitePieces[5] & (move[0] >>> 1)) != 0 && (move[0] >>> 1) == lastWhiteMove[1]) {
					whitePieces[5] = whitePieces[5] & (~(move[0] >>> 1));
					remainingWhitePieces[5]--;
				}
			} else if(move[1] == (move[0] >>> 7)) {
				if((whitePieces[5] & (move[0] << 1)) != 0 && (move[0] << 1) == lastWhiteMove[1]) {
					whitePieces[5] = whitePieces[5] & (~(move[0] << 1));
					remainingWhitePieces[5]--;
				}
			}
		}
	}

	/**
	 *
	 */
	public void pawnPromotion(long pos, Piece.Color color, char newType) {
		long[] pieces = (color == Piece.Color.WHITE) ? whitePieces : blackPieces;
		int[] remainingPieces = (color == Piece.Color.WHITE) ? remainingWhitePieces :
															remainingBlackPieces;
		int indexNewType = Piece.getTypeIndex(newType);

		unsetPosition(pieces, 5, pos);
		pieces[indexNewType] = pieces[indexNewType] | pos;
		
		remainingPieces[5]--;
		remainingPieces[indexNewType]++;
	}

	/**
	 *
	 */
	public void makeCastling(long[] move, Piece.Color color) {
		long[] pieces = (color == Piece.Color.WHITE) ? whitePieces : blackPieces;
		pieces[0] = pieces[0] & (~move[0]);
		pieces[0] = pieces[0] | move[1];

		if(move[0] > move[1]) {
			pieces[2] = pieces[2] & (~(move[0] >>> 4));
			pieces[2] = pieces[2] | (move[1] << 1);
		} else {
			pieces[2] = pieces[2] & (~(move[0] << 3));
			pieces[2] = pieces[2] | (move[1] >>> 1);
		}
	}

	/**
	 * Method that generates the first valid move for pieces of a given color.
	 * @param color the color that is on move.
	 */
	public long[] generateMove(Piece.Color color) {
		return Negamax.negamaxHandler(this, color);
	}

	/**
	 * Generates all possible moves. Called from negamax.
	 */
	public ArrayList<long[]> generateAllMoves(Piece.Color color) {

		ArrayList<long[]> moves = new ArrayList<long[]>();

		/* Goes through each piece and tries to find a valid move.
		 * The order of the pieces is: Pawn, Knight, Bishop, Rook, Queen, King.
		 */
		for(int i = 5; i >= 0; i--) {
			moves.addAll(Piece.generateMoves(Piece.getType(i), color, this));
		}

		ArrayList<long[]> old = moves;
		moves = new ArrayList<long[]>();

		for(long[] move : old) {
			Bitboard nextState = (Bitboard)this.clone();
			nextState.makeMove(move, color);

			if(nextState.isCheck(color) == false) {
				moves.add(move);
			}
		}

		return moves;
	}

	/**
	 * Method which checks if in the current board the King is in check.
	 * @param defenderColor the color of the King that may be in check.
	 * @return true if the King is in check/false otherwise.
	 */
	public boolean isCheck(Piece.Color defenderColor) {
		long[] defender = (defenderColor == Piece.Color.WHITE) ? whitePieces : blackPieces;
		long[] attacker = (defenderColor == Piece.Color.WHITE) ? blackPieces : whitePieces;
		Piece.Color attackerColor = (defenderColor == Piece.Color.WHITE) ? Piece.Color.BLACK :
																		Piece.Color.WHITE;

		ArrayList<long[]> moves;

		for(int i = 5; i >= 0; i--) {
			moves = Piece.generateMoves(Piece.getType(i), attackerColor, this);

			for(long[] move : moves) {
				if(move[1] == defender[0]) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean gameOver() {
		return (whitePieces[0] == 0 || blackPieces[0] == 0);
	}

	public boolean winner(Piece.Color color) {
		if(gameOver() == false) {
			return false;
		}

		if(color == Piece.Color.WHITE && remainingBlackPieces[0] == 0) {
			return true;
		} else if(color == Piece.Color.BLACK && remainingWhitePieces[0] == 0) {
			return true;
		}

		return false;
	}
	
	/** ------------------ FOR CORRECT DEBUGGING ---------------
	 * Get method for the bitboard's white pieces' positions. It is used
	 * only for debugging.
	 * @return the array of the white pieces
	 */
	public long[] getWhitePieces() {
		return this.whitePieces;
	}
	
	/**
	 * Get method for the bitboard's black pieces' positions. It is used
	 * only for debugging.
	 * @return the array of the black pieces
	 */
	public long[] getBlackPieces() {
		return this.blackPieces;
	}
}
