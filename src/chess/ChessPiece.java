package chess;
/**
 * 
 * @author abdu2
 * ChessPiece class defines a generic chess piece where each chess piece 
 * will have its own subclass
 */
public abstract class ChessPiece {

	private Position _location;
	private boolean _blackPiece;
	private boolean _activeInGame;

	public ChessPiece(Position location, boolean blackPiece){
		_location = new Position(location);
		_blackPiece = blackPiece;
		_activeInGame = true;
	}

	public ChessPiece(ChessPiece other){
		copyObject(this, other);
	}

	/**
	 * This method checks whether this ChessPiece (depends on the exact
	 * subclass) can move to the testPosition as by the style of movement
	 * of this ChessPiece.
	 * @param testPosition
	 * @return true if a valid position to move to and false otherwise
	 */
	public abstract boolean isValidMove(Cell targetCell, boolean[][] occupiedMatrix);
	public abstract ChessPiece getANewCopy();
	public abstract String toString();
	public abstract Position[] getPossibleMoves();
	public abstract Position[] getMovementPath(Position target);


	public boolean isBlackPiece(){
		return _blackPiece;
	}

	public void setLocation(Position newLocation){
		_location = newLocation;
	}

	public Position getLocation(){
		return _location;
	}

	/**
	 * 
	 * @return the status of the current chess piece
	 */
	public boolean getActiveStatus( ){
		return _activeInGame;
	}

	public void killPiece(){
		//Probably adding a message
		_location = new Position(-1, -1);
		_activeInGame = false;
	}

	private void copyObject(ChessPiece currentObject, ChessPiece otherObject){

		currentObject._location = new Position(otherObject._location);
		currentObject._blackPiece = otherObject._blackPiece;
		currentObject._activeInGame = otherObject._activeInGame;
	}

	@Override
	public boolean equals(Object other){
		if (other instanceof ChessPiece){
			ChessPiece chessPieceOther = (ChessPiece) other;
			boolean sameLocation = _location.equals(chessPieceOther._location);
			boolean sameColor = _blackPiece == chessPieceOther._blackPiece;
			boolean sameActivety = _activeInGame == chessPieceOther._activeInGame;
			return  sameLocation && sameColor && sameActivety;
		}
		return false;
	}

}
