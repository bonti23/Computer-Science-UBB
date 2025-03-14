package ubb.scs.map.vacante.domain;

public enum Hobby {
    reading("reading"), music("music"), hiking("hiking"),walking("walking"),extrem_sports("extrem_sports");

    private final String text;
    Hobby(String text){
        this.text=text;
    }

    @Override
    public String toString(){
        return this.text;
    }
}
