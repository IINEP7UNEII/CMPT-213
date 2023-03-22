package WebApp.Controllers;

import WebApp.restapi.*;
import WebApp.model.*;
import static WebApp.model.MoveDirection.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description: This GameController class is the main class where the REST API is implemented.
 * It handles the GET/POST mapping for our game and send certain requests and mappings for the front
 * end to use the returned information to display the game in the browser.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */

@RestController
public class GameController
{
    private ArrayList<MazeGame> games = new ArrayList<>();
    private AtomicLong nextGameID = new AtomicLong();

    @GetMapping("/api/about")
    @ResponseStatus(HttpStatus.OK)
    public String getAbout()
    {
        return "Daniel Tolsky";
    }

    @GetMapping("/api/games")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<ApiGameWrapper> getGameList()
    {
        ArrayList<ApiGameWrapper> tempApiGames = new ArrayList<>();
        for (int count = 0; count < games.size(); ++count)
        {
            tempApiGames.add(new ApiGameWrapper(games.get(count), count));
        }

        return tempApiGames;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createNewGame()
    {
        MazeGame game = new MazeGame();
        ApiGameWrapper apiGame = new ApiGameWrapper(game, nextGameID.incrementAndGet());
        games.add(game);
        return apiGame;
    }

    @GetMapping("/api/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiGameWrapper getGame(@PathVariable("id") long gameID)
    {
        for (int count = 0; count < games.size(); ++count)
        {
            ApiGameWrapper apiGame = new ApiGameWrapper(games.get(count), count + 1);
            if (apiGame.gameNumber == gameID)
            {
                return apiGame;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Game not found");
    }

    @GetMapping("/api/games/{id}/board")
    @ResponseStatus(HttpStatus.OK)
    public ApiBoardWrapper getBoard(@PathVariable("id") long gameID)
    {
        for (int count = 0; count < games.size(); ++count)
        {
            ApiGameWrapper apiGame = new ApiGameWrapper(games.get(count), count + 1);
            ApiBoardWrapper apiBoard = new ApiBoardWrapper(games.get(count));
            if (apiGame.gameNumber == gameID)
            {
                return apiBoard;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Game not found");
    }

    @PostMapping("/api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void newMove(@PathVariable("id") long gameID, @RequestBody String move)
    {
        for (int count = 0; count < games.size(); ++count)
        {
            ApiGameWrapper apiGame = new ApiGameWrapper(games.get(count), count + 1);
            if (apiGame.gameNumber == gameID)
            {
                if (stringToMoveDirection(move) == null)
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR: Invalid move, please select a valid move");
                }
                else if (stringToMoveDirection(move).equals(MOVE_CATS))
                {
                    games.get(count).doCatMoves();
                    return;
                }
                else if (games.get(count).isValidPlayerMove(stringToMoveDirection(move)))
                {
                    games.get(count).recordPlayerMove(stringToMoveDirection(move));
                    return;
                }
                else
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR: Invalid move, mouse will move into wall");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Game not found");
    }

    @PostMapping("/api/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cheats(@PathVariable("id") long gameID, @RequestBody String cheat)
    {
        for (int count = 0; count < games.size(); ++count)
        {
            ApiGameWrapper apiGame = new ApiGameWrapper(games.get(count), count + 1);
            if (apiGame.gameNumber == gameID)
            {
                switch (cheat) {
                    case "1_CHEESE" -> {
                        games.get(count).setNumberCheeseToCollect(1);
                        return; }
                    case "SHOW_ALL" -> {
                        games.get(count).getMaze().makeAllCellsVisible();
                        return; }
                    default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR: Invalid cheat selected");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Game not found");
    }

    private MoveDirection stringToMoveDirection(String move)
    {
        return switch (move) {
            case "MOVE_UP" -> MOVE_UP;
            case "MOVE_DOWN" -> MOVE_DOWN;
            case "MOVE_LEFT" -> MOVE_LEFT;
            case "MOVE_RIGHT" -> MOVE_RIGHT;
            case "MOVE_CATS" -> MOVE_CATS;
            default -> null;
        };
    }

    private void makeAllVisible(ApiBoardWrapper board)
    {
        for (int countY = 0; countY < board.boardHeight; ++countY)
        {
            for (int countX = 0; countX < board.boardWidth; ++countX)
            {
                board.isVisible[countY][countX] = true;
            }
        }
    }
}
