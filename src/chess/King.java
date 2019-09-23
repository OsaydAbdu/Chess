package chess;

/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the King piece in the chess game which inherits from the ChessPiece class.
 */
public class King extends ChessPiece{

	public King(Position location, boolean blackPiece) {
		super(location, blackPiece);
	}

	public King(King other) {
		super(other);
	}

	@Override
	public ChessPiece getANewCopy(){
		King newKing = new King(getLocation(), isBlackPiece());
		return newKing;
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
	 * 1- The movement style is a valid movement style (one cell distance).
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
		boolean isStaying = targetPosition.equals(currentLocation);

		boolean isValidKingMove = !isStaying && (Math.abs(horizontalDifference) <=1 && Math.abs(verticalDifference) <=1);
		if (!isValidKingMove) {
			//System.out.println("King cannot move to that position");
			return false;
		}
		return true;
	}

	@Override
	public String toString(){
		if (isBlackPiece()) {
			return "KING";
		}
		return "king";
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
		int size = 9;
		Position[] possibleMoves = new Position[size];
		Position currentLocation = getLocation();
		int currentRow = currentLocation.getRow();
		int currentColumn = currentLocation.getColumn();
		int arrayIndex = 0;
		int[] offsetArray = {-1, 0, 1};
		for (int row = 0; row < offsetArray.length; row++) {
			for (int column = 0; column < offsetArray.length; column++) {
				possibleMoves[arrayIndex++] = new Position(currentRow + offsetArray[row], currentColumn + offsetArray[column]);
			}
		}
		return possibleMoves;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof King){
			King kingOther = (King) other;
			return super.equals(kingOther);
		}
		return false;
	}

}
