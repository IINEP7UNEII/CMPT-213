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

    public Boolean gameWin()
    {
        if (cheeseToWin <= cheeseCollected)
        {
            return true;
        }
        return false;
    }

    public Boolean gameLose()
    {
        if (eatenByCatCheck())
        {
            board.setDeadObject(board.getMouseCoordinateY(), board.getMouseCoordinateX());
            return true;
        }
        return false;
    }

    private void gameLoop(MazeGame game)
    {
        String move;
        
        while (!gameWin())
        {
            move = menu.updateFrame(game, cheeseCollected, cheeseToWin);
            moveMouse(move);
            cheeseCollectedCheck();
        }

        menu.gameWinMenu(board, cheeseCollected, cheeseToWin);
        System.exit(0);
    }

    private void moveMouse(String move)
    {
        switch (move.toLowerCase())
        {
            case "w":
                board.mouseMoveUp();
                break;

            case "a":
                board.mouseMoveLeft();
                break;

            case "s":
                board.mouseMoveDown();
                break;

            case "d":
                board.mouseMoveRight();
                break;
        }
    }

    private void cheeseCollectedCheck()
    {
        if (board.getMouseCoordinateX() == board.getCheeseCoordinateX() && board.getMouseCoordinateY() == board.getCheeseCoordinateY())
        {
            ++cheeseCollected;
            board.removeCheese();
            board.generateCheese();
        }
    }

    private Boolean eatenByCatCheck()
    {
        if (board.getObject(board.getMouseCoordinateY(), board.getMouseCoordinateX()).getUnderlyingObject().getClass().equals(BoardPeice.Cat.class))
        {
            return true;
        }
        return false;
    }

    // make cats move
}
