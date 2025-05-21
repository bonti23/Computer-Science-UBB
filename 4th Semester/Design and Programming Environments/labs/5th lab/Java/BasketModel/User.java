package ro.mpp2024;

import java.io.Serializable;

public class User extends Entity<Long> implements Serializable {
    private String name;
    private String username;
    private String password;

    public User(Long id, String name, String username, String password) {
        super.set_identitykey(id);
        this.name = name;
        this.username = username;
        this.password = password;
    }
    public Long get_identitykey(){
        return super.get_identitykey();
    }
    public void set_identitykey(Long id){
        super.set_identitykey(id);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
