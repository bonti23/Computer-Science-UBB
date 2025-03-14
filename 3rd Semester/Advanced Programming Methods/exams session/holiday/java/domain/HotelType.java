package ubb.scs.map.vacante.domain;

public enum HotelType {
    family("family"), teenagers("teenagers"), oldpeople("oldpeople");

    private final String text;
    HotelType(String text){
        this.text=text;
    }

    @Override
    public String toString(){
        return this.text;
    }
}
