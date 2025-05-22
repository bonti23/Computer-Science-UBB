namespace basker.domain;

public class User : Entity<long> {
    private string name;
    private string username;
    private string password;

    public User(string name, string username, string password)
    {
        this.name = name;
        this.username = username;
        this.password = password;
    }
    public string get_name()
    {
        return name;
    }

    public string get_username()
    {
        return username;
    }

    public string get_password()
    {
        return password;
    }

    void set_name(string name)
    {
        this.name = name;
    }

    void set_username(string username)
    {
        this.username = username;
    }

    void set_password(string password)
    {
        this.password = password;
    }
    
}
