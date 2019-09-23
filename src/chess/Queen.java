package chess;

/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the Queen piece in the chess game which inherits from the ChessPiece class.
 */
public class Queen extends ChessPiece{

	public Queen(Position location, boolean blackPiece) {
		super(location, blackPiece);
	}

	public Queen(Queen other){
		super(other);
	}

	@Override
	public ChessPiece getANewCopy(){
		Queen newQueen = new Queen(getLocation(), isBlackPiece());
		return newQueen;
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
		
		//Bishop style
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
	
	/**
	 * This method evaluates whether the target cell's position would be 
	 * a valid move for the current piece based on these conditions:
	 * 1- The movement style is a valid movement style (either like a rook
	 *    or like a bishop for the queen).
	 * 2- There is no intermediate pieces between the current position and 
	 * 	  the target cell.
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

		boolean rookStyleMove = verticalDifference == 0 || horizontalDifference == 0;
		boolean bishopStyleMove = Math.abs(verticalDifference) == Math.abs(horizontalDifference);
		boolean isStaying = targetPosition.equals(currentLocation);

		boolean isValidQueenMove = !isStaying && ( bishopStyleMove || rookStyleMove);

		if (!isValidQueenMove) {
			//System.out.println("Queen cannot move to that position");
			return false;
		}
		if(isThereIntermediatePieces(targetPosition, occupiedMatrix)) {
			//System.out.println("Cannot jump over other pieces");
			return false;
		}
		return true;
	}

	/**
	 * Notice: You have to call this method after checking that the moving style
	 * is correct. (The same row for or the same columns for the Rook).
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

		//Rook style
		if (verticalDifference == 0){
			int direction = (int) Math.signum(horizontalDifference)*-1;
			for (int offset = 1; offset < Math.abs(horizontalDifference); offset++) {
				if (occupiedMatrix[currentRow][currentColumn + offset*direction]) {
					return true;
				}
			}
			return false;
		}
		else if (horizontalDifference == 0){
			int direction = (int) Math.signum(verticalDifference)*-1;
			for (int offset = 1; offset < Math.abs(verticalDifference); offset++) {
				if (occupiedMatrix[currentRow + offset*direction][currentColumn]) {
					return true;
				}
			}
			return false;
		}

		//Bishop style
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
			return "QUEEN";
		}
		return "queen";
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
		//The bishop moves
		int size = 18;
		Position[] possibleBishopMoves = new Position[size];
		Position currentLocation = getLocation();
		int currentRow = currentLocation.getRow();
		int currentColumn = currentLocation.getColumn();
		int arrayIndex = 0;
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
		size = possibleRookMoves.length + possibleBishopMoves.length;
		Position[] possibleMoves = new Position[size];
		arrayIndex = 0;
		for (int i = 0; i < possibleBishopMoves.length; i++) {
			possibleMoves[arrayIndex++] = possibleBishopMoves[i];
		}
		for (int i = 0; i < possibleRookMoves.length ; i++) {
			possibleMoves[arrayIndex++] = possibleRookMoves[i];
		}
		return possibleMoves;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Queen){
			Queen queenOther = (Queen) other;
			return super.equals(queenOther);
		}
		return false;
	}
}
