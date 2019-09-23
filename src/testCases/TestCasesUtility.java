package testCases;

import static org.junit.Assert.assertEquals;
import chess.*;

public class TestCasesUtility {
	public static void afterValidReverseMoving(Cell[][] board, boolean[][] occupiedMatrix, int fromRow, int fromColumn, int toRow,
			int toColumn, boolean moved) {
		assertEquals("Piece has not moved", moved, true);
		assertEquals("OccupiedMatrix has not changed after moving", occupiedMatrix[fromRow][fromColumn], true);
		assertEquals("OccupiedMatrix has not changed after moving", occupiedMatrix[toRow][toColumn], false);
		assertEquals("Board cell has not changed after moving", board[fromRow][fromColumn].isOccupied(), true);
		assertEquals("Board cell has not changed after moving", board[toRow][toColumn].isOccupied(), false);
	}

	public static void afterValidMoving(Cell[][] board, boolean[][] occupiedMatrix, int fromRow, int fromColumn, int toRow,
			int toColumn, boolean moved) {
		assertEquals("Piece has not moved", moved, true);
		assertEquals("OccupiedMatrix has not changed after moving", occupiedMatrix[fromRow][fromColumn], false);
		assertEquals("OccupiedMatrix has not changed after moving", occupiedMatrix[toRow][toColumn], true);
		assertEquals("Board cell has not changed after moving", board[fromRow][fromColumn].isOccupied(), false);
		assertEquals("Board cell has not changed after moving", board[toRow][toColumn].isOccupied(), true);
	}

	public static void afterInvalidMoveAttempt(Cell[][] board, boolean[][] occupiedMatrix, int fromRow, int fromColumn,
			int toRow, int toColumn, boolean moved, boolean toOccupied) {
		assertEquals("Piece has not moved", moved, false);
		assertEquals("OccupiedMatrix has not changed after moving", occupiedMatrix[fromRow][fromColumn], true);
		assertEquals("OccupiedMatrix has not changed after moving", occupiedMatrix[toRow][toColumn], toOccupied);
		assertEquals("Board cell has not changed after moving", board[fromRow][fromColumn].isOccupied(), true);
		assertEquals("Board cell has not changed after moving", board[toRow][toColumn].isOccupied(), toOccupied);
	}

	public static void sameBoards(Cell[][] expectedBoard, Cell[][] testBoard, boolean[][] occupaidMatrix) {
		for (int row = 0; row < 8; row++){
			for (int column = 0; column < 8; column++){
				Cell currentCell = expectedBoard[row][column];
				Cell currentTestCell = testBoard[row][column];
				assert (currentCell.equals(currentTestCell)) : "Cell at (" + row + ", " + column + ") is not correct\n";
				String errorMessage = "occupaid matrix is different from board cells at (" + row + ", " + column + ")";
				assertEquals(errorMessage, occupaidMatrix[row][column],testBoard[row][column].isOccupied());
			}
		}
	}

	public static ChessGame createDefaultChessGame() {
		ChessBoard boardObj = createDefaultChessBoard();
		return new ChessGame(boardObj);
	}

