package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import chess.Position;
import gameController.GameHandler;
import gameController.Move;
/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * This class describes the View of the MVC, it is responsible for creating an 
 * initial view of the board and showing the different configurations during the game.
 */
public class GameView implements ActionListener{
	private final int _width = 500;
	private final int _height = 540;
	private final int _numRows = 8;
	private final int _numColumns = 8;
	private final int _cellHeight = 60;
	private final int _cellWidth = 60;
	private final Color _lightColor = new Color(255,196,138) ;
	private final Color _darkColor = new Color(199,121,51) ;
	private JButton[][] _boardButtons;
	private JFrame _window;
	private JPanel _myPanel;
	private boolean _defaultGame;

	/**
	 * This constructor initialize the view of the board along using many 
	 * helper functions. The view includes the window, the board, and the menu.
	 * @param defaultGame determines whether the game should be initialized with
	 * 		  the default pieces or the not (that is the special pieces).
	 */
	public GameView(boolean defaultGame) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			//silently ignore
		}
		_defaultGame = defaultGame;
		_window = new JFrame("Chess");
		_window.setSize(_width, _height);
		_myPanel = initializePanel();
		initializeBoard(_myPanel);
		setUpMenu(_window);
		_window.setContentPane(_myPanel);
		_window.setVisible(true);
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * The method creates a 2-d array of buttons and 
	 * add them to myPanel. This method uses a helper
	 * method (initializeButton(row, column)) to initialize
	 * each button based on the _defaultGame member variable.
	 * @param myPanel
	 */
	private void initializeBoard(JPanel myPanel) {
		_boardButtons = new JButton[8][8];

		for (int row = 0; row<_numRows; row++) {
			for (int column = 0; column<_numColumns; column++) {
				_boardButtons[row][column] = new JButton();
				initializeButton(row, column);
				myPanel.add(_boardButtons[row][column]);
			}
		}
	}

	/**
	 * Given a row and a column, this method initialize a button
	 * at _boradButtons[row][column] with:
	 * 1- position in the window
	 * 2- dimensions of the button (height and width)
	 * 3- background color
	 * 4- the actionListener
	 * 5- the action command as a string of the form row column "32"
	 * @param row
	 * @param column
	 */
	private void initializeButton(int row, int column) {
		int boarderOffset = 10;
		int x = boarderOffset + column * _cellWidth;
		int y = boarderOffset + row * _cellHeight;
		_boardButtons[row][column].setBounds(x, y, _cellWidth, _cellHeight);
		_boardButtons[row][column].addActionListener(this);
		
		boolean isDarkCell = (row%2 == 0 && column%2 == 1) || (row%2 == 1 && column%2 == 0);
		_boardButtons[row][column].setBackground(isDarkCell ? _darkColor : _lightColor );
		_boardButtons[row][column].setOpaque(true);
		_boardButtons[row][column].setBorderPainted(false);
		_boardButtons[row][column].setActionCommand(""+row+column);
		
		BufferedImage cellImg = PhotosUtilities.getDefaultImage(row,column, _defaultGame);
		if (cellImg != null) {
			_boardButtons[row][column].setIcon(new ImageIcon(cellImg));
		}
	}

	/**
	 * This method creates a panel with the _width and _height 
	 * member variables and return it.
	 * @return a new JPanel object with _width and _height.
	 */
	private JPanel initializePanel() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(_width, _height));
		myPanel.setLayout(null);
		return myPanel;
	}

	/**
	 * This method creates all the menu items into the
	 * argument window.
	 * @param window
	 */
	private void setUpMenu(JFrame window) {
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Menu");

		JMenu newGame = new JMenu("New game");
		menu.add(newGame);
		JMenuItem newDefaultGame = new JMenuItem("Defualt game");
		newDefaultGame.addActionListener(this);
		newDefaultGame.setActionCommand("newDefaultGame");
		newGame.add(newDefaultGame);

		JMenuItem newSpecialGame = new JMenuItem("Special game");
		newSpecialGame.addActionListener(this);
		newSpecialGame.setActionCommand("newSpecialGame");
		newGame.add(newSpecialGame);

		JMenuItem forfitGame = new JMenuItem("Forfeit game");
		forfitGame.addActionListener(this);
		forfitGame.setActionCommand("forfeitGame");
		menu.add(forfitGame);

		JMenuItem restartGame = new JMenuItem("Restart game");
		restartGame.addActionListener(this);
		restartGame.setActionCommand("restartGame");
		menu.add(restartGame);
		
		JMenuItem showScore = new JMenuItem("Show score");
		showScore.addActionListener(this);
		showScore.setActionCommand("showScore");
		menu.add(showScore);
		

		JMenuItem undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		undo.setActionCommand("undo");
		

		menubar.add(menu);
		menubar.add(undo);
		window.setJMenuBar(menubar);
	}

	/**
	 * This is the actionListner method that will listen to
	 * all the actions performed by any object and will react
	 * based on the actionCommand received by the object sending
	 * the command.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		int row = command.charAt(0)-'0';
		int column = command.charAt(1)-'0';
		if (row >=0 && row <= 9 && column >=0 && column <= 9) {
			GameHandler.sendLocation(new Position(row, column));
		}
		else if (command.equals("restartGame")) {
			GameHandler.restartGame(_defaultGame);
			restartGame();
			showMessage("The game has restarted");
		}
		else if (command.equals("newDefaultGame")) {
			_defaultGame = true;
			GameHandler.restartGame(_defaultGame);
			restartGame();
		}
		else if (command.equals("newSpecialGame")) {
			_defaultGame = false;
			GameHandler.restartGame(_defaultGame);
			restartGame();
		}
		else if (command.equals("undo")) {
			undo(GameHandler.undo());
		}
		else if (command.equals("forfeitGame")) {
			boolean isBlackTurn = GameHandler.isBlackTurn();
			GameHandler.forfeit(_defaultGame);
			restartGame();
			showMessage((isBlackTurn ? "black":"white") + " player has lost this game");
			String score = GameHandler.getCurrentScore();
			showMessage("The current score is " + score);
		}
		else if (command.equals("showScore")) {
			String score = GameHandler.getCurrentScore();
			showMessage("The current score is " + score);
		}
	}

	/**
	 * This method is simply used to show the String msg
	 * on the screen as a way to give information to the user.
	 * @param msg
	 */
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg,
				"Warning!", JOptionPane.INFORMATION_MESSAGE);
	}

	public void updateView(Move currMove) {
		Position source = currMove.getSource();
		Position target = currMove.getTarget();
		int srcRow = source.getRow();
		int srcColumn = source.getColumn();
		int targetRow = target.getRow();
		int targetColumn = target.getColumn();
		Icon srcIcon = _boardButtons[srcRow][srcColumn].getIcon();
		_boardButtons[srcRow][srcColumn].setIcon(null);
		_boardButtons[targetRow][targetColumn].setIcon(srcIcon);
	}
	
	/**
	 * This method is responsible for the undo command in the 
	 * view part. It takes the argument prevMove and swaps the
	 * source and target using the Move class method swap().
	 * Then, it transfer the icon from the source (the new source
	 * after the swap) to the target, and sets the source icon 
	 * according the deletedPiece object under the prevMove object.
	 * @param prevMove the previous move taken to be undone.
	 */
	public void undo(Move prevMove) {
		if (prevMove == null) {
			return ;
		}
		prevMove.swap();
		updateView(prevMove);
		if (prevMove.getDeletedPiece() == null) {
			return;
		}
		String pieceName = prevMove.getDeletedPiece().toString();
		boolean isBlackPiece = prevMove.getDeletedPiece().isBlackPiece();
		pieceName = "resources/images/" + (isBlackPiece ? "black" : "white") + pieceName.toLowerCase() +".png";

		Position source = prevMove.getSource();
		BufferedImage cellImg = PhotosUtilities.loadImage(pieceName);
		if (cellImg != null) {
			_boardButtons[source.getRow()][source.getColumn()].setIcon(new ImageIcon(cellImg));
		}
	}
	
	/**
	 * This method simply restarts the game by assigning the initial icons
	 * to each cell and making any other cell empty. (This only reset the view).
	 */
	public void restartGame() {
		for (int row = 0; row<_numRows; row++) {
			for (int column = 0; column<_numColumns; column++) {
				BufferedImage cellImg = PhotosUtilities.getDefaultImage(row,column, _defaultGame);
				if (cellImg != null) {
					_boardButtons[row][column].setIcon(new ImageIcon(cellImg));
				}
				else {
					_boardButtons[row][column].setIcon(null);
				}
			}
		}
	}
}