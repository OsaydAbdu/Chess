package chess;

/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the Princess piece in the chess game which inherits from the ChessPiece class.
 */
public class Princess extends ChessPiece{


	public Princess(Position location, boolean blackPiece) {
		super(location, blackPiece);
	}

	public Princess(Princess other){
		super(other);
	}

	@Override
	public ChessPiece getANewCopy(){
		Princess newPrincess = new Princess(getLocation(), isBlackPiece());
		return newPrincess;
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
		boolean bishopStyleMove = Math.abs(verticalDifference) == Math.abs(horizontalDifference);

		//Bishop style
		if (bishopStyleMove) {
			int arrayIndex = 0;
			int arraySize = Math.abs(horizontalDifference);
			Position[] pathBishop = new Position[arraySize];
			int verticalDirection = verticalDifference > 0 ? -1 : 1;
			int horizontalDirection = horizontalDifference > 0 ? -1 : 1;
			for (int offset = 0; offset < arraySize; offset++){
				int newRow = currentRow + offset * verticalDirection;
				int newColumn = currentColumn + offset * horizontalDirection;
				pathBishop[arrayIndex++] = new Position(newRow, newColumn);
			}
			return pathBishop;
		}
		
		Position[] pathKnight = new Position[1];
		pathKnight[0] = getLocation();
		return pathKnight;
	}
	/**
	 * This method evaluates whether the target cell's position would be 
	 * a valid move for the current piece based on these conditions:
	 * 1- The movement style is a valid movement style (either like a knight
	 *    or like a bishop for the princess).
	 * 2- There is no intermediate pieces between the current position and 
	 * 	  the target cell (for the princess this is only true when the style
	 * 	  of movement is similar to a bishop).
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
		boolean bishopStyleMove = Math.abs(verticalDifference) == Math.abs(horizontalDifference);
		boolean isStaying = targetPosition.equals(currentLocation);

		boolean isValidPrincessMove = !isStaying &&  (knightStyleMove || bishopStyleMove);
		if (!isValidPrincessMove) {
			//System.out.println("Princess cannot move to that position");
			return false;
		}	
		
		if(bishopStyleMove && isThereIntermediatePieces(targetPosition, occupiedMatrix)) {
			//System.out.println("Cannot jump over other pieces in a bishop style movement");
			return false;
		}
		return true;
	}
	
	/**
	 * Notice: You have to call this method after checking that the moving style
	 * is correct. This is only called when the style of movement follows a 
	 * bishop style.
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
		int verticalDirection = verticalDifference > 0 ? -1 : 1;
		int horizontalDirection = horizontalDifference > 0 ? -1 : 1;

		for (int offset = 1; offset < Math.abs(horizontalDifference); offset++){
			int newRow = currentRow + offset * verticalDirection;
			int newColumn = currentColumn + offset * horizontalDirection;
			if (occupiedMatrix[newRow][newColumn]) {
				return true;
			}		
		}
		return false;
	}

	@Override
	public String toString(){
		if (isBlackPiece()) {
			return "PRINCESS";
		}
		return "princess";
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
		
		//The bishop moves
		size = 18;
		Position[] possibleBishopMoves = new Position[size];
		arrayIndex = 0;
		int[] rowArray = {1, -1, -1, 1};
		int[] columnArray = {1, -1, 1, -1};
		int nextRow = currentRow;
		int nextColumn = currentColumn;
		boolean validPosition = true;

		for (int i =0; i < 4; i++) {
			while (validPosition) {
				possibleBishopMoves[arrayIndex++] = new Position(nextRow, nextColumn);
				nextRow += rowArray[i];
				nextColumn += columnArray[i] ;
				validPosition = nextRow >=0 && nextRow < 8 && nextColumn >= 0 && nextColumn < 8;
			} 
			validPosition = true;
			nextRow = currentRow;
			nextColumn = currentColumn;
		}

		//Combining both moves
		size = possibleKnightMoves.length + possibleBishopMoves.length;
		Position[] possibleMoves = new Position[size];
		arrayIndex = 0;
		for (int i = 0; i < possibleBishopMoves.length; i++) {
			possibleMoves[arrayIndex++] = possibleBishopMoves[i];
		}
		for (int i = 0; i < possibleKnightMoves.length ; i++) {
			possibleMoves[arrayIndex++] = possibleKnightMoves[i];
		}
		return possibleMoves;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Princess){
			Princess princessOther = (Princess) other;
			return super.equals(princessOther);
		}
		return false;
	}
}

