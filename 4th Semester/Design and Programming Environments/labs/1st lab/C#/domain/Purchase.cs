namespace basker.domain;

public class Purchase:Entity<long>
{
    private string client;
    private long game;
    private int seats;
    private string address;

    public Purchase(string client, long game, int seats, string address)
    {
        this.client = client;
        this.game = game;
        this.seats = seats;
        this.address = address;
    }

    public string get_client()
    {
        return client;
    }

    public void set_client(string client)
    {
        this.client = client;
    }

    public long get_game()
    {
        return game;
    }

    public void set_game(long game)
    {
        this.game = game;
    }

    public int get_seats()
    {
        return seats;
    }

    public void set_seats(int seats)
    {
        this.seats = seats;
    }

    public string get_address()
    {
        return address;
    }

    public void set_address(string address)
    {
        this.address = address;
    }
}
