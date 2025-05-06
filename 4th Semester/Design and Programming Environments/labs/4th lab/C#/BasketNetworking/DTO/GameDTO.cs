using BasketModel;

namespace BasketNetworking.DTO;
[Serializable]public class GameDTO : EntityDTO<Int64>
{
    public long id
    {
        get => identitykey;
        set => identitykey = value;
    }

    public string TeamA { get; set; }
    public string TeamB { get; set; }
    public string Date { get; set; }
    public float Price { get; set; }
    public GameType Type { get; set; }
    public int Seats { get; set; }

    public GameDTO() {}

    public GameDTO(Game game)
    {
        this.identitykey = game.id;
        this.TeamA = game.teamA;
        this.TeamB = game.teamB;
        this.Date = game.date;
        this.Price = game.price;
        this.Type = game.type;
        this.Seats = game.seats;
    }

    public Game ToModel()
    {
        return new Game(this.identitykey, TeamA, TeamB, Date, Price, Type, Seats);
    }
}
