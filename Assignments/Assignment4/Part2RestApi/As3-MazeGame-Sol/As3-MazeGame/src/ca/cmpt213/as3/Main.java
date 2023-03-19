package ca.cmpt213.as3;

import ca.cmpt213.as3.model.MazeGame;
import ca.cmpt213.as3.textui.MazeTextUI;

/**
 * Launch the Maze Game with a text UI.
 */
public class Main {
	public static void main(String[] args) 
	{
		MazeGame game = new MazeGame();
		MazeTextUI ui = new MazeTextUI(game);
		ui.playGame();
	}
}
