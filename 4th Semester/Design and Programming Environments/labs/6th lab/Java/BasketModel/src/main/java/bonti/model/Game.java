package bonti.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Games")
public class Game implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamA;
    private String teamB;
    private String date;
    private float price;
    private String type;

    private int seats;

    public Game() {}

    public Game(Long id, String teamA, String teamB, String date, float price, String type, int seats) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.price = price;
        this.type = type;
        this.seats = seats;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTeamA() { return teamA; }
    public void setTeamA(String teamA) { this.teamA = teamA; }

    public String getTeamB() { return teamB; }
    public void setTeamB(String teamB) { this.teamB = teamB; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", teamA='" + teamA + '\'' +
                ", teamB='" + teamB + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", seats=" + seats +
                '}';
    }
}
