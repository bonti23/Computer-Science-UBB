namespace basket2.domain;

public class Entity<ID>
{
    public ID identitykey;

    public ID get_id()
    {
        return identitykey;
    }

    public void set_id(ID identitykey)
    {
        this.identitykey = identitykey;
    }
}