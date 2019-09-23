package testCases;

import static org.junit.Assert.*;

import org.junit.Test;
import chess.*;

public class EmpressClassTests {

	@Test
	public void testValidMoves() {
		
		ChessGame game = TestCasesUtility.createChessGameWithSpecialBoard1();
		Position[] validMoves = game.getValidMoves(new Position(0,0));
		assertEquals("Empress should have moves", validMoves.length, 9) ;
	}
	
	@Test
	public void testEmpressMoves() {
		Cell[][] tempBoard = TestCasesUtility.createEmptyBoard(8,8);
		ChessBoard board = new ChessBoard(tempBoard, new Position(0,0), new Position(0,0)); 

		//moving to empty cell rook style (from (4,4) to (4,7))
		Position target = new Position(4, 7);
		Position source = new Position(4, 4);
		board.setPiece(source, new Empress(source, true));
		boolean moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
	
		//moving to empty cell knight style (from (4,7) to (3,5))
		target = new Position(3, 5);
		source = new Position(4, 7);
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//capturing another piece rook style (from (3,5) to (3,7))
		target = new Position(3, 7);
		source = new Position(3, 5);
		board.setPiece(target, new Empress(target, false));
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//capturing another piece knight style (from (3,7) to (4,5))
		target = new Position(4, 5);
		source = new Position(3, 7);
		board.setPiece(target, new Empress(target, false));
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("This piece should have moved", moved, true);
		
		//Trying invalid directions
		
		//Out of boarder
		ChessGame game = TestCasesUtility.createChessGameWithSpecialBoard1();
		target = new Position(-1, 7);
		source = new Position(0, 0);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//out of style
		target = new Position(2, 2);
		moved = game.movePiece(source, target);
		assertEquals("This piece should not move", moved, false);
		
		//Cannot kill you piece
		target = new Position(0, 1);
		moved = game.movePiece(source, target);
		assertEquals("You should not capture your piece", moved, false);
		
		//Cannot move to your own position
		target = new Position(0,0);
		moved = game.movePiece(source, target);
		assertEquals("You should not have moved to where you are", moved, false);
		
		//Cannot capture your own piece
		target = new Position(3, 7);
		board.setPiece(target, new Empress(target, true));
		target = new Position(3, 7);
		source = new Position(4, 5);
		moved = board.moveMyPieceTo(source, target, false);
		assertEquals("You should not capture your piece", moved, false);
		
	}
	
	@Test
	public void testIsValidMoveMethod() {
		ChessGame game = TestCasesUtility.createChessGameWithSpecialBoard1();
		Cell[][] boardOfCells = game.getBoard();
		ChessPiece empress = new Empress((Empress)boardOfCells[0][0].getPiece());
		boolean valid = empress.isValidMove(boardOfCells[0][0], null);
		assertEquals("You should not capture your piece", valid, false);
	}

}
