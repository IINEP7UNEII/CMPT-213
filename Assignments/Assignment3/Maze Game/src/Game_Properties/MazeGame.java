package Game_Properties;

import User_Interface.*;

public final class MazeGame 
{
    private Menus menu;
    private Board board;
    private int cheeseToWin;
    private int cheeseCollected;

    public MazeGame()
    {
        menu = new Menus();
        board = new Board();
        cheeseToWin = 5;
        cheeseCollected = 0;
    }

    public void startMazeGame(MazeGame game)
    {
        menu.titleMenu();
        menu.tutorialMenu();
        gameLoop(game);
    }

    public Board getBoard()
    {
        return board;
    }

    public void cheatCheese()
    {
        cheeseToWin = 1;
    }

    public void cheatDisplay()
    {
        board.revealAll();
    }

    private void gameLoop(MazeGame game)
    {
        while (true) //change loop argument later
        {
            menu.updateFrame(game, cheeseCollected, cheeseToWin);
        }
    }
}
