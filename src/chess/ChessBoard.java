package chess;

/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the ChessBoard its movements along with printing the board.
 */
public class ChessBoard {

	Cell[][] _board;
	private int _height, _width;
	ChessPiece _blackKing, _whiteKing;

	public ChessBoard(int height, int width, boolean defaultBoard) {
		_board = new Cell[height][width];
		_height = height;
		_width = width;
		if (defaultBoard) {
			createDefaultBoard();
		}
		else {
			createSpecialBoard();
		}

	}

	public ChessBoard(Cell[][] board, Position blackKing, Position whiteKing) {
		_board = board;
		_height = board.length;
		_width = board[0].length;
		deepCopyBoard(board, _board);
		int blkRow = blackKing.getRow();
		int blkColumn = blackKing.getColumn();
		int whtRow = whiteKing.getRow();
		int whtColumn = whiteKing.getColumn();
		_blackKing = _board[blkRow][blkColumn].getPiece();
		_whiteKing = _board[whtRow][whtColumn].getPiece();

	}

	public ChessBoard(ChessBoard other) {
		_height = other._height;
		_width = other._width;
		_board = new Cell[_height][_width];
		deepCopyBoard(other._board, _board);
		_blackKing = new King((King)other._blackKing);
		_whiteKing = new King((King)other._whiteKing);
	}
	
	private void deepCopyBoard(Cell[][] fromBoard, Cell[][] toBoard) {
		for (int row = 0; row < fromBoard.length; row++){
			for (int column = 0; column < fromBoard[0].length; column++){
				toBoard[row][column] = new Cell(fromBoard[row][column]);
			}
		}

	}

	public void setPiece(Position toBeOccupied, ChessPiece piece) {
		int row = toBeOccupied.getRow();
		int column = toBeOccupied.getColumn();
		_board[row][column].setPiece(piece);
	}

	public int getHeight(){
		return _height;
	}

	public int getWidth(){
		return _height;
	}

	public ChessPiece getPieceCopy(Position at) {
		if (at == null) {
			return null;
		}
		int row = at.getRow();
		int column = at.getColumn();
		if (_board[row][column].getPiece() == null) {
			return null;
		}
		return _board[row][column].getPiece().getANewCopy();
	}

	public boolean[][] getOccupiedMatrix() {
		boolean[][] occupiedMatrix = new boolean[_height][_width];
		for (int row = 0; row < _height; row++) {
			for (int column = 0; column < _width; column++) {
				occupiedMatrix[row][column] = _board[row][column].isOccupied();
			}
		}
		return occupiedMatrix;
	}

	/**
	 * This method returned all the possible moves for the piece at the from position
	 * @param from The position of interest
	 * @return 
	 */
	public Position[] getPossibleMovesForPiece(Position from) {
		int fromRow = from.getRow();
		int fromColumn = from.getColumn();
		return _board[fromRow][fromColumn].getPossibleMovesForPiece(getOccupiedMatrix());

	}

	public boolean canMoveMyPieceTo(Position from, Position to) {
		int fromRow = from.getRow();
		int fromColumn = from.getColumn();
		int toRow = to.getRow();
		int toColumn = to.getColumn();
		return _board[fromRow][fromColumn].canMoveMyPieceTo(_board[toRow][toColumn], getOccupiedMatrix());
	}

	public boolean moveMyPieceTo(Position from, Position to, boolean reverseMove) {
		if (from == null || to == null) {
			return false;
		}
		int fromRow = from.getRow();
		int fromColumn = from.getColumn();
		int toRow = to.getRow();
		int toColumn = to.getColumn();

		return _board[fromRow][fromColumn].moveMyPieceTo( _board[toRow][toColumn], getOccupiedMatrix(), reverseMove);

	}
	public Position[] getMovementPath(Position source, Position target) {
		if (source == null || target == null) {
			return null;
		}
		int fromRow = source.getRow();
		int fromColumn = source.getColumn();
		return _board[fromRow][fromColumn].getMovementPath(target);
	}
	public boolean isBlackPiece(Position from) {
		int fromRow = from.getRow();
		int fromColumn = from.getColumn();
		return _board[fromRow][fromColumn].isBlackPiece();
	}

	public Position getBlackKing() {
		return _blackKing.getLocation();
	}

	public Position getWhiteKing() {
		return _whiteKing.getLocation();
	}

	/**
	 * 
	 * @param location
	 * @return
	 */
	public boolean isOccupied(Position location) {
		int row = location.getRow();
		int column = location.getColumn();
		return _board[row][column].isOccupied();
	}

