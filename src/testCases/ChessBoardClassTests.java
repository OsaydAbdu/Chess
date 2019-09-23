package testCases;

import static org.junit.Assert.*;

import org.junit.Test;
import chess.*;

public class ChessBoardClassTests {

	@Test
	public void testDefaultBoard() {
		ChessBoard boardDefault = new ChessBoard(8, 8, true);
		assertEquals("Should both be the same",boardDefault.equals(TestCasesUtility.createDefaultChessBoard()), true);	
	}
	
	@Test
	public void testSpecialBoard() {
		ChessBoard boardDefault = new ChessBoard(8, 8, false);
		assertEquals("Should both be the same", boardDefault.equals(TestCasesUtility.createSpecialBoard()), true);	
	}

}