	/**
	 * This method returns a ChessBoard with a default board like this 
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
	 *  @return a ChessBoard object with the default board
	 */
	public static ChessBoard createDefaultChessBoard(){
		Cell[][] board = new Cell[8][8];
		for (int row = 0; row < 8; row++){
			for (int column = 0; column < 8; column++){
				board[row][column] = new Cell(new Position(row, column));
			}
		}
		final boolean black = true;
		final boolean white = false;
		final boolean occupied = true;

		changeCellContents(new Rook(new Position(0,0), black), occupied, board[0][0]);
		changeCellContents(new Rook(new Position(0,7), black), occupied, board[0][7]);
		changeCellContents(new Rook(new Position(7,0), white), occupied, board[7][0]);
		changeCellContents(new Rook(new Position(7,7), white), occupied, board[7][7]);

		changeCellContents(new Knight(new Position(0,1), black), occupied, board[0][1]);
		changeCellContents(new Knight(new Position(0,6), black), occupied, board[0][6]);
		changeCellContents(new Knight(new Position(7,1), white), occupied, board[7][1]);
		changeCellContents(new Knight(new Position(7,6), white), occupied, board[7][6]);

		changeCellContents(new Bishop(new Position(0,2), black), occupied, board[0][2]);
		changeCellContents(new Bishop(new Position(0,5), black), occupied, board[0][5]);
		changeCellContents(new Bishop(new Position(7,2), white), occupied, board[7][2]);
		changeCellContents(new Bishop(new Position(7,5), white), occupied, board[7][5]);

		changeCellContents(new Queen(new Position(0,3), black), occupied, board[0][3]);
		changeCellContents(new Queen(new Position(7,3), white), occupied, board[7][3]);

		changeCellContents(new King(new Position(0,4), black), occupied, board[0][4]);
		changeCellContents(new King(new Position(7,4), white), occupied, board[7][4]);

		for (int column = 0; column < 8; column++){
			changeCellContents(new Pawn(new Position(1,column), black), occupied, board[1][column]);
			changeCellContents(new Pawn(new Position(6,column), white), occupied, board[6][column]);
		}

		ChessBoard game = new ChessBoard(board, new Position(0, 4), new Position(7, 4));
		game.printBoard();
		return game;
	}

	public static ChessGame createChessGameWithBoard1() {
		ChessBoard boardObj = createBoard1();
		return new ChessGame(boardObj);
	}
	/**
	 * This method returns a ChessBoard object with a board like this 
	 * 
	 * 
	 *            A         B         C         D         E         F         G         H   
	 *      ----------------------------------------------------------------------------------
	 *      8 | ROOK   || KNIGHT || BISHOP || QUEEN  || KING   || BISHOP || KNIGHT || ROOK   |
	 *      ----------------------------------------------------------------------------------
	 *      7 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      6 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      5 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      4 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      3 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      2 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      1 | rook   || knight || bishop || queen  || king   || bishop || knight || rook   |
	 *      ----------------------------------------------------------------------------------
	 *      
	 *  @return a ChessBoard object with a board without pawns.
	 */
	public static ChessBoard createBoard1(){
		Cell[][] board = new Cell[8][8];
		for (int row = 0; row < 8; row++){
			for (int column = 0; column < 8; column++){
				board[row][column] = new Cell(new Position(row, column));
			}
		}
		final boolean black = true;
		final boolean white = false;
		final boolean occupied = true;

		changeCellContents(new Rook(new Position(0,0), black), occupied, board[0][0]);
		changeCellContents(new Rook(new Position(0,7), black), occupied, board[0][7]);
		changeCellContents(new Rook(new Position(7,0), white), occupied, board[7][0]);
		changeCellContents(new Rook(new Position(7,7), white), occupied, board[7][7]);

		changeCellContents(new Knight(new Position(0,1), black), occupied, board[0][1]);
		changeCellContents(new Knight(new Position(0,6), black), occupied, board[0][6]);
		changeCellContents(new Knight(new Position(7,1), white), occupied, board[7][1]);
		changeCellContents(new Knight(new Position(7,6), white), occupied, board[7][6]);

		changeCellContents(new Bishop(new Position(0,2), black), occupied, board[0][2]);
		changeCellContents(new Bishop(new Position(0,5), black), occupied, board[0][5]);
		changeCellContents(new Bishop(new Position(7,2), white), occupied, board[7][2]);
		changeCellContents(new Bishop(new Position(7,5), white), occupied, board[7][5]);

		changeCellContents(new Queen(new Position(0,3), black), occupied, board[0][3]);
		changeCellContents(new Queen(new Position(7,3), white), occupied, board[7][3]);

		changeCellContents(new King(new Position(0,4), black), occupied, board[0][4]);
		changeCellContents(new King(new Position(7,4), white), occupied, board[7][4]);

		ChessBoard game = new ChessBoard(board, new Position(0, 4), new Position(7, 4));
		return game;
	}

