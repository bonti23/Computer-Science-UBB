public class Cerc {
    private Double raza;

    public Cerc(Double raza) {
        this.raza = raza;
    }

    public Double getRaza() {
        return raza;
    }

    public void setRaza(Double raza) {
        this.raza = raza;
    }

    public Cerc(){
        this.raza = 0.0;
    }

    @Override
    public String toString() {
        return "Cerc{" +
                "raza=" + raza +
                '}';
    }
}
