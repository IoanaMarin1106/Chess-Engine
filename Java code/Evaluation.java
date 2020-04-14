
public class Evaluation {

	public final static int[] pieceValue = {200, 9, 5, 4, 3, 1};

	public static int evaluateMove(Bitboard board, Piece.Color playerColor, long[] move) {
		long[] vsPieces = (playerColor == Piece.Color.WHITE) ? board.blackPieces : 
															board.whitePieces;

		for(int i = 0; i < vsPieces.length; i++) {
			if((move[1] & vsPieces[i]) != 0) {
				return pieceValue[i];
			}
		}

		return 0;
	}

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

		// long[] playerPieces, vsPieces;

		// if(playerColor == Piece.Color.WHITE) {
		// 	playerPieces = board.whitePieces;
		// 	vsPieces = board.blackPieces;
		// } else {
		// 	playerPieces = board.blackPieces;
		// 	vsPieces = board.whitePieces;
		// }

		// for(int i = 0; i < tableValue.length; i++) {
		// 	// for(int rank = 0; rank < Bitboard.RANKS.length; rank++) {
		// 	// 	for(int file = 0; file < Bitboard.FILES.length; file++) {
		// 	// 		if((playerPieces[i] & Bitboard.RANKS[rank] & Bitboard.FILES[file]) != 0) {
		// 	// 			int aux = (playerColor == Piece.Color.WHITE) ? (7-rank) : rank;
		// 	// 			playerScore += tableValue[i][aux][file];
		// 	// 		}

		// 	// 		if((vsPieces[i] & Bitboard.RANKS[rank] & Bitboard.FILES[file]) != 0) {
		// 	// 			int aux = (playerColor == Piece.Color.WHITE) ? rank : (7-rank);
		// 	// 			vsScore += tableValue[i][aux][file];
		// 	// 		}					
		// 	// 	}
		// 	// }
		// 	long pieces = board.playerPieces[i];

		// 	while(pieces != 0) {
		// 		int tile = Long.numberOfTrailingZeros(pieces);
		// 		pieces = (pieces & (~(1L << tile)));

		// 		playerScore += tableValue[i][tile / 8][tile % 8];
		// 	}
		// }

		if(playerColor == Piece.Color.WHITE) {
			for(int i = 0; i < tableValue.length; i++) {
				long pieces = board.whitePieces[i];

				while(pieces != 0) {
					int tile = Long.numberOfTrailingZeros(pieces);
					pieces = (pieces & (~(1L << tile)));

					playerScore += tableValue[i][7 - tile / 8][tile % 8];
				}

				pieces = board.blackPieces[i];

				while(pieces != 0) {
					int tile = Long.numberOfTrailingZeros(pieces);
					pieces = (pieces & (~(1L << tile)));

					vsScore += tableValue[i][(tile / 8)][tile % 8];
				}
			}
		} else {
			for(int i = 0; i < tableValue.length; i++) {
				long pieces = board.whitePieces[i];

				while(pieces != 0) {
					int tile = Long.numberOfTrailingZeros(pieces);
					pieces = (pieces & (~(1L << tile)));

					vsScore += tableValue[i][7 - tile / 8][tile % 8];
				}

				pieces = board.blackPieces[i];

				while(pieces != 0) {
					int tile = Long.numberOfTrailingZeros(pieces);
					pieces = (pieces & (~(1L << tile)));

					playerScore += tableValue[i][(tile / 8)][tile % 8];
				}
			}
		}