	public static ChessGame createChessGameWithSpecialBoard1() {
		ChessBoard boardObj = createSpecialBoard1();
		return new ChessGame(boardObj);
	}

	/**
	 * This method returns a ChessBoard object with a board like this 
	 * 
	 * 
	 *            A         B         C         D         E         F         G         H   
	 *      ----------------------------------------------------------------------------------
	 *      8 | EMPRESS|| KNIGHT ||PRINCESS|| QUEEN  || KING   ||PRINCESS|| KNIGHT || EMPRESS|
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
	 *      1 | empress|| knight ||princess|| queen  || king   ||princess|| knight || empress|
	 *      ----------------------------------------------------------------------------------
	 *      
	 *  @return a ChessBoard object with a board without pawns.
	 */


	public static ChessBoard createSpecialBoard(){
		Cell[][] board = new Cell[8][8];
		for (int row = 0; row < 8; row++){
			for (int column = 0; column < 8; column++){
				board[row][column] = new Cell(new Position(row, column));
			}
		}
		final boolean black = true;
		final boolean white = false;
		final boolean occupied = true;

		changeCellContents(new Empress(new Position(0,0), black), occupied, board[0][0]);
		changeCellContents(new Empress(new Position(0,7), black), occupied, board[0][7]);
		changeCellContents(new Empress(new Position(7,0), white), occupied, board[7][0]);
		changeCellContents(new Empress(new Position(7,7), white), occupied, board[7][7]);

		changeCellContents(new Knight(new Position(0,1), black), occupied, board[0][1]);
		changeCellContents(new Knight(new Position(0,6), black), occupied, board[0][6]);
		changeCellContents(new Knight(new Position(7,1), white), occupied, board[7][1]);
		changeCellContents(new Knight(new Position(7,6), white), occupied, board[7][6]);

		changeCellContents(new Princess(new Position(0,2), black), occupied, board[0][2]);
		changeCellContents(new Princess(new Position(0,5), black), occupied, board[0][5]);
		changeCellContents(new Princess(new Position(7,2), white), occupied, board[7][2]);
		changeCellContents(new Princess(new Position(7,5), white), occupied, board[7][5]);

		changeCellContents(new Queen(new Position(0,3), black), occupied, board[0][3]);
		changeCellContents(new Queen(new Position(7,3), white), occupied, board[7][3]);

		changeCellContents(new King(new Position(0,4), black), occupied, board[0][4]);
		changeCellContents(new King(new Position(7,4), white), occupied, board[7][4]);

		for (int column = 0; column < 8; column++){
			changeCellContents(new Pawn(new Position(1,column), black), occupied, board[1][column]);
			changeCellContents(new Pawn(new Position(6,column), white), occupied, board[6][column]);
		}

		ChessBoard game = new ChessBoard(board, new Position(0, 4), new Position(7, 4));
		return game;
	}

