package testCases;

import static org.junit.Assert.*;
import chess.*;

import org.junit.Test;

public class PawnClassTests {

	@Test
	public void testValidMoves() {
		
		ChessGame game = TestCasesUtility.createDefaultChessGame();
		Position[] validMoves = game.getValidMoves(new Position(1,0));
		assertEquals("Pawn should have moves", validMoves.length, 2) ;
	}
	
	@Test
	public void testPawnMoves() {
		Cell[][] tempBoard = TestCasesUtility.createEmptyBoard(8,8);
		ChessBoard board = new ChessBoard(tempBoard, new Position(0,0), new Position(0,0)); 

		//moving to empty cell pawn style (from (4,4) to (5,4))
		Position target = new Position(5, 4);
		Position source = new Position(4, 4);
		board.setPiece(source, new Pawn(source, true));
		boolean moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
	
		
		//capturing another piece pawn style (from (5,4) to (6,5))
		target = new Position(6, 5);
		source = new Position(5, 4);
		board.setPiece(target, new Pawn(target, false));
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//Move twice  at the first time 
		ChessGame game0 = TestCasesUtility.createDefaultChessGame();
		source = new Position(1,0);
		target = new Position(3,0);
		moved = game0.movePiece(source, target);
		assertEquals("You should be able to move two steps at the first turn", moved, true);
		
		//Trying invalid directions
		
		//Out of boarder
		ChessGame game = TestCasesUtility.createDefaultChessGame();
		target = new Position(-1, 7);
		source = new Position(1, 0);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//out of style
		target = new Position(2, 2);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		
		//Cannot move to your own position
		target = new Position(1,0);
		moved = game.movePiece(source, target);
		assertEquals("You should not have moved to where you are", moved, false);
		
		//Cannot capture your own piece
		target = new Position(7, 4);
		board.setPiece(target, new Pawn(target, true));
		target = new Position(7, 4);
		source = new Position(6, 5);
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("You should not capture your piece", moved, false);
		
		//Cannot move twice  after the first time 
		source = new Position(1,0);
		target = new Position(2,0);
		moved = game.movePiece(source, target);
		assertEquals("You should not have moved to where you are", moved, true);
		target = new Position(4,0);
		source = new Position(2,0);
		moved = game.movePiece(source, target);
		assertEquals("You cannot move two steps after the first move", moved, false);
		
	}
	
	@Test
	public void testIsValidMoveMethod() {
		ChessGame game = TestCasesUtility.createDefaultChessGame();
		Cell[][] boardOfCells = game.getBoard();
		ChessPiece pawn = new Pawn((Pawn)boardOfCells[1][0].getPiece());
		boolean valid = pawn.isValidMove(boardOfCells[1][0], null);
		assertEquals("You should not capture your piece", valid, false);
	}
	

}
