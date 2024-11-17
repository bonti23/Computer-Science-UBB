package ubb.scs.map;

import ubb.scs.map.domain.Friendship;
import ubb.scs.map.domain.Tuple;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.FriendshipValidator;
import ubb.scs.map.domain.validators.UtilizatorValidator;
import ubb.scs.map.domain.validators.ValidationException;
import ubb.scs.map.repository.database.FriendshipDBRepository;
import ubb.scs.map.repository.database.UtilizatorDBRepository;
import ubb.scs.map.repository.memory.InMemoryRepository;
import ubb.scs.map.service.SocialNetwork;
import ubb.scs.map.ui.Console;
/*
public class Main {

    public static void main(String[] args) {


        InMemoryRepository<Long, Utilizator> repoUser = new InMemoryRepository<>(new UtilizatorValidator());
        InMemoryRepository<Long, Friendship> repoFriendship = new InMemoryRepository<>(new FriendshipValidator(repoUser));


        SocialNetwork socialNetwork = new SocialNetwork(repoUser, repoFriendship);
        Console ui = new Console(socialNetwork);

        Utilizator u1 = new Utilizator("Ana", "Pop");
        Utilizator u2 = new Utilizator("Alexandra", "Bontidean");
        Utilizator u3 = new Utilizator("Mihai", "Culda");
        Utilizator u4 = new Utilizator("Sidonia", "Popa");
        Utilizator u5 = new Utilizator("Matei", "Oltean");
        Utilizator u6 = new Utilizator("Cosmin", "Ilea");
        Utilizator u7 = new Utilizator("Raluca", "Moldovan");
        Utilizator u8 = new Utilizator("Andrei", "Crisan");
        Utilizator u9 = new Utilizator("Martin", "Muntean");

        socialNetwork.addUser(u1);
        socialNetwork.addUser(u2);
        socialNetwork.addUser(u3);
        socialNetwork.addUser(u4);
        socialNetwork.addUser(u5);
        socialNetwork.addUser(u6);
        socialNetwork.addUser(u7);
        socialNetwork.addUser(u8);
        socialNetwork.addUser(u9);
        ui.run();
    }
}
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        UtilizatorDBRepository userDBRepository = new UtilizatorDBRepository(new UtilizatorValidator());
        FriendshipDBRepository friendshipDBRepository = new FriendshipDBRepository(new FriendshipValidator(userDBRepository));

        SocialNetwork socialNetwork = new SocialNetwork(userDBRepository, friendshipDBRepository);

        Console ui = new Console(socialNetwork);
        ui.run();
    }
}
