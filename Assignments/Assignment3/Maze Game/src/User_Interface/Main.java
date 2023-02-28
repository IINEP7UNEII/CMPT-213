package User_Interface;

/**
* Description: The Main class is used to create a MazeGame object and to run the game.
*
* @author Daniel Tolsky
* @version 1.0
*/

import Game_Properties.*;

public final class Main 
{
    public static void main(String[] args)
    {
        MazeGame game = new MazeGame();
        game.startMazeGame(game);
    }
}
