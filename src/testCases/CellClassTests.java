package testCases;

import static org.junit.Assert.*;
import chess.*;

import org.junit.Test;

public class CellClassTests {

	@Test
	public void coverageTest() {
		Position start = new Position(0,0);
		ChessPiece firstPiece = new Rook(start, false);
		Cell tempCell = new Cell(null, start);
		Cell wrongCell = new Cell(firstPiece, new Position(1,2));
		Cell rightCell = new Cell(firstPiece, start);
		
		rightCell = new Cell(start);
		tempCell = new Cell(1,1);
	}

}
