package User_Interface;

/**
* Description: The BoardDisplay class is a simple class which provides the ability to easily display the board.
* This is a seperate class mainly due to its ease of use during debugging of the program.
*
* @author Daniel Tolsky
* @version 1.0
*/

import Game_Properties.*;

public final class BoardDisplay 
{
    public BoardDisplay()
    { }

    public void display(Board board)
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
