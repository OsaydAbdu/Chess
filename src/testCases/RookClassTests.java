package testCases;

import static org.junit.Assert.*;
import chess.*;

import org.junit.Test;

public class RookClassTests {

	@Test
	public void testValidMoves() {
		ChessGame game = TestCasesUtility.createChessGameWithBoard1();
		Position[] validMoves = game.getValidMoves(new Position(0,0));
		assertEquals("Rook should have moves", validMoves.length, 7) ;
	}
	
	@Test
	public void testRookMoves() {
		Cell[][] tempBoard = TestCasesUtility.createEmptyBoard(8,8);
		ChessBoard board = new ChessBoard(tempBoard, new Position(0,0), new Position(0,0)); 
		int fromRow = 4; int fromColumn = 4;
		boolean[][] occupiedMatrix = new boolean[8][8];
		occupiedMatrix[4][4] = true;
		//moving to empty cell
		int toRow = 4, toColumn = 7;
		Position target = new Position(toRow, toColumn);
		Position source = new Position(fromRow, fromColumn);
		board.setPiece(source, new Rook(source, true));
		boolean moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
	
		//capturing another piece
		toRow = 4; toColumn = 7;
		target = new Position(toRow, toColumn);
		source = new Position(fromRow, fromColumn);
		board.setPiece(source, new Rook(source, false));
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//Trying invalid directions
		ChessGame game = TestCasesUtility.createChessGameWithBoard1();
		target = new Position(-1, 7);
		source = new Position(0, 0);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		target = new Position(2, 2);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//Cannot kill you piece
		target = new Position(0, 1);
		moved = game.movePiece(source, target);
		assertEquals("You should not capture your piece", moved, false);
		target = new Position(0,0);
		moved = game.movePiece(source, target);
		assertEquals("You should not have moved to where you are", moved, false);
		
		source = new Position(4, 7);
		target = new Position(3, 7);
		board.setPiece(target, new Rook(target, false));
		target = new Position(1, 7);
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("You should not capture your piece", moved, false);

	}
	
	@Test
	public void testIsValidMoveMethod() {
		ChessGame game = TestCasesUtility.createChessGameWithBoard1();
		Cell[][] boardOfCells = game.getBoard();
		ChessPiece rook = new Rook((Rook)boardOfCells[0][0].getPiece());
		boolean valid = rook.isValidMove(boardOfCells[0][0], null);
		assertEquals("You should not capture your piece", valid, false);
	}


}
