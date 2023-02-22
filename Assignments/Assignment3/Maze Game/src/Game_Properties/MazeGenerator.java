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

    public void generateMaze() 
    {
        for (int ver = 0; ver < height; ++ver) 
        {
            for (int hor = 0; hor < width; ++hor) 
            {
                maze[ver][hor] = new Wall();
            }
        }

        int startX = rand.nextInt(width - 2);
        int startY = rand.nextInt(height - 2);
        maze[startY][startX] = new Unexplored();
        carveMaze(startX, startY);
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

    public void printMaze()
    {
        for (int ver = 1; ver < height - 1; ++ver) 
        {
            for (int hor = 1; hor < width - 1; ++hor) 
            {
                System.out.print(maze[ver][hor].getICON());
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) 
    {
        MazeGenerator maze = new MazeGenerator(18, 13);
        maze.generateMaze();
        maze.printMaze();
    }
}