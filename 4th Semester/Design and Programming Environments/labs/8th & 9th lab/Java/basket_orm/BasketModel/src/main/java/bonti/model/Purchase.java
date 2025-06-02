package bonti.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Purchases")
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String client;

    private Long game;

    private int seats;

    private String address;

    public Purchase() {}

    public Purchase(Long id, String client, Long game, int seats, String address) {
        this.id = id;
        this.client = client;
        this.game = game;
        this.seats = seats;
        this.address = address;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public Long getGame() { return game; }
    public void setGame(Long game) { this.game = game; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
