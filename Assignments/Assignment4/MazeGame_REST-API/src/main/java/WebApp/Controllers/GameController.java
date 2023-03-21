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
    private ArrayList<ApiGameWrapper> games = new ArrayList<>();
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
        return games;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createNewGame()
    {
        ApiGameWrapper game = new ApiGameWrapper();
        game.gameNumber = nextGameID.incrementAndGet();
        games.add(game);
        return game;
    }

    @GetMapping("/api/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiGameWrapper getGame(@PathVariable("id") long gameID)
    {
        for (ApiGameWrapper game : games)
        {
            if (game.gameNumber == gameID)
            {
                return game;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Game not found");
    }

    @GetMapping("/api/games/{id}/board")
    @ResponseStatus(HttpStatus.OK)
    public ApiBoardWrapper getBoard(@PathVariable("id") long gameID)
    {
        for (ApiGameWrapper game : games)
        {
            if (game.gameNumber == gameID)
            {
                return game.board;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Game not found");
    }

    @PostMapping("/api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void newMove(@PathVariable("id") long gameID, @RequestBody String move)
    {
        for (ApiGameWrapper game : games)
        {
            if (game.gameNumber == gameID)
            {
                if (stringToMoveDirection(move) == null)
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR: Invalid move, please select a valid move");
                }
                else if (game.getGame().isValidPlayerMove(stringToMoveDirection(move)))
                {
                    game.getGame().recordPlayerMove(stringToMoveDirection(move));
                    game.getBoard().mouseLocation = new ApiLocationWrapper(game.getGame().getMouseLocation());
                    return;
                }
                else if (move.equals("MOVE_CATS"))
                {
                    game.getGame().doCatMoves();
                    game.getBoard().catLocations = catApiLocations(game.getGame().getCatLocations());
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
        for (ApiGameWrapper game : games)
        {
            if (game.gameNumber == gameID)
            {
                switch (cheat) {
                    case "1_CHEESE" -> {
                        game.getGame().setNumberCheeseToCollect(1);
                        game.numCheeseGoal = 1;
                        return; }
                    case "SHOW_ALL" -> {
                        makeAllVisible(game.getBoard());
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

    private List<ApiLocationWrapper> catApiLocations(List<CellLocation> catCells)
    {
        List<ApiLocationWrapper> tempLoc = new ArrayList<>();
        for (CellLocation cell : catCells)
        {
            ApiLocationWrapper apiLoc = new ApiLocationWrapper(cell);
            tempLoc.add(apiLoc);
        }
        return tempLoc;
    }
}
