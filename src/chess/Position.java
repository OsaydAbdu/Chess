package chess;

/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describe the Position where it only has a row and column variables
 * It also has a static method that can produce all the possible pairs given a 
 * height and width.
 */
public class Position {
	private int _row, _column;

	public Position(int row, int column) {
		_row = row;
		_column = column;
	}

	public Position(Position other){
		_row = other._row;
		_column = other._column;
	}

	public int getRow() {
		return _row;
	}

	public int getColumn() {
		return _column;
	}

	public void setRow(int row) {
		_row = row;
	}

	public void setColumn(int column) {
		_column = column;
	}

	public void setNewPosition(int row, int column){
		_row = row;
		_column = column;
	}

	public int rowDifference(Position targetPosition){
		return _row - targetPosition._row;
	}

	public int columnDifference(Position targetPosition){
		return _column - targetPosition._column;
	}

	public static Position[] generateAllPositions(int height, int width) {
		Position[] arrayOfPositions = new Position[height*width];
		int arrayIndex = 0;
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				arrayOfPositions[arrayIndex++] = new Position(row, column);
			}
		}
		return arrayOfPositions;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Position){
			Position positionOther = (Position) other;
			return _row == positionOther._row && _column == positionOther._column;
		}
		return false;
	}
	@Override
	public String toString() {
		return "(" + _row + ", " + _column + ")";
	}
}
