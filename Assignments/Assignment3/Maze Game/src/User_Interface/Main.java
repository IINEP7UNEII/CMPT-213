package User_Interface;

import Game_Properties.*;

public class Main 
{
    public static void main(String[] args) throws Exception 
    {
        Board board = new Board();
        BoardDisplay displayBoard = new BoardDisplay();
        board.generateBasicBoard();
        displayBoard.displayBoard(board);
    }
}
