package chess;

import java.util.Stack;
import gameController.Move;
/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the ChessGame and it controls the allowed moves along with 
 * handling the end of game conditions.
 */
public class ChessGame {

	private ChessBoard _board;
	private int _height, _width;
	private boolean _blackTurn;
	private boolean _isGameDone;
	private Position[] _checkPath;
	private String _message = "";
	private Stack<Move> _previousMoves;

	public ChessGame(boolean defaultBoard){
		_isGameDone = false;
		_height = 8;
		_width = 8;
		_blackTurn = true;
		_board = new ChessBoard(_height, _width, defaultBoard);
		_checkPath = null;
		_previousMoves = new Stack<Move>();
	}

	public ChessGame(boolean defaultBoard, boolean blackFirst){
		_isGameDone = false;
		_height = 8;
		_width = 8;
		_blackTurn = blackFirst;
		_board = new ChessBoard(_height, _width, defaultBoard);
		_checkPath = null;
	}

	//This constructor is only for the testing proposes and it should not be used
	//because it does not have safety checks 
	public ChessGame(Cell[][] board, Position blackKing, Position whiteKing) {
		_board = new ChessBoard(board, blackKing, whiteKing);
		_height = board.length;
		_width = board[0].length;
		_blackTurn = true;
		_isGameDone = false;
		_checkPath = null;
	}
	public ChessGame(ChessBoard board) {
		_board = board;
		_height = _board.getHeight();
		_width = _board.getWidth();
		_blackTurn = true;
		_isGameDone = false;
		_checkPath = null;
	}

	public ChessGame(ChessGame other){
		_board = new ChessBoard(other._board);
		_height = other._height;
		_width = other._width;
		_blackTurn = other._blackTurn;
		_isGameDone = other._isGameDone;
		_checkPath = other._checkPath;
		_checkPath = null;
	}

	public int getHeight(){
		return _height;
	}

	public int getWidth(){
		return _height;
	}

	public String getMessage() {
		return _message;
	}
	/**
	 * This method produce the a new copy (being created inside the ChessBoard
	 * class) of the 2-d array of cells (namely the board).
	 * @return 2-d array of cells
	 */
	public Cell[][] getBoard(){
		return _board.getBoard();
	}

	/**
	 * This method produces a 2-d array of all the positions that are occupied
	 * being set to true and all the positions that are not occupied being false.
	 * @return a 2-d array
	 */
	public boolean[][] getOccupiedMatrix(){
		return 	_board.getOccupiedMatrix();
	}

	/**
	 * This method returns all the possible moves for the piece at fromPosition.
	 * This method does the filtering of out of bounds moves, and dangerous moves
	 * (causing your king being under check), and all the other conditions. So, 
	 * the return moves are all valid.
	 * @param fromPosition
	 * @return null if fromPosition empty or no valid moves from the fromLocation,
	 *         otherwise an array of all the possible valid Positions that the 
	 *         piece at fromPosition can move to.
	 */
	public Position[] getValidMoves(Position fromLocation) {
		if (!_board.isOccupied(fromLocation)) {
			return null;
		}
		boolean isBlackTurn = _board.isBlackPiece(fromLocation);

		Position[] possibleMoves = _board.getPossibleMovesForPiece(fromLocation);
		Position[] tempMoves = new Position[possibleMoves.length];
		int arrayIndex = 0;
		for (int i = 0; i < possibleMoves.length; i++) {
			Position toLocation = possibleMoves[i];
			//Checking in bound conditions
			boolean inBoard = isInBounds(toLocation, null);	

			if (inBoard && _board.canMoveMyPieceTo(fromLocation, toLocation)) {
				boolean reverseMove = false;
				_board.moveMyPieceTo(fromLocation, toLocation, reverseMove);
				if (!isKingUnderCheck(isBlackTurn)) {
					tempMoves[arrayIndex++] = possibleMoves[i];
				}
				reverseMove = true;
				_board.moveMyPieceTo(toLocation, fromLocation, reverseMove);
			}
		}
		if (arrayIndex == 0) {
			return null;
		}
		Position[] validMoves = new Position[arrayIndex];
		for (int i = 0; i < validMoves.length; i++) {
			validMoves[i] = tempMoves[i];
		}
		return validMoves;
	}

