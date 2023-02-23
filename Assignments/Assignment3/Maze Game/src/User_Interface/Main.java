package User_Interface;

import Game_Properties.*;

public final class Main 
{
    public static void main(String[] args)
    {
        Board board = new Board();
        BoardDisplay displayBoard = new BoardDisplay();
        displayBoard.displayBoard(board);
    }
}
