package Game_Properties;

import java.util.Random;

public final class Board 
{
    private final int HORIZONTAL_LENGTH;
    private final int VERTICAL_LENGTH;
    private BoardPeice board[][];
    private Random rand;
    private BoardPeice.Mouse mouse;
    BoardPeice cheese;

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
                board[ver][hor] = new BoardPeice();
                board[ver][hor] = board[ver][hor].new Unexplored();

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
                maze[mazeVer][mazeHor].setUnderlyingObject(maze[mazeVer][mazeHor].new Empty());
                board[ver][hor].setUnderlyingObject(maze[mazeVer][mazeHor]);
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
        board[1][1].setUnderlyingObject(board[1][1].new Empty());
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

    private void reveal(int ver, int hor)
    {
        if (board[ver][hor].getClass() == BoardPeice.Unexplored.class)
        {
            board[ver][hor] = board[ver][hor].getUnderlyingObject();
            board[ver][hor].setUnderlyingObject(board[ver][hor].new Empty());
        }
    }

    public void generateCheese()
    {
        int randX = rand.nextInt(HORIZONTAL_LENGTH - 10); //temp
        int randY = rand.nextInt(VERTICAL_LENGTH - 10);

        while (!(board[randY][randX].getClass() == BoardPeice.Unexplored.class 
        || board[randY][randX].getClass() == BoardPeice.Empty.class) 
        || board[randY][randX].getUnderlyingObject().getClass() != BoardPeice.Empty.class)
        {
            randX = rand.nextInt(HORIZONTAL_LENGTH);
            randY =  rand.nextInt(VERTICAL_LENGTH);
        }

        cheese = new BoardPeice();
        board[randY][randX] = cheese.new Cheese();
        cheese.setCoordY(randY);
        cheese.setCoordX(randX);
    }

    public void revealAroundMouse()
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

                if (newX >= 0 && newY >= 0 && newX < HORIZONTAL_LENGTH && newY < VERTICAL_LENGTH)
                {
                    reveal(newY, newX);
                }
            }
        }
    }

    public void revealAll()
    {
        for (int ver = 0; ver < VERTICAL_LENGTH - 1; ++ver)
        {
            for (int hor = 0; hor < HORIZONTAL_LENGTH; ++hor)
            {
                reveal(ver, hor);
            }
        }
    }

    public void removeCheese()
    {
        board[cheese.getCoordY()][cheese.getCoordX()].setUnderlyingObject(board[cheese.getCoordY()][cheese.getCoordX()].new Empty());
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
        return mouse.getCoordX();
    }

    public int getMouseCoordinateY()
    {
        return mouse.getCoordY();
    }

    public int getCheeseCoordinateX()
    {
        return cheese.getCoordX();
    }

    public int getCheeseCoordinateY()
    {
        return cheese.getCoordY();
    }

    public void setDeadObject(int ver, int hor)
    {
        board[ver][hor] = board[ver][hor].new Dead();
    }

    public void mouseMoveUp()
    {
        int oldX = mouse.getCoordX();
        int oldY = mouse.getCoordY();
        board[oldY][oldX] = mouse.getUnderlyingObject();
        mouse.setUnderlyingObject(board[mouse.getCoordY() - 1][mouse.getCoordX()]);
        board[mouse.getCoordY() - 1][mouse.getCoordX()] = mouse;
        mouse.setCoordY(mouse.getCoordY() - 1);
    }

    public void mouseMoveDown()
    {
        int oldX = mouse.getCoordX();
        int oldY = mouse.getCoordY();
        board[oldY][oldX] = mouse.getUnderlyingObject();
        mouse.setUnderlyingObject(board[mouse.getCoordY() + 1][mouse.getCoordX()]);
        board[mouse.getCoordY() + 1][mouse.getCoordX()] = mouse;
        mouse.setCoordY(mouse.getCoordY() + 1);
    }

    public void mouseMoveRight()
    {
        int oldX = mouse.getCoordX();
        int oldY = mouse.getCoordY();
        board[oldY][oldX] = mouse.getUnderlyingObject();
        mouse.setUnderlyingObject(board[mouse.getCoordY()][mouse.getCoordX() + 1]);
        board[mouse.getCoordY()][mouse.getCoordX() + 1] = mouse;
        mouse.setCoordX(mouse.getCoordX() + 1);
    }

    public void mouseMoveLeft()
    {
        int oldX = mouse.getCoordX();
        int oldY = mouse.getCoordY();
        board[oldY][oldX] = mouse.getUnderlyingObject();
        mouse.setUnderlyingObject(board[mouse.getCoordY()][mouse.getCoordX() - 1]);
        board[mouse.getCoordY()][mouse.getCoordX() - 1] = mouse;
        mouse.setCoordX(mouse.getCoordX() - 1);
    }
}