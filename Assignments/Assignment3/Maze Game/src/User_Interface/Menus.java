package User_Interface;

import Game_Properties.*;
import java.util.Scanner;

public final class Menus 
{
    private BoardDisplay displayBoard;
    private Scanner scan;

    public Menus()
    {
        displayBoard = new BoardDisplay();
        scan = new Scanner(System.in);
    }

    public void titleMenu()
    {
        System.out.println("----------------------------------------");
        System.out.println("Welcome to Cat and Mouse Maze Adventure!");
        System.out.println("by Daniel Tolsky");
        System.out.println("----------------------------------------");
    }

    public void tutorialMenu()
    {
        System.out.println("\nDIRECTIONS:");
        System.out.println("\tFind 5 cheese before a cat eats you!");
        System.out.println("LEGEND:");
        System.out.println("\t#: Wall");
        System.out.println("\t@: You (a mouse)");
        System.out.println("\t!: Cat");
        System.out.println("\t$: Cheese");
        System.out.println("\t.: Unexplored space");
        System.out.println("MOVES:");
        System.out.println("\tUse W (up), A (left), S (down) and D (right) to move.");
        System.out.println("\t(You must press enter after each move).");
    }

    public String updateFrame(MazeGame game, int collected, int toWin)
    {
        System.out.println("\nMaze:");
        game.getBoard().revealAroundMouse();
        displayBoard.display(game.getBoard());
        cheeseCollectedMenu(collected, toWin);

        if (game.gameLose())
        {
            gameLoseMenu(game.getBoard(), collected, toWin);
            System.exit(0);
        }

        String move = moveSelectorMenu(game.getBoard());

        cheats(game, move);
        help(move);
        return move;
    }

    public void gameWinMenu(Board board, int collected, int toWin)
    {
        System.out.println("Congratulations! You won!");
        board.revealAll();
        System.out.println("\nMaze:");
        displayBoard.display(board);
        cheeseCollectedMenu(collected, toWin);
    }

    private void cheeseCollectedMenu(int collected, int toWin)
    {
        System.out.println("Cheese collected: " + collected + " of " + toWin);
    }

    private String moveSelectorMenu(Board board)
    {
        System.out.print("Enter your move [WASD?]: ");
        String move = scan.nextLine();
        while (!isValidSelection(move) || !isValidDirection(move, board))
        {
            if (!isValidSelection(move))
            {
                System.out.print("Invalid move. Please enter just A (left), S (down), D (right), or W (up).\n");
            }
            else if (!isValidDirection(move, board))
            {
                System.out.print("Invalid move: you cannot move through walls!\n");
            }
            System.out.print("Enter your move [WASD?]: ");
            move = scan.nextLine();
        }
        return move;
    }

    private Boolean isValidSelection(String move)
    {
        move = move.toLowerCase();
        if (move.equals("w") || move.equals("a") || move.equals("s") || move.equals("d") || move.equals("c") || move.equals("m") || move.equals("?"))
        {
            return true;
        }
        return false;
    }

    private Boolean isValidDirection(String move, Board board)
    {
        if (moveMouseCheck(move, board).getClass() == BoardPeice.Wall.class)
        {
            return false;
        }
        return true;
    }

    private BoardPeice moveMouseCheck(String move, Board board)
    {
        BoardPeice temp = new BoardPeice();
        switch (move.toLowerCase())
        {
            case "w":
                temp = board.getObject(board.getMouseCoordinateY() - 1, board.getMouseCoordinateX());
                break;

            case "a":
                temp = board.getObject(board.getMouseCoordinateY(), board.getMouseCoordinateX() - 1);
                break;

            case "s":
                temp = board.getObject(board.getMouseCoordinateY() + 1, board.getMouseCoordinateX());
                break;

            case "d":
                temp = board.getObject(board.getMouseCoordinateY(), board.getMouseCoordinateX() + 1);
                break;
        }
        return temp;
    }

    private void cheats(MazeGame game, String input) 
    {
        if (input.toLowerCase().equals("c"))
        {
            game.cheatCheese();
        }
        else if (input.toLowerCase().equals("m"))
        {
            game.cheatDisplay();
        }
    }

    private void help(String input) 
    {
        if (input.equals("?"))
        {
            tutorialMenu();
        }
    }

    private void gameLoseMenu(Board board, int collected, int toWin)
    {
        System.out.println("I'm sorry, you have been eaten!");
        board.revealAll();
        System.out.println("\nMaze:");
        displayBoard.display(board);
        cheeseCollectedMenu(collected, toWin);
        System.out.println("GAME OVER; please try again.");
    }
}