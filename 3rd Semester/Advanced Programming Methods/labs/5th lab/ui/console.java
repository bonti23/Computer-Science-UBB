package ubb.scs.map.ui;

import ubb.scs.map.domain.Friendship;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.ValidationException;
import ubb.scs.map.service.SocialCommunities;
import ubb.scs.map.service.SocialNetwork;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class Console {

    private SocialNetwork socialNetwork;
    private SocialCommunities socialCommunities;

    public Console(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
        this.socialCommunities = new SocialCommunities(socialNetwork);
    }

    void printMenu() {
        System.out.println("MENIU");
        System.out.println("1. Adauga utilizator");
        System.out.println("2. Sterge utilizator");
        System.out.println("3. Adauga o prietenie");
        System.out.println("4. Sterge o prietenie");
        System.out.println("5. Afiseaza utilizatorii");
        System.out.println("6. Afiseaza prieteniile");
        System.out.println("7. Comunitatile");
        System.out.println("8. Cel mai mare grup de prieteni");
        System.out.println("0. EXIT");
    }

    /**
     * Run function
     */
    public void run() {
        Scanner scan = new Scanner(System.in);
        boolean ok = true;
        printMenu();
        while (ok) {
            System.out.println("Instructiunea dorita: ");
            String input = scan.nextLine();
            switch (input) {
                case "1":
                    addUser();
                    break;
                case "2":
                    removeUser();
                    break;
                case "3":
                    addFriendship();
                    break;
                case "4":
                    removeFriendship();
                    break;
                case "5":
                    printUsers();
                    break;
                case "6":
                    printFriendships();
                    break;
                case "7":
                    printConnectedCommunities();
                    break;
                case "8":
                    printMostSocialCommunity();
                    break;
                case "0":
                    System.out.println("exit");
                    ok = false;
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }
        }
    }



    /**
     * Prints the users from the social network
     */
    void printUsers() {
        System.out.println("Utilizatorii: ");
        socialNetwork.getUsers().forEach(u -> {
            System.out.println("ID: " + u.getId() + " " + u.getFirstName() + " " + u.getLastName());
        });
    }


    /**
     * Adds user to the social network
     */
    void addUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("prenume: ");
        String firstName = scan.nextLine();
        System.out.println("nume: ");
        String lastName = scan.nextLine();
        try {
            socialNetwork.addUser(new Utilizator(firstName, lastName));
        } catch (ValidationException e) {
            System.out.println("Invalid user!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument");
        }

    }


    /**
     * Removes user from social network
     */
    void removeUser() {
        printUsers();
        Scanner scan = new Scanner(System.in);
        System.out.println("id: ");
        String var = scan.nextLine();
        try {
            Long id = Long.parseLong(var);
            Utilizator user = socialNetwork.findUser(id);
            if (user == null) throw new ValidationException("User doesn,t exist!");
            socialNetwork.removeUser(id);
            System.out.println("User: " + user.getId() + " " + user.getFirstName() + " " + user.getLastName() + " was removed.");
        } catch (IllegalArgumentException e) {
            System.out.println("ID must be a number! It can't contain letters or symbols! ");
        } catch (ValidationException v) {
            System.out.println("User doesn't exist!");
        }
    }


    /**
     * Prints friendships
     */
    void printFriendships() {
        for (Utilizator u : socialNetwork.getUsers()) {
            System.out.println("Prietenii utilizatorului: " + u.getFirstName() + " " + u.getLastName() + " -> " + socialNetwork.getListFriends(u).size());
            socialNetwork.getListFriends(u).forEach(friend -> {
                System.out.println(friend.getId() + ": " + friend.getFirstName() + " " + friend.getLastName());
            });
        }
    }

    /**
     * Adds a new friendship between two users
     */
    void addFriendship() {
        Scanner scan = new Scanner(System.in);
        System.out.println("id-ul primului utilizator: ");
        String var1 = scan.nextLine();
        System.out.println("id-ul celui de-al doilea utilizator: ");
        String var2 = scan.nextLine();
        try {
            Long id1 = 0L, id2 = 0L;
            try {
                id1 = Long.parseLong(var1);
                id2 = Long.parseLong(var2);
            } catch (IllegalArgumentException e) {
                System.out.println("ID must be a number! It can't contain letters or symbols! ");
            }
            socialNetwork.addFriendship(new Friendship(id1, id2));
        } catch (ValidationException e) {
            System.out.println("Friendship is invalid! ");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid arguments! ");
        }
    }


    /**
     * Removes a friendship between two friends
     */
    private void removeFriendship() {
        Scanner scan = new Scanner(System.in);
        System.out.println("id-ul primului utilizator: ");
        String var1 = scan.nextLine();
        System.out.println("id-ul celui de-al doilea utilizator: ");
        String var2 = scan.nextLine();
        try {
            Long id1 = 0L, id2 = 0L;
            try {
                id1 = Long.parseLong(var1);
                id2 = Long.parseLong(var2);
            } catch (IllegalArgumentException e) {
                System.out.println("ID must be a number! It can't contain letters or symbols! ");
            }
            socialNetwork.removeFriendship(id1, id2);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid arguments! ");
        }
    }


    /**
     * Prints connected communities
     */
    private void printConnectedCommunities() {
        int nrOfCommunities = socialCommunities.connectedCommunities();
        System.out.println("Numarul comunitatilor sociale: " + nrOfCommunities);
    }


    /**
     * Prints the most social community from the social network
     */
    private void printMostSocialCommunity() {
        System.out.println("Cel mai mare grup de prieteni: ");
        List<Long> mostSocialCommunity = socialCommunities.mostSocialCommunity();
        mostSocialCommunity.forEach(System.out::println);
    }

}
