package ubb.scs.map.vacante.domain;

import java.util.Objects;

public class Hotel extends Entity<Long>{
    private Integer location;
    private String name;
    private Integer rooms;
    private Integer price;
    private HotelType type;

    public Hotel(Integer location, String name, Integer rooms, Integer price, HotelType type) {
        this.location = location;
        this.name = name;
        this.rooms = rooms;
        this.price = price;
        this.type = type;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public HotelType getType() {
        return type;
    }

    public void setType(HotelType type) {
        this.type = type;
    }

    public static HotelType stringToType(String type){
        if(type.equals("family"))
            return HotelType.family;
        if(type.equals("teenagers"))
            return HotelType.teenagers;
        if(type.equals("oldpeople"))
            return HotelType.oldpeople;
        return null;
    }
}
