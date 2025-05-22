namespace basket2.domain;

public class Game : Entity<long>
{
    public string TeamA { get; set; }
    public string TeamB { get; set; }
    public string Date { get; set; }
    public float Price { get; set; }
    public Type Type { get; set; }
    public int Seats { get; set; }

    public Game(string teamA, string teamB, string date, float price, Type type, int seats)
    {
        TeamA = teamA;
        TeamB = teamB;
        Date = date;
        Price = price;
        Type = type;
        Seats = seats;
    }
}
