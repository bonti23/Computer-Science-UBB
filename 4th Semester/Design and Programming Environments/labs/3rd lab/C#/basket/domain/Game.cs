namespace basket.domain;
public class Game : Entity<long>
{
    public string teamA;
    public string teamB;
    public string date;
    public float price;
    public Type type;
    public int seats;

    public Game(string teamA, string teamB, string date, float price, Type type, int seats)
    {
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.price = price;
        this.type = type;
        this.seats = seats;
    }

    public string get_teamA()
    {
        return teamA;
    }
    public void set_teamA(string value)
    {
        teamA = value;
    }

    public string get_teamB()
    {
        return teamB;
    }

    public void set_teamB(string value)
    {
        teamB = value;
    }

    public string get_date()
    {
        return date;
    }

    public void set_date(string value)
    {
        date = value;
    }

    public float get_price()
    {
        return price;
    }

    public void set_price(float value)
    {
        price = value;
    }

    public Type get_type()
    {
        return type;
    }

    public void set_type(Type value)
    {
        type = value;
    }

    public int get_seat()
    {
        return seats;
    }

    public void set_seats(int value)
    {
        seats = value;
    }
}
