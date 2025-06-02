package bonti.rest.client;

import bonti.RepositoryGame;
import bonti.RepositoryPurchase;
import bonti.RepositoryUser;
import bonti.model.Game;
import bonti.model.Purchase;
import bonti.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/basket")
@CrossOrigin(origins = {"http://localhost:3000"})
public class EmployeeController {
    private static final long EXPIRATION_TIME = 360000;

    @Autowired
    private RepositoryUser repo_user;
    @Autowired
    private RepositoryGame repo_game;

    @Autowired
    private RepositoryPurchase repo_purchase;

    public static String generateJWToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(JWTUtils.getKey(), SignatureAlgorithm.HS512)
                .compact()
                .trim();
        System.out.println("JWT token generat: " + token);
        return token;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.username().trim();
        String password = loginRequest.password().trim();

        System.out.println("USERNAME primit: '" + username + "'");
        System.out.println("PASSWORD primit: '" + password + "'");

        User persoana = repo_user.findByUsername(username).orElse(null);

        if (persoana == null) {
            System.out.println("Failed login for " + username);
            return ResponseEntity.status(401).body("Failed login. Check your data.");
        }
        if(persoana.getPassword().equals(password)){
            System.out.println("Successful login. Generated token...");
            String token = generateJWToken(username);
            return ResponseEntity.ok(token);
        }
        else{
            System.out.println("Failed login for " + username);
            return ResponseEntity.status(401).body("Failed login. Check your data.");
        }


    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (repo_user.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        repo_user.save(user);
        return ResponseEntity.ok(Map.of("message", "User created"));
    }

    @PostMapping("/purchases")
    public ResponseEntity<?> addPurchase(@RequestBody PurchaseRequest req) {
        Game game = repo_game.findById(req.gameId()).orElse(null);
        if (game == null) {
            return ResponseEntity.status(404).body("Game not found");
        }

        if (req.seats() <= 0 || req.seats() > game.getSeats()) {
            return ResponseEntity.status(400).body("Invalid number of seats");
        }

        game.setSeats(game.getSeats() - req.seats());

        Purchase purchase = new Purchase();
        purchase.setClient(req.clientName());
        purchase.setAddress(req.address());
        purchase.setSeats(req.seats());
        purchase.setGame(game.getId());

        repo_game.save(game);
        repo_purchase.save(purchase);

        return ResponseEntity.ok("Purchase successful");
    }
}