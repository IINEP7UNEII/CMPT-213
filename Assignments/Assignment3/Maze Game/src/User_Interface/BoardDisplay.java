package User_Interface;

import Game_Properties.*;

public class BoardDisplay 
{
    public BoardDisplay()
    {}

    public void displayBoard(Board board)
    {
        for (int ver = 0; ver < board.getVerticalLength(); ++ver)
        {
            for (int hor = 0; hor < board.getHorizontalLength(); ++hor)
            {
                System.out.print(board.getObject(ver, hor).getICON() + " ");
            }
            System.out.println();
        }
    }
}