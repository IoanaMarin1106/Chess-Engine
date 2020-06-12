			PA Project - Chess Engine

TEAM Creierul si cerebelii, consisting of:

	ghiculescualexandru
	IoanaMarin1106
	roxanastiuca

COMPILING INSTRUCTIONS:

	make build -> create .class objects
	make run -> run executable
	java Game -> argument for Xboard Long engine->Engine command
	xboard -debug -fcp "java Game" -> run Xboard directly with our engine

PROJECT STRUCTURE:

	Game is the main class of the project which consists of several input
	and output processing methods. The input is read line by line, and,
	depending on the first word read, it calls a certain method for that
	command.
	
	Bitboard is our engine's represention of the chessboard. Bitboards are
	efficient structures for keeping track of the positions of the pieces.
	Within it, there is one bitmap for each piece-type and color, held inside
	2 arrays, whitePieces and blackPieces. In Bitboard, there are methods
	for generating moves (including special moves such as pawn promotion,
	en passant and castling).
	
	Move is a class with static methods, used mostly for checking the
	input format for a move command and for converting it into bitmaps, as
	well as for its reverse conversion.

	Piece is an abstract class, inherited by all types of pieces in a chess
	game. Within it, there are 2 enums for each color and each type a piece
	may have. The method generateMoves calls the correct method based on the
	type of the piece.

	King inherits Piece and is used to generate a King's moves and its
	attack map.

	Queen inherits Piece and is used to generate a Queen's moves and its
	attack map. Seeing as a Queen piece moves as both a Bishop and a Rook,
	the Queen's methods call the methods of these other 2 pieces.

	Rook inherits Piece and is used to generate all Rooks' moves and their
	attack map.

	Bishop inherits Piece and is used to generate all Bishops' moves and their
	attack map.

	Knight inherits Piece and is used to generate all Knights' moves and their
	attack map.

	Pawn inherits Piece and and is used to generate all Pawns' moves and their
	attack map.

	Negamax is a class used for the negamax algorithm with alpha-beta prunning.
	It generates the moves' tree and returns the best one.

	Evaluation is a class used to evaluate a chess board's state based on
	2 heuristic functions: material value of pieces and table value.

	Magic is a class used to generate rays for all board positions and in
	all directions (N, S, E, W, SE, SW, NE, NW).

	Debug is a class used only for debugging and, while not necessary for
	running the project, we decided to leave it in the project seeing as
	we will use it for further stages (mainly the displayBoard method).

ALGORITHM DESIGN:

	Negamax with alpha-beta prunning
	-> in Negamax.java;
	-> basic implementation;
	-> We used a negamaxHandler that returns the best move as opposed to
	the negamax method which returns the score of that board's state.

	Board's evaluation
	-> materialValue: heuristic function which evaluates a board by the number
	of pieces remaining and their type (some pieces are more valuable than
	others);
	-> tableValue: heuristic function which evaluates a board by the position
	of each piece (eg. pawns are more valuable the more advanced they are
	in ranks).
	-> mobilityValue: heuristic function which evaluates a board by the number
	of possible moves. UNUSED because it proved to be too expensive.

	Magic bitboards
	-> For efficiency, we used bitboard representation for the pieces on the
	chessboard; Bitboards are finite sets of 64 bits, each bit for one square
	of the chessboard.
	-> We used Magic Bitboard (the classical approach) to generate moves for
	sliding pieces (the pieces that can move an indefinite number of squares
	along a column/line/diagonal). In order to do that, when our engine is
	loaded, we generate all rays (in Magic.java) for all positions on the
	chess board and for all directions. The rays are long number where the
	only bits set are the ones in the direction given starting from the
	index of the position on board.
	-> Using rays, we can generate sliding piece moves very fast (without
	any loops), the most expensive operation being bitscan_forward
	(Long.numberOfTrailingZeros) or bitscan_reverse (Long.numberOfLeadingZeros).

	Transpositions:
	-> UNUSED.
	-> We tried to add memoization to our negamax algorithm, but it is not
	yet working properly and it may be something we include in further stages
	of the project.
	-> The idea behind it is that in the negamax tree a certain board is
	evaluated multiple times (thorugh different moves we can end up with
	the same board).
	-> We used a LinkedHashMap to store a board's state and its score and
	the depth for which it was calculated. The map had a maximum capacity and
	it removed the oldest entry when it exceeded that.

ONLINE RESOURCES:

	Negamax:
	-> https://ocw.cs.pub.ro/courses/pa/laboratoare/laborator-06

	Board evaluation:
	-> https://en.wikipedia.org/wiki/Evaluation_function
	-> https://en.wikipedia.org/wiki/Evaluation_function

	Magic Bitboards:
	-> https://www.chessprogramming.org/Bitboards
	-> https://www.chessprogramming.org/Magic_Bitboards
	-> https://rhysre.net/2019/01/15/magic-bitboards.html

	Transpositions:
	-> https://en.wikipedia.org/wiki/Transposition_table
	-> https://en.wikipedia.org/wiki/Zobrist_hashing
