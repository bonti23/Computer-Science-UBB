package ubb.scs.map.vacante.domain;

import java.time.LocalDate;

public class SpecialOffer extends Entity<Long>{
    private Integer idHotel;
    private LocalDate start;
    private LocalDate end;
    private Integer percent;

    public SpecialOffer(Integer idHotel, LocalDate start, LocalDate end, Integer percent) {
        this.idHotel = idHotel;
        this.start = start;
        this.end = end;
        this.percent = percent;
    }

    public Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "start date: " + start +
                ", end date: " + end +
                ", discount: " + percent +
                "%";
    }
}
