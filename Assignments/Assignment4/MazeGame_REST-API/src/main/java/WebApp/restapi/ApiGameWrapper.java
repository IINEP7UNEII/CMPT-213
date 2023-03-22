package WebApp.restapi;

import WebApp.model.MazeGame;

/**
 * Description: This Wrapper class is used to wrap the main MazeGame class which handles most of the gameplay work of
 * the Assignment 3 MazeGame. This wrapper provides the ability for the game controller to effectively used certain methods
 * from the MazeGame class and adapt them for utilization with our REST API.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */

public class ApiGameWrapper
{
    public long gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    public ApiGameWrapper()
    {
        gameNumber = 0;
        isGameWon = false;
        isGameLost = false;
        numCheeseFound = 0;
        numCheeseGoal = 0;
    }

    public ApiGameWrapper(MazeGame game, long index)
    {
        gameNumber = index;
        isGameWon = game.hasUserWon();
        isGameLost = game.hasUserLost();
        numCheeseFound = game.getNumberCheeseCollected();
        numCheeseGoal = game.getNumberCheeseToCollect();
    }
}
