package Game_Properties;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator 
{
    private int width;
    private int height;
    private char[][] maze;

    public MazeGenerator(int width, int height) 
    {
        this.width = width;
        this.height = height;
        maze = new char[height][width];
    }

    public void generateMaze() {
        // Fill maze with walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = '#';
            }
        }
    
        // Generate maze paths using depth-first search
        Stack<int[]> stack = new Stack<>();
        int[] start = {1, 1};
        stack.push(start);
        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int x = current[0];
            int y = current[1];
            maze[y][x] = ' ';
    
            // Check adjacent cells
            List<int[]> neighbors = new ArrayList<>();
            if (x > 2 && maze[y][x-2] == '#') {
                neighbors.add(new int[]{x-2, y});
            }
            if (y > 2 && maze[y-2][x] == '#') {
                neighbors.add(new int[]{x, y-2});
            }
            if (x < width-2 && maze[y][x+2] == '#') {
                neighbors.add(new int[]{x+2, y});
            }
            if (y < height-3 && maze[y+2][x] == '#') {
                neighbors.add(new int[]{x, y+2});
            }
    
            // Choose a random neighbor and connect the cells
            if (!neighbors.isEmpty()) {
                int[] neighbor = neighbors.get(new Random().nextInt(neighbors.size()));
                int nx = neighbor[0];
                int ny = neighbor[1];
                maze[(y + ny) / 2][(x + nx) / 2] = ' ';
    
                // If the neighbor is in the right-most column, add a wall to the right
                if (nx == width - 2) {
                    maze[ny][(nx+1)] = '#';
                }
    
                stack.push(current);
                stack.push(neighbor);
            }
        }
    }

    public void printMaze() 
    {
        for (int i = 0; i < height; i++) 
        {
            for (int j = 0; j < width; j++) 
            {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) 
    {
        MazeGenerator mazeGenerator = new MazeGenerator(18, 13);
        mazeGenerator.generateMaze();
        mazeGenerator.printMaze();
    }
}