	/**
	 * This method creates a new copy of _board and returns it back
	 * it does so because the caller might change the _board, which is
	 * not desirable.
	 * @return a new 2-d array of Cells the same as _board
	 */
	public Cell[][] getBoard() {
		Cell[][] newBoard = new Cell[_height][_width];
		for (int row = 0; row < _height; row++) {
			for (int column = 0; column < _width; column++) {
				newBoard[row][column] = new Cell(_board[row][column]);
			}
		}
		return newBoard;
	}

	private void createDefaultBoard(){
		fillWithRooks();
		fillWithBishops();
		fillBoardWithOhterPieces();
		fillBoardEmptyPieces();
	}

	private void createSpecialBoard(){
		fillWithEmpresses();
		fillWithPrincesses();
		fillBoardWithOhterPieces();
		fillBoardEmptyPieces();
	}

	private void fillBoardWithOhterPieces() {
		fillWithKnights();
		fillWithQueens();
		fillWithKings();
		fillWithPawns();
	}

	private void fillWithRooks() {
		boolean isBlackPiece = true;
		Position[] rookPositions = {new Position(0,0), new Position(7,0), new Position(0,7), new Position(7,7)};
		ChessPiece[] rooks = new ChessPiece[rookPositions.length];

		for (int arrayIndex = 0; arrayIndex < rookPositions.length; arrayIndex++){
			rooks[arrayIndex] = new Rook(rookPositions[arrayIndex], isBlackPiece);
			isBlackPiece = !isBlackPiece;
		}
		fillBoardWithPieces(rooks, rookPositions);
	}

	private void fillWithKnights() {
		boolean isBlackPiece = true;
		Position[] knightPositions = {new Position(0,1), new Position(7,1), new Position(0,6), new Position(7,6)};
		ChessPiece[] knights = new ChessPiece[knightPositions.length];

		for (int arrayIndex = 0; arrayIndex < knightPositions.length; arrayIndex++){
			knights[arrayIndex] = new Knight(knightPositions[arrayIndex], isBlackPiece);
			isBlackPiece = !isBlackPiece;
		}
		fillBoardWithPieces(knights, knightPositions);
	}

	private void fillWithEmpresses() {
		boolean isBlackPiece = true;
		Position[] empressPositions = {new Position(0,0), new Position(7,0), new Position(0,7), new Position(7,7)};
		ChessPiece[] empresses = new ChessPiece[empressPositions.length];

		for (int arrayIndex = 0; arrayIndex < empressPositions.length; arrayIndex++){
			empresses[arrayIndex] = new Empress(empressPositions[arrayIndex], isBlackPiece);
			isBlackPiece = !isBlackPiece;
		}
		fillBoardWithPieces(empresses, empressPositions);
	}

	private void fillWithPrincesses() {
		boolean isBlackPiece = true;
		Position[] princessPositions = {new Position(0,2), new Position(7,2), new Position(0,5), new Position(7,5)};
		ChessPiece[] princess = new ChessPiece[princessPositions.length];

		for (int arrayIndex = 0; arrayIndex < princessPositions.length; arrayIndex++){
			princess[arrayIndex] = new Princess(princessPositions[arrayIndex], isBlackPiece);
			isBlackPiece = !isBlackPiece;
		}
		fillBoardWithPieces(princess, princessPositions);
	}

	private void fillWithBishops() {
		boolean isBlackPiece = true;
		Position[] bishopPositions = {new Position(0,2), new Position(7,2), new Position(0,5), new Position(7,5)};
		ChessPiece[] bishops = new ChessPiece[bishopPositions.length];

		for (int arrayIndex = 0; arrayIndex < bishopPositions.length; arrayIndex++){
			bishops[arrayIndex] = new Bishop(bishopPositions[arrayIndex], isBlackPiece);
			isBlackPiece = !isBlackPiece;
		}
		fillBoardWithPieces(bishops, bishopPositions);
	}

	private void fillWithQueens() {
		boolean isBlackPiece = true;
		Position[] queenPositions = {new Position(0,3), new Position(7,3)};
		ChessPiece[] queens = new ChessPiece[queenPositions.length];

		for (int arrayIndex = 0; arrayIndex < queenPositions.length; arrayIndex++){
			queens[arrayIndex] = new Queen(queenPositions[arrayIndex], isBlackPiece);
			isBlackPiece = !isBlackPiece;
		}
		fillBoardWithPieces(queens, queenPositions);
	}

	private void fillWithKings() {
		boolean isBlackPiece = true;
		Position[] kingPositions = {new Position(0,4), new Position(7,4)};
		ChessPiece[] kings = new ChessPiece[kingPositions.length];

		for (int arrayIndex = 0; arrayIndex < kingPositions.length; arrayIndex++){
			kings[arrayIndex] = new King(kingPositions[arrayIndex], isBlackPiece);
			if (isBlackPiece) {
				_blackKing = kings[arrayIndex];
			}
			else {
				_whiteKing = kings[arrayIndex];
			}
			isBlackPiece = !isBlackPiece;
		}
		fillBoardWithPieces(kings, kingPositions);
	}

