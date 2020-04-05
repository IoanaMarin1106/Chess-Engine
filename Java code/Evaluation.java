
public class Evaluation {

	public final static int[] pieceValue = {200, 9, 5, 3, 3, 1};

	public static int materialValue(Bitboard board, Piece.Color playerColor) {
		int[] playerPieces, vsPieces;

		if(playerColor == Piece.Color.WHITE) {
			playerPieces = board.remainingWhitePieces;
			vsPieces = board.remainingBlackPieces;
		} else {
			playerPieces = board.remainingBlackPieces;
			vsPieces = board.remainingWhitePieces;
		}

		int playerScore = 0, vsScore = 0;

		for(int i = 0; i < 6; i++) {
			playerScore += (pieceValue[i] * playerPieces[i]);
			vsScore += (pieceValue[i] * vsPieces[i]);
		}		

		return (playerScore - vsScore);
	}

	/** pt piesele negre */

	public final static int pawnTableValue[][] = {
		{ 0,  0,  0,  0,  0,  0,  0,  0},
		{50, 50, 50, 50, 50, 50, 50, 50},
		{10, 10, 20, 30, 30, 20, 10, 10},
		{ 5,  5, 10, 25, 25, 10,  5,  5},
		{ 0,  0,  0, 20, 20,  0,  0,  0},
		{ 5, -5,-10,  0,  0,-10, -5,  5},
		{ 5, 10, 10,-20,-20, 10, 10,  5},
		{ 0,  0,  0,  0,  0,  0,  0,  0}
	};
	
	public final static int knightTableValue[][] = {
	    {-50,-40,-30,-30,-30,-30,-40,-50},
	    {-40,-20,  0,  0,  0,  0,-20,-40},
	    {-30,  0, 10, 15, 15, 10,  0,-30},
	    {-30,  5, 15, 20, 20, 15,  5,-30},
	    {-30,  0, 15, 20, 20, 15,  0,-30},
	    {-30,  5, 10, 15, 15, 10,  5,-30},
	    {-40,-20,  0,  5,  5,  0,-20,-40},
	    {-50,-40,-30,-30,-30,-30,-40,-50},
	};

	public final static int bishopTableValue[][] = {
	    { -20,-10,-10,-10,-10,-10,-10,-20},
	    { -10,  0,  0,  0,  0,  0,  0,-10},
	    { -10,  0,  5, 10, 10,  5,  0,-10},
	    { -10,  5,  5, 10, 10,  5,  5,-10},
	    { -10,  0, 10, 10, 10, 10,  0,-10},
	    { -10, 10, 10, 10, 10, 10, 10,-10},
	    { -10,  5,  0,  0,  0,  0,  5,-10},
	    { -20,-10,-10,-10,-10,-10,-10,-20}
	};

	public final static int rookTableValue[][] = {
	    { 0,  0,  0,  0,  0,  0,  0,  0},
	    { 5, 10, 10, 10, 10, 10, 10,  5},
	    {-5,  0,  0,  0,  0,  0,  0, -5},
	    {-5,  0,  0,  0,  0,  0,  0, -5},
	    {-5,  0,  0,  0,  0,  0,  0, -5},
	    {-5,  0,  0,  0,  0,  0,  0, -5},
	    {-5,  0,  0,  0,  0,  0,  0, -5},
	    { 0,  0,  0,  5,  5,  0,  0,  0}
	};

	public final static int queenTableValue[][] = {
		{-20,-10,-10, -5, -5,-10,-10,-20},
		{-10,  0,  0,  0,  0,  0,  0,-10},
		{-10,  0,  5,  5,  5,  5,  0,-10},
 		{-5,  0,  5,  5,  5,  5,  0, -5},
		{ 0,  0,  5,  5,  5,  5,  0, -5},
		{-10,  5,  5,  5,  5,  5,  0,-10},
		{-10,  0,  5,  0,  0,  0,  0,-10},
		{-20,-10,-10, -5, -5,-10,-10,-20}
	};

	public final static int kingTableValue[][] = {
	    {-30,-40,-40,-50,-50,-40,-40,-30},
	    {-30,-40,-40,-50,-50,-40,-40,-30},
	    {-30,-40,-40,-50,-50,-40,-40,-30},
	    {-30,-40,-40,-50,-50,-40,-40,-30},
	    {-20,-30,-30,-40,-40,-30,-30,-20},
	    {-10,-20,-20,-20,-20,-20,-20,-10},
	    { 20, 20,  0,  0,  0,  0, 20, 20},
	    { 20, 30, 10,  0,  0, 10, 30, 20}
	};

	public final static int[][][] tableValue = {
		kingTableValue,
		queenTableValue,
		rookTableValue,
		bishopTableValue,
		knightTableValue,
		pawnTableValue
	};
	
	public static int tableValue(Bitboard board, Piece.Color playerColor) {
		int playerScore = 0, vsScore = 0;

		long[] playerPieces, vsPieces;

		if(playerColor == Piece.Color.WHITE) {
			playerPieces = board.whitePieces;
			vsPieces = board.blackPieces;
		} else {
			playerPieces = board.blackPieces;
			vsPieces = board.whitePieces;
		}

		for(int i = 0; i < tableValue.length; i++) {
			for(int rank = 0; rank < Bitboard.RANKS.length; rank++) {
				for(int file = 0; file < Bitboard.FILES.length; file++) {
					if((playerPieces[i] & Bitboard.RANKS[i] & Bitboard.FILES[i]) != 0) {
						int aux = (playerColor == Piece.Color.WHITE) ? (7-rank) : rank;
						playerScore += tableValue[i][aux][file];
					}

					if((vsPieces[i] & Bitboard.RANKS[i] & Bitboard.FILES[i]) != 0) {
						int aux = (playerColor == Piece.Color.WHITE) ? rank : (7-rank);
						vsScore += tableValue[i][aux][file];
					}					
				}
			}
		}

		return (playerScore - vsScore);
	}


	public static int evaluate(Bitboard board, Piece.Color playerColor) {
		return materialValue(board, playerColor) * 100 + tableValue(board, playerColor);
	}
}
