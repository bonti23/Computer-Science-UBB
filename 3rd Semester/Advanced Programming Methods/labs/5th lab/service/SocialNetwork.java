package ubb.scs.map.service;

import ubb.scs.map.domain.Friendship;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.ValidationException;
import ubb.scs.map.repository.database.FriendshipDBRepository;
import ubb.scs.map.repository.database.UtilizatorDBRepository;

import java.util.*;

public class SocialNetwork {

    private final UtilizatorDBRepository repositoryUser;
    private final FriendshipDBRepository repositoryFriendship;

    public SocialNetwork(UtilizatorDBRepository repositoryUser, FriendshipDBRepository repositoryFriendship) {
        this.repositoryUser = repositoryUser;
        this.repositoryFriendship = repositoryFriendship;
    }

    /**
     * @return all Users from the social network
     */
    public Iterable<Utilizator> getUsers() {
        return repositoryUser.findAll();
    }

    /**
     * @param id Id of the user that needs to be found
     * @return User
     */
    public Utilizator findUser(Long id) {
        return repositoryUser.findOne(id).orElseThrow(() -> new ValidationException("User doesn't exist!"));
    }

    /**
     * @return id(Long)
     */
    public Long getNewUserId() {
        Long id = 0L;
        for (Utilizator u : repositoryUser.findAll()) {
            id = u.getId();
        }
        id++;
        return id;
    }

    /**
     * @param user Adds the user to the repository
     */
    public void addUser(Utilizator user) {
        user.setId(getNewUserId());
        repositoryUser.save(user);
    }

    /**
     * @return All friendships from repository
     */
    public Iterable<Friendship> getFriendships() {
        return repositoryFriendship.findAll();
    }

    public List<Utilizator> getListFriends(Utilizator user) {
        List<Utilizator> friends = new ArrayList<>();
        getFriendships().forEach(friendship -> {
            if (friendship.getIdUser1().equals(user.getId())) {
                friends.add(findUser(friendship.getIdUser2()));
            } else if (friendship.getIdUser2().equals(user.getId())) {
                friends.add(findUser(friendship.getIdUser1()));
            }
        });
        return friends;
    }


    /**
     * @param id - id of the user that needs to be removed
     * @return user
     */
    public Utilizator removeUser(Long id) {
        Utilizator u = null;
        try {
            u = repositoryUser.findOne(id).orElseThrow(() -> new ValidationException("User doesn't exist!"));
            if (u == null) {
                throw new IllegalArgumentException("User doesn't exist!");
            }
            Vector<Long> toDelete = new Vector<>();
            getFriendships().forEach(friendship -> {
                if (friendship.getIdUser2().equals(id) || friendship.getIdUser1().equals(id)) {
                    toDelete.add(friendship.getId());
                }
            });
            toDelete.forEach(repositoryFriendship::delete);
            repositoryUser.delete(id);
            //            u.getFriends().forEach(friend -> friend.removeFriend(u));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid user! ");
        } catch (ValidationException v) {
            System.out.println();
        }
        return u;
    }


    public Long getNewFriendshipId() {
        Long id = 0L;
        for (Friendship f : repositoryFriendship.findAll()) {
            id = f.getId();
        }
        id++;
        return id;
    }

    public void addFriendship(Friendship friendship) {
        if (getFriendships() != null) {
            getFriendships().forEach(f -> {
                if (f.getIdUser1().equals(friendship.getIdUser1()) && f.getIdUser2().equals(friendship.getIdUser2())) {
                    throw new ValidationException("The friendship already exist! ");
                }
            });
            if (repositoryUser.findOne(friendship.getIdUser1()).isEmpty() || repositoryUser.findOne(friendship.getIdUser2()).isEmpty()) {
                throw new ValidationException("User doesn't exist! ");
            }
            if (friendship.getIdUser1().equals(friendship.getIdUser2()))
                throw new ValidationException("IDs can't be the same!!! ");
        }

        friendship.setId(getNewFriendshipId());
        repositoryFriendship.save(friendship);

    }

    public void removeFriendship(Long id1, Long id2) {
        Long id = 0L;
        for (Friendship f : repositoryFriendship.findAll()) {
            if ((f.getIdUser1().equals(id1) && f.getIdUser2().equals(id2)) || (f.getIdUser1().equals(id2) && f.getIdUser2().equals(id1)))
                id = f.getId();
        }
        if (id == 0L)
            throw new IllegalArgumentException("The friendship doesn't exist!");
        repositoryFriendship.delete(id);
    }

}
