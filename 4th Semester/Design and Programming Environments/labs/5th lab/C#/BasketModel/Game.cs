using System;
using System.Text.Json.Serialization;

namespace BasketModel
{
    [Serializable]
    public class Game : Entity<long>
    {
        public string teamA { get; set; }  // Proprietate publică
        public string teamB { get; set; }  // Proprietate publică
        public string date { get; set; }   // Proprietate publică
        public float price { get; set; }   // Proprietate publică
        [JsonConverter(typeof(JsonStringEnumConverter))]

        public GameType type { get; set; }
        public int seats { get; set; }     // Proprietate publică

        // Constructorul rămâne același
        public Game() {}

        public Game(long id, string teamA, string teamB, string date, float price, GameType type, int seats)
        {
            this.id = id;
            this.teamA = teamA;
            this.teamB = teamB;
            this.date = date;
            this.price = price;
            this.type = type;
            this.seats = seats;
        }
    }
}