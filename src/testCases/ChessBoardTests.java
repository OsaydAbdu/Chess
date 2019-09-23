package testCases;

import static org.junit.Assert.*;
import chess.*;
import org.junit.Test;

public class ChessBoardTests {

	@Test
	public void testGameTurns() {
		ChessGame chessGame= new ChessGame(true);


		//Moving a black piece from (1,0) to (2, 0)
		Position source = new Position(1, 0);
		Position target = new Position(2, 0);
		boolean moved = chessGame.movePiece(source, target);
		assertEquals("This piece should have moved", moved, true);

		//Cannot move a black piece twice
		source = new Position(2, 0);
		target = new Position(3, 0);
		moved = chessGame.movePiece(source, target);
		assertEquals("Cannot have two moves for the same player", moved, false);

		//Moving a white piece from (6,0) to (4, 0)
		source = new Position(6, 0);
		target = new Position(4, 0);
		moved = chessGame.movePiece(source, target);
		assertEquals("This piece should have moved", moved, true);

		//Cannot move a white piece twice
		source = new Position(4, 0);
		target = new Position(3, 0);
		moved = chessGame.movePiece(source, target);
		assertEquals("Cannot have two moves for the same player", moved, false);
	}


	@Test
	public void testStalemate() {
		ChessGame game = TestCasesUtility.createStalemateGame();
		game.printBoard();
		Position[] locations = game.getValidMoves(new Position(0,0));
		assertEquals("There should be no move because it's stale mate and the game is draw", locations == null, true);
	}
	@Test
	public void testCheckMate() {
		ChessGame game = TestCasesUtility.createCheckmateGame();
		game.printBoard();
		boolean moved = game.movePiece(new Position(7, 1), new Position(7, 0));
		assertEquals("Rook should have moved", moved, true);
		game.printBoard();
		Position[] locations = game.getValidMoves(new Position(0,0));
		assertEquals("There should be no move because the white player lost", locations == null, true);
		
		//Trying to move
		Position source = new Position(0, 0);
		Position target = new Position(1, 0);
		moved = game.movePiece(source, target);
		assertEquals("Cannot move because the game has ended", moved, false);


	}




}