package gameController;
import java.util.concurrent.ConcurrentLinkedQueue;

import chess.*;
import gui.*;
/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describes the Controller of the MVC, it is responsible for receiving
 * the user actions from the GameView class and send the appropriate action to the 
 * ChessGame class, as it waits in the game loop for any action. It is the only way 
 * for the gameView and ChessGame classes to interact with each other.
 */
public class GameHandler {
	private static Position _source = null;
	private static Position _target = null;
	private static ConcurrentLinkedQueue<Move> _movesQueue;
	private static ChessGame _game;
	private static GameView _defaultView;
	private static int _blackScore, _whiteScore;

	/**
	 * This method restarts the game creating an empty
	 * queue and creating a new ChessGame object while
	 * nulling the _source and _target.
	 * @param defaultGame
	 */
	public static void restartGame(boolean defaultGame) {
		_movesQueue = new ConcurrentLinkedQueue<Move>();
		_source = null;
		_target = null;
		_game = new ChessGame(defaultGame);
	}

	/**
	 * This method restarts the game by calling restartGame. 
	 * And then change the score based by increasing the score
	 * of the opposite player who forfeits.
	 * @param defaultGame
	 */
	public static void forfeit(boolean defaultGame) {
		restartGame(defaultGame);
		_blackScore += isBlackTurn() ? 1 : 0;
		_whiteScore += !isBlackTurn() ? 1 : 0;
	}
	
	/**
	 * produce the current score as a string
	 * @return the current score as as string
	 */
	public static String getCurrentScore() {
		String score = "black " +  _blackScore + " vs. " + _whiteScore + " white";
		return score;
	}

	/**
	 * This method tries to perform the move specified by 
	 * currMove calling the _game object movePiece() method.
	 * @param currMove
	 * @return true if the move was performed in the actual 
	 * 		   _game object, false otherwise.
	 */
	public static boolean tryPerformingTheMove(Move currMove) {
		Position source = currMove.getSource();
		Position target = currMove.getTarget();
		return _game.movePiece(source, target);
	}
	/**
	 * This method return the player that has the current turn
	 * @return true if black has the current turn, false
	 * 		   if the white has the current turn.
	 */
	public static boolean isBlackTurn() {
		return _game.isBlackTurn();
	}
	/**
	 * This method initialize the game by creating a new
	 * ChessGame object, a new GameView object, and a new
	 * thread safe Queue.
	 */
	public static void gameInit() {
		_defaultView = new GameView(true);
		_game = new ChessGame(true);
		_game.printBoard();
		_movesQueue = new ConcurrentLinkedQueue<Move>();
		_blackScore = 0;
		_whiteScore = 0;
	}

	/**
	 * This method calls the undo function in the _game object
	 * to undo the last move in its stack. It also shows a method
	 * in case the undoMove() method returned null.
	 * @return the last move if it successfully undone in the 
	 * 		   ChessGame class, false otherwise.
	 */
	public static Move undo() {
		Move prevMove = _game.undoMove();
		if (prevMove == null) {
			_defaultView.showMessage(_game.getMessage());
		}
		return prevMove;
	}

	/**
	 * The main game loop which initializes a game by calling 
	 * gameInit(), then waits for a move to exists in the Queue.
	 * Then, perform the move (in both the game data under the _game
	 * object and in the GameView), and show the message (in the _game
	 * object) if one exists. Then return to the waiting state.
	 */
	public static void gameLoop() {
		gameInit();
		while (true) {
			if (_movesQueue.isEmpty()) {
				continue;
			}
			Move currMove = _movesQueue.remove();
			boolean shouldUpdatedView = tryPerformingTheMove(currMove);
			if (shouldUpdatedView) {
				_defaultView.updateView(currMove);
				_game.printBoard();
			}

			String msg = _game.getMessage();
			if (msg.length() > 0) {
				_defaultView.showMessage(msg);
				String wonString =  " has won the game";
				if (msg.equals("white" + wonString)) {
					_whiteScore++;
					_defaultView.showMessage(getCurrentScore());
				}
				else if ( msg.equals("black" + wonString)) {
					_blackScore++;
					_defaultView.showMessage(getCurrentScore());
				}
			}
		}
	}

	/**
	 * This method adds the location into the Queue. this 
	 * method is called by another thread (the actionListener
	 * thread) in the GameView class to send the user location
	 * that was pressed into this class.
	 * @param location
	 */
	public static void sendLocation(Position location) {
		if (_source == null) {
			_source = new Position(location);
		}
		else {
			_target = new Position(location);
			Move currMove = new Move(_source, _target);
			_movesQueue.add(currMove);
			_source = null;
			_target = null;
		}
	}

	/**
	 * The main method of the program.
	 * @param args
	 */
	public static void main(String[] args) {
		gameLoop();

	}

}