	private void fillWithPawns() {
		boolean isBlackPiece = true;
		Position[] pawnPositions = new Position[16]; 
		int rowsArray[] = {1, 6};
		int arrayIndex = 0;

		for (int column = 0; column < pawnPositions.length/2; column++){
			for(int rowsIndex = 0; rowsIndex < rowsArray.length; rowsIndex++)
			{
				pawnPositions[arrayIndex++] = new Position(rowsArray[rowsIndex], column);
			}
		}
		ChessPiece[] pawns = new ChessPiece[pawnPositions.length];
		for (arrayIndex = 0; arrayIndex < pawnPositions.length; arrayIndex++){
			pawns[arrayIndex] = new Pawn(pawnPositions[arrayIndex], isBlackPiece);
			isBlackPiece = !isBlackPiece;
		}
		fillBoardWithPieces(pawns, pawnPositions);
	}

	private void fillBoardWithPieces(ChessPiece[] piecesArray, Position[] piecesPositions){

		boolean isBlackPiece = true;
		for(int arrayIndex = 0; arrayIndex < piecesPositions.length; arrayIndex++){
			int row = piecesPositions[arrayIndex].getRow();
			int column = piecesPositions[arrayIndex].getColumn();

			_board[row][column] = new Cell(piecesArray[arrayIndex], new Position(row, column));
			isBlackPiece = !isBlackPiece;
		}	
	}
	private void fillBoardEmptyPieces(){
		for (int row  = 2; row < _height-2; row++) {
			for (int column = 0; column < _width; column++) {
				_board[row][column] = new Cell(new Position(row, column));
			}
		}
	}

	/**
	 * This method print the _board like this 
	 * 
	 * 
	 *            A         B         C         D         E         F         G         H   
	 *      ----------------------------------------------------------------------------------
	 *      8 | ROOK   || KNIGHT || BISHOP || QUEEN  || KING   || BISHOP || KNIGHT || ROOK   |
	 *      ----------------------------------------------------------------------------------
	 *      7 | PAWN   || PAWN   || PAWN   || PAWN   || PAWN   || PAWN   || PAWN   || PAWN   |
	 *      ----------------------------------------------------------------------------------
	 *      6 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      5 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      4 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      3 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      2 | pawn   || pawn   || pawn   || pawn   || pawn   || pawn   || pawn   || pawn   |
	 *      ----------------------------------------------------------------------------------
	 *      1 | rook   || knight || bishop || queen  || king   || bishop || knight || rook   |
	 *      ----------------------------------------------------------------------------------
	 *      
	 */
	public void printBoard() {
		char colNumber = 'A';
		System.out.println("  ");
		for (int count = 0; count < _width; count++) {
			System.out.print("      " + (colNumber++) + "   ");
		}
		System.out.println("");
		for (int count = 0; count < _width*10+2; count++) {
			System.out.print("-");
		}		
		System.out.println("");
		for (int row = 0; row < _height; row++) {
			System.out.print(_height - row + " ");
			for (int column = 0; column < _width; column++) {
				ChessPiece currentPiece = _board[row][column].getPiece();
				if (currentPiece != null) {
					String buffer = "|"+currentPiece.toString();
					int size = buffer.length();
					for (int i = 0; i < 9-size; i++) {
						buffer += " ";
					}
					System.out.print(buffer+"|");
				}
				else {
					System.out.print("|        |");
				}
			}
			System.out.println("");
			for (int count = 0; count < _width*10+2; count ++) {
				System.out.print("-");
			}
			System.out.println("");
		}
	}

	@Override
	public boolean equals(Object other){
		if (other instanceof ChessBoard) {
			ChessBoard chessBoardOther = (ChessBoard) other;
			boolean sameHeight =  _height == chessBoardOther._height;
			boolean sameWidth = _width == chessBoardOther._width;
			boolean sameBlackKing = _blackKing.equals(chessBoardOther._blackKing);
			boolean sameWhiteKing = _whiteKing.equals(chessBoardOther._whiteKing);
			if (!sameHeight || !sameWidth || !sameBlackKing || !sameWhiteKing) {
				return false;
			}

			for (int row = 0; row < _height; row++){
				for (int column = 0; column < _width; column++){
					boolean sameCell = _board[row][column].equals(chessBoardOther._board[row][column]);
					if (!sameCell){
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	public void freeReversePieces() {
		for (int row = 0; row < _height; row++){
			for (int column = 0; column < _width; column++){
				_board[row][column].freeReversePiece();
			}
		}
	}
}
