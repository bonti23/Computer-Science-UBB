package ubb.scs.map.zboruri.domain;

public class Entity<ID> {
    private ID identityKey;

    public ID getID() {
        return identityKey;
    }

    public void setID(ID identityKey) {
        this.identityKey = identityKey;
    }
}
