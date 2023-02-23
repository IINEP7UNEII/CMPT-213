package Game_Properties;

import java.util.Random;

public final class Board 
{
    private final int HORIZONTAL_LENGTH;
    private final int VERTICAL_LENGTH;
    private BoardPeice board[][];
    private Random rand;
    private BoardPeice.Mouse mouse;

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
        revealAroundMouse(); //temp, later put in game loop
    }

    private void generateBasicBoard()
    {
        for (int ver = 0; ver < VERTICAL_LENGTH; ++ver)
        {
            for (int hor = 0; hor < HORIZONTAL_LENGTH; ++hor)
            {
                board[ver][hor] = new BoardPeice('0');
                board[ver][hor] = board[ver][hor].new Unexplored();
                board[ver][hor].setUnderlyingObject(board[ver][hor].new Empty());
                if (hor == 0 || hor == HORIZONTAL_LENGTH - 1 || ver == 0 || ver == VERTICAL_LENGTH - 1)
                {
                    board[ver][hor] = board[ver][hor].new Wall();
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
                board[ver][hor].setUnderlyingObject(maze[mazeVer][mazeHor]);
                //board[ver][hor] = maze[mazeVer][mazeHor];
                ++mazeHor;
            }
            mazeHor = 0;
            ++mazeVer;
        }
    }

    private void addMouse()
    {
        mouse = board[1][1].new Mouse();
        mouse.setCoordX(1);
        mouse.setCoordY(1);
        board[1][1] = mouse;
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
        if (board[y][x].getUnderlyingObject().getClass() != BoardPeice.Wall.class)
        {
            BoardPeice cat = new BoardPeice('!', x, y);
            board[y][x] = cat.new Cat();
        }
        else
        {
            while (board[y][x].getUnderlyingObject().getClass() == BoardPeice.Wall.class) // bottom right cat
            {
                x += rand.nextInt(2);
                y -= rand.nextInt(2);
            }
            BoardPeice cat = new BoardPeice('!', x, y);
            board[y][x] = cat.new Cat();
        }
    }

    private void addBottomRightCat()
    {
        int x = HORIZONTAL_LENGTH - 2;
        int y = VERTICAL_LENGTH - 2;
        if (board[y][x].getUnderlyingObject().getClass() != BoardPeice.Wall.class)
        {
            BoardPeice cat = new BoardPeice('!', x, y);
            board[y][x] = cat.new Cat();
        }
        else
        {
            while (board[y][x].getUnderlyingObject().getClass() == BoardPeice.Wall.class) // bottom left cat
            {
                x -= rand.nextInt(2);
                y -= rand.nextInt(2);
            }
            BoardPeice cat = new BoardPeice('!', x, y);
            board[y][x] = cat.new Cat();
        }
    }

    private void addTopRightCat()
    {
        int x = HORIZONTAL_LENGTH - 2;
        int y = 1;
        if (board[y][x].getUnderlyingObject().getClass() != BoardPeice.Wall.class)
        {
            BoardPeice cat = new BoardPeice('!', x, y);
            board[y][x] = cat.new Cat();
        }
        else
        {
            while (board[y][x].getUnderlyingObject().getClass() == BoardPeice.Wall.class) // top right cat
            {
                x -= rand.nextInt(2);
                y += rand.nextInt(2);
            }
            BoardPeice cat = new BoardPeice('!', x, y);
            board[y][x] = cat.new Cat();
        }
    }

    private void generateCheese()
    {
        int randX = rand.nextInt(HORIZONTAL_LENGTH);
        int randY =  rand.nextInt(VERTICAL_LENGTH);

        while (board[randY][randX].getClass() != BoardPeice.Unexplored.class)
        {
            randX = rand.nextInt(HORIZONTAL_LENGTH);
            randY =  rand.nextInt(VERTICAL_LENGTH);
        }

        BoardPeice cheese = new BoardPeice('!', randX, randY);
        board[randY][randX] = cheese.new Cheese();
    }

    private void revealAroundMouse()
    {
        for (int dirX = -1; dirX < 2; ++dirX)
        {
            for (int dirY = -1; dirY < 2; ++dirY) 
            {
                if (dirX == 0 && dirY == 0) 
                {
                    continue;
                }

                int newX = mouse.getCoordX() + dirX;
                int newY = mouse.getCoordY() + dirY;

                if (newX >= 0 && newY >= 0 && newX < VERTICAL_LENGTH && newY < HORIZONTAL_LENGTH)
                {
                    reveal(newY, newX);
                }
            }
        }
    }

    private void reveal(int ver, int hor)
    {
        if (board[ver][hor].getClass() == BoardPeice.Unexplored.class)
        {
            BoardPeice.Unexplored temp = (BoardPeice.Unexplored) board[ver][hor];
            board[ver][hor] = temp.getUnderlyingObject();
        }
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

    public int getMouseCoordinateX()
    {
        return VERTICAL_LENGTH;
    }

    public int getMouseCoordinateY()
    {
        return VERTICAL_LENGTH;
    }

    public void setMouseCoordinateX(int x)
    {
        mouse.setCoordX(x);
    }

    public void setMouseCoordinateY(int y)
    {
        mouse.setCoordX(y);
    }
}
