package WebApp.restapi;

import WebApp.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: Similar to the ApiGameWrapper class, the ApiBoardWrapper class wraps the Maze class from the Assignment 3
 * MazeGame and allow the REST API to use certain methods from the model.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
public class ApiBoardWrapper 
{
    public int boardWidth;
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

//     Accept whatever object(s) you need to populate this object.
    public ApiBoardWrapper(MazeGame game)
    {
        boardWidth = game.getMaze().getWidth();
        boardHeight = game.getMaze().getHeight();
        mouseLocation = new ApiLocationWrapper(game.getMouseLocation());
        cheeseLocation = new ApiLocationWrapper(game.getCheeseLocation());
        catLocations = getCatLocations(game);
        hasWalls = isWallArray(game);
        isVisible = isVisibleArray(game);
    }

    public List<ApiLocationWrapper> getCatLocations(MazeGame game)
    {
        List<ApiLocationWrapper> catsLoc = new ArrayList<ApiLocationWrapper>();
        for (Cat cat : game.getCats())
        {
            ApiLocationWrapper loc = new ApiLocationWrapper(cat.getLocation());
            catsLoc.add(loc);
        }
        return catsLoc;
    }

    private boolean[][] isWallArray(MazeGame game)
    {
        CellLocation tempLoc = new CellLocation();
        boolean[][] isWallArr = new boolean[boardHeight][boardWidth];

        for (int countY = 0; countY < boardHeight; ++countY)
        {
            for (int countX = 0; countX < boardWidth; ++countX)
            {
                tempLoc.setX(countX);
                tempLoc.setY(countY);
                isWallArr[countY][countX] = game.getMaze().isCellAWall(tempLoc);
            }
        }
        return isWallArr;
    }

    private boolean[][] isVisibleArray(MazeGame game)
    {
        CellLocation tempLoc = new CellLocation();
        boolean[][] isVisibleArr = new boolean[boardHeight][boardWidth];

        for (int countY = 0; countY < boardHeight; ++countY)
        {
            for (int countX = 0; countX < boardWidth; ++countX)
            {
                tempLoc.setX(countX);
                tempLoc.setY(countY);
                isVisibleArr[countY][countX] = game.getMaze().getCellState(tempLoc).isVisible();
            }
        }
        return isVisibleArr;
    }
}