		return (playerScore - vsScore);
	}

	public static int pawnRankValue(Bitboard board, Piece.Color playerColor) {
		int playerScore = 0, vsScore = 0;

		long playerPawns, vsPawns;

		if(playerColor == Piece.Color.WHITE) {
			playerPawns = board.whitePieces[5];
			vsPawns = board.blackPieces[5];
		} else {
			playerPawns = board.blackPieces[5];
			vsPawns = board.whitePieces[5];
		}

		for(int i = 0; i < Bitboard.RANKS.length; i++) {
			if((playerPawns & Bitboard.RANKS[i]) != 0) {
				for(int j = 0; j < Bitboard.FILES.length; j++) {
					if((playerPawns & Bitboard.RANKS[i] & Bitboard.FILES[j]) != 0) {
						playerScore += (playerColor == Piece.Color.WHITE) ? i : (7 - i);
					}
				}
			}

			if((vsPawns & Bitboard.RANKS[i]) != 0) {
				for(int j = 0; j < Bitboard.FILES.length; j++) {
					if((vsPawns & Bitboard.RANKS[i] & Bitboard.FILES[j]) != 0) {
						vsScore += (playerColor == Piece.Color.WHITE) ? (7 - i) : i;
					}
				}
			}
		}

		return (playerScore - vsScore);
	}

	public static int mobilityValue(Bitboard board, Piece.Color playerColor) {
		Piece.Color vsColor = (playerColor == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
		return (board.generateAllMoves(playerColor).size() - board.generateAllMoves(vsColor).size());
		
	}

	public static int evaluate(Bitboard board, Piece.Color playerColor) {
		if(board.gameOver()) {
			return ((board.winner(playerColor)) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
		}

		//System.out.println("# TABLE VALUE E = " + tableValue(board, playerColor));
		return materialValue(board, playerColor) * 752 + tableValue(board, playerColor);// + mobilityValue(board, playerColor);
			//pawnRankValue(board, playerColor) + tableValue(board, playerColor);
	}

	// static int[][] PSQ_TABLE_PAWN_MG = {
	//     {0, 0, 0, 0, 0, 0, 0, 0},
	//     {-20, -14, -8, -1, 8, 27, 32, -22},
	//     {-21, -13, -4, 5, 25, 9, 19, -8},
	//     {-17, -13, 4, 25, 23, 24, -1, -9},
	//     {-3, 1, 16, 24, 39, 38, 21, 7},
	//     {30, 40, 50, 55, 82, 106, 91, 39},
	//     {48, 52, 66, 85, 47, -43, -100, -101},
	//     {0, 0, 0, 0, 0, 0, 0, 0}
	// };
	// static int[][] PSQ_TABLE_KNIGHT_MG = {
	//     {-76, -10, -15, 5, 35, 26, -7, -7},
	//     {-42, -10, 19, 42, 34, 54, 29, 28},
	//     {-3, 8, 26, 30, 64, 41, 46, 20},
	//     {2, 4, 36, 43, 60, 67, 73, 32},
	//     {3, 8, 25, 81, 48, 73, 34, 71},
	//     {-25, 32, 26, 43, 115, 90, 58, 56},
	//     {1, 37, 57, 36, 55, 106, 17, 52},
	//     {-148, -106, -33, -50, 25, -90, -40, -84}
	// };
	// static int[][] PSQ_TABLE_BISHOP_MG = {
	//     {-16, 17, 11, 0, 14, 14, 15, 2},
	//     {30, 29, 40, 18, 33, 39, 62, 9},
	//     {4, 39, 28, 30, 32, 43, 30, 22},
	//     {-4, 9, 20, 53, 58, 7, 6, 37},
	//     {-11, 22, 12, 57, 44, 34, 17, -1},
	//     {5, 23, 20, 48, 40, 106, 34, 39},
	//     {-28, -3, 0, -37, -19, -6, -36, -12},
	//     {-27, -67, -68, -52, -65, -74, -59, -66}
	// };
	// static int[][] PSQ_TABLE_ROOK_MG = {
	//     {8, 14, 14, 34, 41, 40, 45, 27},
	//     {-30, -13, -10, 13, 12, 40, 46, -23},
	//     {-32, -20, -17, -4, 12, 28, 44, 21},
	//     {-32, -29, -11, -13, -8, 7, 29, 7},
	//     {-15, -5, -7, 19, 21, 55, 60, 46},
	//     {8, 19, 10, 36, 87, 77, 125, 67},
	//     {-6, -10, 31, 19, 32, 67, 34, 66},
	//     {23, 22, -4, 39, 24, 42, 48, 44}
	// };
	// static int[][] PSQ_TABLE_QUEEN_MG = {
	//     {10, 4, 8, 24, 17, -9, -12, -53},
	//     {-12, 5, 8, 30, 29, 43, 42, 9},
	//     {-28, 1, -1, 6, 20, 19, 21, 34},
	//     {-7, -11, -3, 6, 13, 8, 9, 15},
	//     {-29, -8, 2, 0, 29, 28, 36, 7},
	//     {3, 10, 13, 15, 51, 140, 85, 75},
	//     {-6, -47, 4, -38, -36, 55, 51, 114},
	//     {-78, -23, -19, -1, -25, 22, 89, -7}
	// };
	// static int[][] PSQ_TABLE_KING_MG = {
	//     {-21, 7, -46, -92, 0, -65, 3, -10},
	//     {51, 7, -26, -73, -54, -36, 37, 35},
	//     {-14, -20, -43, -72, -63, -60, -36, -55},
	//     {-11, 24, -62, -83, -90, -78, -84, -126},
	//     {-54, 18, -83, -86, -115, -84, -23, -124},
	//     {74, 38, 24, -44, -38, 14, 52, 12},
	//     {153, 18, -2, 67, 43, -6, -19, 17},
	//     {13, 191, 179, 67, -87, 43, 98, 50}
	// };
	// static int[][] PSQ_TABLE_PAWN_EG = {
	//     {0, 0, 0, 0, 0, 0, 0, 0},
	//     {37, 27, 20, 14, 33, 22, 12, 13},
	//     {36, 29, 16, 16, 21, 22, 15, 15},
	//     {41, 34, 18, 6, 6, 16, 24, 21},
	//     {59, 50, 26, -1, 9, 14, 32, 34},
	//     {87, 76, 38, -4, -9, 15, 43, 51},
	//     {126, 119, 73, 40, 44, 72, 93, 109},
	//     {0, 0, 0, 0, 0, 0, 0, 0}
	// };
	// static int[][] PSQ_TABLE_KNIGHT_EG = {
	//     {16, -19, 7, 19, 0, 18, -13, -23},
	//     {24, 24, 16, 17, 22, 11, 11, 1},
	//     {2, 25, 24, 43, 36, 15, 8, -2},
	//     {9, 31, 47, 48, 56, 31, 18, 3},
	//     {19, 44, 46, 40, 49, 45, 44, 15},
	//     {18, 14, 46, 34, 16, 24, 10, 11},
	//     {11, 7, 19, 12, 13, -11, 20, -21},
	//     {-45, -20, 10, -8, -6, 4, -36, -98}
	// };
	// static int[][] PSQ_TABLE_BISHOP_EG = {
	//     {37, 32, 9, 28, 19, 14, 20, 19},
	//     {19, 6, 20, 18, 13, 16, -1, -9},
	//     {18, 26, 25, 29, 33, 12, 12, 15},
	//     {23, 16, 24, 15, 7, 26, 20, 3},
	//     {36, 20, 26, 17, 21, 19, 24, 23},
	//     {27, 17, 18, 4, 16, 1, 20, 31},
	//     {26, 23, 25, 31, 13, 22, 16, -4},
	//     {-7, 14, 24, 36, 22, 14, 23, 13}
	// };
	// static int[][] PSQ_TABLE_ROOK_EG = {
	//     {34, 36, 41, 27, 23, 30, 21, 9},
	//     {54, 35, 49, 39, 33, 24, 18, 44},
	//     {45, 50, 56, 44, 35, 32, 17, 18},
	//     {61, 68, 61, 60, 53, 52, 52, 41},
	//     {66, 54, 70, 64, 49, 46, 37, 46},
	//     {56, 59, 61, 53, 37, 34, 30, 37},
	//     {49, 58, 38, 42, 44, 38, 38, 22},
	//     {54, 43, 60, 43, 56, 52, 53, 51}
	// };
	// static int[][] PSQ_TABLE_QUEEN_EG = {
	//     {-32, -57, -37, -29, -25, -35, -46, -13},
	//     {-12, -24, -18, -30, -14, -60, -63, -54},
	//     {1, -2, 14, -1, 18, 34, 22, -23},
	//     {-2, 10, 18, 60, 34, 68, 62, 52},
	//     {43, 50, 51, 81, 79, 102, 107, 111},
	//     {35, 15, 38, 82, 103, 41, 44, 62},
	//     {3, 56, 50, 113, 113, 63, 73, 47},
	//     {8, 6, 38, 28, 49, 2, -40, -16}
	// };
	// static int[][] PSQ_TABLE_KING_EG = {
	//     {-76, -51, -27, -3, -33, -23, -60, -82},
	//     {-31, -9, 6, 24, 23, 9, -15, -33},
	//     {-28, -5, 11, 37, 40, 25, 7, -9},
	//     {-32, -9, 28, 42, 52, 44, 23, 2},
	//     {-7, 1, 30, 35, 48, 45, 29, 22},
	//     {-13, 5, 13, 19, 25, 39, 43, 14},
	//     {-39, 3, -4, -14, 9, 17, 59, 35},
	//     {-46, -48, -70, -23, 16, -2, 19, -40}
	// };
}
