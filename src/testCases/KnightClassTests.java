package testCases;

import static org.junit.Assert.*;

import org.junit.Test;
import chess.*;

public class KnightClassTests {


	@Test
	public void testValidMoves() {
		
		ChessGame game = TestCasesUtility.createChessGameWithBoard1();
		Position[] validMoves = game.getValidMoves(new Position(0,1));
		assertEquals("Knight should have moves", validMoves.length, 3) ;
	}
	
	@Test
	public void testKnightMoves() {
		Cell[][] tempBoard = TestCasesUtility.createEmptyBoard(8,8);
		ChessBoard board = new ChessBoard(tempBoard, new Position(0,0), new Position(0,0)); 
	
		//moving to empty cell knight style (from (4,4) to (5,6))
		Position target = new Position(5, 6);
		Position source = new Position(4, 4);
		board.setPiece(source, new Knight(source, true));
		boolean moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		
		//capturing another piece knight style (from (5,6) to (7,5))
		target = new Position(7, 5);
		source = new Position(5, 6);
		board.setPiece(target, new Knight(target, false));
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//Trying invalid directions
		
		//Out of boarder
		ChessGame game = TestCasesUtility.createDefaultChessGame();
		target = new Position(-1, 7);
		source = new Position(0, 1);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		target = new Position(2, 6);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//Cannot capture you piece
		target = new Position(1, 3);
		moved = game.movePiece(source, target);
		assertEquals("You should not capture your piece", moved, false);
		
		//Cannot move to your own position
		target = new Position(0,1);
		moved = game.movePiece(source, target);
		assertEquals("You should not have moved to where you are", moved, false);
		
	}
	
	@Test
	public void testIsValidMoveMethod() {
		ChessGame game = TestCasesUtility.createChessGameWithBoard1();
		Cell[][] boardOfCells = game.getBoard();
		ChessPiece knight = new Knight((Knight)boardOfCells[0][1].getPiece());
		boolean valid = knight.isValidMove(boardOfCells[0][1], null);
		assertEquals("You should not capture your piece", valid, false);
	}



}
