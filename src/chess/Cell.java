package chess;

/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the cell used by the ChessBoard class.
 */
public class Cell {

	private ChessPiece _piece;
	final private Position _location;
	private boolean _occupied;
	private ChessPiece _reversePiece;

	public Cell(ChessPiece piece, Position location){
		_piece = piece;
		_occupied = true;
		_reversePiece = null;
		if (_piece != null && !_piece.getLocation().equals(location)) {
			//System.out.println("Cell location cannot be different from ChessPiece location, so Cell location now equals ChessPiece location.");
			_location = _piece.getLocation();		
		}
		else {
			_location = location;
		}
	}

	public Cell(Position location){
		_occupied = false;
		_piece = null;		
		_location = location;
		_reversePiece = null;
	}

	public Cell(int row, int column) {
		_occupied = false;
		_piece = null;		
		_location = new Position(row, column);
		_reversePiece = null;
	}

	public Cell(Cell other){
		if (other._piece != null) {
			_piece = other._piece.getANewCopy();
		}
		else {
			_piece = null;
		}
		_occupied = other._occupied;
		if (other._location != null) {
			_location = new Position(other._location);
		}
		else {
			_location = null;
		}
		_reversePiece = other._reversePiece;
	}

	public ChessPiece getPiece() {
		return _piece;
	}

	public void setPiece(ChessPiece piece) {
		_piece = piece;
		_occupied = piece == null ? false : true;
	}

	public boolean isOccupied() {
		if (_piece == null) {
			return false;
		}
		return _occupied;
	}

	public void setOccupied(boolean occupied) {
		_occupied = occupied;
	}

	public Position getLocation() {
		return _location;
	}

	public boolean isBlackPiece() {
		if (_piece != null){
			return _piece.isBlackPiece();
		}
		return false;
	}

	public Position[] getPossibleMovesForPiece(boolean[][] occupiedMatrix) {
		return _piece.getPossibleMoves();
	}

	/**
	 * This method check if the ChessPiece under this Cell can move to the targetCell when: 
	 * 1- The source position (this Cell) is occupied.
	 * 2- The target is empty, or the target is occupied with a piece of the other player.
	 * 3- There is no piece between the source and the target interrupting (except when allowed).
	 * 4- The target in valid style of movement (e.g. the same row or column if source is rook).
	 * Notice: conditions 3, and 4 are inside the ChessPiece class and is invoked with
	 * 		   the method _piece.isValidMove(...).
	 * @param targetCell
	 * @param occupiedMatrix
	 * @return true if can move to targetCell false otherwise.
	 */
	public boolean canMoveMyPieceTo(Cell targetCell, boolean[][] occupiedMatrix){
		//Checking condition number 1
		if (!_occupied){
			//System.out.println("Source cell is empty");
			return false;
		}

		//Checking condition number 2
		if (targetCell.isOccupied() && isBlackPiece() == targetCell.isBlackPiece()){
			//System.out.println("Cannot kill your piece");
			return false;	
		}

		//Checking conditions number 3, and 4
		boolean validStylePath = _piece.isValidMove(targetCell, occupiedMatrix);
		if (!validStylePath){
			return false;
		}

		return true;
	}

	/**
	 * This method move the ChessPiece under this cell to the targetCell after
	 * checking that it is an allowed move by calling CanMoveMyPieceTo method.
	 * Also if reverseMove is set true, and the move is actually valid (it satisfy all the previous 
	 * conditions), then the move is reversible).
	 * @param targetCell
	 * @param occupiedMatrix
	 * @param reverseMove indicates whether this move is just for test or not
	 * @return true performed the intended move to targetCell, false otherwise.
	 */
	public boolean moveMyPieceTo(Cell targetCell, boolean[][] occupiedMatrix, boolean reverseMove) {
		if (!reverseMove && !canMoveMyPieceTo(targetCell, occupiedMatrix)) {
			return false;
		}
		//Performing the move after the move passed all the conditions in the CanMoveMyPieceTo method.
		//String currentPlayer = (targetCell.isBlackPiece() ? "black" : "white");
		//ChessPiece targetPiece = targetCell._piece;
		if (targetCell._occupied){
			//System.out.println("Player "+ currentPlayer + " lost a " + targetPiece.toString());
			if (!reverseMove) {
				targetCell._reversePiece = targetCell.getPiece();
			}
			movePiece(targetCell);
		}
		else {
			movePiece(targetCell);
			//System.out.println("Player " + currentPlayer + " moved a" + targetCell.getPiece().toString());
			if (reverseMove) {
				_piece = _reversePiece;
				_occupied = _reversePiece == null ? false : true;
				_reversePiece = null;
			}
		}
		return true;
	}

	public Position[] getMovementPath(Position target) {
		return _piece.getMovementPath(target);
	}
	
	private void movePiece(Cell targetCell){
		_piece.setLocation(targetCell.getLocation());
		targetCell._piece = _piece;
		targetCell.setOccupied(true);
		_piece = null;
		_occupied = false;

	}

	@Override
	public boolean equals(Object other){
		if (other instanceof Cell){
			Cell cellOther = ((Cell)other);
			if (_piece != null && cellOther._piece != null) {
				return _occupied == cellOther._occupied && _piece.equals(cellOther._piece) && _location.equals(cellOther._location);
			}
			if (_piece == null && cellOther._piece == null) {
				return _location.equals(cellOther._location);
			}
		}
		return false;
	}
	public void freeReversePiece() {
		_reversePiece = null;
	}

}