	/**
	 * This method moves the ChessPiece at fromPosition toPosition if 
	 * 1- The fromPosition and toPosition are within the board boarders.
	 * 2- It is the turn of the piece at fromPosition.
	 * 3- The move does not cause the player moving to be under check.
	 * 4- The fromPosition is occupied.
	 * 5- The toPosition is empty, or the toPosition is occupied with a 
	 *    piece of the other player.
	 * 6- There is no piece between the fromPosition and the toPosition 
	 *    interrupting (except when allowed).
	 * 7- The toPosition in valid style of movement (e.g. the same row 
	 *    or column if source is rook).
	 * After a valid move, the method checks whether the other player has
	 * lost the game (by being checkmated) or the game ended by a tie (by
	 * being stalemate for the other player).
	 * Notice: that only 1, 2, and 3 are enforced directly here, and the 
	 * other are enforced in the cell class moveMyPieceTo method.
	 * @param from a position to move from 
	 * @param to a target position to move to
	 * @return true if performed the move of the source piece, false otherwise.
	 */
	public boolean movePiece(Position from, Position to) {
		_message = "";
		if (_isGameDone) {
			_message = "This Game has finished, you cannot make any moves";
			return false;
		}
		//Checking in bound conditions
		if (!isInBounds(from, to)) {
			return false;
		}
		ChessPiece prevPiece = _board.getPieceCopy(to);

		if (_board.isBlackPiece(from) != _blackTurn) {
			_message = "It is not " + (_blackTurn ? "white" : "black") + " turn";
			return false;
		}

		boolean reverseMove = false;
		boolean validMove = _board.moveMyPieceTo(from, to, reverseMove);	
		if (validMove) {
			if (isKingUnderCheck(_blackTurn)) {
				_message = "You cannot move here beacause this move will make your king under check";
				reverseMove = true;
				_board.moveMyPieceTo(to, from, reverseMove);
				return false;
			}

			if (isCheckMateForPlayer(!_blackTurn)) {
				_message = (_blackTurn ? "black" : "white") + " has won the game";
				_isGameDone = true;
			}
			else if (isStalemateForPlayer(!_blackTurn)) {
				_message = "The game has ended with a draw";
				_isGameDone = true;
			}

			_previousMoves.add(new Move(from, to, prevPiece));
			_board.freeReversePieces();
			_blackTurn = !_blackTurn;
			return true;
		}
		_message = "Invalid move";
		return false;
	}

	/**
	 * return true if the other ChessGame object equals the current one
	 * @param other to be checked 
	 * @return true if the other ChessGame object equals the current one
	 */
	public boolean equals(ChessGame other){
		if (other instanceof ChessGame){
			boolean sameHeight = _height == other._height;
			boolean sameWidth = _width == other._width;
			boolean sameTurn =  _blackTurn ==  other._blackTurn;;
			boolean sameGameStatus = _isGameDone == other._isGameDone;
			boolean same = sameHeight && sameWidth && sameTurn && sameGameStatus;

			return same && _board.equals(other._board);
		}
		return false;
	}

	/**
	 * This method is used to check whether the king of the player with isBlackTurn 
	 * is under check or not.
	 * @param isBlackTurn 
	 * @return true if the player isBlackTurn is under check, false otherwise
	 */
	public boolean isKingUnderCheck(boolean isBlackTurn) {
		Position kingLocation = (isBlackTurn ? _board.getBlackKing() : _board.getWhiteKing());

		Position[] testLocations = Position.generateAllPositions(_height, _width);
		for (int i = 0; i < testLocations.length; i++) {
			boolean oppositeColor = _board.isBlackPiece(testLocations[i]) != isBlackTurn;
			if (oppositeColor && _board.canMoveMyPieceTo(testLocations[i], kingLocation)) {
				_checkPath = _board.getMovementPath(testLocations[i], kingLocation);
				return true;
			}
		}

		_checkPath = null;
		return false;
	}

