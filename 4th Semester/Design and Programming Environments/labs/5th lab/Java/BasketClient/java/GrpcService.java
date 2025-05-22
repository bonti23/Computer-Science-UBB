package ro.mpp2024;
import org.example.clientfx.grpc.*;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ro.mpp2024.Game;
import ro.mpp2024.IService;
import ro.mpp2024.Purchase;
import ro.mpp2024.User;
import ro.mpp2024.IObserver;

import java.util.ArrayList;
import java.util.List;

public class GrpcService implements IService {

    private final BasketServiceGrpc.BasketServiceBlockingStub stub;

    public GrpcService(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        stub = BasketServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public List<User> findAllUsers() {
        Empty request = Empty.newBuilder().build();
        BasketServiceProto.UserList response = stub.findAllUsers(request);

        List<User> result = new ArrayList<>();
        for (BasketServiceProto.UserDTO u : response.getUsersList()) {
            result.add(new User(
                    u.getIdentitykey(),
                    u.getName(),
                    u.getUsername(),
                    u.getPassword()
            ));
        }

        return result;
    }

    @Override
    public List<Purchase> findAllPurchases() {
        Empty request = Empty.newBuilder().build();

        BasketServiceProto.PurchaseResponse response = stub.findAllPurchases(request);

        List<Purchase> result = new ArrayList<>();
        if (response.hasPurchases()) {
            for (BasketServiceProto.PurchaseDTO p : response.getPurchases().getPurchasesList()) {
                result.add(new Purchase(
                        p.getIdentitykey(),
                        p.getClient(),
                        p.getGame(),
                        p.getSeats(),
                        p.getAddress()
                ));
            }
        }
        return result;
    }


    @Override
    public User login(String username, String password, IObserver observer) throws Exception {
        BasketServiceProto.UserDTO request = BasketServiceProto.UserDTO.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        BasketServiceProto.DefaultResponse response = stub.login(request);

        if (response.getSuccess()) {
            BasketServiceProto.UserDTO userProto = response.getUser();
            return new User(
                    userProto.getIdentitykey(),
                    userProto.getName(),
                    userProto.getUsername(),
                    userProto.getPassword()
            );
        } else {
            throw new Exception("Login failed: " + response.getError());
        }
    }


    @Override
    public List<Game> all_games() {
        BasketServiceProto.GameResponse response = stub.showGames(Empty.newBuilder().build());

        List<Game> result = new ArrayList<>();
        if (response.hasGames()) {
            for (BasketServiceProto.GameDTO g : response.getGames().getGamesList()) {
                result.add(new Game(
                        g.getIdentitykey(),
                        g.getTeamA(),
                        g.getTeamB(),
                        g.getDate(),
                        g.getPrice(),
                        ro.mpp2024.Type.valueOf(g.getType().name()),
                        g.getSeats()
                ));
            }
        }
        return result;
    }

    @Override
    public void logout(int id, IObserver client) {
        System.out.println("User logged out with ID: " + id);
    }

    @Override
    public void signup(String name, String username, String password) {
        BasketServiceProto.UserDTO request = BasketServiceProto.UserDTO.newBuilder()
                .setName(name)
                .setUsername(username)
                .setPassword(password)
                .build();

        BasketServiceProto.DefaultResponse response = stub.signup(request);

        if (!response.hasSuccess() || !response.getSuccess()) {
            throw new RuntimeException("Signup failed: " + response.getError());
        }
    }

    @Override
    public List<Purchase> find_by_client(String client) {
        BasketServiceProto.UsernameRequest request = BasketServiceProto.UsernameRequest.newBuilder()
                .setUsername(client)
                .build();

        BasketServiceProto.PurchaseResponse response = stub.findPurchasesByClient(request);

        List<Purchase> result = new ArrayList<>();
        if (response.hasPurchases()) {
            for (BasketServiceProto.PurchaseDTO p : response.getPurchases().getPurchasesList()) {
                result.add(new Purchase(p.getIdentitykey(), p.getClient(), p.getGame(), p.getSeats(), p.getAddress()));
            }
        }
        return result;
    }

    @Override
    public List<Game> showcase_games_by_type(String type) {
        BasketServiceProto.GameType gameType;
        try {
            gameType = BasketServiceProto.GameType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid game type: " + type);
        }

        BasketServiceProto.GameTypeRequest request = BasketServiceProto.GameTypeRequest.newBuilder()
                .setType(gameType)
                .build();

        BasketServiceProto.GameResponse response = stub.showcaseGamesByType(request);

        List<Game> result = new ArrayList<>();
        if (response.hasGames()) {
            for (BasketServiceProto.GameDTO g : response.getGames().getGamesList()) {
                result.add(new Game(g.getIdentitykey(), g.getTeamA(), g.getTeamB(), g.getDate(),
                        g.getPrice(), ro.mpp2024.Type.valueOf(g.getType().name()), g.getSeats()));
            }
        }
        return result;
    }

    @Override
    public void add_purchase(Purchase purchase) throws Exception {
        BasketServiceProto.PurchaseDTO request = BasketServiceProto.PurchaseDTO.newBuilder()
                .setIdentitykey(purchase.get_identitykey())
                .setClient(purchase.getClient())
                .setGame(purchase.getGame())
                .setSeats(purchase.getSeats())
                .setAddress(purchase.getAddress())
                .build();

        BasketServiceProto.DefaultResponse response = stub.addPurchase(request);

        if (response.hasSuccess() && !response.getSuccess()) {
            throw new Exception("Purchase failed: " + response.getError());
        }
    }

    @Override
    public Game update_seats(Game game, int seats) {
        game.setSeats(seats);
        return game;
    }

    @Override
    public void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new RuntimeException("Password too short. Must be at least 8 characters.");
        }
    }

    @Override
    public List<Game> show_games() {
        BasketServiceProto.GameResponse response = stub.showGames(Empty.newBuilder().build());

        List<Game> result = new ArrayList<>();
        if (response.hasGames()) {
            for (BasketServiceProto.GameDTO g : response.getGames().getGamesList()) {
                result.add(new Game(g.getIdentitykey(), g.getTeamA(), g.getTeamB(), g.getDate(),
                        g.getPrice(), ro.mpp2024.Type.valueOf(g.getType().name()), g.getSeats()));
            }
        }
        return result;
    }

    @Override
    public boolean find_by_username(String username) {
        BasketServiceProto.UserDTO request = BasketServiceProto.UserDTO.newBuilder()
                .setUsername(username)
                .build();

        BasketServiceProto.DefaultResponse response = stub.login(request);
        return response.hasSuccess() && response.getSuccess();
    }
}
