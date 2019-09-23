package testCases;

import static org.junit.Assert.*;
import chess.*;

import org.junit.Test;

public class PrincessClassTests {

	@Test
	public void testValidMoves() {
		
		ChessGame game = TestCasesUtility.createChessGameWithSpecialBoard1();
		Position[] validMoves = game.getValidMoves(new Position(0,0));
		assertEquals("Princess should have moves", validMoves.length, 9) ;
	}
	
	@Test
	public void testPrincessMoves() {
		Cell[][] tempBoard = TestCasesUtility.createEmptyBoard(8,8);
		ChessBoard board = new ChessBoard(tempBoard, new Position(0,0), new Position(0,0)); 

		//moving to empty cell bishop style (from (4,4) to 7,7))
		Position target = new Position(7, 7);
		Position source = new Position(4, 4);
		board.setPiece(source, new Princess(source, true));
		boolean moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
	
		//moving to empty cell knight style (from (7,7) to (6,5))
		target = new Position(6, 5);
		source = new Position(7, 7);
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//capturing another piece bishop style (from (6,5) to (3,2))
		target = new Position(3, 2);
		source = new Position(6, 5);
		board.setPiece(target, new Princess(target, false));
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//capturing another piece knight style (from (3,2) to (4,4))
		target = new Position(4, 4);
		source = new Position(3, 2);
		board.setPiece(target, new Princess(target, false));
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//Trying invalid directions
		
		//Out of boarder
		ChessGame game = TestCasesUtility.createChessGameWithSpecialBoard1();
		target = new Position(-1, 7);
		source = new Position(0, 2);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		target = new Position(2, 2);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//Cannot kill you piece
		target = new Position(0, 1);
		moved = game.movePiece(source, target);
		assertEquals("You should not capture your piece", moved, false);
		
		//Cannot move to your own position
		target = new Position(0,2);
		moved = game.movePiece(source, target);
		assertEquals("You should not have moved to where you are", moved, false);
		
		//Cannot capture your own piece
		target = new Position(1, 1);
		board.setPiece(target, new Princess(target, true));
		target = new Position(1, 1);
		source = new Position(4, 4);
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("You should not capture your piece", moved, false);
		
	}
	
	@Test
	public void testIsValidMoveMethod() {
		ChessGame game = TestCasesUtility.createChessGameWithSpecialBoard1();
		Cell[][] boardOfCells = game.getBoard();
		ChessPiece princess = new Princess((Princess)boardOfCells[0][2].getPiece());
		boolean valid = princess.isValidMove(boardOfCells[0][2], null);
		assertEquals("You should not capture your piece", valid, false);
	}

}
