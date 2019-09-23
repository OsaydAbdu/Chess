package testCases;

import chess.*;
import static org.junit.Assert.*;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.junit.Test;

@RunWith(Suite.class)
@Suite.SuiteClasses({CellClassTests.class, KingClassTests.class, QueenClassTests.class, BishopClassTests.class, 
	KnightClassTests.class, RookClassTests.class, PawnClassTests.class, ChessPieceClassTests.class, ChessBoardTests.class})
public class RunAllTests extends TestSuite {


}

