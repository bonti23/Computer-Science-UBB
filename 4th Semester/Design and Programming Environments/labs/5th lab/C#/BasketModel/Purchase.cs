using System;

namespace BasketModel
{
    [Serializable]
    public class Purchase : Entity<long>
    {
        private String client;
        private long game;
        private int seats;
        private String address;
        public Purchase(String client, long game, int seats, String address) {
            this.client = client;
            this.game = game;
            this.seats = seats;
            this.address = address;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public long getGame() {
            return game;
        }

        public void setGame(long game) {
            this.game = game;
        }

        public int getSeats() {
            return seats;
        }

        public void setSeats(int seats) {
            this.seats = seats;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}