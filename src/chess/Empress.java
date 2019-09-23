package chess;

/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the Empress piece in the chess game which inherits from the ChessPiece class.
 */
public class Empress extends ChessPiece{


	public Empress(Position location, boolean blackPiece) {
		super(location, blackPiece);
	}

	public Empress(Empress other){
		super(other);
	}

	@Override
	public ChessPiece getANewCopy(){
		Empress newEmpress = new Empress(getLocation(), isBlackPiece());
		return newEmpress;
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

		Position currentLocation = getLocation();
		int currentRow = currentLocation.getRow();
		int currentColumn = currentLocation.getColumn();
		int verticalDifference = currentLocation.rowDifference(target);
		int horizontalDifference = currentLocation.columnDifference(target);
		boolean rookStyleMove = verticalDifference == 0 || horizontalDifference == 0;

		//Rook style
		if (rookStyleMove) {
			verticalDifference = currentLocation.rowDifference(target);
			horizontalDifference = currentLocation.columnDifference(target);
			int arrayIndex = 0;
			int axis = verticalDifference == 0 ? horizontalDifference : verticalDifference;
			int arraySize = Math.abs(axis);
			Position[] pathRook = new Position[arraySize];
			int dirVir = (int) Math.signum(verticalDifference)*-1;
			int dirHorz = (int) Math.signum(horizontalDifference)*-1;
			for (int offset = 0; offset < arraySize; offset++) {
				pathRook[arrayIndex++] = new Position(currentRow + offset*dirVir, currentColumn + offset*dirHorz);
			}
			return pathRook;
		}

		Position[] pathKnight = new Position[1];
		pathKnight[0] = getLocation();
		return pathKnight;
	}

	/**
	 * This method evaluates whether the target cell's position would be 
	 * a valid move for the current piece based on these conditions:
	 * 1- The movement style is a valid movement style (either like a knight
	 *    or like a rook for the empress).
	 * 2- There is no intermediate pieces between the current position and 
	 * 	  the target cell (for the princess this is only true when the style
	 * 	  of movement is similar to a rook).
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

		boolean horizintalMove = Math.abs(verticalDifference) == 2 && Math.abs(horizontalDifference) == 1;
		boolean verticalMove = Math.abs(verticalDifference) == 1 && Math.abs(horizontalDifference) == 2;
		boolean knightStyleMove = horizintalMove || verticalMove;
		boolean rookStyleMove = verticalDifference == 0 || horizontalDifference == 0;
		boolean isStaying = targetPosition.equals(currentLocation);

		boolean isValidPrincessMove =  !isStaying && (knightStyleMove || rookStyleMove);
		if (!isValidPrincessMove) {
			//System.out.println("Empress cannot move to that position");
			return false;
		}	

		if(rookStyleMove && isThereIntermediatePieces(targetPosition, occupiedMatrix)) {
			//System.out.println("Cannot jump over other pieces in a rook style movement");
			return false;
		}
		return true;
	}

	/**
	 * Notice: You have to call this method after checking that the moving style
	 * is correct. This is only called when the style of movement follows a 
	 * rook style.
	 * @param targetPosition
	 * @return true if there is pieces between the current piece and the
	 * target piece
	 */
	private boolean isThereIntermediatePieces(Position targetPosition, boolean[][] occupiedMatrix) {
		Position currentLocation = getLocation();
		int verticalDifference = currentLocation.rowDifference(targetPosition);
		int horizontalDifference = currentLocation.columnDifference(targetPosition);
		int currentRow = currentLocation.getRow();
		int currentColumn = currentLocation.getColumn();

		if (verticalDifference == 0){
			int direction = (int) Math.signum(horizontalDifference)*-1;
			for (int offset = 1; offset < Math.abs(horizontalDifference); offset++) {
				if (occupiedMatrix[currentRow][currentColumn + offset*direction]) {
					return true;
				}
			}
		}
		else{
			int direction = (int) Math.signum(verticalDifference)*-1;
			for (int offset = 1; offset < Math.abs(verticalDifference); offset++) {
				if (occupiedMatrix[currentRow + offset*direction][currentColumn]) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString(){
		if (isBlackPiece()) {
			return "EMPRESS";
		}
		return "empress";
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
		//The knight moves
		int size = 8;
		Position[] possibleKnightMoves = new Position[size];
		Position currentLocation = getLocation();
		int currentRow = currentLocation.getRow();
		int currentColumn = currentLocation.getColumn();
		int arrayIndex = 0;
		int[] offsetRows = {+2, +1, -2, -1};
		int[] offsetColumns = {+1, -1, +2, -2, +1, -1, +2, -2};

		for (int i = 0; i < offsetColumns.length; i++) {
			possibleKnightMoves[arrayIndex++] = new Position(currentRow + offsetRows[i/2], currentColumn + offsetColumns[i]);
		}

		//The rook moves
		size = 16;
		Position[] possibleRookMoves = new Position[size];
		arrayIndex = 0;
		for (int row = 0; row < 8; row++) {
			possibleRookMoves[arrayIndex++] = new Position(row, currentColumn);
		}
		for (int column = 0; column < 8; column++) {
			possibleRookMoves[arrayIndex++] = new Position(currentRow, column);
		}

		//Combining both moves
		size = possibleKnightMoves.length + possibleRookMoves.length;
		Position[] possibleMoves = new Position[size];
		arrayIndex = 0;
		for (int i = 0; i < possibleRookMoves.length; i++) {
			possibleMoves[arrayIndex++] = possibleRookMoves[i];
		}
		for (int i = 0; i < possibleKnightMoves.length ; i++) {
			possibleMoves[arrayIndex++] = possibleKnightMoves[i];
		}
		return possibleMoves;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Empress){
			Empress empressOther = (Empress) other;
			return super.equals(empressOther);
		}
		return false;
	}
}

