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
    MazeGame game;
    public long gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;
    public ApiBoardWrapper board;

    public ApiGameWrapper()
    {
        game = new MazeGame();
        isGameWon = false;
        isGameLost = false;
        numCheeseFound = game.getNumberCheeseCollected();
        numCheeseGoal = game.getNumberCheeseToCollect();
        board = new ApiBoardWrapper(game);
    }

    public MazeGame getGame()
    {
        return game;
    }
    public ApiBoardWrapper getBoard()
    {
        return board;
    }
}
