package User_Interface;

import Game_Properties.*;

public final class Main 
{
    public static void main(String[] args)
    {
        MazeGame game = new MazeGame();
        game.startMazeGame(game);
    }
}
