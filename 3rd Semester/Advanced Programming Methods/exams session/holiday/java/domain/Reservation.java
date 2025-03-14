package ubb.scs.map.vacante.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation extends Entity<Long>{
    private Integer idClient;
    private Integer idHotel;
    private LocalDate date;
    private Integer nights;

    public Reservation(Integer idClient,Integer idHotel,LocalDate date,Integer nights) {
        this.idClient=idClient;
        this.idHotel=idHotel;
        this.date=date;
        this.nights=nights;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }
}
