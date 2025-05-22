using BasketModel;
using System;

namespace BasketNetworking.DTO
{
    [Serializable]
    public class PurchaseDTO : EntityDTO<long>
    {
        public string Client { get; set; }
        public long Game { get; set; }
        public int Seats { get; set; }
        public string Address { get; set; }

        public PurchaseDTO() {}
        public PurchaseDTO(Purchase purchase)
        {
            this.Client = purchase.getClient();
            this.Game = purchase.getGame();
            this.Seats = purchase.getSeats();
            this.Address = purchase.getAddress();
        }

        public Purchase ToModel()
        {
            Purchase purchase = new Purchase(Client, Game, Seats, Address);
            purchase.id=this.identitykey;
            return purchase;
        }
    }
}