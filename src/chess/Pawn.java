package chess;

/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the Pawn piece in the chess game which inherits from the ChessPiece class.
 */
public class Pawn extends ChessPiece{

	boolean _firstMove;

	public Pawn(Position location, boolean blackPiece) {
		super(location, blackPiece);
		_firstMove = true;
	}

	public Pawn(Pawn other){
		super(other);
		_firstMove = true;
	}

	@Override
	public ChessPiece getANewCopy(){
		Pawn newPawn = new Pawn(getLocation(), isBlackPiece());
		return newPawn;
	}

	/**
	 * This method should only be called after checking that the current piece
	 * can move to the target, otherwise the behavior is undefined. 
	 * Notice: Not validity checking is happening here for speed.
	 * @param target position this piece can go to
	 * @return the path of all the Position between the current location 
	 * and the target (include the current location but exclude the target).
	 */
	@Override
	public Position[] getMovementPath(Position target) {
		Position[] path = new Position[1];
		path[0] = getLocation();
		return path;
	}

	/**
	 * This method evaluates whether the target cell's position would be 
	 * a valid move for the current piece based on these conditions:
	 * 1- The movement style is a valid movement style (either going one cell
	 * 	  forward when there is no piece ahead or going diagonally forward to 
	 * 	  capture an opponent piece).
	 * This method assumes that it is called after checking that the
	 * target cell is valid.
	 * @param targetCell which the current piece wants to move to.
	 * @param occupiedMatrix defines which cells are occupied and which are not.
	 * @return true if the current piece can move to the target cell, false otherwise
	 */
	@Override
	public boolean isValidMove(Cell targetCell, boolean[][] occupiedMatrix) {
		Position targetPosition = targetCell.getLocation();
		Position currentLocation = getLocation();
		int verticalDifference = currentLocation.rowDifference(targetPosition);
		int horizontalDifference = currentLocation.columnDifference(targetPosition);
		if ((!isBlackPiece() && getLocation().getRow() == 6) || (isBlackPiece() && getLocation().getRow() == 1)) {
			_firstMove = true;
		}

		boolean whitePieceDirection = !isBlackPiece() && verticalDifference == 1;
		boolean blackPieceDirection = isBlackPiece() && verticalDifference == -1;
		boolean whiteTwoMoves = !isBlackPiece() && getLocation().getRow() == 6 && verticalDifference == 2;
		boolean blackTwoMoves = isBlackPiece() && getLocation().getRow() == 1 && verticalDifference == -2;
		boolean isStaying = targetPosition.equals(currentLocation);
		if (isStaying) {
			//System.out.println("You cannot move to your own position");
			return false;
		}

		if (whitePieceDirection || blackPieceDirection){
			switch (horizontalDifference) {
			case 1: case -1:
				if (targetCell.isOccupied() && targetCell.isBlackPiece() == isBlackPiece() ) {
					//System.out.println("Cannot kill your piece");
					return false;
				}
				else if (!targetCell.isOccupied()) {
					//System.out.println("Cannot move to this position while it is empty");
					return false;
				}
				break;
			case 0:
				if (targetCell.isOccupied()) {
					//System.out.println("Cannot move to this position while it is occupied");
					return false;
				}
				break;
			default:
				//System.out.println("Cannot move to this position");
				return false;
			}
			_firstMove = false;
			return true;
		}
		else if ( (whiteTwoMoves || blackTwoMoves) && _firstMove && horizontalDifference == 0) {
			_firstMove = false;
			int targetColumn = targetPosition.getColumn();
			int targetRow = targetPosition.getRow();
			int middleRow = whiteTwoMoves ? 5 : 2;
			return !occupiedMatrix[targetRow][targetColumn] && !occupiedMatrix[middleRow][targetColumn];
		}

		//System.out.println("Cannot move to this position");
		return false;
	}

	@Override
	public String toString(){
		if (isBlackPiece()) {
			return "PAWN";
		}
		return "pawn";
	}

	/**
	 * This method finds all the possible moves of the current piece
	 * based on the its location and the movement style of the current
	 * piece.
	 * Notice: the possible moves are not checked for validity (they 
	 * could also be out of bounds), so the result should be filtered 
	 * by the isValidMove() method.
	 * @return An array of all the possible moves in all the directions
	 * 	       based on the current location of the piece.
	 */
	@Override
	public Position[] getPossibleMoves() {
		int size = _firstMove ? 8 : 6;
		Position[] possibleMoves = new Position[size];
		Position currentLocation = getLocation();
		int currentRow = currentLocation.getRow();
		int currentColumn = currentLocation.getColumn();
		int arrayIndex = 0;
		int[] offsetColumns = {-1, 0, 1};
		int[] offsetRows = {-1, 1};
		for (int row = 0; row < offsetRows.length; row++) {
			for (int column = 0; column < offsetColumns.length; column++) {
				possibleMoves[arrayIndex++] = new Position(currentRow + offsetRows[row], currentColumn + offsetColumns[column]);
			}
		}
		if (_firstMove) {
			possibleMoves[arrayIndex++] = new Position(currentRow +2, currentColumn);
			possibleMoves[arrayIndex++] = new Position(currentRow -2, currentColumn);
		}
		return possibleMoves;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Pawn){
			Pawn pawnOther = (Pawn) other;
			return super.equals(pawnOther) && _firstMove == pawnOther._firstMove;
		}
		return false;
	}
}
