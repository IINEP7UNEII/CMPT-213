package Game_Properties;

import User_Interface.*;

/**
* Description: This MazeGame class is the main logic class which handles most events of the maze game itself.
* It contains many functions relating to the logic of the game's operation and uses those function to compile
* together a working game.
*
* @author Daniel Tolsky
* @version 1.0
*/

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

            if (gameLose())
            {
                menu.gameLoseMenu(board, cheeseCollected, cheeseToWin);
                System.exit(0);
            }
            else if (!(move.equalsIgnoreCase("c")) && !(move.equalsIgnoreCase("m")))
            {
                moveCats();
            }
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

    private void moveCats()
    {
        int randMove = board.getRand().nextInt(4);
        moveCat1(randMove);
        randMove = board.getRand().nextInt(4);
        moveCat2(randMove);
        randMove = board.getRand().nextInt(4);
        moveCat3(randMove);
    }

    private void moveCat1(int move)
    {
        Boolean catMoved = false;
        while (!catMoved)
        {
            switch (move)
            {
                case 0:
                    if (!catMoved && board.catCanMoveUp(board.getCat1()) 
                    && (!board.getCat1().getLastMove().equals("down") || catDownBlocked(board.getCat1())))
                    {
                        catMoved = true;
                        board.catMoveUp(board.getCat1());
                        board.getCat1().setLastMove("up");
                    }
                    break;
                case 1:
                    if (!catMoved && board.catCanMoveDown(board.getCat1()) 
                    && (!board.getCat1().getLastMove().equals("up") || catUpBlocked(board.getCat1())))
                    {
                        catMoved = true;
                        board.catMoveDown(board.getCat1());
                        board.getCat1().setLastMove("down");
                    }
                    break;
                case 2:
                    if (!catMoved && board.catCanMoveRight(board.getCat1()) 
                    && (!board.getCat1().getLastMove().equals("left") || catLeftBlocked(board.getCat1())))
                    {
                        catMoved = true;
                        board.catMoveRight(board.getCat1());
                        board.getCat1().setLastMove("right");
                    }
                    break;
                case 3:
                    if (!catMoved && board.catCanMoveLeft(board.getCat1()) 
                    && (!board.getCat1().getLastMove().equals("right") || catRightBlocked(board.getCat1())))
                    {
                        catMoved = true;
                        board.catMoveLeft(board.getCat1());
                        board.getCat1().setLastMove("left");
                    }
                    break;
            }
            move = board.getRand().nextInt(4);
        }
    }

    private void moveCat2(int move)
    {
        Boolean catMoved = false;
        while (!catMoved)
        {
            switch (move)
            {
                case 0:
                    if (!catMoved && board.catCanMoveUp(board.getCat2()) 
                    && (!board.getCat2().getLastMove().equals("down") || catDownBlocked(board.getCat2())))
                    {
                        catMoved = true;
                        board.catMoveUp(board.getCat2());
                        board.getCat2().setLastMove("up");
                    }
                    break;
                case 1:
                    if (!catMoved && board.catCanMoveDown(board.getCat2()) 
                    && (!board.getCat2().getLastMove().equals("up") || catUpBlocked(board.getCat2())))
                    {
                        catMoved = true;
                        board.catMoveDown(board.getCat2());
                        board.getCat2().setLastMove("down");
                    }
                    break;
                case 2:
                    if (!catMoved && board.catCanMoveRight(board.getCat2()) 
                    && (!board.getCat2().getLastMove().equals("left") || catLeftBlocked(board.getCat2())))
                    {
                        catMoved = true;
                        board.catMoveRight(board.getCat2());
                        board.getCat2().setLastMove("right");
                    }
                    break;
                case 3:
                    if (!catMoved && board.catCanMoveLeft(board.getCat2()) 
                    && (!board.getCat2().getLastMove().equals("right") || catRightBlocked(board.getCat2())))
                    {
                        catMoved = true;
                        board.catMoveLeft(board.getCat2());
                        board.getCat2().setLastMove("left");
                    }
                    break;
            }
            move = board.getRand().nextInt(4);
        }
    }

    private void moveCat3(int move)
    {
        Boolean catMoved = false;
        while (!catMoved)
        {
            switch (move)
            {
                case 0:
                    if (!catMoved && board.catCanMoveUp(board.getCat3()) 
                    && (!board.getCat3().getLastMove().equals("down") || catDownBlocked(board.getCat3())))
                    {
                        catMoved = true;
                        board.catMoveUp(board.getCat3());
                        board.getCat3().setLastMove("up");
                    }
                    break;
                case 1:
                    if (!catMoved && board.catCanMoveDown(board.getCat3()) 
                    && (!board.getCat3().getLastMove().equals("up") || catUpBlocked(board.getCat3())))
                    {
                        catMoved = true;
                        board.catMoveDown(board.getCat3());
                        board.getCat3().setLastMove("down");
                    }
                    break;
                case 2:
                    if (!catMoved && board.catCanMoveRight(board.getCat3()) 
                    && (!board.getCat3().getLastMove().equals("left") || catLeftBlocked(board.getCat3())))
                    {
                        catMoved = true;
                        board.catMoveRight(board.getCat3());
                        board.getCat3().setLastMove("right");
                    }
                    break;
                case 3:
                    if (!catMoved && board.catCanMoveLeft(board.getCat3()) 
                    && (!board.getCat3().getLastMove().equals("right") || catRightBlocked(board.getCat3())))
                    {
                        catMoved = true;
                        board.catMoveLeft(board.getCat3());
                        board.getCat3().setLastMove("left");
                    }
                    break;
            }
            move = board.getRand().nextInt(4);
        }
    }

    private Boolean catUpBlocked(BoardPeice.Cat cat)
    {
        if (board.getObject(cat.getCoordY() - 1, cat.getCoordX()).getClass() == BoardPeice.Wall.class
        && board.getObject(cat.getCoordY(), cat.getCoordX() - 1).getClass() == BoardPeice.Wall.class
        && board.getObject(cat.getCoordY(), cat.getCoordX() + 1).getClass() == BoardPeice.Wall.class)
        {
            return true;
        }
        return false;
    }

    private Boolean catDownBlocked(BoardPeice.Cat cat)
    {
        if (board.getObject(cat.getCoordY() + 1, cat.getCoordX()).getClass() == BoardPeice.Wall.class
        && board.getObject(cat.getCoordY(), cat.getCoordX() - 1).getClass() == BoardPeice.Wall.class
        && board.getObject(cat.getCoordY(), cat.getCoordX() + 1).getClass() == BoardPeice.Wall.class)
        {
            return true;
        }
        return false;
    }

    private Boolean catRightBlocked(BoardPeice.Cat cat)
    {
        if (board.getObject(cat.getCoordY() - 1, cat.getCoordX()).getClass() == BoardPeice.Wall.class
        && board.getObject(cat.getCoordY() + 1, cat.getCoordX()).getClass() == BoardPeice.Wall.class
        && board.getObject(cat.getCoordY(), cat.getCoordX() + 1).getClass() == BoardPeice.Wall.class)
        {
            return true;
        }
        return false;
    }

    private Boolean catLeftBlocked(BoardPeice.Cat cat)
    {
        if (board.getObject(cat.getCoordY() - 1, cat.getCoordX()).getClass() == BoardPeice.Wall.class
        && board.getObject(cat.getCoordY() + 1, cat.getCoordX()).getClass() == BoardPeice.Wall.class
        && board.getObject(cat.getCoordY(), cat.getCoordX() - 1).getClass() == BoardPeice.Wall.class)
        {
            return true;
        }
        return false;
    }

    private void cheeseCollectedCheck()
    {
        if (board.getMouseCoordinateX() == board.getCheeseCoordinateX() 
        && board.getMouseCoordinateY() == board.getCheeseCoordinateY())
        {
            ++cheeseCollected;
            board.removeCheese();
            board.generateCheese();
        }
    }

    private Boolean eatenByCatCheck()
    {
        if (board.getObject(board.getMouseCoordinateY(), board.getMouseCoordinateX())
        .getUnderlyingObject() == null)
        {
            board.getObject(board.getMouseCoordinateY(), board.getMouseCoordinateX()).setUnderlyingObject
            (board.getObject(board.getMouseCoordinateY(), board.getMouseCoordinateX()).new Empty());
        }
        if (board.getObject(board.getMouseCoordinateY(), board.getMouseCoordinateX())
        .getUnderlyingObject().getClass().equals(BoardPeice.Cat.class))
        {
            return true;
        }
        return false;
    }
}
