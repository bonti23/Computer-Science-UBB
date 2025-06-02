package bonti.basket.services.rest;

import bonti.model.Game;
import bonti.RepositoryGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/basket/games")
public class BasketGameController {

    @Autowired
    private RepositoryGame repositoryGame;

    @RequestMapping(method = RequestMethod.GET)
    public Game[] getAll() {
        System.out.println("Get all games ...");
        List<Game> gameList = repositoryGame.findAll();
        return gameList.toArray(new Game[0]);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Game> getById(@PathVariable Long id) {
        return repositoryGame.findById(id)
                .map(game -> ResponseEntity.ok(game))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @RequestMapping(method = RequestMethod.POST)
    public Game create(@RequestBody Game game){
        repositoryGame.save(game);
        System.out.println("Result added");
        return game;
    }


    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Game updatedGame) {
        System.out.println("Updating game ...");
        if (!repositoryGame.existsById(id)) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        updatedGame.setId(id);
        repositoryGame.save(updatedGame);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @RequestMapping (value="/{id}",method=RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        System.out.println("Deleting game ... " + id);
        try {
            repositoryGame.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("Ctrl Delete game exception");
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}