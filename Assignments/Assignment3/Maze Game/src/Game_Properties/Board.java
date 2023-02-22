package Game_Properties;

public class Board 
{
    private final int HORIZONTAL_LENGTH;
    private final int VERTICAL_LENGTH;
    private BoardPeice board[][];

    public Board()
    {
        HORIZONTAL_LENGTH = 20;
        VERTICAL_LENGTH = 15;
        board = new BoardPeice[VERTICAL_LENGTH][HORIZONTAL_LENGTH];

        generateBasicBoard();
        //combineBoardAndMaze();
    }

    public void generateBasicBoard()
    {
        for (int ver = 0; ver < VERTICAL_LENGTH; ++ver)
        {
            for (int hor = 0; hor < HORIZONTAL_LENGTH; ++hor)
            {
                if (hor == 0 || hor == HORIZONTAL_LENGTH - 1 || ver == 0 || ver == VERTICAL_LENGTH - 1)
                {
                    board[ver][hor] = new Wall();
                }
                else
                {
                    board[ver][hor] = new Unexplored();
                }
            }
        }
    }

    private void combineBoardAndMaze(BoardPeice[][] maze)
    {
        
    }

    public int getHorizontalLength()
    {
        return HORIZONTAL_LENGTH;
    }

    public int getVerticalLength()
    {
        return VERTICAL_LENGTH;
    }

    public BoardPeice getObject(int ver, int hor)
    {
        return board[ver][hor];
    }
}