	/**
	 * This method returns a ChessBoard object with a board like this 
	 * 
	 * 
	 *            A         B         C         D         E         F         G         H   
	 *      ----------------------------------------------------------------------------------
	 *      8 | EMPRESS|| KNIGHT ||PRINCESS|| QUEEN  || KING   ||PRINCESS|| KNIGHT || EMPRESS|
	 *      ----------------------------------------------------------------------------------
	 *      7 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      6 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      5 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      4 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      3 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      2 |        ||        ||        ||        ||        ||        ||        ||        |
	 *      ----------------------------------------------------------------------------------
	 *      1 | empress|| knight ||princess|| queen  || king   ||princess|| knight || empress|
	 *      ----------------------------------------------------------------------------------
	 *      
	 *  @return a ChessBoard object with a board without pawns.
	 */
	public static ChessBoard createSpecialBoard1(){
		Cell[][] board = new Cell[8][8];
		for (int row = 0; row < 8; row++){
			for (int column = 0; column < 8; column++){
				board[row][column] = new Cell(new Position(row, column));
			}
		}
		final boolean black = true;
		final boolean white = false;
		final boolean occupied = true;

		changeCellContents(new Empress(new Position(0,0), black), occupied, board[0][0]);
		changeCellContents(new Empress(new Position(0,7), black), occupied, board[0][7]);
		changeCellContents(new Empress(new Position(7,0), white), occupied, board[7][0]);
		changeCellContents(new Empress(new Position(7,7), white), occupied, board[7][7]);

		changeCellContents(new Knight(new Position(0,1), black), occupied, board[0][1]);
		changeCellContents(new Knight(new Position(0,6), black), occupied, board[0][6]);
		changeCellContents(new Knight(new Position(7,1), white), occupied, board[7][1]);
		changeCellContents(new Knight(new Position(7,6), white), occupied, board[7][6]);

		changeCellContents(new Princess(new Position(0,2), black), occupied, board[0][2]);
		changeCellContents(new Princess(new Position(0,5), black), occupied, board[0][5]);
		changeCellContents(new Princess(new Position(7,2), white), occupied, board[7][2]);
		changeCellContents(new Princess(new Position(7,5), white), occupied, board[7][5]);

		changeCellContents(new Queen(new Position(0,3), black), occupied, board[0][3]);
		changeCellContents(new Queen(new Position(7,3), white), occupied, board[7][3]);

		changeCellContents(new King(new Position(0,4), black), occupied, board[0][4]);
		changeCellContents(new King(new Position(7,4), white), occupied, board[7][4]);

		ChessBoard game = new ChessBoard(board, new Position(0, 4), new Position(7, 4));
		return game;
	}



	public static void changeCellContents(ChessPiece newPiece, boolean occupied, Cell cellToBeChanged){
		cellToBeChanged.setOccupied(occupied);
		cellToBeChanged.setPiece(newPiece);
	}

	public static Cell[][] createEmptyBoard(int  height, int width) {
		Cell[][] board = new Cell[height][width];
		for (int row = 0; row < height; row++){
			for (int column = 0; column < width; column++){
				board[row][column] = new Cell(row, column);
			}
		}
		return board;
	}

	public static boolean[][] createOccupiedMatrix(int height, int width) {
		boolean[][] matrix = new boolean[height][width];
		for (int row = 0; row < height; row++){
			for (int column = 0; column < width; column++){
				matrix[row][column] = false;
			}
		}
		return matrix;
	}

	public static ChessGame createCheckmateGame() {
		Cell[][] board = new Cell[8][8];
		for (int row = 0; row < 8; row++){
			for (int column = 0; column < 8; column++){
				board[row][column] = new Cell(new Position(row, column));
			}
		}
		final boolean black = true;
		final boolean white = false;
		final boolean occupied = true;

		changeCellContents(new King(new Position(0,0), white), occupied, board[0][0]);
		changeCellContents(new King(new Position(0,2), black), occupied, board[0][2]);
		changeCellContents(new Rook(new Position(7,1), black), occupied, board[7][1]);
		ChessGame game = new ChessGame(board, new Position(0,2), new Position(0,0));
		return game;
	}

	public static ChessGame createStalemateGame() {
		Cell[][] board = new Cell[8][8];
		for (int row = 0; row < 8; row++){
			for (int column = 0; column < 8; column++){
				board[row][column] = new Cell(new Position(row, column));
			}
		}
		final boolean black = true;
		final boolean white = false;
		final boolean occupied = true;

		changeCellContents(new King(new Position(0,0), black), occupied, board[0][0]);
		changeCellContents(new King(new Position(1,2), white), occupied, board[1][2]);
		changeCellContents(new Queen(new Position(2,1), white), occupied, board[2][1]);
		ChessGame game = new ChessGame(board, new Position(0,0), new Position(1,2));
		return game;

	}
}
