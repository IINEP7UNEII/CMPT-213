package WebApp.restapi;

import WebApp.model.CellLocation;

/**
 * Description: Again, similar to the ApiGameWrapper and ApiBoardWrapper classes, the ApiLocationWrapper
 * class wraps the CellLocation class from the Assignment 3 MazeGame and allow the REST API to use certain methods from the model.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
public class ApiLocationWrapper
{
    public int x;
    public int y;

    public ApiLocationWrapper(CellLocation loc)
    {
        x = loc.getX();
        y = loc.getY();
    }

    public void setX(int newX)
    {
        x = newX;
    }

    public void setY(int newY)
    {
        y = newY;
    }
}
