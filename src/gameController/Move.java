package gameController;
import chess.*;
public class Move {
	private Position _source;
	private Position _target;
	private ChessPiece _deletedPiece;
	
	public Move(Position source, Position target) {
		_source = source;
		_target = target;
		_deletedPiece = null;
	}
	public Move(Position source, Position target, ChessPiece deletedPiece) {
		_source = source;
		_target = target;
		_deletedPiece = deletedPiece;
	}
	public Position getSource() {
		return _source;
	}
	public void setSource(Position source) {
		_source = source;
	}
	public Position getTarget() {
		return _target;
	}
	public void setTarget(Position target) {
		_target = target;
	}
	public ChessPiece getDeletedPiece() {
		return _deletedPiece;
	}
	public void setDeletedPiece(ChessPiece deletedPiece) {
		_deletedPiece = deletedPiece;
	}
	public void swap() {
		Position prevSource = _source;
		_source = _target;
		_target = prevSource;
	}

}
