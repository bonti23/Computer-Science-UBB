package ro.mpp2024;

import java.io.Serializable;

public class Purchase extends Entity<Long> implements Serializable {
    private String client;
    private Long game;
    private int seats;
    private String address;
    public Purchase(Long id, String client, Long game, int seats, String address) {
        super.set_identitykey(id);
        this.client = client;
        this.game = game;
        this.seats = seats;
        this.address = address;
    }
    public Long get_identitykey(){
        return super.get_identitykey();
    }
    public void set_identitykey(Long id){
        super.set_identitykey(id);
    }
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getGame() {
        return game;
    }

    public void setGame(Long game) {
        this.game = game;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
