package ubb.scs.map.ui;

import ubb.scs.map.domain.Friendship;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.ValidationException;
import ubb.scs.map.service.SocialCommunities;
import ubb.scs.map.service.SocialNetwork;

import java.util.List;
import java.util.Scanner;

public class Console {
    private SocialNetwork socialNetwork;
    private SocialCommunities socialCommunities;

    public Console(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
        this.socialCommunities = new SocialCommunities(socialNetwork);
    }

    void printMenu() {
        System.out.println("Meniul aplicatiei:");
        System.out.println("1. Adauga un utilizator.");
        System.out.println("2. Elimina un utilizator.");
        System.out.println("3. Adauga o prietenie.");
        System.out.println("4. Elimina o prietenie.");
        System.out.println("5. Afiseaza utilizatorii.");
        System.out.println("6. Afiseaza prieteniile.");
        System.out.println("7. Comunitati sociale.");
        System.out.println("8. Cea mai sociala comunitate.");
        System.out.println("9. Iesire din aplicatie.");
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        boolean ok = true;
        printMenu();
        while (ok) {
            System.out.println("Introduceti numarul instructiunii dorite: ");
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
                case "9":
                    System.out.println("iesire");
                    ok = false;
                    break;
                default:
                    System.out.println("Input invalid!");
                    break;
            }
        }
    }

    void printUsers() {
        System.out.println("UTILIZATORI:");
        for (Utilizator u : socialNetwork.getUsers()) {
            System.out.println("Id: " + u.getId() + " " + u.getFirstName() + " " + u.getLastName());
        }
    }
    void addUser() {
        System.out.println("Adauga utilizator");
        Scanner scan = new Scanner(System.in);
        System.out.println("Prenume: ");
        String firstName = scan.nextLine();
        System.out.println("Nume: ");
        String lastName = scan.nextLine();
        try {
            socialNetwork.addUser(new Utilizator(firstName, lastName));
        } catch (ValidationException e) {
            System.out.println("Utilizator invalid!");
        } catch (IllegalArgumentException e) {
            System.out.println("Argumente invalid!");
        }

    }

    void removeUser() {
        printUsers();
        System.out.println("Elimina utilizator");
        Scanner scan = new Scanner(System.in);
        System.out.println("Id: ");
        String var = scan.nextLine();
        try {
            Long id = Long.parseLong(var);
            Utilizator user = socialNetwork.removeUser(id);
            System.out.println("Utilizatorul: " + user.getId() + " " + user.getFirstName() + " " + user.getLastName() + " a fost eliminat.");
        } catch (IllegalArgumentException e) {
            System.out.println("Id-ul trebuie sa fie un numar!");
        }
    }

    void printFriendships() {
        for (Utilizator u : socialNetwork.getUsers()) {
            System.out.println("Prietenii utilizatorului: " + u.getFirstName() + " " + u.getLastName() + " (Numarul de prieteni: " + u.getFriends().size() + " )");
            if (u.getFriends() != null) {
                for (Utilizator f : u.getFriends()) {
                    System.out.println("( Id: " + f.getId() + " ) " + f.getFirstName() + " " + f.getLastName());
                }
            }
        }
    }

    void addFriendship() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Id-ul primului utilizator: ");
        String var1 = scan.nextLine();
        System.out.println("Id-ul celui de-al doilea utilizator: ");
        String var2 = scan.nextLine();
        try {
            Long id1 = 0L, id2 = 0L;
            try {
                id1 = Long.parseLong(var1);
                id2 = Long.parseLong(var2);
            } catch (IllegalArgumentException e) {
                System.out.println("Id-ul trebuie sa fie un numar! ");
            }
            socialNetwork.addFriendship(new Friendship(id1, id2));
        } catch (ValidationException e) {
            System.out.println("Prietenie invalida! ");
        } catch (IllegalArgumentException e) {
            System.out.println("Argumente invalide! ");
        }
    }

    private void removeFriendship() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Id-ul primului utilizator: ");
        String var1 = scan.nextLine();
        System.out.println("Id-ul celui de-al doilea utilizator: ");
        String var2 = scan.nextLine();
        try {
            Long id1 = 0L, id2 = 0L;
            try {
                id1 = Long.parseLong(var1);
                id2 = Long.parseLong(var2);
            } catch (IllegalArgumentException e) {
                System.out.println("Id-ul trebuie sa fie un numar! ");
            }
            socialNetwork.removeFriendship(id1, id2);
        } catch (IllegalArgumentException e) {
            System.out.println("Argumente invalide! ");
        }
    }

    private void printConnectedCommunities() {
        System.out.println("Comunitati sociale:\n");
        int nrOfCommunities = socialCommunities.connectedCommunities();
        System.out.println("Numarul total: " + nrOfCommunities);
    }

    private void printMostSocialCommunity() {
        System.out.println("Comunitatea cea mai sociala: ");
        List<Long> mostSocialCommunity = socialCommunities.mostSocialCommunity();
        mostSocialCommunity.forEach(System.out::println);
    }

}
