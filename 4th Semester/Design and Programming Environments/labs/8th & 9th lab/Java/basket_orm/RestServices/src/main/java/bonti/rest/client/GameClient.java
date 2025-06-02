package bonti.rest.client;

import bonti.model.Game;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class GameClient {
    public static final String URL = "http://localhost:8080/basket/games";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) throws Exception {
        try {
            return callable.call();
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Connection error while accessing the resource", e);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Client error: " + e.getStatusCode(), e);
        }
    }

    public Game[] getAll() throws Exception {
        return execute(() -> restTemplate.getForObject(URL, Game[].class));
    }

    public Game getByIdentityKey(Long id) throws Exception {
        return execute(() -> restTemplate.getForObject(URL + "/" + id, Game.class));
    }

    public Game create(@RequestBody Game game) throws Exception {
        return execute(() -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Game> request = new HttpEntity<>(game, headers);
            return restTemplate.postForObject(URL, request, Game.class);
        });
    }


    public void update(Game game) throws Exception {
        execute(() -> {
            HttpEntity<Game> requestUpdate = new HttpEntity<>(game);
            restTemplate.exchange(URL + "/" + game.getId(), HttpMethod.PUT, requestUpdate, Void.class);
            return null;
        });
    }

    public void delete(Long id) throws Exception {
        execute(() -> {
            restTemplate.delete(URL + "/" + id);
            return null;
        });
    }
}