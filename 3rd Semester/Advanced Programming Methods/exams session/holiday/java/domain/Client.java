package ubb.scs.map.vacante.domain;

public class Client extends Entity<Long>{
    private String name;
    private Integer fidelitygrade;
    private Integer age;
    private Hobby hobby;

    public Client(String name, Integer fidelitygrade, Integer age, Hobby hobby) {
        this.name = name;
        this.fidelitygrade = fidelitygrade;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFidelitygrade() {
        return fidelitygrade;
    }

    public void setFidelitygrade(Integer fidelitygrade) {
        this.fidelitygrade = fidelitygrade;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }
}
