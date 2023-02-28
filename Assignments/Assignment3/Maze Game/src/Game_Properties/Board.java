package Game_Properties;

import java.util.Random;

/**
* Description: This Board class contains all of the properties of the game board including the peices which are relevant
* to the gameplay of the maze game. It handles the raw movement and generation of the board peices and their positions.
* The board class hands off a usable board for the MazeGame class to use to create the game environment.
*
* @author Daniel Tolsky
* @version 1.0
*/

public final class Board 
{
    private final int HORIZONTAL_LENGTH;
    private final int VERTICAL_LENGTH;
    private BoardPeice board[][];
    private Random rand;
    private BoardPeice.Mouse mouse;
    BoardPeice.Cheese cheese;
    BoardPeice.Cat cat1;
    BoardPeice.Cat cat2;
    BoardPeice.Cat cat3;

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
                    board[ver][hor].setUnderlyingObject(board[ver][hor].new Empty());
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

        while (board[y][x].getUnderlyingObject().getClass() == BoardPeice.Wall.class)
        {
            x += rand.nextInt(2);
            y -= rand.nextInt(2);
        }
        cat1 = board[y][x].new Cat();
        board[y][x] = cat1;
        cat1.setCoordY(y);
        cat1.setCoordX(x);
    }

    private void addBottomRightCat()
    {
        int x = HORIZONTAL_LENGTH - 2;
        int y = VERTICAL_LENGTH - 2;

        while (board[y][x].getUnderlyingObject().getClass() == BoardPeice.Wall.class)
        {
            x -= rand.nextInt(2);
            y -= rand.nextInt(2);
        }
        cat2 = board[y][x].new Cat();
        board[y][x] = cat2;
        cat2.setCoordY(y);
        cat2.setCoordX(x);
    }

    private void addTopRightCat()
    {
        int x = HORIZONTAL_LENGTH - 2;
        int y = 1;
        while (board[y][x].getUnderlyingObject().getClass() == BoardPeice.Wall.class)
        {
            x -= rand.nextInt(2);
            y += rand.nextInt(2);
        }
        cat3 = board[y][x].new Cat();
        board[y][x] = cat3;
        cat3.setCoordY(y);
        cat3.setCoordX(x);
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
        int randX = rand.nextInt(HORIZONTAL_LENGTH - 1);
        int randY = rand.nextInt(VERTICAL_LENGTH - 1);

        if (board[randY][randX].getUnderlyingObject() == null)
        {
            board[randY][randX].setUnderlyingObject(board[randY][randX].new Empty());
        }

        while (board[randY][randX].getClass() == BoardPeice.Wall.class 
        || board[randY][randX].getClass() == BoardPeice.Cat.class
        || board[randY][randX].getClass() == BoardPeice.Mouse.class
        || board[randY][randX].getUnderlyingObject().getClass() != BoardPeice.Empty.class)
        {
            randX = rand.nextInt(HORIZONTAL_LENGTH - 1);
            randY =  rand.nextInt(VERTICAL_LENGTH - 1);

            if (board[randY][randX].getUnderlyingObject() == null)
            {
                board[randY][randX].setUnderlyingObject(board[randY][randX].new Empty());
            }
        }

        cheese = board[randY][randX].new Cheese();
        cheese.setUnderlyingObject(board[randY][randX].new Empty());
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

    public Random getRand()
    {
        return rand;
    }

    public BoardPeice.Cat getCat1()
    {
        return cat1;
    }

    public BoardPeice.Cat getCat2()
    {
        return cat2;
    }

    public BoardPeice.Cat getCat3()
    {
        return cat3;
    }

    public Boolean catCanMoveUp(BoardPeice.Cat cat)
    {
        if ((board[cat.getCoordY() - 1][cat.getCoordX()].getClass() == BoardPeice.Unexplored.class
        && board[cat.getCoordY() - 1][cat.getCoordX()].getUnderlyingObject().getClass() == BoardPeice.Empty.class)
        || board[cat.getCoordY() - 1][cat.getCoordX()].getClass() == BoardPeice.Empty.class
        || board[cat.getCoordY() - 1][cat.getCoordX()].getClass() == BoardPeice.Cheese.class
        || board[cat.getCoordY() - 1][cat.getCoordX()].getClass() == BoardPeice.Cat.class)
        {
            return true;
        }
        return false;
    }

    public void catMoveUp(BoardPeice.Cat cat)
    {
        if (board[cat.getCoordY() - 1][cat.getCoordX()].getClass() != BoardPeice.Cat.class)
        {
            BoardPeice tempCat = board[cat.getCoordY()][cat.getCoordX()];
            if (tempCat.getUnderlyingObject() == null)
            {
                tempCat.setUnderlyingObject(tempCat.new Empty());
            }

            board[cat.getCoordY()][cat.getCoordX()] = tempCat.getUnderlyingObject();
            board[cat.getCoordY()][cat.getCoordX()].setUnderlyingObject(board[cat.getCoordY()][cat.getCoordX()].new Empty());
            tempCat.setUnderlyingObject(board[cat.getCoordY() - 1][cat.getCoordX()]);
            board[cat.getCoordY() - 1][cat.getCoordX()] = tempCat;
            cat.setCoordY(cat.getCoordY() - 1);
        }
        else
        {
            catMoveDown(cat);
        }
    }

    public Boolean catCanMoveDown(BoardPeice.Cat cat)
    {
        if ((board[cat.getCoordY() + 1][cat.getCoordX()].getClass() == BoardPeice.Unexplored.class
        && board[cat.getCoordY() + 1][cat.getCoordX()].getUnderlyingObject().getClass() == BoardPeice.Empty.class)
        || board[cat.getCoordY() + 1][cat.getCoordX()].getClass() == BoardPeice.Empty.class
        || board[cat.getCoordY() + 1][cat.getCoordX()].getClass() == BoardPeice.Cheese.class
        || board[cat.getCoordY() + 1][cat.getCoordX()].getClass() == BoardPeice.Cat.class)
        {
            return true;
        }
        return false;
    }

    public void catMoveDown(BoardPeice.Cat cat)
    {
        if (board[cat.getCoordY() + 1][cat.getCoordX()].getClass() != BoardPeice.Cat.class)
        {
            BoardPeice tempCat = board[cat.getCoordY()][cat.getCoordX()];
            if (tempCat.getUnderlyingObject() == null)
            {
                tempCat.setUnderlyingObject(tempCat.new Empty());
            }
            
            board[cat.getCoordY()][cat.getCoordX()] = tempCat.getUnderlyingObject();
            board[cat.getCoordY()][cat.getCoordX()].setUnderlyingObject(board[cat.getCoordY()][cat.getCoordX()].new Empty());
            tempCat.setUnderlyingObject(board[cat.getCoordY() + 1][cat.getCoordX()]);
            board[cat.getCoordY() + 1][cat.getCoordX()] = tempCat;
            cat.setCoordY(cat.getCoordY() + 1);
        }
        else
        {
            catMoveUp(cat);
        }
    }

    public Boolean catCanMoveRight(BoardPeice.Cat cat)
    {
        if ((board[cat.getCoordY()][cat.getCoordX() + 1].getClass() == BoardPeice.Unexplored.class
        && board[cat.getCoordY()][cat.getCoordX() + 1].getUnderlyingObject().getClass() == BoardPeice.Empty.class)
        || board[cat.getCoordY()][cat.getCoordX() + 1].getClass() == BoardPeice.Empty.class
        || board[cat.getCoordY()][cat.getCoordX() + 1].getClass() == BoardPeice.Cheese.class
        || board[cat.getCoordY()][cat.getCoordX() + 1].getClass() == BoardPeice.Cat.class)
        {
            return true;
        }
        return false;
    }

    public void catMoveRight(BoardPeice.Cat cat)
    {
        if (board[cat.getCoordY()][cat.getCoordX() + 1].getClass() != BoardPeice.Cat.class)
        {
            BoardPeice tempCat = board[cat.getCoordY()][cat.getCoordX()];
            if (tempCat.getUnderlyingObject() == null)
            {
                tempCat.setUnderlyingObject(tempCat.new Empty());
            }

            board[cat.getCoordY()][cat.getCoordX()] = tempCat.getUnderlyingObject();
            board[cat.getCoordY()][cat.getCoordX()].setUnderlyingObject(board[cat.getCoordY()][cat.getCoordX()].new Empty());
            tempCat.setUnderlyingObject(board[cat.getCoordY()][cat.getCoordX() + 1]);
            board[cat.getCoordY()][cat.getCoordX() + 1] = tempCat;
            cat.setCoordX(cat.getCoordX() + 1);
        }
        else
        {
            catMoveLeft(cat);
        }
    }

    public Boolean catCanMoveLeft(BoardPeice.Cat cat)
    {
        if ((board[cat.getCoordY()][cat.getCoordX() - 1].getClass() == BoardPeice.Unexplored.class
        && board[cat.getCoordY()][cat.getCoordX() - 1].getUnderlyingObject().getClass() == BoardPeice.Empty.class)
        || board[cat.getCoordY()][cat.getCoordX() - 1].getClass() == BoardPeice.Empty.class
        || board[cat.getCoordY()][cat.getCoordX() - 1].getClass() == BoardPeice.Cheese.class
        || board[cat.getCoordY()][cat.getCoordX() - 1].getClass() == BoardPeice.Cat.class)
        {
            return true;
        }
        return false;
    }

    public void catMoveLeft(BoardPeice.Cat cat)
    {
        if (board[cat.getCoordY()][cat.getCoordX() - 1].getClass() != BoardPeice.Cat.class)
        {
            BoardPeice tempCat = board[cat.getCoordY()][cat.getCoordX()];
            if (tempCat.getUnderlyingObject() == null)
            {
                tempCat.setUnderlyingObject(tempCat.new Empty());
            }

            board[cat.getCoordY()][cat.getCoordX()] = tempCat.getUnderlyingObject();
            board[cat.getCoordY()][cat.getCoordX()].setUnderlyingObject(board[cat.getCoordY()][cat.getCoordX()].new Empty());
            tempCat.setUnderlyingObject(board[cat.getCoordY()][cat.getCoordX() - 1]);
            board[cat.getCoordY()][cat.getCoordX() - 1] = tempCat;
            cat.setCoordX(cat.getCoordX() - 1);
        }
        else
        {
            catMoveRight(cat);
        }
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