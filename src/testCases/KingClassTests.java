package testCases;

import static org.junit.Assert.*;
import chess.*;
import org.junit.Test;

public class KingClassTests {

	@Test
	public void testValidMoves() {
		
		ChessGame game = TestCasesUtility.createChessGameWithBoard1();
		game.printBoard();
		Position[] validMoves = game.getValidMoves(new Position(0,4));
		assertEquals("King should have moves", validMoves.length, 2) ;
	}
	
	@Test
	public void testKingMoves() {
		Cell[][] tempBoard = TestCasesUtility.createEmptyBoard(8,8);
		ChessBoard board = new ChessBoard(tempBoard, new Position(0,0), new Position(0,0)); 

		//moving to empty cell King style (from (4,4) to (4,5))
		Position source = new Position(4, 4);
		Position target = new Position(4, 5);
		board.setPiece(source, new King(source, true));
		boolean moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
	
		//capturing another piece King style (from (4,5) to (5,6))
		source = new Position(4, 5);
		target = new Position(5, 6);
		board.setPiece(target, new King(target, false));
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
	
		
		//Trying invalid directions
		
		//Out of boarder
		ChessGame game = TestCasesUtility.createChessGameWithBoard1();
		source = new Position(-1, 5);
		target = new Position(0, 4);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//Out of style 1
		target = new Position(2, 2);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//Out of style 2
		target = new Position(2, 4);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//Cannot capture you piece
		target = new Position(0, 3);
		moved = game.movePiece(source, target);
		assertEquals("You should not capture your piece", moved, false);
		
		//Cannot move to your own position
		target = new Position(0,4);
		moved = game.movePiece(source, target);
		assertEquals("You should not have moved to where you are", moved, false);
		
	}
	
	@Test
	public void testIsValidMoveMethod() {
		ChessGame game = TestCasesUtility.createChessGameWithBoard1();
		Cell[][] boardOfCells = game.getBoard();
		ChessPiece king = new King((King)boardOfCells[0][4].getPiece());
		boolean valid = king.isValidMove(boardOfCells[0][4], null);
		assertEquals("You should not capture your piece", valid, false);
	}
}
