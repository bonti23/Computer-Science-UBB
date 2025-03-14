package ubb.scs.map.zboruri.domain;

import java.time.LocalDateTime;

public class Ticket  extends Entity<Long>{
    private String username;
    private Integer idFlight;
    private LocalDateTime purchaseTime;
    public Ticket(String username, Integer idFlight, LocalDateTime purchaseTime) {
        this.username = username;
        this.idFlight = idFlight;
        this.purchaseTime = purchaseTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(Integer idFlight) {
        this.idFlight = idFlight;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
}
