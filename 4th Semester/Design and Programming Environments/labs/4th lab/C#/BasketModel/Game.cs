using System;

namespace BasketModel
{
    [Serializable]
    public class Game : Entity<long>
    {
        public long id
        {
            get => get_identitykey();
            set => set_identitykey(value);
        }
        public string teamA { get; set; }  // Proprietate publică
        public string teamB { get; set; }  // Proprietate publică
        public string date { get; set; }   // Proprietate publică
        public float price { get; set; }   // Proprietate publică
        public GameType type { get; set; } // Proprietate publică
        public int seats { get; set; }     // Proprietate publică

        // Constructorul rămâne același
        public Game(long id, string teamA, string teamB, string date, float price, GameType type, int seats)
        {
            set_identitykey(id);
            this.teamA = teamA;
            this.teamB = teamB;
            this.date = date;
            this.price = price;
            this.type = type;
            this.seats = seats;
        }
    }
}
