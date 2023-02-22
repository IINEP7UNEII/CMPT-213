package Game_Properties;

import java.util.Random;

public class Board 
{
    private final int HORIZONTAL_LENGTH;
    private final int VERTICAL_LENGTH;
    private BoardPeice board[][];
    private Random rand;

    public Board()
    {
        HORIZONTAL_LENGTH = 20;
        VERTICAL_LENGTH = 15;
        board = new BoardPeice[VERTICAL_LENGTH][HORIZONTAL_LENGTH];
        rand = new Random();

        generateBasicBoard();
        generateBoardWithMaze();
        addMouse();
        addCats();
        generateCheese();
    }

    private void generateBasicBoard()
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

    private void generateBoardWithMaze()
    {
        MazeGenerator mazeGen = new MazeGenerator(HORIZONTAL_LENGTH, VERTICAL_LENGTH);
        BoardPeice[][] maze = mazeGen.generateMaze();

        int mazeVer = 0;
        int mazeHor = 0;

        for (int ver = 0; ver < VERTICAL_LENGTH - 1; ++ver)
        {
            for (int hor = 0; hor < HORIZONTAL_LENGTH; ++hor)
            {
                board[ver][hor] = maze[mazeVer][mazeHor];
                ++mazeHor;
            }
            mazeHor = 0;
            ++mazeVer;
        }
    }

    private void addMouse()
    {
        board[1][1] = new Mouse(); //add mouse to top left
    }

    private void addCats()
    {
        addBottomLeftCat();
        addBottomRightCat();
        addTopRightCat();
    }

    private void addBottomLeftCat()
    {
        int x = 1;
        int y = VERTICAL_LENGTH - 2;
        if (board[y][x].getICON() != '#')
        {
            board[y][x] = new Cat();
        }
        else
        {
            while (board[y][x].getICON() == '#') // bottom right cat
            {
                x += rand.nextInt(2);
                y -= rand.nextInt(2);
            }
            board[y][x] = new Cat();
        }
    }

    private void addBottomRightCat()
    {
        int x = HORIZONTAL_LENGTH - 2;
        int y = VERTICAL_LENGTH - 2;
        if (board[y][x].getICON() != '#')
        {
            board[y][x] = new Cat();
        }
        else
        {
            while (board[y][x].getICON() == '#') // bottom left cat
            {
                x -= rand.nextInt(2);
                y -= rand.nextInt(2);
            }
            board[y][x] = new Cat();
        }
    }

    private void addTopRightCat()
    {
        int x = HORIZONTAL_LENGTH - 2;
        int y = 1;
        if (board[y][x].getICON() != '#')
        {
            board[y][x] = new Cat();
        }
        else
        {
            while (board[y][x].getICON() == '#') // top right cat
            {
                x -= rand.nextInt(2);
                y += rand.nextInt(2);
            }
            board[y][x] = new Cat();
        }
    }

    private void generateCheese()
    {
        int randX = rand.nextInt(HORIZONTAL_LENGTH);
        int randY =  rand.nextInt(VERTICAL_LENGTH);

        while (board[randY][randX].getICON() != '.')
        {
            randX = rand.nextInt(HORIZONTAL_LENGTH);
            randY =  rand.nextInt(VERTICAL_LENGTH);
        }

        board[randY][randX] = new Cheese();
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
