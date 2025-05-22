namespace basker.domain;

public class Entity<ID>
{
    private ID identitykey;

    public ID get_id()
    {
        return identitykey;
    }

    public void set_id(ID identitykey)
    {
        this.identitykey = identitykey;
    }
}
