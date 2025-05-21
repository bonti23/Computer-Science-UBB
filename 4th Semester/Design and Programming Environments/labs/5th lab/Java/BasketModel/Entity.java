package ro.mpp2024;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID identitykey;
    public ID get_identitykey() {
        return identitykey;
    }
    public void set_identitykey(ID identitykey) {
        this.identitykey = identitykey;
    }
}