	/**
	 * This method finds whether the player isBlackTurn lost the game or not by checking
	 *  these conditions:
	 *  1- The king is under check.
	 *  2- There is no piece that can prevent the check.
	 *  3- All the possible moves of the king will lead to a check.
	 * @param isBlackTurn defines the player to see whether he has a chekcmate or not
	 * @return true if isBlackTurn player  has lost the game, false otherwise.
	 */
	public boolean isCheckMateForPlayer(boolean isBlackTurn) {
		Position kingCurrLocation = isBlackTurn ? _board.getBlackKing() : _board.getWhiteKing();
		if (!isKingUnderCheck(isBlackTurn)) {
			return false;
		}
		_message = (isBlackTurn ? "black" : "white") + " is under check";
		Position[] checkPath = _checkPath;
		//--------------------------------------------------------------------------------
		//Checking if any friendly piece can interrupt the piece that is making the king
		//under check. And after that if the king becomes not under check return false.
		//otherwise, you should go to the next step.
		//--------------------------------------------------------------------------------
		Position[] allPositions = Position.generateAllPositions(_height, _width);
		for (int i = 0; i < checkPath.length; i++) {
			for (int positionsIndex = 0; positionsIndex < allPositions.length; positionsIndex++) {
				Position testLocation = allPositions[positionsIndex];
				boolean sameColor = _board.isBlackPiece(testLocation) == isBlackTurn;
				boolean kingsPosition = testLocation.equals(kingCurrLocation);
				if (!kingsPosition && sameColor && _board.canMoveMyPieceTo(testLocation, checkPath[i])) {
					boolean reverseMove = false;
					_board.moveMyPieceTo(testLocation, checkPath[i], reverseMove);
					reverseMove = true;
					if (!isKingUnderCheck(isBlackTurn)) {
						try {
							_board.moveMyPieceTo(checkPath[i], testLocation, reverseMove);
						}
						catch (java.lang.NullPointerException e){
							System.out.println(i);
						}
						return false;
					}
					_board.moveMyPieceTo(checkPath[i], testLocation, reverseMove);
				}
			}
		}

		//Generating the position that the king may move to
		Position[] possibleLocations = getValidMoves(kingCurrLocation);

		//If there is a place to go to then it is not a checkmate for isBlackTurn
		if (possibleLocations != null) {
			for (int index = 0; index < possibleLocations.length; index++) {
				boolean reverseMove = false;
				boolean moved = _board.moveMyPieceTo(kingCurrLocation, possibleLocations[index], reverseMove);
				if (moved && !isKingUnderCheck(isBlackTurn)) {
					reverseMove = true;
					_board.moveMyPieceTo(possibleLocations[index], kingCurrLocation, reverseMove);
					return false;
				}
				if (moved) {
					reverseMove = true;
					_board.moveMyPieceTo(possibleLocations[index], kingCurrLocation, reverseMove);
				}
			}
		}
		return true;
	}

	/**
	 * This method check all the friendly pieces and if all of them 
	 * have zero moves, then it return false (known as stalemate situation), 
	 * otherwise it returns true (that is if at least one of the friendly 
	 * pieces has at least one valid move the method returns true).
	 * @param isBlackTurn
	 * @return true if stalemate, false otherwise
	 */
	public boolean isStalemateForPlayer(boolean isBlackTurn) {
		Position[] allPositions = Position.generateAllPositions(_height, _width);
		for (int i = 0; i < allPositions.length; i++) {
			if (i == 54) {
				System.out.println("");
			}
			Position testLocation = allPositions[i];
			boolean sameColor = _board.isBlackPiece(testLocation) == isBlackTurn;
			if (sameColor && getValidMoves(testLocation) != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * print the board using the style specified in the _board class.
	 */
	public void printBoard() {
		_board.printBoard();
	}

	/**
	 * Checks if both of the two arguments are in bounds, if only one of 
	 * them is null then the method checks the other one, if both are null
	 * then the method returns false.
	 * @param from
	 * @param to
	 * @return true if both are in bounds (or one of them in case the 
	 * 		   other one was null).
	 */
	private boolean isInBounds(Position from, Position to) {
		if (from != null) {
			int fromRow = from.getRow();
			int fromColumn = from.getColumn();
			if (fromRow < 0 || fromRow >= _height || fromColumn < 0 || fromColumn >= _width) {
				//System.out.println("Source position is out of the board");
				return false;
			}
		}

		if (to != null) {
			int toRow = to.getRow();
			int toColumn = to.getColumn();
			if (toRow < 0 || toRow >= _height || toColumn < 0 || toColumn >= _width) {
				//System.out.println("Target position is out of the board");
				return false;
			}
		}
		if (from == null && to == null) {
			return false;
		}
		return true;
	}
	public Move undoMove() {
		if (_previousMoves.isEmpty()) {
			_message = "There is no previous move";
			return null;
		}
		Move prevMove = _previousMoves.pop();
		Position to = prevMove.getSource();
		Position from = prevMove.getTarget();
		ChessPiece prevPiece = prevMove.getDeletedPiece();
		_board.moveMyPieceTo(from, to, true);
		_board.setPiece(from, prevPiece);
		_blackTurn = !_blackTurn;
		_isGameDone = false;
		printBoard();
		return prevMove;
	}

	public boolean isBlackTurn() {
		return _blackTurn;
	}

}
