package Game_Properties;

import java.util.Random;

/**
* Description: The MazeGenerator class implements a maze generator for the Maze game. It includes functions which generate a random maze
* and uses objects from the BoardPeice class to map out a maze with Walls and Empty spaces. This class also fixes some 
* generation issues if they appear in the randomly generated maze.
*
* @author Daniel Tolsky
* @version 1.0
*/

public final class MazeGenerator 
{
    private BoardPeice[][] maze;
    private int width;
    private int height;
    private Random rand;

    public MazeGenerator(final int width, final int height) 
    {
        this.width = width;
        this.height = height;
        maze = new BoardPeice[height][width];
        rand = new Random();
    }

    public BoardPeice[][] generateMaze() 
    {
        for (int ver = 0; ver < height; ++ver) 
        {
            for (int hor = 0; hor < width; ++hor) 
            {
                maze[ver][hor] = new BoardPeice('0');
                maze[ver][hor] = maze[ver][hor].new Wall();
                maze[ver][hor].setUnderlyingObject(maze[ver][hor].new Empty());
            }
        }

        int startX = 1;
        int startY = 1;

        maze[startY][startX] = maze[startY][startX].new Empty();
        carveMaze(startX, startY);
        fixMazeLoops();
        return maze;
    }

    private void carveMaze(int x, int y) 
    {
        int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        chooseDirection(directions);

        for (int[] dir : directions) 
        {
            int dirX = dir[0];
            int dirY = dir[1];
            int nextX = x + dirX * 2;
            int nextY = y + dirY * 2;

            if ((nextX >= 0 && nextX < width - 1) && (nextY >= 0 && nextY < height - 1) && maze[nextY][nextX].getClass() == BoardPeice.Wall.class) 
            {
                maze[y + dirY][x + dirX] = maze[y + dirY][x + dirX].new Empty();
                maze[nextY][nextX] = maze[nextY][nextX].new Empty();
                carveMaze(nextX, nextY);
            }
        }
        fixMazeSideWall();
    }

    private void fixMazeSideWall() 
    {
        for (int ver = 1; ver < height - 1; ++ver)
        {
            if (maze[ver + 1][width - 2].getClass() == BoardPeice.Wall.class && maze[ver - 1][width - 2].getClass() == BoardPeice.Wall.class)
            {
                int toPlace = rand.nextInt(2);

                if (toPlace == 1)
                {
                    maze[ver][width - 2] = maze[ver][width - 2].new Empty();
                }
                else if (maze[ver][width - 3].getClass() == BoardPeice.Wall.class)
                {
                    maze[ver][width - 2] = maze[ver][width - 2].new Wall();
                }
            }
        }
    }

    private void fixMazeLoops() 
    {
        int loopDensity = 20;
        int hor = rand.nextInt(width);
        int ver = rand.nextInt(height);

        for (int count = 0; count < loopDensity; ++count)
        {
            if (maze[ver][hor].getClass() != BoardPeice.Empty.class)
            {
                maze[ver][hor] = maze[ver][hor].new Empty();

                if (mazeHasFourEmpty())
                {
                    maze[ver][hor] = maze[ver][hor].new Wall();
                    ++loopDensity;
                    ++count;
                }
            }
            else
            {
                ++loopDensity;
                ++count;
            }
            hor = rand.nextInt(width);
            ver = rand.nextInt(height);
        }
    }

    private Boolean mazeHasFourEmpty()
    {
        for (int ver = 0; ver < height - 1; ++ver) 
        {
            for (int hor = 0; hor < width - 1; ++hor) 
            {
                if (maze[ver][hor].getUnderlyingObject() == null)
                {
                    maze[ver][hor].setUnderlyingObject(maze[ver][hor].new Empty());
                }
                if (maze[ver][hor + 1].getUnderlyingObject() == null)
                {
                    maze[ver][hor + 1].setUnderlyingObject(maze[ver][hor].new Empty());
                }
                if (maze[ver + 1][hor].getUnderlyingObject() == null)
                {
                    maze[ver + 1][hor].setUnderlyingObject(maze[ver][hor].new Empty());
                }
                if (maze[ver + 1][hor + 1].getUnderlyingObject() == null)
                {
                    maze[ver + 1][hor + 1].setUnderlyingObject(maze[ver][hor].new Empty());
                }

                if (maze[ver][hor].getClass() == BoardPeice.Empty.class
                && maze[ver][hor].getUnderlyingObject().getClass() == BoardPeice.Empty.class
                && maze[ver][hor + 1].getClass() == BoardPeice.Empty.class
                && maze[ver][hor + 1].getUnderlyingObject().getClass() == BoardPeice.Empty.class
                && maze[ver + 1][hor].getClass() == BoardPeice.Empty.class 
                && maze[ver + 1][hor].getUnderlyingObject().getClass() == BoardPeice.Empty.class
                && maze[ver + 1][hor + 1].getClass() == BoardPeice.Empty.class 
                && maze[ver + 1][hor + 1].getUnderlyingObject().getClass() == BoardPeice.Empty.class)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void chooseDirection(int[][] arr) 
    {
        for (int count = arr.length - 1; count > 0; --count) 
        {
            int dir = rand.nextInt(count + 1);
            int[] temp = arr[count];
            arr[count] = arr[dir];
            arr[dir] = temp;
        }
    }
}