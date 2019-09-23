package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
/**
 * This class is written for the chess package during the CS242 class and it is
 * copyrighted for the author.
 * @author Osayd Abdu
 * @netid  abdu2
 * @version 1.1
 * All the images (unless it stated otherwise) are taken from wikipedia https://en.wikipedia.org/wiki/Chess_piece 
 */
public class PhotosUtilities {

	/**
	 * This method takes a row, column, and defaultGame arguments and creates the 
	 * image name corresponding to each cell at the (row, column) index based on the 
	 * defaultGame argument, based on the default configurations. Then it calls loadImage
	 * method to load the image with that name and return the result.
	 * @param row
	 * @param column
	 * @param defaultGame
	 * @return a BufferedImage object corresponds to the (row, column) index and
	 * 			based on the defaultGame argument.
	 */
	public static BufferedImage getDefaultImage(int row, int column, boolean defaultGame) {

		String imageName = "resources/images/";
		if (row == 0 ) {
			switch (column) {
			case 0: case 7:
				imageName +=  defaultGame ? "blackRook" : "blackEmpress";
				break;
			case 1: case 6:
				imageName +=  "blackKnight";
				break;
			case 2: case 5:
				imageName +=  defaultGame ? "blackBishop" : "blackPrincess";
				break;
			case 3:
				imageName +=  "blackQueen";
				break;
			case 4:
				imageName +=  "blackKing";
				break;
			default:
				break;
			}
		}
		else if (row == 1 ) {
			imageName +=  "blackPawn";
		}
		else if (row == 6 ) {
			imageName +=  "whitePawn";
		}
		else if (row == 7) {
			switch (column) {
			case 0: case 7:
				imageName +=  defaultGame ? "whiteRook" : "whiteEmpress";
				break;
			case 1: case 6:
				imageName +=  "whiteKnight";
				break;
			case 2: case 5:
				imageName +=  defaultGame ? "whiteBishop" : "whitePrincess";
				break;
			case 3:
				imageName +=  "whiteQueen";
				break;
			case 4:
				imageName +=  "whiteKing";
				break;
			default:
				break;
			}
		}
		else {
			return null;
		}
		imageName += ".png";
		return loadImage(imageName);

	}
	
	/**
	 * The method takes a string fileName and which corresponds to the
	 * full image path to be loaded and returns null if no such 
	 * image exists.
	 * @param fileName  a full path of an image to be loaded.
	 * @return the loaded fileName image or no null if no such image 
	 * 		   exists at the path specified by filename
	 */
	public static BufferedImage loadImage(String fileName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.out.println("Error while loading image " + fileName);
		}
		return img;
	}

}
