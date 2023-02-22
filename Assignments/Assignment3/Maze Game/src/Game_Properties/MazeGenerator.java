package Game_Properties;

import java.util.Random;

public class MazeGenerator 
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
                maze[ver][hor] = new Wall();
            }
        }

        int startX = 1; // start at top left corner
        int startY = 1;
        maze[startY][startX] = new Unexplored();
        carveMaze(startX, startY);
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

            if ((nextX >= 0 && nextX < width - 1) && (nextY >= 0 && nextY < height - 1) && maze[nextY][nextX].getICON() == '#') 
            {
                maze[y + dirY][x + dirX] = new Unexplored();
                maze[nextY][nextX] = new Unexplored();
                carveMaze(nextX, nextY);
            }
        }
        fixMaze();
    }

    private void fixMaze() 
    {
        for (int ver = 1; ver < height - 1; ++ver)
        {
            if (maze[ver + 1][width - 2].getICON() == '#' && maze[ver - 1][width - 2].getICON() == '#')
            {
                int toPlace = rand.nextInt(2);

                if (toPlace == 1)
                {
                    maze[ver][width - 2] = new Unexplored();
                }
                else if (maze[ver][width - 3].getICON() == '#')
                {
                    maze[ver][width - 2] = new Wall();
                }
            }
        }
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