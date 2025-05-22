package bonti.start;

import bonti.model.Game;
import bonti.rest.client.GameClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class StartRestClient {
    private final static GameClient gameClient = new GameClient();

    public static void main(String[] args) {
        Game game = new Game(null, "U-Cluj", "CFR", "2025-05-21", 120f, "FINAL", 20000);

        show(() -> {
            try {
                System.out.println(gameClient.create(game));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        show(() -> {
            Game[] games;
            try {
                games = gameClient.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            for (Game g : games) {
                System.out.println(g.getTeamA() + " vs " + g.getTeamB());
            }
        });

        show(() -> {
            try {
                System.out.println(gameClient.getByIdentityKey(11L));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void show(Runnable task){
        try{
            task.run();
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
    }
}
